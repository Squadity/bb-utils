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

}
