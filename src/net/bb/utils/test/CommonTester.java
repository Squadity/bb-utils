package net.bb.utils.test;

import java.lang.reflect.Constructor;

import junit.framework.Assert;
import net.bb.utils.reflect.Instantiator;

/**
 * Utility for common testing approaches, like exception's testing (mostly for code coverage).
 * 
 * @author Alexandr Bolbat
 */
public final class CommonTester {

	/**
	 * Private constructor for preventing class instantiation.
	 */
	private CommonTester() {
		throw new IllegalAccessError("Can't instantiate.");
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
				Assert.assertNull("Exception message should be null", instance.getMessage());
				// cause validation
				Assert.assertNull("Exception cause should be null", instance.getCause());
				continue;
			}

			if (constructor.getParameterTypes().length == 1 && constructor.getParameterTypes()[0] == String.class) {
				@SuppressWarnings("unchecked")
				T instance = (T) Instantiator.instantiate(constructor, exceptionMessage);
				// message validation
				Assert.assertNotNull("Exception message should be not null", instance.getMessage());
				Assert.assertEquals(exceptionMessage, instance.getMessage());
				// cause validation
				Assert.assertNull("Exception cause should be null", instance.getCause());
				continue;
			}
			if (constructor.getParameterTypes().length == 1 && constructor.getParameterTypes()[0] == Throwable.class) {
				@SuppressWarnings("unchecked")
				T instance = (T) Instantiator.instantiate(constructor, exceptionCause);
				// message validation
				Assert.assertNotNull("Exception message should be null", instance.getMessage());
				Assert.assertEquals(exceptionCause.toString(), instance.getMessage());
				// cause validation
				Assert.assertNotNull("Exception cause should be not null", instance.getCause());
				Assert.assertEquals(exceptionCause.getMessage(), instance.getCause().getMessage());
				continue;
			}
			if (constructor.getParameterTypes().length == 2 && constructor.getParameterTypes()[0] == String.class
					&& constructor.getParameterTypes()[1] == Throwable.class) {
				@SuppressWarnings("unchecked")
				T instance = (T) Instantiator.instantiate(constructor, exceptionMessage, exceptionCause);
				// message validation
				Assert.assertNotNull("Exception message should be null", instance.getMessage());
				Assert.assertEquals(exceptionMessage, instance.getMessage());
				// cause validation
				Assert.assertNotNull("Exception cause should be not null", instance.getCause());
				Assert.assertEquals(exceptionCause.getMessage(), instance.getCause().getMessage());
				continue;
			}
		}
	}

}
