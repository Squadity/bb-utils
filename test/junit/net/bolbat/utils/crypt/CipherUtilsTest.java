package net.bolbat.utils.crypt;

import java.util.Date;

import net.bolbat.utils.crypt.CipherUtils.Algorithm;
import net.bolbat.utils.test.CommonTester;

import org.apache.commons.codec.DecoderException;
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
	private static final Logger LOGGER = Logger.getLogger(CipherUtilsTest.class);

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
		try {
			CipherUtils.decode(Algorithm.BLOWFISH, "not-valid-hex", "mock", "mock");
			Assert.fail("Exception shold be thrown before this step.");
		} catch (CipherRuntimeException e) {
			Assert.assertTrue("Exception should be there.", e.getCause() instanceof DecoderException);
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
		} catch (CipherRuntimeException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("algorithm[wrong]"));
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

		// algorithm: DES
		try {
			Algorithm.DES.crateKey(null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("value"));
		}
		try {
			Algorithm.DES.crateKey("mock", null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("salt"));
		}

		// algorithm: DE_SEDE
		try {
			Algorithm.DE_SEDE.crateKey(null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("value"));
		}
		try {
			Algorithm.DE_SEDE.crateKey("mock", null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("salt"));
		}

		// algorithm: BLOWFISH
		try {
			Algorithm.BLOWFISH.crateKey(null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("value"));
		}
		try {
			Algorithm.BLOWFISH.crateKey("mock", null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("salt"));
		}
		try {
			Algorithm.BLOWFISH.encode(new byte[1], null, null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("key"));
		}
		try {
			Algorithm.BLOWFISH.encode(new byte[1], "mock", null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("salt"));
		}
		try {
			Algorithm.BLOWFISH.decode(new byte[1], null, null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("key"));
		}
		try {
			Algorithm.BLOWFISH.decode(new byte[1], "mock", null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("salt"));
		}

		// algorithm: PBE_WITH_MD5_AND_DES
		try {
			Algorithm.PBE_WITH_MD5_AND_DES.crateKey(null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("value"));
		}
		try {
			Algorithm.PBE_WITH_MD5_AND_DES.crateKey("mock", null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("salt"));
		}
		try {
			Algorithm.PBE_WITH_MD5_AND_DES.encode(new byte[1], null, null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("key"));
		}
		try {
			Algorithm.PBE_WITH_MD5_AND_DES.encode(new byte[1], "mock", null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("salt"));
		}
		try {
			Algorithm.PBE_WITH_MD5_AND_DES.decode(new byte[1], null, null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("key"));
		}
		try {
			Algorithm.PBE_WITH_MD5_AND_DES.decode(new byte[1], "mock", null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("salt"));
		}

		// algorithm: PBE_WITH_SHA1_AND_DE_SEDE
		try {
			Algorithm.PBE_WITH_SHA1_AND_DE_SEDE.crateKey(null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("value"));
		}
		try {
			Algorithm.PBE_WITH_SHA1_AND_DE_SEDE.crateKey("mock", null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("salt"));
		}
		try {
			Algorithm.PBE_WITH_SHA1_AND_DE_SEDE.encode(new byte[1], null, null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("key"));
		}
		try {
			Algorithm.PBE_WITH_SHA1_AND_DE_SEDE.encode(new byte[1], "mock", null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("salt"));
		}
		try {
			Algorithm.PBE_WITH_SHA1_AND_DE_SEDE.decode(new byte[1], null, null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("key"));
		}
		try {
			Algorithm.PBE_WITH_SHA1_AND_DE_SEDE.decode(new byte[1], "mock", null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("salt"));
		}

		// algorithm: PBE_WITH_SHA1_AND_RC2_40
		try {
			Algorithm.PBE_WITH_SHA1_AND_RC2_40.crateKey(null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("value"));
		}
		try {
			Algorithm.PBE_WITH_SHA1_AND_RC2_40.crateKey("mock", null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("salt"));
		}
		try {
			Algorithm.PBE_WITH_SHA1_AND_RC2_40.encode(new byte[1], null, null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("key"));
		}
		try {
			Algorithm.PBE_WITH_SHA1_AND_RC2_40.encode(new byte[1], "mock", null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("salt"));
		}
		try {
			Algorithm.PBE_WITH_SHA1_AND_RC2_40.decode(new byte[1], null, null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("key"));
		}
		try {
			Algorithm.PBE_WITH_SHA1_AND_RC2_40.decode(new byte[1], "mock", null, null);
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
		CommonTester.checkExceptionInstantiation(CipherRuntimeException.class);

		Assert.assertEquals(Algorithm.BLOWFISH, Algorithm.get(Algorithm.BLOWFISH.getAlgorithmName()));
		Assert.assertEquals(Algorithm.PBE_WITH_MD5_AND_DES, Algorithm.get(Algorithm.PBE_WITH_MD5_AND_DES.getAlgorithmName()));

		Assert.assertArrayEquals(CipherUtils.DEFAULT_KEY_SUFFIX.getBytes(CipherUtils.DEFAULT_CHARSET), Algorithm.toRawKey(null, null));
		Assert.assertArrayEquals(CipherUtils.DEFAULT_KEY_SUFFIX.getBytes(CipherUtils.DEFAULT_CHARSET), Algorithm.toRawKey("", ""));

		final String salt = "123";
		Assert.assertArrayEquals((CipherUtils.DEFAULT_KEY_SUFFIX + salt).getBytes(CipherUtils.DEFAULT_CHARSET),
				Algorithm.toRawKey(CipherUtils.DEFAULT_KEY_SUFFIX, salt));

		Assert.assertEquals(CipherUtils.EMPTY_STRING, CipherUtils.encode(Algorithm.BLOWFISH, null, null, null));
		Assert.assertEquals(CipherUtils.EMPTY_STRING, CipherUtils.encode(Algorithm.BLOWFISH, "", null, null));
		Assert.assertEquals(CipherUtils.EMPTY_STRING, CipherUtils.encode(Algorithm.BLOWFISH, "     ", null, null));
		Assert.assertEquals(CipherUtils.EMPTY_STRING, CipherUtils.decode(Algorithm.BLOWFISH, null, null, null));
		Assert.assertEquals(CipherUtils.EMPTY_STRING, CipherUtils.decode(Algorithm.BLOWFISH, "", null, null));
		Assert.assertEquals(CipherUtils.EMPTY_STRING, CipherUtils.decode(Algorithm.BLOWFISH, "     ", null, null));

		Assert.assertEquals(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.BLOWFISH.encode(null, null, null, null));
		Assert.assertEquals(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.BLOWFISH.encode(new byte[0], null, null, null));
		Assert.assertEquals(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.BLOWFISH.decode(null, null, null, null));
		Assert.assertEquals(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.BLOWFISH.decode(new byte[0], null, null, null));

		Assert.assertEquals(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_MD5_AND_DES.encode(null, null, null, null));
		Assert.assertEquals(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_MD5_AND_DES.encode(new byte[0], null, null, null));
		Assert.assertEquals(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_MD5_AND_DES.decode(null, null, null, null));
		Assert.assertEquals(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_MD5_AND_DES.decode(new byte[0], null, null, null));

		Assert.assertEquals(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_SHA1_AND_DE_SEDE.encode(null, null, null, null));
		Assert.assertEquals(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_SHA1_AND_DE_SEDE.encode(new byte[0], null, null, null));
		Assert.assertEquals(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_SHA1_AND_DE_SEDE.decode(null, null, null, null));
		Assert.assertEquals(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_SHA1_AND_DE_SEDE.decode(new byte[0], null, null, null));

		Assert.assertEquals(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_SHA1_AND_RC2_40.encode(null, null, null, null));
		Assert.assertEquals(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_SHA1_AND_RC2_40.encode(new byte[0], null, null, null));
		Assert.assertEquals(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_SHA1_AND_RC2_40.decode(null, null, null, null));
		Assert.assertEquals(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_SHA1_AND_RC2_40.decode(new byte[0], null, null, null));
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
