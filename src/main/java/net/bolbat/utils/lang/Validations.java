package net.bolbat.utils.lang;

import net.bolbat.utils.annotation.Audience;
import net.bolbat.utils.annotation.Stability;

/**
 * Useful validations.
 * 
 * @author Alexandr Bolbat
 */
@Audience.Public
@Stability.Evolving
public final class Validations {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private Validations() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Ensures the truth of an expression.<br>
	 * Throws {@link IllegalArgumentException} if {@code expression} is <code>false</code>.
	 * 
	 * @param expression
	 *            a boolean expression
	 */
	public static void checkArgument(final boolean expression) {
		if (!expression)
			throw new IllegalArgumentException();
	}

	/**
	 * Ensures the truth of an expression.<br>
	 * Throws {@link IllegalArgumentException} if {@code expression} is <code>false</code>.
	 * 
	 * @param expression
	 *            a boolean expression
	 * @param message
	 *            the exception message to use if the check fails, will be converted to a string using {@link String#valueOf(Object)}
	 */
	public static void checkArgument(final boolean expression, final Object message) {
		if (!expression)
			throw new IllegalArgumentException(String.valueOf(message));
	}

	/**
	 * Ensures the truth of an expression.<br>
	 * Throws {@link IllegalArgumentException} if {@code expression} is <code>false</code>.
	 * 
	 * @param expression
	 *            a boolean expression
	 * @param template
	 *            a template for the exception message to use if the check fails, will be processed using {@link String#valueOf(Object)}
	 * @param args
	 *            the arguments to be substituted into the message template using {@link String#format(String, Object[])}
	 */
	public static void checkArgument(final boolean expression, final String template, final Object... args) {
		if (!expression)
			throw new IllegalArgumentException(String.format(String.valueOf(template), args));
	}

	/**
	 * Ensures the truth of an expression.<br>
	 * Throws {@link IllegalStateException} if {@code expression} is <code>false</code>.
	 * 
	 * @param expression
	 *            a boolean expression
	 */
	public static void checkState(final boolean expression) {
		if (!expression)
			throw new IllegalStateException();
	}

	/**
	 * Ensures the truth of an expression.<br>
	 * Throws {@link IllegalStateException} if {@code expression} is <code>false</code>.
	 * 
	 * @param expression
	 *            a boolean expression
	 * @param message
	 *            the exception message to use if the check fails, will be converted to a string using {@link String#valueOf(Object)}
	 */
	public static void checkState(final boolean expression, final Object message) {
		if (!expression)
			throw new IllegalStateException(String.valueOf(message));
	}

	/**
	 * Ensures the truth of an expression.<br>
	 * Throws {@link IllegalStateException} if {@code expression} is <code>false</code>.
	 * 
	 * @param expression
	 *            a boolean expression
	 * @param template
	 *            a template for the exception message to use if the check fails, will be processed using {@link String#valueOf(Object)}
	 * @param args
	 *            the arguments to be substituted into the message template using {@link String#format(String, Object[])}
	 */
	public static void checkState(final boolean expression, final String template, final Object... args) {
		if (!expression)
			throw new IllegalStateException(String.format(String.valueOf(template), args));
	}

	/**
	 * Ensures the {@code reference} in not <code>null</code>.<br>
	 * Throws {@link NullPointerException} if {@code reference} is <code>null</code>.
	 * 
	 * @param reference
	 *            an object reference
	 * @return the non-null reference that was validated
	 */
	public static <T> T checkNotNull(final T reference) {
		if (reference == null)
			throw new NullPointerException();

		return reference;
	}

	/**
	 * Ensures the {@code reference} in not <code>null</code>.<br>
	 * Throws {@link NullPointerException} if {@code reference} is <code>null</code>.
	 * 
	 * @param reference
	 *            an object reference
	 * @param message
	 *            the exception message to use if the check fails, will be converted to a string using {@link String#valueOf(Object)}
	 * @return the non-null reference that was validated
	 */
	public static <T> T checkNotNull(final T reference, final Object message) {
		if (reference == null)
			throw new NullPointerException(String.valueOf(message));

		return reference;
	}

	/**
	 * Ensures the {@code reference} in not <code>null</code>.<br>
	 * Throws {@link NullPointerException} if {@code reference} is <code>null</code>.
	 * 
	 * @param reference
	 *            an object reference
	 * @param template
	 *            a template for the exception message to use if the check fails, will be processed using {@link String#valueOf(Object)}
	 * @param args
	 *            the arguments to be substituted into the message template using {@link String#format(String, Object[])}
	 * @return the non-null reference that was validated
	 */
	public static <T> T checkNotNull(final T reference, final String template, final Object... args) {
		if (reference == null)
			throw new NullPointerException(String.format(String.valueOf(template), args));

		return reference;
	}

}
