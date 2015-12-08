package net.bolbat.utils.reflect;

import org.junit.Assert;
import org.junit.Test;

import net.bolbat.utils.annotation.Mark.ToDo;

/**
 * {@link ClassUtils} test.
 * 
 * @author Alexandr Bolbat
 */
@ToDo("Write test cases for 'executePostConstruct', 'executePreDestroy', 'execute' functionality and resolve other @ToDo")
public class ClassUtilsTest {

	@Test
	public void complexTest() {
		Assert.assertEquals(Byte.class, ClassUtils.convertPrimitive(byte.class));
		Assert.assertEquals(Short.class, ClassUtils.convertPrimitive(short.class));
		Assert.assertEquals(Integer.class, ClassUtils.convertPrimitive(int.class));
		Assert.assertEquals(Long.class, ClassUtils.convertPrimitive(long.class));
		Assert.assertEquals(Float.class, ClassUtils.convertPrimitive(float.class));
		Assert.assertEquals(Double.class, ClassUtils.convertPrimitive(double.class));
		Assert.assertEquals(Boolean.class, ClassUtils.convertPrimitive(boolean.class));
		Assert.assertEquals(Character.class, ClassUtils.convertPrimitive(char.class));

		Assert.assertEquals(Boolean.class, ClassUtils.convertPrimitive(Boolean.class));

		Assert.assertEquals(byte.class, ClassUtils.convertNotPrimitive(Byte.class));
		Assert.assertEquals(short.class, ClassUtils.convertNotPrimitive(Short.class));
		Assert.assertEquals(int.class, ClassUtils.convertNotPrimitive(Integer.class));
		Assert.assertEquals(long.class, ClassUtils.convertNotPrimitive(Long.class));
		Assert.assertEquals(float.class, ClassUtils.convertNotPrimitive(Float.class));
		Assert.assertEquals(double.class, ClassUtils.convertNotPrimitive(Double.class));
		Assert.assertEquals(boolean.class, ClassUtils.convertNotPrimitive(Boolean.class));
		Assert.assertEquals(char.class, ClassUtils.convertNotPrimitive(Character.class));

		Assert.assertEquals(boolean.class, ClassUtils.convertNotPrimitive(boolean.class));
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
	public void errorCasesTest() {
		try {
			ClassUtils.convertPrimitive(null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", true);
		}

		try {
			ClassUtils.convertNotPrimitive(null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", true);
		}
	}

	public interface Root {
	}

	public interface Parent extends Root {
	}

	public interface Global extends Parent {
	}

	public static class AbstractParent implements Parent {
	}

	public static class GlobalImpl extends AbstractParent implements Global {
	}

}
