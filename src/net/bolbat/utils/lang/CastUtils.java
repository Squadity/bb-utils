package net.bolbat.utils.lang;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.bolbat.utils.annotation.Audience;
import net.bolbat.utils.annotation.Stability;

/**
 * Casting utilities.
 * 
 * @author Alexandr Bolbat
 *
 */
@Audience.Public
@Stability.Stable
public final class CastUtils {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private CastUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Cast {@link Object} to {@link List}.
	 * 
	 * @param object
	 *            {@link Object}
	 * @return {@link List}
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> castList(final Object object) {
		return object != null ? (List<T>) object : null;
	}

	/**
	 * Cast {@link Object} to {@link Set}.
	 * 
	 * @param object
	 *            {@link Object}
	 * @return {@link Set}
	 */
	@SuppressWarnings("unchecked")
	public static <T> Set<T> castSet(final Object object) {
		return object != null ? (Set<T>) object : null;
	}

	/**
	 * Cast {@link Object} to {@link Map}.
	 * 
	 * @param object
	 *            {@link Object}
	 * @return {@link Map}
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> castMap(final Object object) {
		return object != null ? (Map<K, V>) object : null;
	}

}
