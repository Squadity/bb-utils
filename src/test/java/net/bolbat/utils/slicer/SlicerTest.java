package net.bolbat.utils.slicer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import net.bolbat.test.utils.CommonTester;

/**
 * {@link Slicer} utility test.
 * 
 * @author Alexandr Bolbat
 */
public class SlicerTest {

	/**
	 * Check that utility is protected from instantiation.
	 */
	@Test
	public void protectedUtilityInstantiation() {
		CommonTester.checkNotInstantiableDefaultConstructor(Slicer.class);
		CommonTester.checkNotInstantiableDefaultConstructor(Slicer.class, IllegalAccessError.class);
	}

	/**
	 * Testing slicing.
	 */
	@Test
	public void testSlicing() {
		List<String> originalList = new ArrayList<>();

		// checking default values on wrong arguments
		Assert.assertEquals(0, Slicer.slice(null, 1, 10).size());
		Assert.assertEquals(0, Slicer.slice(originalList, -1, -10).size());

		// checking on empty original list
		Assert.assertEquals(0, Slicer.slice(originalList, 1, 10).size());

		// checking with different parameters
		originalList.add("test1");
		Assert.assertEquals(0, Slicer.slice(originalList, 1, 0).size());
		Assert.assertEquals(0, Slicer.slice(originalList, 0, 0).size());
		Assert.assertEquals(0, Slicer.slice(originalList, 2, 10).size());

		Assert.assertEquals(1, Slicer.slice(originalList, 0, 10).size());
		Assert.assertEquals("test1", Slicer.slice(originalList, 0, 10).get(0));

		Assert.assertEquals(1, Slicer.slice(originalList, 0, 10).size());
		Assert.assertEquals("test1", Slicer.slice(originalList, 0, 10).get(0));

		originalList.add("test2");
		originalList.add("test3");
		Assert.assertEquals(0, Slicer.slice(originalList, 1, 0).size());
		Assert.assertEquals(0, Slicer.slice(originalList, 0, 0).size());

		Assert.assertEquals(2, Slicer.slice(originalList, 1, 10).size());
		Assert.assertEquals("test2", Slicer.slice(originalList, 1, 10).get(0));
		Assert.assertEquals("test3", Slicer.slice(originalList, 1, 10).get(1));

		Assert.assertEquals(3, Slicer.slice(originalList, 0, 10).size());
		Assert.assertEquals("test1", Slicer.slice(originalList, 0, 10).get(0));
		Assert.assertEquals("test2", Slicer.slice(originalList, 0, 10).get(1));
		Assert.assertEquals("test3", Slicer.slice(originalList, 0, 10).get(2));

		Assert.assertEquals(3, Slicer.slice(originalList, 0, 10).size());
		Assert.assertEquals("test1", Slicer.slice(originalList, 0, 10).get(0));
		Assert.assertEquals("test2", Slicer.slice(originalList, 0, 10).get(1));
		Assert.assertEquals("test3", Slicer.slice(originalList, 0, 10).get(2));

		Assert.assertEquals(1, Slicer.slice(originalList, 2, 1).size());
		Assert.assertEquals("test2", Slicer.slice(originalList, 1, 1).get(0));
	}

	/**
	 * Testing paging.
	 */
	@Test
	public void testPaging() {
		List<String> originalList = new ArrayList<>();

		// checking default values on wrong arguments
		Assert.assertEquals(0, Slicer.sliceTo(null, 1, 10).size());
		Assert.assertEquals(0, Slicer.sliceTo(originalList, 1, 1).size());

		// checking on empty original list
		Assert.assertEquals(0, Slicer.sliceTo(originalList, 1, 10).size());

		// checking with different parameters
		originalList.add("test1");
		Assert.assertEquals(1, Slicer.sliceTo(originalList, 0, 1).size());
		Assert.assertEquals("test1", Slicer.sliceTo(originalList, 0, 1).get(0));
		Assert.assertEquals(0, Slicer.sliceTo(originalList, 1, 1).size());
		Assert.assertEquals(0, Slicer.sliceTo(originalList, 1, 10).size());

		Assert.assertEquals(1, Slicer.sliceTo(originalList, 0, 10).size());
		Assert.assertEquals("test1", Slicer.sliceTo(originalList, 0, 10).get(0));

		originalList.add("test2");
		originalList.add("test3");
		Assert.assertEquals(0, Slicer.sliceTo(originalList, 1, 0).size());
		Assert.assertEquals(0, Slicer.sliceTo(originalList, 0, 0).size());

		Assert.assertEquals(0, Slicer.sliceTo(originalList, 2, 10).size());

		Assert.assertEquals(1, Slicer.sliceTo(originalList, 2, 1).size());
		Assert.assertEquals("test3", Slicer.sliceTo(originalList, 2, 1).get(0));

		Assert.assertEquals(3, Slicer.sliceTo(originalList, 0, 10).size());
		Assert.assertEquals("test1", Slicer.sliceTo(originalList, 0, 10).get(0));
		Assert.assertEquals("test2", Slicer.sliceTo(originalList, 0, 10).get(1));
		Assert.assertEquals("test3", Slicer.sliceTo(originalList, 0, 10).get(2));

		Assert.assertEquals(1, Slicer.sliceTo(originalList, 1, 2).size());
		Assert.assertEquals("test3", Slicer.sliceTo(originalList, 1, 2).get(0));
	}

	/**
	 * Testing pages.
	 */
	@Test
	public void testPages() {
		final List<String> list = new ArrayList<>();
		for (int i = 0; i < 4999; i++)
			list.add(String.valueOf(i));

		final int perPage = 1000;
		// first page
		List<String> slice = Slicer.sliceTo(list, 0, perPage);
		Assert.assertEquals(perPage, slice.size());
		for (int v = 0; v < 999; v++)
			Assert.assertEquals(String.valueOf(v), slice.get(v));
		// second page
		slice = Slicer.sliceTo(list, 1, perPage);
		Assert.assertEquals(perPage, slice.size());
		for (int v = 0; v < 999; v++)
			Assert.assertEquals(String.valueOf(1000 + v), slice.get(v));
		// third page
		slice = Slicer.sliceTo(list, 2, perPage);
		Assert.assertEquals(perPage, slice.size());
		for (int v = 0; v < 999; v++)
			Assert.assertEquals(String.valueOf(2000 + v), slice.get(v));
		// fourth page
		slice = Slicer.sliceTo(list, 3, perPage);
		Assert.assertEquals(perPage, slice.size());
		for (int v = 0; v < 999; v++)
			Assert.assertEquals(String.valueOf(3000 + v), slice.get(v));
		// fifth page
		slice = Slicer.sliceTo(list, 4, perPage);
		Assert.assertEquals(999, slice.size());
		for (int v = 0; v < 999; v++)
			Assert.assertEquals(String.valueOf(4000 + v), slice.get(v));
	}

	/**
	 * Testing dividing list to sub-lists with elements limit.
	 */
	@Test
	public void testDividing() {
		final List<String> list = new ArrayList<>();
		for (int i = 0; i < 4999; i++)
			list.add(String.valueOf(i));

		final int perPage = 1000;
		final List<List<String>> divided = Slicer.divide(list, perPage);

		Assert.assertEquals(5, divided.size());

		// first page
		List<String> slice = divided.get(0);
		Assert.assertEquals(perPage, slice.size());
		for (int v = 0; v < 999; v++)
			Assert.assertEquals(String.valueOf(v), slice.get(v));
		// second page
		slice = divided.get(1);
		Assert.assertEquals(perPage, slice.size());
		for (int v = 0; v < 999; v++)
			Assert.assertEquals(String.valueOf(1000 + v), slice.get(v));
		// third page
		slice = divided.get(2);
		Assert.assertEquals(perPage, slice.size());
		for (int v = 0; v < 999; v++)
			Assert.assertEquals(String.valueOf(2000 + v), slice.get(v));
		// fourth page
		slice = divided.get(3);
		Assert.assertEquals(perPage, slice.size());
		for (int v = 0; v < 999; v++)
			Assert.assertEquals(String.valueOf(3000 + v), slice.get(v));
		// fifth page
		slice = divided.get(4);
		Assert.assertEquals(999, slice.size());
		for (int v = 0; v < 999; v++)
			Assert.assertEquals(String.valueOf(4000 + v), slice.get(v));

	}

}
