package net.bolbat.utils.test;

import java.io.File;

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
		String result;

		result = System.getProperty("java.io.tmpdir"); // trying to obtain current system user temporary directory

		if (result == null || result.trim().length() == 0) // if temporary directory not exist/configured
			result = System.getProperty("user.home"); // trying to obtain current system user home directory

		if (result == null || result.trim().length() == 0) // if home directory not exist/configured
			return File.separator + "tmp" + File.separator + clazz.getName() + File.separator;

		if (!result.endsWith(File.separator)) // checking is path ends with path separator
			result += File.separator;

		return result + clazz.getName() + File.separator;
	}

}
