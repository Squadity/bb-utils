package net.bolbat.utils.io;

import static net.bolbat.utils.lang.StringUtils.EMPTY;

import java.io.File;

import net.bolbat.utils.lang.PathUtils;
import net.bolbat.utils.lang.StringUtils;

/**
 * File system utilities.
 * 
 * @author Alexandr Bolbat
 */
public final class FSUtils {

	/**
	 * Doubled file separator constant.
	 */
	private static final String DOUBLE_FILE_SEPARATOR = File.separator + File.separator;

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private FSUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Get path to class temporary folder.<br>
	 * Path always ends with path separator.
	 * 
	 * @param clazz
	 *            class, used to construct sub path from class name, optional
	 * @return {@link String} path
	 */
	public static String getTmpFolder(final Class<?> clazz) {
		return getTmpFolder(clazz != null ? clazz.getName() : EMPTY);
	}

	/**
	 * Get path to temporary folder.<br>
	 * Path always ends with path separator.
	 * 
	 * @param subPaths
	 *            sub paths, optional
	 * @return {@link String} path
	 */
	public static String getTmpFolder(final String... subPaths) {
		// trying to obtain current system user temporary directory
		String result = System.getProperty("java.io.tmpdir");

		// if temporary directory not exist/configured
		if (StringUtils.isEmpty(result)) {
			// trying to obtain current system user home directory
			result = System.getProperty("user.home");
		}

		// if home directory not exist/configured
		if (StringUtils.isEmpty(result)) {
			// preparing common (linux like) path to temporary folder
			// can be improved in future to support other operating systems
			result = File.separator + "tmp";
		}

		// adding trailing separator if needed
		if (!result.endsWith(File.separator))
			result = result + File.separator;

		// if subPath is empty
		final String subPath = PathUtils.createPath(subPaths);
		if (StringUtils.isEmpty(subPath))
			return result;

		// adding subPath
		result = result + subPath + File.separator;

		// removing redundant separators
		while (result.contains(DOUBLE_FILE_SEPARATOR))
			result = result.replaceAll(DOUBLE_FILE_SEPARATOR, File.separator);

		return result;
	}

}
