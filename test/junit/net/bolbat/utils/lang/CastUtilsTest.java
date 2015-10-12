package net.bolbat.utils.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link CastUtils} test.
 * 
 * @author Alexandr Bolbat
 */
public class CastUtilsTest {

	/**
	 * Complex test.
	 */
	@Test
	public void complexTest() {
		Assert.assertNull(CastUtils.castList(null));
		Assert.assertNull(CastUtils.castSet(null));
		Assert.assertNull(CastUtils.castMap(null));

		Object rawList = new ArrayList<String>();
		Object rawSet = new HashSet<String>();
		Object rawMap = new HashMap<String, String>();

		final List<String> list = CastUtils.castList(rawList);
		Assert.assertNotNull(list);
		Assert.assertSame(rawList, list);

		final Set<String> set = CastUtils.castSet(rawSet);
		Assert.assertNotNull(set);
		Assert.assertSame(rawSet, set);

		final Map<String, String> map = CastUtils.castMap(rawMap);
		Assert.assertNotNull(map);
		Assert.assertSame(rawMap, map);
	}

	/**
	 * Error cases test.
	 */
	@Test
	public void errorsTest() {
		try {
			final Object expected = new ArrayList<String>();
			final Set<String> actual = CastUtils.castSet(expected);
			Assert.fail("Should fail, expected[" + expected.getClass() + "], actual[" + actual.getClass() + "]");
		} catch (final RuntimeException e) {
			Assert.assertEquals(ClassCastException.class, e.getClass());
		}

		try {
			final Object expected = new HashSet<String>();
			final List<String> actual = CastUtils.castList(expected);
			Assert.fail("Should fail, expected[" + expected.getClass() + "], actual[" + actual.getClass() + "]");
		} catch (final RuntimeException e) {
			Assert.assertEquals(ClassCastException.class, e.getClass());
		}

		try {
			final Object expected = new HashSet<String>();
			final Map<String, String> actual = CastUtils.castMap(expected);
			Assert.fail("Should fail, expected[" + expected.getClass() + "], actual[" + actual.getClass() + "]");
		} catch (final RuntimeException e) {
			Assert.assertEquals(ClassCastException.class, e.getClass());
		}
	}

}
