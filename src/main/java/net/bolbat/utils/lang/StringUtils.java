package net.bolbat.utils.lang;

/**
 * Utility methods for {@link String} type.
 * 
 * @author Alexandr Bolbat
 */
public final class StringUtils {

	/**
	 * Empty {@link String} constant.
	 */
	public static final String EMPTY = "";

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private StringUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Is {@link String} value <code>null</code> or empty (length is '0' after trim).
	 * 
	 * @param value
	 *            {@link String} value
	 * @return <code>true</code> if empty or <code>false</code>
	 */
	public static boolean isEmpty(final String value) {
		return value == null || value.trim().isEmpty();
	}

	/**
	 * Is {@link String} value not <code>null</code> and not empty (length not '0' after trim).
	 * 
	 * @param value
	 *            {@link String} value
	 * @return <code>true</code> if not empty or <code>false</code>
	 */
	public static boolean isNotEmpty(final String value) {
		return !isEmpty(value);
	}

	/**
	 * Check value and return it if not <code>null</code> otherwise return 'empty' value.
	 *
	 * @param value
	 *            value
	 * @return value if not <code>null</code>, otherwise 'empty' value
	 */
	public static String notNull(final String value) {
		return value != null ? value : EMPTY;
	}

	/**
	 * Check value and return it if not <code>null</code> otherwise return 'default' value.
	 *
	 * @param value
	 *            value
	 * @param def
	 *            default value
	 * @return value if not <code>null</code>, otherwise 'default' value
	 */
	public static String notNull(final String value, final String def) {
		return value != null ? value : def;
	}

	/**
	 * Check value and return it if not empty otherwise return 'default' value.
	 *
	 * @param value
	 *            value
	 * @param def
	 *            default value
	 * @return value if not empty, otherwise 'default' value
	 */
	public static String notEmpty(final String value, final String def) {
		return isNotEmpty(value) ? value : def;
	}

}
