package net.bolbat.utils.crypt;

import static net.bolbat.utils.lang.StringUtils.isNotEmpty;

import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import net.bolbat.utils.lang.StringUtils;

/**
 * {@link Cipher} utilities.
 * 
 * @author Alexandr Bolbat
 */
public final class CipherUtils {

	/**
	 * Default {@link Charset}.
	 */
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	/**
	 * Minimum raw key length.
	 */
	public static final int MINIMUM_KEY_LENGTH = 64;

	/**
	 * Default key suffix.
	 */
	public static final String DEFAULT_KEY_SUFFIX = "0bf741053be36390b1bfb3580b3a2ab565c530957b8d12fe96d658fc5d1152e2";

	/**
	 * Default {@link SecretKeyFactory} algorithm.
	 */
	public static final String DEFAULT_SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA1";

	/**
	 * Default {@link SecretKeyFactory} iterations to generate strong {@link SecretKeySpec}.
	 */
	public static final int DEFAULT_SECRET_KEY_ITERATIONS = 5;

	/**
	 * Default {@link SecretKeySpec} key size.
	 */
	public static final int DEFAULT_SECRET_KEY_SIZE = 128;

	/**
	 * Empty {@link String} constant.
	 */
	public static final String EMPTY_STRING = "";

	/**
	 * Empty byte array constant.
	 */
	public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private CipherUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Encode {@link String}.<br>
	 * Key will be used also as secure salt.
	 * 
	 * @param algorithm
	 *            algorithm
	 * @param value
	 *            {@link String} to encode
	 * @param key
	 *            secure key
	 * @return encoded {@link String}
	 */
	public static String encode(final Algorithm algorithm, final String value, final String key) {
		return encode(algorithm, value, key, key);
	}

	/**
	 * Encode {@link String}.
	 * 
	 * @param algorithm
	 *            algorithm
	 * @param value
	 *            {@link String} to encode
	 * @param key
	 *            secure key
	 * @param salt
	 *            secure salt
	 * @return encoded {@link String}
	 */
	public static String encode(final Algorithm algorithm, final String value, final String key, final String salt) {
		if (algorithm == null)
			throw new IllegalArgumentException("algorithm argument is null.");
		if (StringUtils.isEmpty(value))
			return EMPTY_STRING;
		if (StringUtils.isEmpty(key))
			throw new IllegalArgumentException("key argument is empty.");
		if (StringUtils.isEmpty(salt))
			throw new IllegalArgumentException("salt argument is empty.");

		final byte[] encoded = algorithm.encode(value.getBytes(DEFAULT_CHARSET), key, salt, null);
		return Hex.encodeHexString(encoded);
	}

	/**
	 * Decode {@link String}.<br>
	 * Key will be used also as secure salt.
	 * 
	 * @param algorithm
	 *            algorithm
	 * @param value
	 *            {@link String} to decode
	 * @param key
	 *            secure key
	 * @return decoded {@link String}
	 */
	public static String decode(final Algorithm algorithm, final String value, final String key) {
		return decode(algorithm, value, key, key);
	}

	/**
	 * Decode {@link String}.
	 * 
	 * @param algorithm
	 *            algorithm
	 * @param value
	 *            {@link String} to decode
	 * @param key
	 *            secure key
	 * @param salt
	 *            secure salt
	 * @return decoded {@link String}
	 */
	public static String decode(final Algorithm algorithm, final String value, final String key, final String salt) {
		if (algorithm == null)
			throw new IllegalArgumentException("algorithm argument is null.");
		if (StringUtils.isEmpty(value))
			return EMPTY_STRING;
		if (StringUtils.isEmpty(key))
			throw new IllegalArgumentException("key argument is empty.");
		if (StringUtils.isEmpty(salt))
			throw new IllegalArgumentException("salt argument is empty.");

		try {
			final byte[] decoded = algorithm.decode(Hex.decodeHex(value.toCharArray()), key, salt, null);
			return new String(decoded, DEFAULT_CHARSET);
		} catch (DecoderException e) {
			throw new CipherRuntimeException(e);
		}
	}

	/**
	 * Supported algorithms.
	 * 
	 * @author Alexandr Bolbat
	 */
	public enum Algorithm {

		/**
		 * Advanced Encryption Standard as specified by NIST in FIPS 197.<br>
		 * AES is a 128-bit block cipher supporting keys of 128, 192, and 256 bits.
		 */
		AES("AES"),

		/**
		 * A stream cipher believed to be fully interoperable with the RC4 cipher.
		 */
		ARCFOUR("ARCFOUR"),

		/**
		 * The Blowfish block cipher.
		 */
		BLOWFISH("Blowfish"),

		/**
		 * The Digital Encryption Standard as described in FIPS PUB 46-3.
		 */
		DES("DES") {
			@Override
			public Key crateKey(final String key, final String salt) {
				if (StringUtils.isEmpty(key))
					throw new IllegalArgumentException("key argument is empty.");
				if (StringUtils.isEmpty(salt))
					throw new IllegalArgumentException("salt argument is empty.");

				try {
					final DESKeySpec keyspec = new DESKeySpec(toRawKey(key, salt));
					return SecretKeyFactory.getInstance(getAlgorithmName()).generateSecret(keyspec);
				} catch (final InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException e) {
					throw new CipherRuntimeException(e);
				}
			}
		},

		/**
		 * Triple DES Encryption (also known as DES-EDE, 3DES, or Triple-DES).<br>
		 * Data is encrypted using the DES algorithm three separate times.<br>
		 * It is first encrypted using the first sub-key, then decrypted with the second sub-key, and encrypted with the third sub-key.
		 */
		DE_SEDE("DESede") {
			@Override
			public Key crateKey(final String key, final String salt) {
				if (StringUtils.isEmpty(key))
					throw new IllegalArgumentException("key argument is empty.");
				if (StringUtils.isEmpty(salt))
					throw new IllegalArgumentException("salt argument is empty.");

				try {
					final DESedeKeySpec keyspec = new DESedeKeySpec(toRawKey(key, salt));
					return SecretKeyFactory.getInstance(getAlgorithmName()).generateSecret(keyspec);
				} catch (final InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException e) {
					throw new CipherRuntimeException(e);
				}
			}
		},

		/**
		 * Variable-key-size encryption algorithm.
		 */
		RC2("RC2"),

		/**
		 * The password-based encryption algorithm found in (PKCS5).<br>
		 * Using the specified message digest/pseudo-random function (MD5) and encryption algorithm (DES).
		 */
		PBE_WITH_MD5_AND_DES("PBEWithMD5AndDES") {

			/**
			 * Salt key size.
			 */
			private static final int SALT_KEY_SIZE = 64;

			@Override
			public Key crateKey(final String key, final String salt) {
				if (StringUtils.isEmpty(key))
					throw new IllegalArgumentException("key argument is empty.");
				if (StringUtils.isEmpty(salt))
					throw new IllegalArgumentException("salt argument is empty.");

				try {
					final SecretKey saltKey = generateSecretKeySpec(key, salt, DEFAULT_SECRET_KEY_ALGORITHM, DEFAULT_SECRET_KEY_ITERATIONS, SALT_KEY_SIZE);
					final KeySpec keySpec = new PBEKeySpec(key.toCharArray(), saltKey.getEncoded(), DEFAULT_SECRET_KEY_ITERATIONS);
					return SecretKeyFactory.getInstance(getAlgorithmName()).generateSecret(keySpec);
				} catch (final NoSuchAlgorithmException | InvalidKeySpecException e) {
					throw new CipherRuntimeException(e);
				}
			}

			@Override
			public byte[] encode(final byte[] toEncode, final String key, final String salt, final AlgorithmParameterSpec paramSpec) {
				if (toEncode == null || toEncode.length == 0)
					return EMPTY_BYTE_ARRAY;
				if (StringUtils.isEmpty(key))
					throw new IllegalArgumentException("key argument is empty.");
				if (StringUtils.isEmpty(salt))
					throw new IllegalArgumentException("salt argument is empty.");

				final SecretKey saltKey = generateSecretKeySpec(key, salt, DEFAULT_SECRET_KEY_ALGORITHM, DEFAULT_SECRET_KEY_ITERATIONS, SALT_KEY_SIZE);
				final AlgorithmParameterSpec param = new PBEParameterSpec(saltKey.getEncoded(), DEFAULT_SECRET_KEY_ITERATIONS);
				return super.encode(toEncode, key, salt, param);
			}

			@Override
			public byte[] decode(final byte[] toDecode, final String key, final String salt, final AlgorithmParameterSpec paramSpec) {
				if (toDecode == null || toDecode.length == 0)
					return EMPTY_BYTE_ARRAY;
				if (StringUtils.isEmpty(key))
					throw new IllegalArgumentException("key argument is empty.");
				if (StringUtils.isEmpty(salt))
					throw new IllegalArgumentException("salt argument is empty.");

				final SecretKey saltKey = generateSecretKeySpec(key, salt, DEFAULT_SECRET_KEY_ALGORITHM, DEFAULT_SECRET_KEY_ITERATIONS, SALT_KEY_SIZE);
				final AlgorithmParameterSpec param = new PBEParameterSpec(saltKey.getEncoded(), DEFAULT_SECRET_KEY_ITERATIONS);
				return super.decode(toDecode, key, salt, param);
			}

		},

		/**
		 * The password-based encryption algorithm found in (PKCS5).<br>
		 * Using the specified message digest/pseudo-random function (SHA1) and encryption algorithm (DESede).
		 */
		PBE_WITH_SHA1_AND_DE_SEDE("PBEWithSHA1AndDESede") {
			@Override
			public Key crateKey(final String key, final String salt) {
				if (StringUtils.isEmpty(key))
					throw new IllegalArgumentException("key argument is empty.");
				if (StringUtils.isEmpty(salt))
					throw new IllegalArgumentException("salt argument is empty.");

				try {
					final KeySpec keySpec = new PBEKeySpec(key.toCharArray(), salt.getBytes(DEFAULT_CHARSET), DEFAULT_SECRET_KEY_ITERATIONS);
					return SecretKeyFactory.getInstance(getAlgorithmName()).generateSecret(keySpec);
				} catch (final NoSuchAlgorithmException | InvalidKeySpecException e) {
					throw new CipherRuntimeException(e);
				}
			}

			@Override
			public byte[] encode(final byte[] toEncode, final String key, final String salt, final AlgorithmParameterSpec paramSpec) {
				if (toEncode == null || toEncode.length == 0)
					return EMPTY_BYTE_ARRAY;
				if (StringUtils.isEmpty(key))
					throw new IllegalArgumentException("key argument is empty.");
				if (StringUtils.isEmpty(salt))
					throw new IllegalArgumentException("salt argument is empty.");

				final AlgorithmParameterSpec param = new PBEParameterSpec(salt.getBytes(DEFAULT_CHARSET), DEFAULT_SECRET_KEY_ITERATIONS);
				return super.encode(toEncode, key, salt, param);
			}

			@Override
			public byte[] decode(final byte[] toDecode, final String key, final String salt, final AlgorithmParameterSpec paramSpec) {
				if (toDecode == null || toDecode.length == 0)
					return EMPTY_BYTE_ARRAY;
				if (StringUtils.isEmpty(key))
					throw new IllegalArgumentException("key argument is empty.");
				if (StringUtils.isEmpty(salt))
					throw new IllegalArgumentException("salt argument is empty.");

				final AlgorithmParameterSpec param = new PBEParameterSpec(salt.getBytes(DEFAULT_CHARSET), DEFAULT_SECRET_KEY_ITERATIONS);
				return super.decode(toDecode, key, salt, param);
			}
		},

		/**
		 * The password-based encryption algorithm found in (PKCS5).<br>
		 * Using the specified message digest/pseudo-random function (SHA1) and encryption algorithm (RC2_40).
		 */
		PBE_WITH_SHA1_AND_RC2_40("PBEWithSHA1AndRC2_40") {
			@Override
			public Key crateKey(final String key, final String salt) {
				if (StringUtils.isEmpty(key))
					throw new IllegalArgumentException("key argument is empty.");
				if (StringUtils.isEmpty(salt))
					throw new IllegalArgumentException("salt argument is empty.");

				try {
					final KeySpec keySpec = new PBEKeySpec(key.toCharArray(), salt.getBytes(DEFAULT_CHARSET), DEFAULT_SECRET_KEY_ITERATIONS);
					return SecretKeyFactory.getInstance(getAlgorithmName()).generateSecret(keySpec);
				} catch (final NoSuchAlgorithmException | InvalidKeySpecException e) {
					throw new CipherRuntimeException(e);
				}
			}

			@Override
			public byte[] encode(final byte[] toEncode, final String key, final String salt, final AlgorithmParameterSpec paramSpec) {
				if (toEncode == null || toEncode.length == 0)
					return EMPTY_BYTE_ARRAY;
				if (StringUtils.isEmpty(key))
					throw new IllegalArgumentException("key argument is empty.");
				if (StringUtils.isEmpty(salt))
					throw new IllegalArgumentException("salt argument is empty.");

				final AlgorithmParameterSpec param = new PBEParameterSpec(salt.getBytes(DEFAULT_CHARSET), DEFAULT_SECRET_KEY_ITERATIONS);
				return super.encode(toEncode, key, salt, param);
			}

			@Override
			public byte[] decode(final byte[] toDecode, final String key, final String salt, final AlgorithmParameterSpec paramSpec) {
				if (toDecode == null || toDecode.length == 0)
					return EMPTY_BYTE_ARRAY;
				if (StringUtils.isEmpty(key))
					throw new IllegalArgumentException("key argument is empty.");
				if (StringUtils.isEmpty(salt))
					throw new IllegalArgumentException("salt argument is empty.");

				final AlgorithmParameterSpec param = new PBEParameterSpec(salt.getBytes(DEFAULT_CHARSET), DEFAULT_SECRET_KEY_ITERATIONS);
				return super.decode(toDecode, key, salt, param);
			}
		};

		/**
		 * Algorithm name.
		 */
		private final String algorithmName;

		/**
		 * Default constructor.
		 * 
		 * @param aAlgorithmName
		 *            algorithm key
		 */
		Algorithm(final String aAlgorithmName) {
			this.algorithmName = aAlgorithmName;
		}

		public String getAlgorithmName() {
			return algorithmName;
		}

		/**
		 * Create key.
		 * 
		 * @param key
		 *            secure key
		 * @param salt
		 *            secure salt
		 * @return {@link Key}
		 */
		public Key crateKey(final String key, final String salt) {
			if (StringUtils.isEmpty(key))
				throw new IllegalArgumentException("key argument is empty.");
			if (StringUtils.isEmpty(salt))
				throw new IllegalArgumentException("salt argument is empty.");

			return generateSecretKeySpec(key, salt, getAlgorithmName(), DEFAULT_SECRET_KEY_ITERATIONS, DEFAULT_SECRET_KEY_SIZE);
		}

		/**
		 * Encode.
		 * 
		 * @param toEncode
		 *            value to encode
		 * @param key
		 *            secure key
		 * @param salt
		 *            secure salt
		 * @param paramSpec
		 *            algorithm parameter, optional
		 * @return encoded value in byte array
		 */
		public byte[] encode(final byte[] toEncode, final String key, final String salt, final AlgorithmParameterSpec paramSpec) {
			if (toEncode == null || toEncode.length == 0)
				return EMPTY_BYTE_ARRAY;
			if (StringUtils.isEmpty(key))
				throw new IllegalArgumentException("key argument is empty.");
			if (StringUtils.isEmpty(salt))
				throw new IllegalArgumentException("salt argument is empty.");

			try {
				final Cipher cipher = Cipher.getInstance(getAlgorithmName());
				if (paramSpec != null) {
					cipher.init(Cipher.ENCRYPT_MODE, crateKey(key, salt), paramSpec);
				} else {
					cipher.init(Cipher.ENCRYPT_MODE, crateKey(key, salt));
				}
				return cipher.doFinal(toEncode);
			} catch (final NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException //
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
				throw new CipherRuntimeException(e);
			}
		}

		/**
		 * Decode.
		 * 
		 * @param toDecode
		 *            value to decode
		 * @param key
		 *            secure value
		 * @param salt
		 *            secure salt
		 * @param paramSpec
		 *            algorithm parameter, optional
		 * @return decoded value in byte array
		 */
		public byte[] decode(final byte[] toDecode, final String key, final String salt, final AlgorithmParameterSpec paramSpec) {
			if (toDecode == null || toDecode.length == 0)
				return EMPTY_BYTE_ARRAY;
			if (StringUtils.isEmpty(key))
				throw new IllegalArgumentException("key argument is empty.");
			if (StringUtils.isEmpty(salt))
				throw new IllegalArgumentException("salt argument is empty.");

			try {
				final Cipher cipher = Cipher.getInstance(getAlgorithmName());
				if (paramSpec != null) {
					cipher.init(Cipher.DECRYPT_MODE, crateKey(key, salt), paramSpec);
				} else {
					cipher.init(Cipher.DECRYPT_MODE, crateKey(key, salt));
				}
				return cipher.doFinal(toDecode);
			} catch (final NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException //
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
				throw new CipherRuntimeException(e);
			}
		}

		/**
		 * Get {@link Algorithm} by it name.<br>
		 * {@link IllegalArgumentException} will be thrown if algorithm with given key is not supported or key is empty.
		 * 
		 * @param name
		 *            algorithm name, is not case-sensitive
		 * @return {@link Algorithm}
		 */
		public static Algorithm get(final String name) {
			if (StringUtils.isEmpty(name))
				throw new IllegalArgumentException("name argument is empty.");

			for (final Algorithm a : Algorithm.values())
				if (a.getAlgorithmName().equalsIgnoreCase(name))
					return a;

			throw new CipherRuntimeException("algorithm[" + name + "] not supported.");
		}

		/**
		 * Generate {@link SecretKeySpec}.
		 * 
		 * @param key
		 *            secure key
		 * @param salt
		 *            secure salt
		 * @param algorithm
		 *            algorithm name
		 * @param i
		 *            iterations amount
		 * @param size
		 *            result key size
		 * @return {@link SecretKeySpec}
		 */
		public static SecretKeySpec generateSecretKeySpec(final String key, final String salt, final String algorithm, final int i, final int size) {
			if (StringUtils.isEmpty(key))
				throw new IllegalArgumentException("key argument is empty.");
			if (StringUtils.isEmpty(salt))
				throw new IllegalArgumentException("salt argument is empty.");
			if (StringUtils.isEmpty(algorithm))
				throw new IllegalArgumentException("algorithm argument is empty.");

			try {
				final SecretKeyFactory factory = SecretKeyFactory.getInstance(DEFAULT_SECRET_KEY_ALGORITHM);
				final KeySpec spec = new PBEKeySpec(key.toCharArray(), salt.getBytes(DEFAULT_CHARSET), i, size);
				return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), algorithm);
			} catch (final NoSuchAlgorithmException | InvalidKeySpecException e) {
				throw new CipherRuntimeException(e);
			}
		}

		/**
		 * Convert raw key + salt to byte array.<br>
		 * Additional suffix (check <code>DEFAULT_KEY_SUFFIX</code> constant) will be applied if key length + salt length less then minimum required (check
		 * <code>MINIMUM_KEY_LENGTH</code> constant).
		 * 
		 * @param key
		 *            secure key
		 * @param salt
		 *            secure salt
		 * @return raw key in byte array
		 */
		public static byte[] toRawKey(final String key, final String salt) {
			final StringBuilder rawKey = new StringBuilder();
			if (isNotEmpty(key))
				rawKey.append(key);
			if (isNotEmpty(salt))
				rawKey.append(salt);
			if (rawKey.length() < MINIMUM_KEY_LENGTH)
				rawKey.append(DEFAULT_KEY_SUFFIX.substring(rawKey.length()));

			return rawKey.toString().getBytes(DEFAULT_CHARSET);
		}

	}

}
