package net.bolbat.utils.crypt;

import java.util.Date;

import net.bolbat.utils.crypt.DigestUtils.Algorithm;
import net.bolbat.utils.test.CommonTester;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * {@link DigestUtils} test.
 * 
 * @author Alexandr Bolbat
 */
public class DigestUtilsTest {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(DigestUtilsTest.class);

	/**
	 * General test.
	 */
	@Test
	public void generalTest() {
		final String salt = "1234567890";
		final long timestamp = System.currentTimeMillis();
		final String value = "Hello world, current date and time: " + new Date(timestamp) + ", timestamp: " + timestamp;

		LOGGER.info("---------> Parameters <---------");
		LOGGER.info("salt[" + salt + "], value[" + value + "]");
		algorithmTest(Algorithm.MD2, salt, value);
		algorithmTest(Algorithm.MD5, salt, value);
		algorithmTest(Algorithm.SHA_1, salt, value);
		algorithmTest(Algorithm.SHA_256, salt, value);
		algorithmTest(Algorithm.SHA_384, salt, value);
		algorithmTest(Algorithm.SHA_512, salt, value);
	}

	/**
	 * Error cases test.
	 */
	@Test
	public void errorCasesTest() {
		// digesting
		try {
			DigestUtils.digest(null, null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("algorithm"));
		}
		try {
			DigestUtils.digest(Algorithm.MD2, "mock", null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("salt"));
		}

		// algorithm enumeration
		try {
			Algorithm.get(null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("aAlgorithmName"));
		}
		try {
			Algorithm.get("wrong");
			Assert.fail("Exception shold be thrown before this step.");
		} catch (DigestRuntimeException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("algorithm[wrong]"));
		}
		try {
			Algorithm.MD2.digest(new byte[1], null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("salt"));
		}
	}

	/**
	 * Cases mostly for good code coverage.
	 */
	@Test
	public void coverageTest() {
		CommonTester.checkExceptionInstantiation(DigestRuntimeException.class);

		Assert.assertEquals(Algorithm.MD2, Algorithm.get(Algorithm.MD2.getAlgorithmName()));
		Assert.assertEquals(Algorithm.SHA_1, Algorithm.get(Algorithm.SHA_1.getAlgorithmName()));
		Assert.assertEquals(Algorithm.SHA_512, Algorithm.get(Algorithm.SHA_512.getAlgorithmName()));

		Assert.assertEquals(CipherUtils.EMPTY_STRING, DigestUtils.digest(Algorithm.MD2, null, null));
		Assert.assertEquals(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.MD2.digest(null, null));
		Assert.assertEquals(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.MD2.digest(new byte[0], null));
	}

	/**
	 * Real algorithm test.
	 * 
	 * @param a
	 *            algorithm
	 * @param salt
	 *            secure salt
	 * @param value
	 *            value to digest
	 */
	private static void algorithmTest(final Algorithm a, final String salt, final String value) {
		LOGGER.info("---------> " + a.getAlgorithmName() + " <---------");

		long startTimestamp = System.currentTimeMillis();
		String digest1 = DigestUtils.digest(a, value, salt);
		LOGGER.info("digest1[" + digest1 + "], time[" + (System.currentTimeMillis() - startTimestamp) + "ms]");
		Assert.assertNotNull(digest1);
		Assert.assertNotEquals(value, digest1);

		String digest2 = DigestUtils.digest(a, value, salt);
		LOGGER.info("digest2[" + digest2 + "], time[" + (System.currentTimeMillis() - startTimestamp) + "ms]");
		Assert.assertNotNull(digest2);
		Assert.assertNotEquals(value, digest2);

		Assert.assertEquals(digest1, digest2);

		String digestWithDoubleSalt = DigestUtils.digest(a, value, salt + salt);
		LOGGER.info("digestWithDoubleSalt[" + digestWithDoubleSalt + "], time[" + (System.currentTimeMillis() - startTimestamp) + "ms]");
		Assert.assertNotNull(digestWithDoubleSalt);
		Assert.assertNotEquals(value, digestWithDoubleSalt);
		Assert.assertNotEquals(digest1, digestWithDoubleSalt);
		Assert.assertNotEquals(digest2, digestWithDoubleSalt);
	}

}
