package net.bolbat.utils.lang;

/**
 * Utility methods for {@link String} matches.
 *
 * @author rkapushchak
 */
public final class StringMatchUtils {
	/**
	 * Wildcard for any symbol ?.
	 */
	public static final String ANY_SYMBOL = "\\u003f";

	/**
	 * Wildcard for any set of symbols *.
	 */
	public static final String ANY_SYMBOLS_SET = "\\u002A";

	/**
	 * Wildcard for any set of dot symbol.
	 */
	public static final String ANY_DOT_SYMBOLS = "\\.";

	/**
	 * Wildcard for any symbol replacement for regular expression check.
	 */
	private static final String ANY_SYMBOL_REGEXP_REPLACEMENT = "[\\\\w\\\\s-\\.]";

	/**
	 * Wildcard for any set of symbols for regular expression check.
	 */
	private static final String ANY_SYMBOLS_SET_REGEXP_REPLACEMENT = "[\\\\w\\\\s-\\.]*";

	/**
	 * Wildcard for any set of symbols for regular expression check.
	 */
	private static final String ANY_DOT_SYMBOL_REGEXP_REPLACEMENT = "\\\\.";

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private StringMatchUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Return true if text matches with entered wildcard pattern.
	 * Symbols that filters by wildcard is characters, numbers, underline symbol, dot symbol, space symbol and hyphen symbol.
	 *
	 * @param text
	 * 		{@link String} under investigation text
	 * @param wildcard
	 * 		{@link String} wildcard for text check
	 * @return true if text match with pattern
	 */
	public static boolean wildcardMatch(final String text, final String wildcard) {
		if (text == null && wildcard == null)
			return true;

		String wildcardRegex = wildcard;
		if (wildcard != null) {
			wildcardRegex = wildcardRegex.replaceAll(ANY_SYMBOL, ANY_SYMBOL_REGEXP_REPLACEMENT);
			wildcardRegex = wildcardRegex.replaceAll(ANY_DOT_SYMBOLS, ANY_DOT_SYMBOL_REGEXP_REPLACEMENT);
			wildcardRegex = wildcardRegex.replaceAll(ANY_SYMBOLS_SET, ANY_SYMBOLS_SET_REGEXP_REPLACEMENT);
		}

		return regexMatch(text, wildcardRegex);
	}

	/**
	 * Return true if text matches with entered regexp pattern.
	 *
	 * @param text
	 * 		{@link String} under investigation text
	 * @param reqex
	 * 		{@link String} reqex for text check
	 * @return true if text match with pattern
	 */
	public static boolean regexMatch(final String text, final String reqex) {
		if (text == null && reqex == null)
			return true;

		return text != null && reqex != null && text.matches(reqex);
	}

}
