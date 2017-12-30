package net.bolbat.utils.lang;

import org.junit.Assert;
import org.junit.Test;

import net.bolbat.utils.annotation.Mark.ToDo;

/**
 * {@link ArrayUtils} test.
 * 
 * @author Alexandr Bolbat
 */
@ToDo({ "clone methods", "addAll methods" })
public class ArrayUtilsTest {

	@Test
	public void concatenationTest() {
		// initial data
		final String element0 = "zero";
		final String element1 = "one";
		final String element2 = "two";
		final String[] elements = new String[] { element1, element2 };

		// element + array
		String[] result = ArrayUtils.concat(element0, elements);
		Assert.assertNotNull(result);
		Assert.assertEquals(3, result.length);
		Assert.assertEquals(element0, result[0]);
		Assert.assertEquals(element1, result[1]);
		Assert.assertEquals(element2, result[2]);

		// array + element
		result = ArrayUtils.concat(elements, element0);
		Assert.assertNotNull(result);
		Assert.assertEquals(3, result.length);
		Assert.assertEquals(element1, result[0]);
		Assert.assertEquals(element2, result[1]);
		Assert.assertEquals(element0, result[2]);

		// array + array
		result = ArrayUtils.concat(elements, elements);
		Assert.assertNotNull(result);
		Assert.assertEquals(4, result.length);
		Assert.assertEquals(element1, result[0]);
		Assert.assertEquals(element2, result[1]);
		Assert.assertEquals(element1, result[2]);
		Assert.assertEquals(element2, result[3]);
	}

}
