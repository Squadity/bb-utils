package net.bolbat.utils.i18n;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import net.bolbat.utils.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility for loading localized messages by it's keys in various ways.
 * 
 * @author Alexandr Bolbat
 */
public final class MessageUtils {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageUtils.class);

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private MessageUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Get localized message.
	 * 
	 * @param module
	 *            module name, can't be empty
	 * @param key
	 *            message key, can't be empty
	 * @return {@link String}
	 */
	public static String getMessage(final String module, final String key) {
		return getMessage(null, module, key, null);
	}

	/**
	 * Get localized message.
	 * 
	 * @param module
	 *            module name, can't be empty
	 * @param key
	 *            message key, can't be empty
	 * @param locale
	 *            {@link Locale}, default locale will be used if it <code>null</code>
	 * @return {@link String}
	 */
	public static String getMessage(final String module, final String key, final Locale locale) {
		return getMessage(null, module, key, locale);
	}

	/**
	 * Get localized message.<br>
	 * Path parameter should be used to load bundle from specific package, for example: <code>java.lang</code>.
	 * 
	 * @param path
	 *            bundle path, ignored if empty
	 * @param module
	 *            module name, can't be empty
	 * @param key
	 *            message key, can't be empty
	 * @return {@link String}
	 */
	public static String getMessage(final String path, final String module, final String key) {
		return getMessage(path, module, key, null);
	}

	/**
	 * Get localized message.<br>
	 * Path parameter should be used to load bundle from specific package, for example: <code>java.lang</code>.
	 * 
	 * @param path
	 *            bundle path, ignored if empty
	 * @param module
	 *            module name, can't be empty
	 * @param key
	 *            message key, can't be empty
	 * @param locale
	 *            {@link Locale}, default locale will be used if it <code>null</code>
	 * @return {@link String}
	 */
	public static String getMessage(final String path, final String module, final String key, final Locale locale) {
		if (StringUtils.isEmpty(module))
			throw new IllegalArgumentException("module argument is empty.");
		if (StringUtils.isEmpty(key))
			throw new IllegalArgumentException("key argument is empty.");

		ResourceBundle bundle = null;
		try {
			final String bundleName = StringUtils.isNotEmpty(path) ? path + "." + module : module;
			bundle = ResourceBundle.getBundle(bundleName, locale != null ? locale : LocaleUtils.getCurrentLocale());
		} catch (MissingResourceException e) {
			LOGGER.warn("Bundle[" + module + "] not found.");
		}

		if (bundle != null)
			try {
				return bundle.getString(key);
			} catch (MissingResourceException e) {
				LOGGER.warn("Bundle[" + module + "] message[" + key + "] not found.");
			}

		return "!" + key + "!";
	}

}
