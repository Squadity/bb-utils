package net.bolbat.utils.crypt;

import java.util.Date;

import net.bolbat.utils.crypt.CipherUtils.Algorithm;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * {@link CipherUtils} test.
 * 
 * @author Alexandr Bolbat
 */
public class CipherUtilsTest {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(CipherUtils.class);

	/**
	 * General test.
	 */
	@Test
	public void generalTest() {
		final String key = "qweasdzxc";
		final String salt = "1234567890";
		final long timestamp = System.currentTimeMillis();
		final String value = "Hello world, current date and time: " + new Date(timestamp) + ", timestamp: " + timestamp;

		LOGGER.info("---------> Parameters <---------");
		LOGGER.info("key[" + key + "], salt[" + salt + "], value[" + value + "]");
		algorithmTest(Algorithm.AES, key, salt, value);
		algorithmTest(Algorithm.ARCFOUR, key, salt, value);
		algorithmTest(Algorithm.BLOWFISH, key, salt, value);
		algorithmTest(Algorithm.DES, key, salt, value);
		algorithmTest(Algorithm.DE_SEDE, key, salt, value);
		algorithmTest(Algorithm.RC2, key, salt, value);
		algorithmTest(Algorithm.PBE_WITH_MD5_AND_DES, key, salt, value);
		algorithmTest(Algorithm.PBE_WITH_SHA1_AND_DE_SEDE, key, salt, value);
		algorithmTest(Algorithm.PBE_WITH_SHA1_AND_RC2_40, key, salt, value);
	}

	/**
	 * Error cases test.
	 */
	@Test
	public void errorCasesTest() {
		// encoding
		try {
			CipherUtils.encode(null, "mock", null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("algorithm"));
		}
		try {
			CipherUtils.encode(Algorithm.BLOWFISH, "mock", null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("key"));
		}
		try {
			CipherUtils.encode(Algorithm.BLOWFISH, "mock", "mock", null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("salt"));
		}

		// decoding
		try {
			CipherUtils.decode(null, "mock", null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("algorithm"));
		}
		try {
			CipherUtils.decode(Algorithm.BLOWFISH, "mock", null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("key"));
		}
		try {
			CipherUtils.decode(Algorithm.BLOWFISH, "mock", "mock", null);
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
			Algorithm.generateSecretKeySpec(null, null, null, 0, 0);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("value"));
		}
		try {
			Algorithm.generateSecretKeySpec("value", null, null, 0, 0);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("salt"));
		}
		try {
			Algorithm.generateSecretKeySpec("value", "salt", null, 0, 0);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("name"));
		}
	}

	/**
	 * Real algorithm test.
	 * 
	 * @param a
	 *            algorithm
	 * @param key
	 *            raw key value
	 * @param salt
	 *            secure key salt
	 * @param value
	 *            value to encode and decode
	 */
	private static void algorithmTest(final Algorithm a, final String key, final String salt, final String value) {
		LOGGER.info("---------> " + a.getAlgorithmName() + " <---------");

		long startTimestamp = System.currentTimeMillis();
		String encodedValue = CipherUtils.encode(a, value, key, salt);
		LOGGER.info("encoded[" + encodedValue + "], time[" + (System.currentTimeMillis() - startTimestamp) + "ms]");
		Assert.assertNotNull(encodedValue);
		Assert.assertNotEquals(value, encodedValue);

		startTimestamp = System.currentTimeMillis();
		String decodedValue = CipherUtils.decode(a, encodedValue, key, salt);
		LOGGER.info("decoded[" + decodedValue + "], time[" + (System.currentTimeMillis() - startTimestamp) + "ms]");
		Assert.assertNotNull(encodedValue);
		Assert.assertNotEquals(encodedValue, decodedValue);
		Assert.assertEquals(value, decodedValue);
	}

}
