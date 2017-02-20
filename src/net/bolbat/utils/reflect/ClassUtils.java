package net.bolbat.utils.reflect;

import static net.bolbat.utils.lang.Validations.checkArgument;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.bolbat.utils.logging.LoggingUtils;
import net.bolbat.utils.reflect.proxy.ProxyUtils;

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
	 * Get all class interfaces.
	 * 
	 * @param type
	 *            class
	 * @return array of {@link Class}
	 */
	public static Class<?>[] getAllInterfaces(final Class<?> type) {
		final Set<Class<?>> interfaces = new LinkedHashSet<>();

		if (type != null)
			fillAllInterfaces(type, interfaces);

		return interfaces.toArray(new Class<?>[interfaces.size()]);
	}

	/**
	 * Fill recursively class interfaces to result set.
	 * 
	 * @param type
	 *            class
	 * @param result
	 *            result set
	 */
	private static void fillAllInterfaces(final Class<?> type, final Set<Class<?>> result) {
		Class<?> currentClazz = type;
		while (currentClazz != null) {
			for (final Class<?> i : currentClazz.getInterfaces())
				if (result.add(i))
					fillAllInterfaces(i, result);

			currentClazz = currentClazz.getSuperclass();
		}
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
		checkArgument(clazz != null, "clazz argument is null");

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
		checkArgument(clazz != null, "clazz argument is null");

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
	 * Execute {@code PostConstruct} methods.<br>
	 * Check {@code execute(Object,boolean,Class<? extends Annotation>[])} for details.
	 * 
	 * @param instance
	 *            {@link Object}
	 */
	public static void executePostConstruct(final Object instance) {
		executePostConstruct(instance, false);
	}

	/**
	 * Execute {@code PostConstruct} methods.<br>
	 * Check {@code execute(Object,boolean,Class<? extends Annotation>[])} for details.
	 * 
	 * @param instance
	 *            {@link Object}
	 * @param unwrapIfProxy
	 *            if <code>true</code> try to unwrap if instance if a {@link Proxy} by {@code ProxyUtils.unwrapProxy(proxy)}
	 */
	public static void executePostConstruct(final Object instance, final boolean unwrapIfProxy) {
		execute(instance, unwrapIfProxy, PostConstruct.class);
	}

	/**
	 * Execute {@code @PreDestroy} methods.<br>
	 * Check {@code execute(Object,boolean,Class<? extends Annotation>[])} for details.
	 * 
	 * @param instance
	 *            {@link Object}
	 */
	public static void executePreDestroy(final Object instance) {
		executePreDestroy(instance, false);
	}

	/**
	 * Execute {@code @PreDestroy} methods.<br>
	 * Check {@code execute(Object,boolean,Class<? extends Annotation>[])} for details.
	 * 
	 * @param instance
	 *            {@link Object}
	 * @param unwrapIfProxy
	 *            if <code>true</code> try to unwrap if instance if a {@link Proxy} by {@code ProxyUtils.unwrapProxy(proxy)}
	 */
	public static void executePreDestroy(final Object instance, final boolean unwrapIfProxy) {
		execute(instance, unwrapIfProxy, PreDestroy.class);
	}

	/**
	 * Execute all methods for given instance marked with at least one of given annotations.<br>
	 * Check {@code execute(Object,boolean,Class<? extends Annotation>[])} for details.
	 * 
	 * @param instance
	 *            {@link Object}
	 * @param annotations
	 *            annotations types
	 */
	@SafeVarargs
	public static void execute(final Object instance, final Class<? extends Annotation>... annotations) {
		execute(instance, false, annotations);
	}

	/**
	 * Execute all methods for given instance marked with at least one of given annotations.<br>
	 * Rules for marked methods:<br>
	 * - MUST be void;<br>
	 * - MUST NOT have any parameters;<br>
	 * - MUST NOT be static;<br>
	 * - MUST NOT throw a checked exception;<br>
	 * - MAY be final;<br>
	 * - MAY be public, protected, package private or private;<br>
	 * - excludes inherited methods.
	 * 
	 * @param instance
	 *            {@link Object}
	 * @param unwrapIfProxy
	 *            if <code>true</code> try to unwrap if instance if a {@link Proxy} by {@code ProxyUtils.unwrapProxy(proxy)}
	 * @param annotations
	 *            annotations types
	 */
	@SafeVarargs
	public static void execute(final Object instance, final boolean unwrapIfProxy, final Class<? extends Annotation>... annotations) {
		execute(instance, unwrapIfProxy, false, annotations);
	}

	/**
	 * Execute all methods for given instance marked with at least one of given annotations.<br>
	 * Rules for marked methods:<br>
	 * - MUST be void;<br>
	 * - MUST NOT have any parameters;<br>
	 * - MUST NOT be static;<br>
	 * - MUST NOT throw a checked exception;<br>
	 * - MAY be final;<br>
	 * - MAY be public, protected, package private or private;<br>
	 * - excludes inherited methods.
	 * 
	 * @param instance
	 *            {@link Object}
	 * @param unwrapIfProxy
	 *            if <code>true</code> try to unwrap if instance if a {@link Proxy} by {@code ProxyUtils.unwrapProxy(proxy)}
	 * @param skipOnError
	 *            skip execution on any exception
	 * @param annotations
	 *            annotations types
	 */
	@SafeVarargs
	public static void execute(final Object instance, final boolean unwrapIfProxy, final boolean skipOnError,
			final Class<? extends Annotation>... annotations) {
		checkArgument(instance != null, "instance argument is null");
		if (annotations == null || annotations.length == 0)
			return;

		// unwrapping if proxy and invocation handler is supported
		Object target = instance;
		if (unwrapIfProxy)
			try {
				target = ProxyUtils.unwrapProxy(target);
				// CHECKSTYLE:OFF
			} catch (final RuntimeException e) {
				// CHECKSTYLE:ON
				LoggingUtils.warn(LOGGER, "Can't unwrap from proxy[" + target + "]", e);
			}

		for (final Method m : target.getClass().getDeclaredMethods()) {
			boolean process = false;
			for (final Class<? extends Annotation> aClass : annotations) {
				if (aClass != null && m.getAnnotation(aClass) != null) {
					process = true;
					break;
				}
			}

			if (!process)
				continue;

			// The return type MUST be void
			if (!m.getReturnType().equals(Void.TYPE)) {
				LoggingUtils.debug(LOGGER, "Skipping method[" + m + "] execution, cause[MUST be void]");
				continue;
			}

			// The method MUST NOT have any parameters
			if (m.getParameterTypes().length > 0) {
				LoggingUtils.debug(LOGGER, "Skipping method[" + m + "] execution, cause[MUST NOT have any parameters]");
				continue;
			}

			// The method MUST NOT be static
			if (Modifier.isStatic(m.getModifiers())) {
				LoggingUtils.debug(LOGGER, "Skipping method[" + m + "] execution, cause[MUST NOT be static]");
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
				LoggingUtils.debug(LOGGER, "Skipping method[" + m + "] execution, cause[MUST NOT throw a checked exception]");
				continue;
			}

			// processing
			try {
				m.setAccessible(true);
				m.invoke(target);
				// CHECKSTYLE:OFF
			} catch (final IllegalAccessException | InvocationTargetException e) {
				// CHECKSTYLE:ON
				LoggingUtils.debug(LOGGER, "Can't execute method[" + m + "]", e);

				if (skipOnError)
					return;

				if (e instanceof InvocationTargetException) // unwrapping
					throw new RuntimeException(((InvocationTargetException) e).getTargetException());

				throw new RuntimeException(e);
			}
		}
	}

}
