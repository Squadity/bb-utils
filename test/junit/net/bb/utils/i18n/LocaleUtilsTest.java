package net.bb.utils.i18n;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link LocaleUtils} test.
 * 
 * @author Alexandr Bolbat
 */
public class LocaleUtilsTest {

	/**
	 * Complex test.
	 */
	@Test
	public void complexTest() {
		Assert.assertEquals(Locale.getDefault(), LocaleUtils.getCurrentLocale());
		Assert.assertSame(Locale.getDefault(), LocaleUtils.getCurrentLocale());
	}

}
