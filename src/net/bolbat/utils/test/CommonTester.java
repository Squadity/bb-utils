package net.bolbat.utils.test;

import java.lang.reflect.Constructor;

import net.bolbat.utils.reflect.Instantiator;

/**
 * Utility for common testing approaches, like exception's testing (mostly for code coverage).
 * 
 * @author Alexandr Bolbat
 */
public final class CommonTester {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private CommonTester() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Check exceptions instantiation.
	 * 
	 * @param clazz
	 *            exception class
	 */
	public static <T extends Exception> void checkExceptionInstantiation(final Class<T> clazz) {
		String exceptionMessage = "Test exception";
		Throwable exceptionCause = new IllegalArgumentException("Test cause");

		Constructor<?>[] constructors = clazz.getConstructors();
		for (Constructor<?> constructor : constructors) {
			if (constructor.getParameterTypes().length == 0) {
				@SuppressWarnings("unchecked")
				T instance = (T) Instantiator.instantiate(constructor);
				// message validation
				if (instance.getMessage() != null)
					throw new AssertionError("Exception[" + instance + "] message should be null.");
				// cause validation
				if (instance.getCause() != null)
					throw new AssertionError("Exception[" + instance + "] cause should be null.");
				continue;
			}

			if (constructor.getParameterTypes().length == 1 && constructor.getParameterTypes()[0] == String.class) {
				@SuppressWarnings("unchecked")
				T instance = (T) Instantiator.instantiate(constructor, exceptionMessage);
				// message validation
				if (instance.getMessage() == null)
					throw new AssertionError("Exception[" + instance + "] message shouldn't be null.");
				if (!exceptionMessage.equals(instance.getMessage()))
					throw new AssertionError("Exception[" + instance + "] message should be equal with [" + exceptionMessage + "].");
				// cause validation
				if (instance.getCause() != null)
					throw new AssertionError("Exception[" + instance + "] cause should be null.");
				continue;
			}
			if (constructor.getParameterTypes().length == 1 && constructor.getParameterTypes()[0] == Throwable.class) {
				@SuppressWarnings("unchecked")
				T instance = (T) Instantiator.instantiate(constructor, exceptionCause);
				// message validation
				if (instance.getMessage() == null)
					throw new AssertionError("Exception[" + instance + "] message shouldn't be null.");
				if (!exceptionCause.toString().equals(instance.getMessage()))
					throw new AssertionError("Exception[" + instance + "] message should be equal with [" + exceptionCause.toString() + "].");
				// cause validation
				if (instance.getCause() == null)
					throw new AssertionError("Exception[" + instance + "] cause shouldn't be null.");
				if (!exceptionCause.getMessage().equals(instance.getCause().getMessage()))
					throw new AssertionError("Exception[" + instance + "] cause message should be equal with [" + exceptionCause.getMessage() + "].");
				continue;
			}
			if (constructor.getParameterTypes().length == 2 && constructor.getParameterTypes()[0] == String.class
					&& constructor.getParameterTypes()[1] == Throwable.class) {
				@SuppressWarnings("unchecked")
				T instance = (T) Instantiator.instantiate(constructor, exceptionMessage, exceptionCause);
				// message validation
				if (instance.getMessage() == null)
					throw new AssertionError("Exception[" + instance + "] message shouldn't be null.");
				if (!exceptionMessage.equals(instance.getMessage()))
					throw new AssertionError("Exception[" + instance + "] message should be equal with [" + exceptionMessage + "].");
				// cause validation
				if (instance.getCause() == null)
					throw new AssertionError("Exception[" + instance + "] cause shouldn't be null.");
				if (!exceptionCause.getMessage().equals(instance.getCause().getMessage()))
					throw new AssertionError("Exception[" + instance + "] cause message should be equal with [" + exceptionCause.getMessage() + "].");
				continue;
			}
		}
	}

}
