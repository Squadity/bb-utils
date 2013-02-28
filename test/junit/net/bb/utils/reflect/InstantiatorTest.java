package net.bb.utils.reflect;

import java.lang.reflect.Constructor;

import junit.framework.Assert;

import org.junit.Test;

/**
 * {@link Instantiator} utility test.
 * 
 * @author Alexandr Bolbat
 */
public class InstantiatorTest {

	/**
	 * Complex test.
	 */
	@Test
	public void complexTest() {
		SampleClass instance = Instantiator.instantiate(SampleClass.class);
		Assert.assertNotNull(instance);
		Assert.assertEquals(SampleClass.DEFAULT_VALUE, instance.getValue());

		instance = SampleClass.class.cast(Instantiator.instantiate(SampleClass.class.getName()));
		Assert.assertNotNull(instance);
		Assert.assertEquals(SampleClass.DEFAULT_VALUE, instance.getValue());

		for (Constructor<?> constructor : SampleClass.class.getConstructors()) {
			if (constructor.getParameterTypes().length == 0) {
				instance = SampleClass.class.cast(Instantiator.instantiate(constructor));
				Assert.assertEquals(SampleClass.DEFAULT_VALUE, instance.getValue());
				continue;
			}
			if (constructor.getParameterTypes().length == 1 && constructor.getParameterTypes()[0] == String.class) {
				String param = "SOME_VALUE";
				instance = SampleClass.class.cast(Instantiator.instantiate(constructor, param));
				Assert.assertEquals(param, instance.getValue());
				continue;
			}

			Assert.fail("All implemented constructor's should be tested");
		}

	}

	/**
	 * Error cases test.
	 */
	@Test
	public void errorCasesTest() {
		Class<SampleClass> clazz = null;
		try {
			Instantiator.instantiate(clazz);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("[clazz]"));
		}

		String className = null;
		try {
			Instantiator.instantiate(className);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("[clazzName]"));
		}

		className = "";
		try {
			Instantiator.instantiate(className);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("[clazzName]"));
		}

		className = "               ";
		try {
			Instantiator.instantiate(className);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("[clazzName]"));
		}

		className = "not.exist.class";
		try {
			Instantiator.instantiate(className);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (RuntimeException e) {
			Assert.assertNotNull(e.getCause());
			Assert.assertTrue(e.getCause() instanceof ClassNotFoundException);
		}

		Constructor<SampleClass> constructor = null;
		try {
			Instantiator.instantiate(constructor);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Right exception should be there.", e.getMessage().startsWith("[constructor]"));
		}
	}

}
