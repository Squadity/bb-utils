package net.bolbat.utils.reflect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * {@link Class} utilities.
 * 
 * @author Alexandr Bolbat
 */
public final class ClassUtils {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private ClassUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Get all class types (self, super classes and interfaces).
	 * 
	 * @param type
	 *            class
	 * @return {@link Set} of {@link Class}
	 */
	public static Set<Class<?>> getAllTypes(final Class<?> type) {
		final Set<Class<?>> result = new LinkedHashSet<Class<?>>();

		if (type != null && !type.equals(Object.class)) {
			result.add(type);
			result.addAll(getAllTypes(type.getSuperclass()));
			for (final Class<?> ifc : type.getInterfaces())
				result.addAll(getAllTypes(ifc));
		}

		return result;
	}

	/**
	 * Get all class fields.
	 * 
	 * @param type
	 *            class
	 * @return {@link Set} of {@link Field}
	 */
	public static Set<Field> getAllFields(final Class<?> type) {
		final Set<Field> result = new LinkedHashSet<Field>();

		for (final Class<?> t : getAllTypes(type))
			result.addAll(Arrays.asList(t.getDeclaredFields()));

		return result;
	}

	/**
	 * Convert primitive type to it's not primitive analog. <br>
	 * Supported primitive types of: {@link Byte}, {@link Short}, {@link Integer}, {@link Long}, {@link Float}, {@link Double}, {@link Boolean},
	 * {@link Character}. <br>
	 * Original type will be returned if argument of not supported type.
	 * 
	 * @param clazz
	 *            {@link Class} of primitive type
	 * @return {@link Class} of not primitive analog
	 */
	public static Class<?> convertPrimitive(final Class<?> clazz) {
		if (clazz == null)
			throw new IllegalArgumentException("clazz argument is null.");

		if (clazz.isAssignableFrom(byte.class) || clazz.isAssignableFrom(Byte.class))
			return Byte.class;
		if (clazz.isAssignableFrom(short.class) || clazz.isAssignableFrom(Short.class))
			return Short.class;
		if (clazz.isAssignableFrom(int.class) || clazz.isAssignableFrom(Integer.class))
			return Integer.class;
		if (clazz.isAssignableFrom(long.class) || clazz.isAssignableFrom(Long.class))
			return Long.class;
		if (clazz.isAssignableFrom(float.class) || clazz.isAssignableFrom(Float.class))
			return Float.class;
		if (clazz.isAssignableFrom(double.class) || clazz.isAssignableFrom(Double.class))
			return Double.class;
		if (clazz.isAssignableFrom(boolean.class) || clazz.isAssignableFrom(Boolean.class))
			return Boolean.class;
		if (clazz.isAssignableFrom(char.class) || clazz.isAssignableFrom(Character.class))
			return Character.class;

		return clazz;
	}

	/**
	 * Convert not primitive type to it's primitive analog. <br>
	 * Supported types: {@link Byte}, {@link Short}, {@link Integer}, {@link Long}, {@link Float}, {@link Double}, {@link Boolean}, {@link Character}. <br>
	 * Original type will be returned if argument of not supported type.
	 * 
	 * @param clazz
	 *            {@link Class} of not primitive type
	 * @return {@link Class} of primitive analog
	 */
	public static Class<?> convertNotPrimitive(Class<?> clazz) {
		if (clazz == null)
			throw new IllegalArgumentException("clazz argument is null.");

		if (clazz.isPrimitive()) // if it's already primitive
			return clazz;

		if (clazz.isAssignableFrom(Byte.class))
			return byte.class;
		if (clazz.isAssignableFrom(Short.class))
			return short.class;
		if (clazz.isAssignableFrom(Integer.class))
			return int.class;
		if (clazz.isAssignableFrom(Long.class))
			return long.class;
		if (clazz.isAssignableFrom(Float.class))
			return float.class;
		if (clazz.isAssignableFrom(Double.class))
			return double.class;
		if (clazz.isAssignableFrom(Boolean.class))
			return boolean.class;
		if (clazz.isAssignableFrom(Character.class))
			return char.class;

		return clazz;
	}

}
