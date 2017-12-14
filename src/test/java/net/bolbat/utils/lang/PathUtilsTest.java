package net.bolbat.utils.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link PathUtils} test.
 * 
 * @author Alexandr Bolbat
 */
public class PathUtilsTest {

	/**
	 * Complex test for create path functionality.
	 */
	@Test
	public void createPathTest() {
		Assert.assertEquals("", PathUtils.createPath(""));
		Assert.assertEquals("first/second", PathUtils.createPath("first/", "second"));
		Assert.assertEquals("first/second/", PathUtils.createPath("first/", "/second/"));
		Assert.assertEquals("/first/second/third", PathUtils.createPath("/first/second/", "third"));
		Assert.assertEquals("/first/second/third/four/five/", PathUtils.createPath("/first/second/", "/third", "/four/five/"));
	}

}
