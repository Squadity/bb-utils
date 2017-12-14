package net.bolbat.utils.reflect.proxy;

import static net.bolbat.utils.lang.Validations.checkArgument;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.bolbat.utils.lang.CastUtils;

/**
 * {@link Proxy} utilities.
 *
 * @author Alexandr Bolbat
 */
public final class ProxyUtils {

	/**
	 * The CGLIB class separator character "$$".
	 */
	private static final String CGLIB_CLASS_SEPARATOR = "$$";

	/**
	 * {@link ProxySupport}'s holder.
	 */
	private static final Map<Class<?>, ProxySupport<?>> SUPPORTS = new ConcurrentHashMap<>();

	/**
	 * {@link ProxyHandlerSupport}'s holder.
	 */
	private static final Map<Class<?>, ProxyHandlerSupport> SUPPORTS_BY_HANDLERS = new ConcurrentHashMap<>();

	/**
	 * Static initialization.
	 */
	static {
		registerProxyHandlerSupport(new AdvisedHandlerSupport());
	}

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private ProxyUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Register {@link ProxySupport}.
	 * 
	 * @param support
	 *            {@link ProxySupport}
	 */
	public static void registerProxyHandlerSupport(final ProxySupport<?> support) {
		checkArgument(support != null, "support argument is null");

		if (support instanceof ProxyHandlerSupport) {
			SUPPORTS_BY_HANDLERS.put(support.getClass(), (ProxyHandlerSupport) support);
		} else {
			SUPPORTS.put(support.getClass(), support);
		}
	}

	/**
	 * Unwrap proxied target.<br>
	 * {@link ClassCastException} would be thrown if result instance can't be casted to expected type.<br>
	 * {@link ProxyUnsupportedException} would be thrown if required proxy support is not registered.
	 *
	 * @param proxy
	 *            proxy instance, can be <code>null</code>
	 * @return expected instance
	 */
	public static <T> T unwrapProxy(final Object proxy) {
		Object result = proxy;
		while (isProxy(result)) {
			boolean unwrapped = false;
			final Class<?> clazz = result.getClass();
			for (final ProxySupport<?> support : SUPPORTS.values())
				if (support.getSupportedType().isAssignableFrom(clazz)) {
					final ProxySupport<Object> oSupport = CastUtils.cast(support);
					result = oSupport.getTarget(result);
					unwrapped = true;
					break;
				}

			if (unwrapped)
				continue;

			if (isJdkDynamicProxy(clazz)) {
				final InvocationHandler handler = Proxy.getInvocationHandler(result);
				for (final ProxyHandlerSupport support : SUPPORTS_BY_HANDLERS.values())
					if (support.getSupportedType().isAssignableFrom(handler.getClass())) {
						result = support.getTarget(handler);
						unwrapped = true;
						break;
					}
			}

			if (!unwrapped)
				throw new ProxyUnsupportedException(clazz);
		}

		return CastUtils.cast(result);
	}

	/**
	 * Is given object a proxy.
	 * 
	 * @param proxy
	 *            given object
	 * @return <code>true</code> if proxy or <code>false</code>
	 */
	public static boolean isProxy(final Object proxy) {
		return proxy != null && isProxy(proxy.getClass());
	}

	/**
	 * Is given class a proxy class.
	 * 
	 * @param proxyClass
	 *            given class
	 * @return <code>true</code> if proxy or <code>false</code>
	 */
	public static boolean isProxy(final Class<?> proxyClass) {
		return proxyClass != null && (isJdkDynamicProxy(proxyClass) || isCglibProxy(proxyClass));
	}

	private static boolean isJdkDynamicProxy(final Class<?> clazz) {
		return Proxy.isProxyClass(clazz);
	}

	private static boolean isCglibProxy(final Class<?> clazz) {
		return clazz.getName().contains(CGLIB_CLASS_SEPARATOR);
	}

}
