package net.bolbat.utils.crypt;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.bolbat.utils.crypt.DigestUtils.Algorithm;
import net.bolbat.utils.test.CommonTester;

/**
 * {@link DigestUtils} test.
 * 
 * @author Alexandr Bolbat
 */
public class DigestUtilsTest {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DigestUtilsTest.class);

	/**
	 * Encoding test with salt.
	 */
	@Test
	public void encodingWithSalt() {
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
	 * Encoding test with salt.
	 */
	@Test
	public void encodingWithoutSalt() {
		final String value = "Hello world, current date and time: Tue Oct 27 21:48:40 EET 2015, timestamp: 1445975320585";

		LOGGER.info("---------> Parameters <---------");
		LOGGER.info("value[" + value + "]");
		algorithmTestWithoutSalt(Algorithm.MD2, "2ac625b7de632a614931fe127af9c287", value);
		algorithmTestWithoutSalt(Algorithm.MD5, "487e0fea9972c40b1b59db5f1e2bdedf", value);
		algorithmTestWithoutSalt(Algorithm.SHA_1, "efb7dbca3ee691fd11c3f3e2690253bc9c8866a0", value);
		algorithmTestWithoutSalt(Algorithm.SHA_256, "6e46c16e4de2a281c78f247f9aa6e139c461ec89e8ef4c9c7bb260ddd181f072", value);
		algorithmTestWithoutSalt(Algorithm.SHA_384, "5964fa265d18ad1d4fc9d9a1416eeb22ab4019310050d76112fe2a62f267fa376ce283264c5f1029242aaaf228677a26", value);
		algorithmTestWithoutSalt(Algorithm.SHA_512,
				"ac56bc259f71ee16a5f171aed1b6312248b37aacbbe53a593fcf57b633d7432806a94738952db65433f296b486075c23aebdfd2079b47cb224cf310cd5f5dfea", value);
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
		final String digest1 = DigestUtils.digest(a, value, salt);
		LOGGER.info("first[" + digest1 + "], time[" + (System.currentTimeMillis() - startTimestamp) + "ms]");
		Assert.assertNotNull(digest1);
		Assert.assertNotEquals(value, digest1);

		startTimestamp = System.currentTimeMillis();
		final String digest2 = DigestUtils.digest(a, value, salt);
		LOGGER.info("second[" + digest2 + "], time[" + (System.currentTimeMillis() - startTimestamp) + "ms]");
		Assert.assertNotNull(digest2);
		Assert.assertNotEquals(value, digest2);

		Assert.assertEquals(digest1, digest2);

		startTimestamp = System.currentTimeMillis();
		final String digestWithDoubleSalt = DigestUtils.digest(a, value, salt + salt);
		LOGGER.info("thirdWithDoubleSalt[" + digestWithDoubleSalt + "], time[" + (System.currentTimeMillis() - startTimestamp) + "ms]");
		Assert.assertNotNull(digestWithDoubleSalt);
		Assert.assertNotEquals(value, digestWithDoubleSalt);
		Assert.assertNotEquals(digest1, digestWithDoubleSalt);
		Assert.assertNotEquals(digest2, digestWithDoubleSalt);
	}

	/**
	 * Real algorithm test.
	 * 
	 * @param a
	 *            algorithm
	 * @param expected
	 *            expected value after encoding
	 * @param value
	 *            value to digest
	 */
	private static void algorithmTestWithoutSalt(final Algorithm a, final String expected, final String value) {
		LOGGER.info("---------> " + a.getAlgorithmName() + " <---------");

		long startTimestamp = System.currentTimeMillis();
		final String digest1 = DigestUtils.digest(a, value);
		LOGGER.info("first[" + digest1 + "], time[" + (System.currentTimeMillis() - startTimestamp) + "ms]");
		Assert.assertNotNull(digest1);
		Assert.assertNotEquals(value, digest1);
		Assert.assertEquals(expected, digest1);

		startTimestamp = System.currentTimeMillis();
		final String digest2 = DigestUtils.digest(a, value);
		LOGGER.info("second[" + digest2 + "], time[" + (System.currentTimeMillis() - startTimestamp) + "ms]");
		Assert.assertNotNull(digest2);
		Assert.assertNotEquals(value, digest2);
		Assert.assertEquals(expected, digest2);

		Assert.assertEquals(digest1, digest2);
	}

}
