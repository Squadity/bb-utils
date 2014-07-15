package net.bolbat.utils.lang;

import java.io.File;

/**
 * Path utilities.
 *
 * @author Alexandr Bolbat
 */
public final class PathUtils {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private PathUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Create path {@link String} from {@link String} array of path elements.
	 *
	 * @param pathElement
	 *            {@link String} array
	 * @return {@link String}
	 */
	public static String createPath(final String... pathElement) {
		if (pathElement == null || pathElement.length == 0)
			return StringUtils.EMPTY;

		final StringBuilder result = new StringBuilder();
		String previous = null;
		for (String path : pathElement) {
			if (path == null)
				continue;

			if (previous == null) {
				result.append(path);
				previous = path;
				continue;
			}

			final String prefix = previous.endsWith(File.separator) ? StringUtils.EMPTY : File.separator;
			final String toAdd = path.startsWith(File.separator) ? path.substring(1) : path;
			result.append(prefix).append(toAdd);
			previous = path;
		}

		return result.toString();
	}

}
