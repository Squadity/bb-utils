package net.bb.utils.reflect;

import junit.framework.Assert;

import org.junit.Test;

/**
 * {@link ClassUtil} test.
 * 
 * @author Alexandr Bolbat
 */
public class ClassUtilTest {

	/**
	 * Complex test.
	 */
	@Test
	public void complexTest() {
		Assert.assertEquals(Byte.class, ClassUtil.convertPrimitive(byte.class));
		Assert.assertEquals(Short.class, ClassUtil.convertPrimitive(short.class));
		Assert.assertEquals(Integer.class, ClassUtil.convertPrimitive(int.class));
		Assert.assertEquals(Long.class, ClassUtil.convertPrimitive(long.class));
		Assert.assertEquals(Float.class, ClassUtil.convertPrimitive(float.class));
		Assert.assertEquals(Double.class, ClassUtil.convertPrimitive(double.class));
		Assert.assertEquals(Boolean.class, ClassUtil.convertPrimitive(boolean.class));
		Assert.assertEquals(Character.class, ClassUtil.convertPrimitive(char.class));

		Assert.assertEquals(Boolean.class, ClassUtil.convertPrimitive(Boolean.class));

		Assert.assertEquals(byte.class, ClassUtil.convertNotPrimitive(Byte.class));
		Assert.assertEquals(short.class, ClassUtil.convertNotPrimitive(Short.class));
		Assert.assertEquals(int.class, ClassUtil.convertNotPrimitive(Integer.class));
		Assert.assertEquals(long.class, ClassUtil.convertNotPrimitive(Long.class));
		Assert.assertEquals(float.class, ClassUtil.convertNotPrimitive(Float.class));
		Assert.assertEquals(double.class, ClassUtil.convertNotPrimitive(Double.class));
		Assert.assertEquals(boolean.class, ClassUtil.convertNotPrimitive(Boolean.class));
		Assert.assertEquals(char.class, ClassUtil.convertNotPrimitive(Character.class));

		Assert.assertEquals(boolean.class, ClassUtil.convertNotPrimitive(boolean.class));
	}

	/**
	 * Error cases test.
	 */
	@Test
	public void errorCasesTest() {
		try {
			ClassUtil.convertPrimitive(null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", true);
		}

		try {
			ClassUtil.convertNotPrimitive(null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", true);
		}
	}

}
