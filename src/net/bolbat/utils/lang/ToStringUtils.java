package net.bolbat.utils.lang;

import static net.bolbat.utils.lang.StringUtils.EMPTY;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Utilities for <code>toString</code>.
 * 
 * @author Alexandr Bolbat
 */
public final class ToStringUtils {

	/**
	 * Result first character.
	 */
	public static final String FIRST_CHAR = "[";

	/**
	 * Result last character.
	 */
	public static final String LAST_CHAR = "]";

	/**
	 * Result elements delimiter.
	 */
	public static final String DELIMITER = ",";

	/**
	 * Result elements delimiter for map elements.
	 */
	public static final String MAP_VALUE_DELIMITER = "|";

	/**
	 * Result suffix if elements in collection more then allowed in result.
	 */
	public static final String MORE_CHARS = "...";

	/**
	 * Result for empty or <code>null</code> collection.
	 */
	public static final String EMPTY_RESULT = FIRST_CHAR + LAST_CHAR;

	/**
	 * Result for not empty collection but with limit less than '1'.
	 */
	public static final String EMPTY_MORE_RESULT = FIRST_CHAR + MORE_CHARS + LAST_CHAR;

	/**
	 * Default collection elements limit for representation.
	 */
	public static final int DEFAULT_LIMIT = 10;

	/**
	 * Private constructor.
	 */
	private ToStringUtils() {
		throw new IllegalAccessError("Can't be instantiated.");
	}

	/**
	 * Create string representation for any array with default elements limit.
	 *
	 * @param array
	 *            array, can be <code>null</code> or empty
	 * @return {@link String}
	 */
	public static String toString(final Object[] array) {
		return toString(array, DEFAULT_LIMIT);
	}

	/**
	 * Create string representation for any array with default elements limit.
	 *
	 * @param array
	 *            array, can be <code>null</code> or empty
	 * @param maxLen
	 *            elements limit used in representation
	 * @return {@link String}
	 */
	public static String toString(final Object[] array, final int maxLen) {
		return toString(array == null || array.length == 0 ? Collections.emptyList() : Arrays.asList(array), maxLen);
	}

	/**
	 * Create string representation for any collection with default elements limit.
	 * 
	 * @param collection
	 *            collection, can be <code>null</code> or empty
	 * @return {@link String}
	 */
	public static String toString(final Collection<?> collection) {
		return toString(collection, DEFAULT_LIMIT);
	}

	/**
	 * Create string representation for any collection.
	 * 
	 * @param collection
	 *            collection, can be <code>null</code> or empty
	 * @param maxLen
	 *            elements limit used in representation
	 * @return {@link String}
	 */
	public static String toString(final Collection<?> collection, final int maxLen) {
		if (collection == null || collection.isEmpty())
			return EMPTY_RESULT;
		if (maxLen < 1)
			return EMPTY_MORE_RESULT;

		final StringBuilder builder = new StringBuilder(FIRST_CHAR);
		int i = 0;
		for (final Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
			if (i > 0)
				builder.append(DELIMITER);

			builder.append(iterator.next());
		}

		if (collection.size() > maxLen)
			builder.append(DELIMITER).append(MORE_CHARS);

		builder.append(LAST_CHAR);
		return builder.toString();
	}

	/**
	 * Create string representation for map with default elements limit.
	 * 
	 * @param map
	 *            map, can be <code>null</code> or empty
	 * @return {@link String}
	 */
	public static String toString(final Map<?, ?> map) {
		return toString(map, DEFAULT_LIMIT);
	}

	/**
	 * Create string representation for map.
	 * 
	 * @param map
	 *            map, can be <code>null</code> or empty
	 * @param maxLen
	 *            elements limit used in representation
	 * @return {@link String}
	 */
	public static String toString(final Map<?, ?> map, final int maxLen) {
		if (map == null || map.isEmpty())
			return EMPTY_RESULT;
		if (maxLen < 1)
			return EMPTY_MORE_RESULT;

		final StringBuilder builder = new StringBuilder(FIRST_CHAR);
		int i = 0;
		for (final Entry<?, ?> entry : map.entrySet()) {
			if (i > 0)
				builder.append(DELIMITER);

			final Object key = entry.getKey();
			final Object value = entry.getValue();
			builder.append(key).append(MAP_VALUE_DELIMITER).append(value instanceof Collection ? toString(Collection.class.cast(value), maxLen) : value);
			if (++i >= maxLen) // increment and break if limit exceeded
				break;
		}

		if (map.size() > maxLen)
			builder.append(DELIMITER).append(MORE_CHARS);

		builder.append(LAST_CHAR);
		return builder.toString();
	}

	/**
	 * Create string representation for {@link Class}.
	 * 
	 * @param type
	 *            {@link Class}
	 * @return {@link String}
	 */
	public static String toTypeName(final Class<?> type) {
		if (type == null)
			return EMPTY;

		if (!type.isArray())
			return type.getName();

		Class<?> cType = type;
		int dimensions = 0;
		while (cType.isArray()) {
			dimensions++;
			cType = cType.getComponentType();
		}

		final StringBuffer result = new StringBuffer();
		result.append(cType.getName());
		for (int i = 0; i < dimensions; i++)
			result.append("[]");

		return result.toString();
	}

	/**
	 * Create string representation for {@link Method}.
	 * 
	 * @param method
	 *            {@link Method}
	 * @return {@link String}
	 */
	public static String toMethodName(final Method method) {
		if (method == null)
			return EMPTY;

		final StringBuilder result = new StringBuilder();
		result.append(method.getName()).append('(');

		final Class<?>[] params = method.getParameterTypes();
		for (int i = 0; i < params.length; i++) {
			result.append(toTypeName(params[i]));
			if (i < params.length - 1)
				result.append(',');
		}

		result.append(')');
		return result.toString();
	}

}
