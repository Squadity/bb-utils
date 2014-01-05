package net.bolbat.utils.test;

import java.io.File;

import net.bolbat.utils.lang.StringUtils;

/**
 * Utility with some helping functionality.
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
	 * Get path to folder where temporary data can be stored.<br>
	 * Path always ends with path separator.
	 * 
	 * @param clazz
	 *            class, used to construct path suffix from class name, optional
	 * @return {@link String} path
	 */
	public static String getTemporaryFolder(final Class<?> clazz) {
		return getTemporaryFolder(clazz != null ? clazz.getName() : StringUtils.EMPTY);
	}

	/**
	 * Get path to folder where test can store some temporary data.<br>
	 * Path always ends with path separator.
	 * 
	 * @param suffix
	 *            path suffix, optional
	 * @return {@link String} path
	 */
	public static String getTemporaryFolder(final String suffix) {
		String result = System.getProperty("java.io.tmpdir"); // trying to obtain current system user temporary directory
		if (StringUtils.isEmpty(result)) // if temporary directory not exist/configured
			result = System.getProperty("user.home"); // trying to obtain current system user home directory
		if (StringUtils.isEmpty(result)) // if home directory not exist/configured
			result = File.separator + "tmp";
		if (StringUtils.isEmpty(suffix)) // if suffix is empty
			return result.endsWith(File.separator) ? result : result + File.separator;

		if (result.endsWith(File.separator)) // removing redundant separator
			result = result.substring(0, result.length() - 1);

		result += suffix.startsWith(File.separator) ? suffix : File.separator + suffix; // adding suffix
		return result.endsWith(File.separator) ? result : result + File.separator;
	}

}
