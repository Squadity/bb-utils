package net.bolbat.utils.reflect;

import java.lang.reflect.Proxy;

import org.junit.Assert;
import org.junit.Test;

import net.bolbat.utils.annotation.Mark.ToDo;
import net.bolbat.utils.common.AbstractParent;
import net.bolbat.utils.common.Execute;
import net.bolbat.utils.common.Global;
import net.bolbat.utils.common.GlobalImpl;
import net.bolbat.utils.common.ManagedService;
import net.bolbat.utils.common.ManagedServiceImpl;
import net.bolbat.utils.common.Parent;
import net.bolbat.utils.common.Root;

/**
 * {@link ClassUtils} test.
 * 
 * @author Alexandr Bolbat
 */
public class ClassUtilsTest {

	@Test
	public void convertPrimitive() {
		Assert.assertEquals(Byte.class, ClassUtils.convertPrimitive(byte.class));
		Assert.assertEquals(Short.class, ClassUtils.convertPrimitive(short.class));
		Assert.assertEquals(Integer.class, ClassUtils.convertPrimitive(int.class));
		Assert.assertEquals(Long.class, ClassUtils.convertPrimitive(long.class));
		Assert.assertEquals(Float.class, ClassUtils.convertPrimitive(float.class));
		Assert.assertEquals(Double.class, ClassUtils.convertPrimitive(double.class));
		Assert.assertEquals(Boolean.class, ClassUtils.convertPrimitive(boolean.class));
		Assert.assertEquals(Character.class, ClassUtils.convertPrimitive(char.class));

		Assert.assertEquals(Boolean.class, ClassUtils.convertPrimitive(Boolean.class));

		try {
			ClassUtils.convertPrimitive(null);
			Assert.fail("Exception should be thrown before this step");
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there", e.getMessage().equals("clazz argument is null"));
		}
	}

	@Test
	public void convertNotPrimitive() {
		Assert.assertEquals(byte.class, ClassUtils.convertNotPrimitive(Byte.class));
		Assert.assertEquals(short.class, ClassUtils.convertNotPrimitive(Short.class));
		Assert.assertEquals(int.class, ClassUtils.convertNotPrimitive(Integer.class));
		Assert.assertEquals(long.class, ClassUtils.convertNotPrimitive(Long.class));
		Assert.assertEquals(float.class, ClassUtils.convertNotPrimitive(Float.class));
		Assert.assertEquals(double.class, ClassUtils.convertNotPrimitive(Double.class));
		Assert.assertEquals(boolean.class, ClassUtils.convertNotPrimitive(Boolean.class));
		Assert.assertEquals(char.class, ClassUtils.convertNotPrimitive(Character.class));

		Assert.assertEquals(boolean.class, ClassUtils.convertNotPrimitive(boolean.class));

		try {
			ClassUtils.convertNotPrimitive(null);
			Assert.fail("Exception should be thrown before this step");
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there", e.getMessage().equals("clazz argument is null"));
		}
	}

	@Test
	@ToDo("Implement me")
	public void getAllTypesTest() {
	}

	@Test
	public void getAllIntefracesTest() {
		Class<?>[] interfaces = ClassUtils.getAllInterfaces(GlobalImpl.class);
		Assert.assertNotNull(interfaces);
		Assert.assertEquals(3, interfaces.length);
		Assert.assertEquals(Global.class, interfaces[0]);
		Assert.assertEquals(Parent.class, interfaces[1]);
		Assert.assertEquals(Root.class, interfaces[2]);

		interfaces = ClassUtils.getAllInterfaces(AbstractParent.class);
		Assert.assertNotNull(interfaces);
		Assert.assertEquals(2, interfaces.length);
		Assert.assertEquals(Parent.class, interfaces[0]);
		Assert.assertEquals(Root.class, interfaces[1]);

		interfaces = ClassUtils.getAllInterfaces(Global.class);
		Assert.assertNotNull(interfaces);
		Assert.assertEquals(2, interfaces.length);
		Assert.assertEquals(Parent.class, interfaces[0]);
		Assert.assertEquals(Root.class, interfaces[1]);

		interfaces = ClassUtils.getAllInterfaces(Parent.class);
		Assert.assertNotNull(interfaces);
		Assert.assertEquals(1, interfaces.length);
		Assert.assertEquals(Root.class, interfaces[0]);

		interfaces = ClassUtils.getAllInterfaces(Root.class);
		Assert.assertNotNull(interfaces);
		Assert.assertEquals(0, interfaces.length);

		interfaces = ClassUtils.getAllInterfaces(Object.class);
		Assert.assertNotNull(interfaces);
		Assert.assertEquals(0, interfaces.length);
	}

	@Test
	@ToDo("Implement me")
	public void getAllFieldsTest() {
	}

	@Test
	@ToDo("Implement me")
	public void getAllMethodsTest() {
	}

	@Test
	public void executePostConstruct() {
		final ManagedService service = ManagedServiceImpl.createManagedService();
		Assert.assertEquals(ManagedServiceImpl.class, service.getClass());

		ClassUtils.executePostConstruct(service);
		Assert.assertEquals(2, service.getPostConstructExecutions());
	}

	@Test
	public void executePostConstructOnProxy() {
		final ManagedService service = ManagedServiceImpl.createProxiedManagedService();
		Assert.assertTrue(Proxy.isProxyClass(service.getClass()));

		ClassUtils.executePostConstruct(service, true);
		Assert.assertEquals(2, service.getPostConstructExecutions());
	}

	@Test
	public void executePreDestroy() {
		final ManagedService service = ManagedServiceImpl.createManagedService();
		Assert.assertEquals(ManagedServiceImpl.class, service.getClass());

		ClassUtils.executePreDestroy(service);
		Assert.assertEquals(2, service.getPreDestroyExecutions());
	}

	@Test
	public void executePreDestroyOnProxy() {
		final ManagedService service = ManagedServiceImpl.createProxiedManagedService();
		Assert.assertTrue(Proxy.isProxyClass(service.getClass()));

		ClassUtils.executePreDestroy(service, true);
		Assert.assertEquals(2, service.getPreDestroyExecutions());
	}

	@Test
	public void executeAnnotated() {
		final ManagedService service = ManagedServiceImpl.createManagedService();
		Assert.assertEquals(ManagedServiceImpl.class, service.getClass());

		ClassUtils.execute(service, false, true, Execute.class);
		Assert.assertEquals(2, service.getExecuteExecutions());

		try {
			ClassUtils.execute(null, Execute.class);
			Assert.fail("Exception should be thrown before this step");
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there", e.getMessage().equals("instance argument is null"));
		}

		try {
			ClassUtils.execute(service, Execute.class);
			Assert.fail("Exception should be thrown before this step");
		} catch (final RuntimeException e) {
			Assert.assertTrue("Exception should be there", e.getMessage().equals("java.lang.IllegalArgumentException: Testing case"));
		}
	}

	@Test
	public void executeAnnotatedOnProxy() {
		final ManagedService service = ManagedServiceImpl.createProxiedManagedService();
		Assert.assertTrue(Proxy.isProxyClass(service.getClass()));

		ClassUtils.execute(service, true, true, Execute.class);
		Assert.assertEquals(2, service.getExecuteExecutions());

		try {
			ClassUtils.execute(null, true, Execute.class);
			Assert.fail("Exception should be thrown before this step");
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there", e.getMessage().equals("instance argument is null"));
		}

		try {
			ClassUtils.execute(service, true, false, Execute.class);
			Assert.fail("Exception should be thrown before this step");
		} catch (final RuntimeException e) {
			Assert.assertTrue("Exception should be there", e.getMessage().equals("java.lang.IllegalArgumentException: Testing case"));
		}
	}

}
