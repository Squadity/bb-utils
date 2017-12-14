package net.bolbat.utils.lang;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link EnvironmentUtils} test.
 * 
 * @author Alexandr Bolbat
 */
public class EnvironmentUtilsTest {

	/**
	 * Variable resolving test.
	 */
	@Test
	public void variablesResolvingTest() {
		final Set<String> processed = new HashSet<>();
		for (final String propKey : System.getProperties().stringPropertyNames()) {
			Assert.assertEquals(EnvironmentUtils.getActualVariableValue(propKey), System.getProperty(propKey));
			processed.add(propKey);
		}

		for (final Entry<String, String> envVar : System.getenv().entrySet()) {
			if (processed.contains(envVar.getKey()))
				continue;

			Assert.assertEquals(EnvironmentUtils.getActualVariableValue(envVar.getKey()), envVar.getValue());
			processed.add(envVar.getKey());
		}

		Assert.assertEquals(processed.size(), EnvironmentUtils.getVariablesNames().size());
	}

}
