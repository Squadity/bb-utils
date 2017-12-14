package net.bolbat.utils.io;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link FSUtils} test.
 * 
 * @author Alexandr Bolbat
 */
public class FSUtilsTest {

	@Test
	public void getTmpFolderForClass() {
		final String expectedSubPath = "/net.bolbat.utils.io.FSUtilsTest/";
		final String path = FSUtils.getTmpFolder(getClass());
		Assert.assertNotNull(path);
		Assert.assertNotEquals(expectedSubPath, path);
		Assert.assertTrue(path.endsWith(expectedSubPath));

		final Class<?> nullClass = null;
		final String nullClassPath = FSUtils.getTmpFolder(nullClass);
		final String rootTmpPath = FSUtils.getTmpFolder();
		Assert.assertEquals(rootTmpPath, nullClassPath);
	}

	@Test
	public void getTmpFolder() {
		final String path = FSUtils.getTmpFolder();
		Assert.assertNotNull(path);
		Assert.assertTrue(path.length() > 0);
	}

	@Test
	public void checkForDoubleFileSeparators() {
		final String expectedSubPath = "/some/folder/and/sub/folder/";
		final String path = FSUtils.getTmpFolder("//some/folder", "and//sub/folder//");
		Assert.assertNotNull(path);
		Assert.assertNotEquals(expectedSubPath, path);
		Assert.assertTrue(path.endsWith(expectedSubPath));
		Assert.assertTrue(!path.contains(File.separator + File.separator));
	}

}
