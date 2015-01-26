package net.bolbat.utils.lang;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
		Assert.assertEquals("[1]", ToStringUtils.toString(new Integer[] { 1 }));
		Assert.assertEquals("[1,2,3,4,5]", ToStringUtils.toString(new Integer[] { 1, 2, 3, 4, 5 }));
		Assert.assertEquals("[1,2,3,4,5,6,7,8,9,10,...]", ToStringUtils.toString(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }));
		Assert.assertEquals("[]", ToStringUtils.toString(nullArray, 0));
		Assert.assertEquals("[...]", ToStringUtils.toString(new Integer[] { 1, 2, 3, 4, 5 }, 0));
		Assert.assertEquals("[1,2]", ToStringUtils.toString(new Integer[] { 1, 2 }, 5));
		Assert.assertEquals("[1,2,3,4,5,...]", ToStringUtils.toString(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }, 5));
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

	/**
	 * Complex utility test with maps.
	 */
	@Test
	public void complexMapsTest() {
		final Map<Object, Object> nullMap = null;
		Assert.assertEquals("[]", ToStringUtils.toString(nullMap));
		Assert.assertEquals("[]", ToStringUtils.toString(nullMap, 0));

		final Map<Object, Object> map = new HashMap<>();
		map.put("key1", "value1");
		Assert.assertEquals("[key1|value1]", ToStringUtils.toString(map));
		Assert.assertEquals("[...]", ToStringUtils.toString(map, 0));

		map.put("key2", "value2");
		map.put("key3", "value3");
		map.put("list1", Arrays.asList(1, 2, 3));
		Assert.assertTrue(ToStringUtils.toString(map).contains("key1|value1"));
		Assert.assertTrue(ToStringUtils.toString(map).contains("key2|value2"));
		Assert.assertTrue(ToStringUtils.toString(map).contains("key3|value3"));
		Assert.assertTrue(ToStringUtils.toString(map).contains("list1|[1,2,3]"));

		map.put("list2", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
		Assert.assertTrue(ToStringUtils.toString(map).contains("list2|[1,2,3,4,5,6,7,8,9,10,...]"));
		Assert.assertFalse(ToStringUtils.toString(map, 12).contains("list2|[1,2,3,4,5,6,7,8,9,10,...]"));

		final Map<Object, Object> bigMap = new HashMap<>();
		for (int i = 0; i < 15; i++)
			bigMap.put("key" + i, "value" + i);

		Assert.assertTrue(ToStringUtils.toString(bigMap).contains("...]"));
		Assert.assertFalse(ToStringUtils.toString(bigMap, 15).contains("...]"));
	}
}
