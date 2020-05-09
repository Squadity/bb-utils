package net.bolbat.utils.i18n;

import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link LocaleUtils} test.
 * 
 * @author Alexandr Bolbat
 */
public class LocaleUtilsTest {

	/**
	 * Clearing context before each test.
	 */
	@Before
	public void beforeTest() {
		LocaleUtils.cleanup();
	}

	/**
	 * Clearing context after each test.
	 */
	@After
	public void afterTest() {
		LocaleUtils.cleanup();
	}

	/**
	 * Complex test.
	 */
	@Test
	public void complexTest() {
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
	}

	/**
	 * Error cases test.
	 */
	@Test
	public void errorCasesTest() {
		// wrong arguments
		try {
			LocaleUtils.setCurrentLocale(null);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().contains("locale"));
		}
	}

}
