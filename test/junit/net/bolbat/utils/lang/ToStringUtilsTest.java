package net.bolbat.utils.lang;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link ToStringUtils} test.
 * 
 * @author Alexandr Bolbat
 */
public class ToStringUtilsTest {

	/**
	 * Complex utility test with arrays.
	 */
	@Test
	public void complexArraysTest() {
		final Object[] nullArray = null;
		Assert.assertEquals("[]", ToStringUtils.toString(nullArray));
		Assert.assertEquals("[1]", ToStringUtils.toString(new Integer[] {1}));
		Assert.assertEquals("[1,2,3,4,5]", ToStringUtils.toString(new Integer[] {1, 2, 3, 4, 5}));
		Assert.assertEquals("[1,2,3,4,5,6,7,8,9,10,...]", ToStringUtils.toString(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}));
		Assert.assertEquals("[]", ToStringUtils.toString(nullArray, 0));
		Assert.assertEquals("[...]", ToStringUtils.toString(new Integer[] {1, 2, 3, 4, 5}, 0));
		Assert.assertEquals("[1,2]", ToStringUtils.toString(new Integer[] {1, 2}, 5));
		Assert.assertEquals("[1,2,3,4,5,...]", ToStringUtils.toString(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}, 5));
	}

	/**
	 * Complex utility test with collections.
	 */
	@Test
	public void complexCollectionsTest() {
		final Collection<Object> nullCollection = null;
		Assert.assertEquals("[]", ToStringUtils.toString(nullCollection));
		Assert.assertEquals("[1]", ToStringUtils.toString(Arrays.asList(1)));
		Assert.assertEquals("[1,2,3,4,5]", ToStringUtils.toString(Arrays.asList(1, 2, 3, 4, 5)));
		Assert.assertEquals("[1,2,3,4,5,6,7,8,9,10,...]", ToStringUtils.toString(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)));
		Assert.assertEquals("[]", ToStringUtils.toString(nullCollection, 0));
		Assert.assertEquals("[...]", ToStringUtils.toString(Arrays.asList(1, 2, 3, 4, 5), 0));
		Assert.assertEquals("[1,2]", ToStringUtils.toString(Arrays.asList(1, 2), 5));
		Assert.assertEquals("[1,2,3,4,5,...]", ToStringUtils.toString(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), 5));
	}

}
