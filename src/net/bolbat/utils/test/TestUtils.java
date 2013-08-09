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
	 * Get path to folder where test can store some temporary data. Path always ends with path separator.
	 * 
	 * @param clazz
	 *            test class name
	 * @return {@link String} path
	 */
	public static String getTemporaryFolder(final Class<?> clazz) {
		return getTemporaryFolder(clazz.getName());
	}

	/**
	 * Get path to folder where test can store some temporary data. Path always ends with path separator.
	 * 
	 * @param suffix
	 *            some path suffix
	 * @return {@link String} path
	 */
	public static String getTemporaryFolder(final String suffix) {
		String result = System.getProperty("java.io.tmpdir"); // trying to obtain current system user temporary directory
		if (StringUtils.isEmpty(result)) // if temporary directory not exist/configured
			result = System.getProperty("user.home"); // trying to obtain current system user home directory
		if (StringUtils.isEmpty(result)) // if home directory not exist/configured
			result = File.separator + "tmp";
		if (StringUtils.isEmpty(suffix)) // if suffix is empty
			return result + File.separator;

		result += suffix.startsWith(File.separator) ? suffix : File.separator + suffix; // adding suffix
		if (!result.endsWith(File.separator)) // checking is path ends with path separator
			result += File.separator;

		return result;
	}

}
