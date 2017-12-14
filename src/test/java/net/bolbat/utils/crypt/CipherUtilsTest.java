package net.bolbat.utils.crypt;

import java.util.Date;

import org.apache.commons.codec.DecoderException;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.bolbat.utils.crypt.CipherUtils.Algorithm;
import net.bolbat.utils.test.CommonTester;

/**
 * {@link CipherUtils} test.
 * 
 * @author Alexandr Bolbat
 */
public class CipherUtilsTest {

	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CipherUtilsTest.class);

	/**
	 * Testing encryption and decryption with secure key and salt.
	 */
	@Test
	public void encryptionWithKeyAndSalt() {
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
	 * Testing encryption and decryption with secure key.
	 */
	@Test
	public void encryptionWithKey() {
		final String key = "qweasdzxc";
		final long timestamp = System.currentTimeMillis();
		final String value = "Hello world, current date and time: " + new Date(timestamp) + ", timestamp: " + timestamp;

		LOGGER.info("---------> Parameters <---------");
		LOGGER.info("key[" + key + "], value[" + value + "]");
		algorithmTest(Algorithm.AES, key, value);
		algorithmTest(Algorithm.ARCFOUR, key, value);
		algorithmTest(Algorithm.BLOWFISH, key, value);
		algorithmTest(Algorithm.DES, key, value);
		algorithmTest(Algorithm.DE_SEDE, key, value);
		algorithmTest(Algorithm.RC2, key, value);
		algorithmTest(Algorithm.PBE_WITH_MD5_AND_DES, key, value);
		algorithmTest(Algorithm.PBE_WITH_SHA1_AND_DE_SEDE, key, value);
		algorithmTest(Algorithm.PBE_WITH_SHA1_AND_RC2_40, key, value);
	}

	/**
	 * Testing decryption with predefined encrypted and decrypted values.
	 */
	@Test
	public void decryptionTest() {
		final String key = "qweasdzxc";
		final String decrypted = "Hello world, current date and time: Wed Oct 28 13:56:02 EET 2015, timestamp: 1446033362960";

		LOGGER.info("---------> Parameters <---------");
		LOGGER.info("key[" + key + "], decrypted[" + decrypted + "]");
		decryptionTest(Algorithm.AES, key,
				"681dc2a02c4c54a2ed59bf5068a64d422ddf701ff706254442c7444ad014da54496c41906307d78e4544b0ddb1d315a23d1f2d5d98b2c95820958e41a3bb7673e28167b84d369500e29224fc7ac78f848ca510478c816e99ed6ad9f903cec1c1",
				decrypted);
		decryptionTest(Algorithm.ARCFOUR, key,
				"28a69710a5a53c3ec68f4070a1a31333cb131ea5906e1fddf5b5658482010221c48d96bce0ebf2f5377a9947ce421d32f6dc55cbf7581ca2aa6f57372177518e67914477d79e6c1228f128dd50c2132fcf64512ba810617423a2",
				decrypted);
		decryptionTest(Algorithm.BLOWFISH, key,
				"012b021072bbc453260a52da97da5c6f94fd9b19003e660bb4f460b828f19e344c042205d2404852ad28415f305630f2a3f02177590891428aebe6f482ba91ce7357851f1381b7e55e88b7c9ba7462ddfeb9b40c83d2731e2c63377b46f7816f",
				decrypted);
		decryptionTest(Algorithm.DES, key,
				"65c530957b8d12fe94a1443c38d3226c48fe33170df7876c89ff0ff5c21b7f0d603c27ce48d9b352593c384fe79dd3ed687ea1f2313187a3081a1f905276b0e224bb062236cc4c442f08f9eb81a177b13d4fe1eeb6f020136fe1a0d493aed684",
				decrypted);
		decryptionTest(Algorithm.DE_SEDE, key,
				"34caabf761e12f2def237ace9ee865fe8875eb5b1750fd9cb8bb292d71fec6627c65d46131cd345258592423a37120d1fe4a5cecdd04036a581364d6c75fe7953fc8a362ff477358883e3cfc7bad71d6864df172a562dd8f325a34a413251d28",
				decrypted);
		decryptionTest(Algorithm.RC2, key,
				"67e4caf634b2f5044b427c33c970782da69a0711e33109eeb256720c166ba891c0b1c34eea5efd9d1dd44eff8629fc5a4eee05dce81f8ec889188f17d1abcf09b38a3eb1bc09d86d8532f9d18e8413bd165b6cf449d17296c9d4392012641522",
				decrypted);
		decryptionTest(Algorithm.PBE_WITH_MD5_AND_DES, key,
				"04a60a38a9857ea5a8b799317c8d7d6fdc5db9118ecb7a3b09dc021a4df806a6ccf10f8e53654fc2198c06831daa46e260265c4a8064a051d9baafe9b3379345c28aa032629c78987c8167ef8fc99db0efd4342c137456d3860479d954f5bd7f",
				decrypted);
		decryptionTest(Algorithm.PBE_WITH_SHA1_AND_DE_SEDE, key,
				"52d3ee3ae080bd7d0aa87c5982bd0a77a3105ebdcba308a05122d7096a16bd640eaecdeaf971089b6e0fc1be9221e6b876855e0e29ba80c3a709ffe3f665b1bf584be6fe4a128d3d3b428af1a3c9c8fb80193dd1e231b534cc7f5a123b1f9de2",
				decrypted);
		decryptionTest(Algorithm.PBE_WITH_SHA1_AND_RC2_40, key,
				"69b4f05696348b1820ad5b5b35dcbb4490631114586d296f44ce753c9f16c9c569638209a0d1f7b565a31bc110b49d54488d399f3976e355d256f7307a3e8ae88e559df616d02afde2f482a02a8a050f161cd1a2478317cfe93b14b612809c6b",
				decrypted);
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
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("name"));
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
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("key"));
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
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("algorithm"));
		}

		// algorithm: DES
		try {
			Algorithm.DES.crateKey(null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("key"));
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
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("key"));
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
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("key"));
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
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("key"));
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
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("key"));
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
			Assert.assertTrue("Exception should be there.", e.getMessage().startsWith("key"));
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

		Assert.assertSame(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.BLOWFISH.encode(null, null, null, null));
		Assert.assertSame(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.BLOWFISH.encode(new byte[0], null, null, null));
		Assert.assertSame(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.BLOWFISH.decode(null, null, null, null));
		Assert.assertSame(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.BLOWFISH.decode(new byte[0], null, null, null));

		Assert.assertSame(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_MD5_AND_DES.encode(null, null, null, null));
		Assert.assertSame(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_MD5_AND_DES.encode(new byte[0], null, null, null));
		Assert.assertSame(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_MD5_AND_DES.decode(null, null, null, null));
		Assert.assertSame(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_MD5_AND_DES.decode(new byte[0], null, null, null));

		Assert.assertSame(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_SHA1_AND_DE_SEDE.encode(null, null, null, null));
		Assert.assertSame(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_SHA1_AND_DE_SEDE.encode(new byte[0], null, null, null));
		Assert.assertSame(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_SHA1_AND_DE_SEDE.decode(null, null, null, null));
		Assert.assertSame(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_SHA1_AND_DE_SEDE.decode(new byte[0], null, null, null));

		Assert.assertSame(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_SHA1_AND_RC2_40.encode(null, null, null, null));
		Assert.assertSame(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_SHA1_AND_RC2_40.encode(new byte[0], null, null, null));
		Assert.assertSame(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_SHA1_AND_RC2_40.decode(null, null, null, null));
		Assert.assertSame(CipherUtils.EMPTY_BYTE_ARRAY, Algorithm.PBE_WITH_SHA1_AND_RC2_40.decode(new byte[0], null, null, null));
	}

	/**
	 * Algorithm encryption test with secure key and salt.
	 * 
	 * @param a
	 *            algorithm
	 * @param key
	 *            secure key
	 * @param salt
	 *            secure salt
	 * @param value
	 *            value to encrypt and decrypt
	 */
	private static void algorithmTest(final Algorithm a, final String key, final String salt, final String value) {
		LOGGER.info("---------> " + a.getAlgorithmName() + " <---------");

		long startTimestamp = System.currentTimeMillis();
		final String encryptedValue = CipherUtils.encode(a, value, key, salt);
		LOGGER.info("encrypted[" + encryptedValue + "], time[" + (System.currentTimeMillis() - startTimestamp) + "ms]");
		Assert.assertNotNull(encryptedValue);
		Assert.assertNotEquals(value, encryptedValue);

		startTimestamp = System.currentTimeMillis();
		final String decryptedValue = CipherUtils.decode(a, encryptedValue, key, salt);
		LOGGER.info("decrypted[" + decryptedValue + "], time[" + (System.currentTimeMillis() - startTimestamp) + "ms]");
		Assert.assertNotNull(encryptedValue);
		Assert.assertNotEquals(encryptedValue, decryptedValue);
		Assert.assertEquals(value, decryptedValue);
	}

	/**
	 * Algorithm encryption test with secure key.
	 * 
	 * @param a
	 *            algorithm
	 * @param key
	 *            secure key
	 * @param value
	 *            value to encrypt and decrypt
	 */
	private static void algorithmTest(final Algorithm a, final String key, final String value) {
		LOGGER.info("---------> " + a.getAlgorithmName() + " <---------");

		long startTimestamp = System.currentTimeMillis();
		final String encryptedValue = CipherUtils.encode(a, value, key);
		LOGGER.info("encrypted[" + encryptedValue + "], time[" + (System.currentTimeMillis() - startTimestamp) + "ms]");
		Assert.assertNotNull(encryptedValue);
		Assert.assertNotEquals(value, encryptedValue);

		startTimestamp = System.currentTimeMillis();
		final String decryptedValue = CipherUtils.decode(a, encryptedValue, key);
		LOGGER.info("decrypted[" + decryptedValue + "], time[" + (System.currentTimeMillis() - startTimestamp) + "ms]");
		Assert.assertNotNull(encryptedValue);
		Assert.assertNotEquals(encryptedValue, decryptedValue);
		Assert.assertEquals(value, decryptedValue);
	}

	/**
	 * Algorithm decryption test.
	 * 
	 * @param a
	 *            algorithm
	 * @param key
	 *            secure key
	 * @param encrypted
	 *            encrypted value
	 * @param expected
	 *            expected decrypted value
	 */
	private static void decryptionTest(final Algorithm a, final String key, final String encrypted, final String expected) {
		LOGGER.info("---------> " + a.getAlgorithmName() + " <---------");

		long startTimestamp = System.currentTimeMillis();
		final String decrypted = CipherUtils.decode(a, encrypted, key);
		LOGGER.info("decrypted[" + decrypted + "], time[" + (System.currentTimeMillis() - startTimestamp) + "ms]");
		Assert.assertNotNull(decrypted);
		Assert.assertNotEquals(encrypted, decrypted);
		Assert.assertEquals(expected, decrypted);
	}

}
