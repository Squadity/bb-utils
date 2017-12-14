package net.bolbat.utils.lang;

import static net.bolbat.utils.lang.StringUtils.EMPTY;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * {@link StringUtils} test.
 * 
 * @author Alexandr Bolbat
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StringUtilsTest {

	@Test
	public void isEmpty() {
		Assert.assertTrue(StringUtils.isEmpty(null));
		Assert.assertTrue(StringUtils.isEmpty(""));
		Assert.assertTrue(StringUtils.isEmpty(" "));
		Assert.assertTrue(StringUtils.isEmpty("     "));
		Assert.assertFalse(StringUtils.isEmpty("test text"));
	}

	@Test
	public void isNotEmpty() {
		Assert.assertFalse(StringUtils.isNotEmpty(null));
		Assert.assertFalse(StringUtils.isNotEmpty(""));
		Assert.assertFalse(StringUtils.isNotEmpty(" "));
		Assert.assertFalse(StringUtils.isNotEmpty("     "));
		Assert.assertTrue(StringUtils.isNotEmpty("test text"));
	}

	@Test
	public void notNull() {
		Assert.assertEquals("", StringUtils.notNull(""));
		Assert.assertEquals("val1", StringUtils.notNull("val1"));
		Assert.assertEquals(EMPTY, StringUtils.notNull(null));
	}

	@Test
	public void notNullOrDefault() {
		Assert.assertEquals("", StringUtils.notNull("", "defVal1"));
		Assert.assertEquals("val1", StringUtils.notNull("val1", "defVal1"));
		Assert.assertEquals("defVal1", StringUtils.notNull(null, "defVal1"));
		Assert.assertEquals(null, StringUtils.notNull(null, null));
	}

	@Test
	public void notEmptyOrDefault() {
		Assert.assertEquals("defVal1", StringUtils.notEmpty("", "defVal1"));
		Assert.assertEquals("defVal1", StringUtils.notEmpty("     ", "defVal1"));
		Assert.assertEquals("val1", StringUtils.notEmpty("val1", "defVal1"));
		Assert.assertEquals("defVal1", StringUtils.notEmpty(null, "defVal1"));
		Assert.assertEquals(null, StringUtils.notEmpty(null, null));
	}

}
