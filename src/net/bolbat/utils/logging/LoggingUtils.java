package net.bolbat.utils.logging;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Utility methods for logging purposes.
 * 
 * @author Alexandr Bolbat
 */
public final class LoggingUtils {

	/**
	 * {@link Marker} name for 'FATAL' error case.
	 */
	public static final String FATAL_NAME = "FATAL";

	/**
	 * {@link Marker} for 'FATAL' error case.
	 */
	public static final Marker FATAL = MarkerFactory.getMarker(FATAL_NAME);

}
