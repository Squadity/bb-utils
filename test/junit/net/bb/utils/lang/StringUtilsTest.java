package net.bb.utils.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link StringUtils} test.
 * 
 * @author Alexandr Bolbat
 */
public class StringUtilsTest {

	/**
	 * Is empty test.
	 */
	@Test
	public void testIsEmpty() {
		Assert.assertTrue(StringUtils.isEmpty(null));
		Assert.assertTrue(StringUtils.isEmpty(""));
		Assert.assertTrue(StringUtils.isEmpty(" "));
		Assert.assertTrue(StringUtils.isEmpty("     "));
		Assert.assertFalse(StringUtils.isEmpty("test text"));
	}

	/**
	 * Is not empty test.
	 */
	@Test
	public void testNotEmpty() {
		Assert.assertFalse(StringUtils.isNotEmpty(null));
		Assert.assertFalse(StringUtils.isNotEmpty(""));
		Assert.assertFalse(StringUtils.isNotEmpty(" "));
		Assert.assertFalse(StringUtils.isNotEmpty("     "));
		Assert.assertTrue(StringUtils.isNotEmpty("test text"));
	}

}
