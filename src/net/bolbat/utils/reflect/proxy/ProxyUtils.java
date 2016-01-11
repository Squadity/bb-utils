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
	 * {@link ProxyHandlerSupport}'s holder.
	 */
	private static final Map<Class<? extends ProxyHandlerSupport>, ProxyHandlerSupport> HANDLERS_SUPPORTS = new ConcurrentHashMap<>();

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
	 * Register {@link ProxyHandlerSupport}.
	 * 
	 * @param support
	 *            {@link ProxyHandlerSupport}
	 */
	public static void registerProxyHandlerSupport(final ProxyHandlerSupport support) {
		checkArgument(support != null, "support argument is null");

		HANDLERS_SUPPORTS.put(support.getClass(), support);
	}

	/**
	 * Unwrap proxied target from supported proxy invocation handlers.<br>
	 * {@link ClassCastException} would be thrown if result instance can't be casted to expected type.<br>
	 * {@link ProxyHandlerUnsupportedException} would be thrown if proxy invocation handler is unsupported.
	 *
	 * @param proxy
	 *            proxy instance, can be <code>null</code>
	 * @return expected instance
	 */
	public static <T> T unwrapProxy(final Object proxy) {
		if (proxy == null || !Proxy.isProxyClass(proxy.getClass()))
			return CastUtils.cast(proxy);

		Object result = proxy;
		while (Proxy.isProxyClass(result.getClass())) {
			final InvocationHandler handler = Proxy.getInvocationHandler(result);
			boolean unwrapped = false;
			for (final ProxyHandlerSupport support : HANDLERS_SUPPORTS.values())
				if (support.getHandlerClass().isAssignableFrom(handler.getClass())) {
					result = support.getTarget(handler);
					unwrapped = true;
					break;
				}

			if (!unwrapped)
				throw new ProxyHandlerUnsupportedException(handler.getClass());
		}

		return CastUtils.cast(result);
	}

}
