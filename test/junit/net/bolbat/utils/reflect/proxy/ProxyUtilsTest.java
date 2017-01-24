package net.bolbat.utils.reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Assert;
import org.junit.Test;

import net.bolbat.utils.common.ManagedService;
import net.bolbat.utils.common.ManagedServiceHandler;
import net.bolbat.utils.common.ManagedServiceImpl;
import net.bolbat.utils.lang.CastUtils;
import net.bolbat.utils.reflect.ClassUtils;

/**
 * {@link ProxyUtils} test.
 * 
 * @author Alexandr Bolbat
 */
public class ProxyUtilsTest {

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
	public void unsupportedHandler() {
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
			Assert.assertTrue("Exception should be there", e.getMessage().equals("Proxy[" + handler.getClass() + "] is unsupported"));
		}
	}

	@Test
	public void advisedHandlerSupport() {
		final ManagedService service = new ManagedServiceImpl();
		final Class<?>[] interfaces = ClassUtils.getAllInterfaces(ManagedServiceImpl.class);
		final InvocationHandler handler = new ManagedServiceHandler(service, interfaces);

		final ProxyHandlerSupport support = new AdvisedHandlerSupport();
		Assert.assertEquals(AdvisedHandler.class, support.getHandlerClass());

		final Object target = support.getTarget(handler);
		Assert.assertEquals(service, target);
		Assert.assertSame(service, target);
	}

}
