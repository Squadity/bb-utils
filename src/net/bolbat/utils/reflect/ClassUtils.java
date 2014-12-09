package net.bolbat.utils.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.bolbat.utils.logging.LoggingUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link Class} utilities.
 * 
 * @author Alexandr Bolbat
 */
public final class ClassUtils {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtils.class);

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
		final Set<Class<?>> result = new LinkedHashSet<>();

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
		final Set<Field> result = new LinkedHashSet<>();

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

	/**
	 * Execute 'post-construct' for given instance.<br>
	 * Rules for marked methods:<br>
	 * - MUST be void;<br>
	 * - MUST NOT have any parameters;<br>
	 * - MUST NOT be static;<br>
	 * - MUST NOT throw a checked exception;<br>
	 * - MAY be final;<br>
	 * - MAY be public, protected, package private or private;<br>
	 * - excludes inherited methods;<br>
	 * - unchecked exceptions are not ignored.
	 * 
	 * @param instance
	 *            {@link Object}
	 */
	public static void executePostConstruct(final Object instance) {
		if (instance == null)
			throw new IllegalArgumentException("instance argument is null.");

		for (final Method m : instance.getClass().getDeclaredMethods()) {
			final Annotation a = m.getAnnotation(PostConstruct.class);
			if (a == null)
				continue;

			// The return type MUST be void
			if (!m.getReturnType().equals(Void.TYPE)) {
				LoggingUtils.trace(LOGGER, "Skipping @PostConstruct method[" + m + "] execution, cause[MUST be void]");
				continue;
			}

			// The method MUST NOT have any parameters
			if (m.getParameterTypes().length > 0) {
				LoggingUtils.trace(LOGGER, "Skipping @PostConstruct method[" + m + "] execution, cause[MUST NOT have any parameters]");
				continue;
			}

			// The method MUST NOT be static
			if (Modifier.isStatic(m.getModifiers())) {
				LoggingUtils.trace(LOGGER, "Skipping @PostConstruct method[" + m + "] execution, cause[MUST NOT be static]");
				continue;
			}

			boolean isBreak = false;
			// The method MUST NOT throw a checked exception
			for (final Class<?> exception : m.getExceptionTypes())
				if (!RuntimeException.class.isAssignableFrom(exception)) {
					isBreak = true;
					break;
				}

			if (isBreak) {
				LoggingUtils.trace(LOGGER, "Skipping @PostConstruct method[" + m + "] execution, cause[MUST NOT throw a checked exception]");
				continue;
			}

			// processing
			try {
				m.setAccessible(true);
				m.invoke(instance);
				// CHECKSTYLE:OFF
			} catch (final IllegalAccessException | InvocationTargetException | RuntimeException e) {
				final String message = "Can't execute @PostConstruct method[" + m + "]";
				LoggingUtils.error(LOGGER, message, e);
				throw new RuntimeException(message, e); // Maybe should be some other exception?
				// CHECKSTYLE:ON
			}
		}
	}

	/**
	 * Execute 'pre-destroy' for given instance.<br>
	 * Rules for marked methods:<br>
	 * - MUST be void;<br>
	 * - MUST NOT have any parameters;<br>
	 * - MUST NOT be static;<br>
	 * - MUST NOT throw a checked exception;<br>
	 * - MAY be final;<br>
	 * - MAY be public, protected, package private or private;<br>
	 * - excludes inherited methods;<br>
	 * - unchecked exceptions ignored, cause logged with 'DEBUG' level.
	 * 
	 * @param instance
	 *            {@link Object}
	 */
	public static void executePreDestroy(final Object instance) {
		if (instance == null)
			throw new IllegalArgumentException("instance argument is null.");

		for (final Method m : instance.getClass().getDeclaredMethods()) {
			final Annotation a = m.getAnnotation(PreDestroy.class);
			if (a == null)
				continue;

			// The return type MUST be void
			if (!m.getReturnType().equals(Void.TYPE)) {
				LoggingUtils.trace(LOGGER, "Skipping @PreDestroy method[" + m + "] execution, cause[MUST be void]");
				continue;
			}

			// The method MUST NOT have any parameters
			if (m.getParameterTypes().length > 0) {
				LoggingUtils.trace(LOGGER, "Skipping @PreDestroy method[" + m + "] execution, cause[MUST NOT have any parameters]");
				continue;
			}

			// The method MUST NOT be static
			if (Modifier.isStatic(m.getModifiers())) {
				LoggingUtils.trace(LOGGER, "Skipping @PreDestroy method[" + m + "] execution, cause[MUST NOT be static]");
				continue;
			}

			boolean isBreak = false;
			// The method MUST NOT throw a checked exception
			for (final Class<?> exception : m.getExceptionTypes())
				if (!RuntimeException.class.isAssignableFrom(exception)) {
					isBreak = true;
					break;
				}

			if (isBreak) {
				LoggingUtils.trace(LOGGER, "Skipping @PreDestroy method[" + m + "] execution, cause[MUST NOT throw a checked exception]");
				continue;
			}

			// processing
			try {
				m.setAccessible(true);
				m.invoke(instance);
				// CHECKSTYLE:OFF
			} catch (final IllegalAccessException | InvocationTargetException | RuntimeException e) {
				// CHECKSTYLE:ON
				// If the method throws an exception it is ignored
				LoggingUtils.debug(LOGGER, "Can't execute @PreDestroy method[" + m + "]", e);
			}
		}
	}

}
