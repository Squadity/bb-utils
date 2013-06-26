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
		// clearing context
		LocaleUtils.setCurrentLocale(Locale.getDefault());

		// check default locale
		Assert.assertEquals(Locale.getDefault(), LocaleUtils.getCurrentLocale());
		Assert.assertSame(Locale.getDefault(), LocaleUtils.getCurrentLocale());

		// set new to current locale
		Locale newLocale = new Locale("en", "GB");
		LocaleUtils.setCurrentLocale(newLocale);

		// check current locale
		Assert.assertEquals(newLocale, LocaleUtils.getCurrentLocale());
		Assert.assertSame(newLocale, LocaleUtils.getCurrentLocale());

		// check current with default locale
		Assert.assertNotEquals(Locale.getDefault(), LocaleUtils.getCurrentLocale());
		Assert.assertNotSame(Locale.getDefault(), LocaleUtils.getCurrentLocale());

		// clearing context
		LocaleUtils.setCurrentLocale(Locale.getDefault());
	}

}
