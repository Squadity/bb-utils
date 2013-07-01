package net.bb.utils.i18n;

import java.util.Locale;

/**
 * Utility for managing locale in multi-threaded environment.<br>
 * This utility based on {@link ThreadLocal} functionality.
 * 
 * @author Alexandr Bolbat
 */
public final class LocaleUtils {

	/**
	 * Locale.
	 */
	private static final ThreadLocal<Locale> HOLDER = new InheritableThreadLocal<Locale>() {
		@Override
		protected Locale initialValue() {
			return Locale.getDefault();
		}
	};

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private LocaleUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Get current {@link Locale}.
	 * 
	 * @return {@link Locale}
	 */
	public static Locale getCurrentLocale() {
		return HOLDER.get();
	}

	/**
	 * Set current {@link Locale}.
	 * 
	 * @param locale
	 *            locale to set
	 */
	public static void setCurrentLocale(final Locale locale) {
		if (locale == null)
			throw new IllegalArgumentException("locale argument is null.");

		HOLDER.set(locale);
	}

	/**
	 * Cleanup current {@link Locale}.
	 */
	public static void cleanup() {
		HOLDER.remove();
	}

}
