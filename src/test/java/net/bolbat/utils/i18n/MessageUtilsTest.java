package net.bolbat.utils.i18n;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link MessageUtils} test.
 * 
 * @author Alexandr Bolbat
 */
public class MessageUtilsTest {

	/**
	 * Complex test.
	 */
	@Test
	public void complexTest() {
		// clearing context
		LocaleUtils.setCurrentLocale(Locale.getDefault());

		// basic check
		Assert.assertEquals("Sample message", MessageUtils.getMessage("i18n-test-module", "sample-message"));

		// usage of additional path and locale parameters
		final String path = "i18n.nested";
		final String name = "i18n-test-module";
		final String key = "nested-bundle-message";
		Assert.assertEquals("Message from nested bundle", MessageUtils.getMessage(path, name, key));
		Assert.assertEquals("Message on the German", MessageUtils.getMessage(path, name, key, new Locale("de")));
		Assert.assertEquals("Message from the Great Britain", MessageUtils.getMessage(path, name, key, new Locale("en", "GB")));

		// clearing context
		LocaleUtils.setCurrentLocale(Locale.getDefault());
	}

	/**
	 * Error cases test.
	 */
	@Test
	public void errorCasesTest() {
		// wrong arguments
		try {
			MessageUtils.getMessage(null, null);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().contains("module"));
		}
		try {
			MessageUtils.getMessage("", null);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().contains("module"));
		}

		try {
			MessageUtils.getMessage("mock-module", null);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().contains("key"));
		}
		try {
			MessageUtils.getMessage("mock-module", "");
			Assert.fail();
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().contains("key"));
		}

		// not exist bundle
		Assert.assertEquals("!msg!", MessageUtils.getMessage("not-exist-module", "msg"));

		// not exist message
		Assert.assertEquals("!msg!", MessageUtils.getMessage("i18n-test-module", "msg"));
	}

}
