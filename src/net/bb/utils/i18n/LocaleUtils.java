package net.bb.utils.i18n;

import java.util.Locale;

/**
 * Utility for managing locale in multi-threaded environment.<br>
 * This utility based on {@link ThreadLocal} functionality.
 * 
 * @author Alexandr Bolbat
 */
// TODO Finish me
public final class LocaleUtils {

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
		return Locale.getDefault();
	}

}
