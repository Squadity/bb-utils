package net.bolbat.utils.test;

import net.bolbat.utils.io.FSUtils;

/**
 * Utility with some helping functionality for testing purposes.
 * 
 * @author Alexandr Bolbat
 */
public final class TestUtils {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private TestUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Get path to temporary folder.<br>
	 * Path always ends with path separator.<br>
	 * Deprecated, use {@code FSUtils.getTmpFolder(Class<?> clazz)} instead.
	 * 
	 * @param clazz
	 *            class, used to construct path suffix from class name, optional
	 * @return {@link String} path
	 */
	@Deprecated
	public static String getTemporaryFolder(final Class<?> clazz) {
		return FSUtils.getTmpFolder(clazz);
	}

	/**
	 * Get path to temporary folder.<br>
	 * Path always ends with path separator.<br>
	 * Deprecated, use {@code FSUtils.getTmpFolder(String... subPaths)} instead.
	 * 
	 * @param subPath
	 *            sub path, optional
	 * @return {@link String} path
	 */
	@Deprecated
	public static String getTemporaryFolder(final String subPath) {
		return FSUtils.getTmpFolder(subPath);
	}

}
