package net.bolbat.utils.io;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;

/**
 * Input/Output utilities.
 * 
 * @author Alexandr Bolbat
 */
public final class IOUtils {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private IOUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Close {@link Closeable} resource.
	 * 
	 * @param closeables
	 *            {@link Closeable} array
	 */
	public static void close(final Closeable... closeables) {
		close(null, closeables);
	}

	/**
	 * Close {@link Closeable} resource.
	 * 
	 * @param logger
	 *            {@link Logger} instance, can be <code>null</code> if logging not required
	 * @param closeables
	 *            {@link Closeable} array
	 */
	public static void close(final Logger logger, final Closeable... closeables) {
		if (closeables == null || closeables.length == 0)
			return;

		for (final Closeable c : closeables)
			if (c != null)
				try {
					c.close();
				} catch (final IOException e) {
					if (logger != null && logger.isDebugEnabled())
						logger.debug("Closing[" + c + "] fail", e);
				}
	}

}
