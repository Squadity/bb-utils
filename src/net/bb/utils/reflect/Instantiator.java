package net.bb.utils.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Utility for instantiating classes.
 * 
 * @author Alexandr Bolbat
 */
public final class Instantiator {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private Instantiator() {
		throw new IllegalAccessError();
	}

	/**
	 * Instantiate class by name.
	 * 
	 * @param clazzName
	 *            - class name
	 * @return instance
	 */
	public static Object instantiate(final String clazzName) {
		if (isEmpty(clazzName))
			throw new IllegalArgumentException("[clazzName] argument is empty.");

		try {
			Class<?> clazz = Class.forName(clazzName);
			return instantiate(clazz);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Instantiate given class type.
	 * 
	 * @param clazz
	 *            - class type
	 * @return instance
	 */
	public static <T> T instantiate(final Class<T> clazz) {
		if (isNull(clazz))
			throw new IllegalArgumentException("[clazz] argument are null.");

		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Instantiate class by given constructor and parameters.
	 * 
	 * @param constructor
	 *            - class constructor
	 * @param parameters
	 *            - constructor parameters
	 * @return instance
	 */
	public static <T> T instantiate(final Constructor<T> constructor, Object... parameters) {
		if (isNull(constructor))
			throw new IllegalArgumentException("[constructor] argument are null.");

		try {
			if (parameters == null || parameters.length == 0)
				return constructor.newInstance();

			return constructor.newInstance(parameters);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Check is given object are <code>null</code>.
	 * 
	 * @param obj
	 *            - object
	 * @return <code>true</code> if <code>null</code> or <code>false</code>
	 */
	private static boolean isNull(final Object obj) {
		return obj == null;
	}

	/**
	 * Check is given {@link String} object are empty.
	 * 
	 * @param obj
	 *            - {@link String} object
	 * @return <code>true</code> if <code>empty</code> or <code>false</code>
	 */
	private static boolean isEmpty(final String obj) {
		return isNull(obj) || obj.trim().isEmpty();
	}

}
