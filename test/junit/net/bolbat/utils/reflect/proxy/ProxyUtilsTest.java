package net.bolbat.utils.reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import net.bolbat.utils.common.ManagedService;
import net.bolbat.utils.common.ManagedServiceHandler;
import net.bolbat.utils.common.ManagedServiceImpl;
import net.bolbat.utils.lang.CastUtils;
import net.bolbat.utils.reflect.ClassUtils;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;

/**
 * {@link ProxyUtils} test.
 *
 * @author Alexandr Bolbat
 */
public class ProxyUtilsTest {

	@BeforeClass
	public static void beforeClass(){
		ProxyUtils.registerProxyHandlerSupport(new AdditionalAdvisedProxySupport());
		ProxyUtils.registerProxyHandlerSupport(new CglibProxySupport());
	}

	@Test
	public void unwrapProxy() {
		final ManagedService service = new ManagedServiceImpl();
		final Class<?>[] interfaces = ClassUtils.getAllInterfaces(ManagedServiceImpl.class);
		final InvocationHandler handler = new ManagedServiceHandler(service, interfaces);
		final ManagedService proxy = CastUtils.cast(Proxy.newProxyInstance(service.getClass().getClassLoader(), interfaces, handler));

		Assert.assertTrue(Proxy.isProxyClass(proxy.getClass()));
		Assert.assertNotEquals(service, proxy);
		Assert.assertNotSame(service, proxy);

		final ManagedService unwrapped = ProxyUtils.unwrapProxy(proxy);
		Assert.assertFalse(Proxy.isProxyClass(unwrapped.getClass()));
		Assert.assertEquals(service, unwrapped);
		Assert.assertSame(service, unwrapped);
	}

	@Test
	public void wrongArgs() {
		Assert.assertNull(ProxyUtils.unwrapProxy(null));

		final String test = "test";
		Assert.assertNotNull(ProxyUtils.unwrapProxy(test));
		Assert.assertEquals(test, ProxyUtils.unwrapProxy(test));
		Assert.assertSame(test, ProxyUtils.unwrapProxy(test));
	}

	@Test
	public void unsupportedProxy() {
		final ManagedService service = new ManagedServiceImpl();
		final Class<?>[] interfaces = ClassUtils.getAllInterfaces(ManagedServiceImpl.class);
		final InvocationHandler handler = new InvocationHandler() {
			@Override
			public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
				throw new UnsupportedOperationException();
			}
		};
		final ManagedService proxy = CastUtils.cast(Proxy.newProxyInstance(service.getClass().getClassLoader(), interfaces, handler));

		Assert.assertTrue(Proxy.isProxyClass(proxy.getClass()));
		Assert.assertNotEquals(service, proxy);
		Assert.assertNotSame(service, proxy);

		try {
			ProxyUtils.unwrapProxy(proxy);
			Assert.fail("Exception should be thrown before this step");
		} catch (final ProxyUnsupportedException e) {
			Assert.assertTrue("Exception should be there", e.getMessage().equals("Proxy[" + proxy.getClass() + "] is unsupported"));
		}
	}

	@Test
	public void advisedHandlerSupport() {
		final ManagedService service = new ManagedServiceImpl();
		final Class<?>[] interfaces = ClassUtils.getAllInterfaces(ManagedServiceImpl.class);
		final InvocationHandler handler = new ManagedServiceHandler(service, interfaces);

		final ProxyHandlerSupport support = new AdvisedHandlerSupport();
		Assert.assertEquals(AdvisedHandler.class, support.getSupportedType());

		final Object target = support.getTarget(handler);
		Assert.assertEquals(service, target);
		Assert.assertSame(service, target);
	}


	/**
	 * Generic proxy unwrap test case.. Uses  same Advised interface, but with {@link AdditionalAdvisedProxySupport} instead of
	 * {@link AdvisedHandlerSupport}.
	 */
	@Test
	public void genericProxySupportTest() {
		final ManagedService service = new ManagedServiceImpl();
		final Class<?>[] interfaces = ClassUtils.getAllInterfaces(ManagedServiceImpl.class);
		List<Class<?>> interfacesList = new ArrayList<>(Arrays.asList(interfaces));
		//appending Advised to interfaces list... ( as spring @ least does )....
		interfacesList.add(Advised.class);
		final InvocationHandler handler = new ManagedServiceHandler(service, interfaces);
		final ManagedService proxy = CastUtils.cast(Proxy.newProxyInstance(service.getClass().getClassLoader(), interfacesList.toArray(new Class<?>[interfacesList.size()]), handler));

		Assert.assertTrue(Proxy.isProxyClass(proxy.getClass()));
		Assert.assertNotEquals(service, proxy);
		Assert.assertNotSame(service, proxy);

		final Object unwrapped = ProxyUtils.unwrapProxy(proxy);
		Assert.assertSame(service, unwrapped);
		Assert.assertFalse(Proxy.isProxyClass(unwrapped.getClass()));
	}

	/**
	 * Current test case performs double proxy preparation and  further unwrap..
	 */
	@Test
	public void proxyOverProxyTestCase() {
		final ManagedService service = new ManagedServiceImpl();
		final Class<?>[] interfaces = ClassUtils.getAllInterfaces(ManagedServiceImpl.class);
		// Prepare  first  proxy .. based on AdvisedHandler .. where   AdvisedHandlerSupport used - as main unwrapped
		final InvocationHandler firstProxyHandler = new ManagedServiceHandler(service, interfaces);
		final ManagedService proxy = CastUtils.cast(Proxy.newProxyInstance(service.getClass().getClassLoader(), interfaces, firstProxyHandler));
		//unwrapping
		final Object unwrapped = ProxyUtils.unwrapProxy(proxy);
		Assert.assertTrue(Proxy.isProxyClass(proxy.getClass()));
		Assert.assertNotEquals(service, proxy);
		Assert.assertNotSame(service, proxy);
		Assert.assertFalse(Proxy.isProxyClass(unwrapped.getClass()));

		// NOW lets create additional proxyUnder CurrentProxy....
		final List<Class<?>> interfacesList = new ArrayList<>(Arrays.asList(interfaces));
		// appending Advised  to the proxied interfaces....
		interfacesList.add(Advised.class);
		final Class<?>[] requiredProxyOverProxyInterfaces = interfacesList.toArray(new Class<?>[interfacesList.size()]);
		final InvocationHandler secondProxyHandler = new ManagedServiceHandler(proxy, requiredProxyOverProxyInterfaces);

		final ManagedService proxyOverProxy =
				CastUtils.cast(Proxy.newProxyInstance(service.getClass().getClassLoader(), requiredProxyOverProxyInterfaces, secondProxyHandler));

		final Object secondTimeUnwrapped = ProxyUtils.unwrapProxy(proxyOverProxy);
		Assert.assertSame(service, unwrapped);
		Assert.assertSame(service, secondTimeUnwrapped);
		Assert.assertSame(unwrapped, secondTimeUnwrapped);
		Assert.assertFalse(Proxy.isProxyClass(secondTimeUnwrapped.getClass()));
	}

	/**
	 * CGLIB proxy unwrap test case.. Uses  same Advised interface, but with {@link AdditionalAdvisedProxySupport} instead of
	 * {@link AdvisedHandlerSupport}.
	 */
	@Test
	public void cglibProxySupportTest() {
		final ManagedService service = new ManagedServiceImpl();
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(ManagedServiceImpl.class);
		enhancer.setCallback(new CglibInvocationHandler(service));
		ManagedService proxy = (ManagedService) enhancer.create();

		Assert.assertTrue(ProxyUtils.isProxy(proxy));
		Assert.assertNotEquals(service, proxy);
		Assert.assertNotSame(service, proxy);

		final Object unwrapped = ProxyUtils.unwrapProxy(proxy);
		Assert.assertSame(service, unwrapped);
		Assert.assertFalse(ProxyUtils.isProxy(unwrapped));
	}

	@Test
	public void genericProxyDetecting() throws Exception {
		final ManagedService service = new ManagedServiceImpl();
		final Class<?>[] interfaces = ClassUtils.getAllInterfaces(ManagedServiceImpl.class);
		List<Class<?>> interfacesList = new ArrayList<>(Arrays.asList(interfaces));
		//appending Advised to interfaces list... ( as spring @ least does )....
		interfacesList.add(Advised.class);
		final InvocationHandler handler = new ManagedServiceHandler(service, interfaces);
		final ManagedService proxy = CastUtils.cast(Proxy.newProxyInstance(service.getClass().getClassLoader(), interfacesList.toArray(new Class<?>[interfacesList.size()]), handler));

		Assert.assertTrue(ProxyUtils.isProxy(proxy));
	}

	@Test
	public void cglibProxyDetecting() throws Exception {
		final ManagedService service = new ManagedServiceImpl();
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(ManagedServiceImpl.class);
		enhancer.setCallback(new CglibInvocationHandler(service));
		ManagedService proxy = CastUtils.cast(enhancer.create());

		Assert.assertTrue(ProxyUtils.isProxy(proxy));
	}

	@Test
	public void nullDetecting() throws Exception {
		ManagedService proxy = null;

		Assert.assertFalse(ProxyUtils.isProxy(proxy));
	}

	/**
	 * Additional ProxySupport for Advised. Required for generic testCase.
	 */
	private static class AdditionalAdvisedProxySupport implements ProxySupport<Advised> {
		@Override
		public Class<?> getSupportedType() {
			return Advised.class;
		}

		@Override
		public Object getTarget(Advised source) {
			if (Proxy.isProxyClass(source.getClass()))
				return Advised.class.cast(Proxy.getInvocationHandler(source)).getProxiedTarget();
			return source.getProxiedTarget();
		}

	}

	/**
	 * Dummy cglib invocation handler
	 */
	private class CglibInvocationHandler implements net.sf.cglib.proxy.InvocationHandler {
		private final Object target;

		public CglibInvocationHandler(Object target) {
			this.target = target;
		}

		public Object getTarget() {
			return target;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			return method.invoke(target, args);
		}
	}

	/**
	 * Additional ProxySupport for CGLIB proxy. Required for generic testCase.
	 */
	private static class CglibProxySupport implements ProxySupport<Factory> {
		@Override
		public Class<?> getSupportedType() {
			return Factory.class;
		}

		@Override
		public Object getTarget(Factory source) {
			final Callback[] callbacks = source.getCallbacks();
			for (Callback callback :callbacks) {
				if (callback instanceof CglibInvocationHandler) {
					return ((CglibInvocationHandler) callback).getTarget();
				}
			}

			throw new ProxyUnsupportedException(source.getClass());
		}
	}
}
