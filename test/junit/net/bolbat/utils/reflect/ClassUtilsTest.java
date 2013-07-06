package net.bolbat.utils.reflect;

import net.bolbat.utils.reflect.ClassUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link ClassUtils} test.
 * 
 * @author Alexandr Bolbat
 */
public class ClassUtilsTest {

	/**
	 * Complex test.
	 */
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

	/**
	 * Error cases test.
	 */
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

}
