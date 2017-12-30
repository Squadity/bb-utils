package net.bolbat.utils.crypt;

import static net.bolbat.utils.lang.StringUtils.EMPTY;
import static net.bolbat.utils.lang.StringUtils.isNotEmpty;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.bolbat.utils.lang.ArrayUtils;
import net.bolbat.utils.lang.StringUtils;

/**
 * {@link MessageDigest} utilities.
 * 
 * @author Alexandr Bolbat
 */
public final class DigestUtils {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private DigestUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Create digest for a {@link String}.
	 * 
	 * @param algorithm
	 *            algorithm
	 * @param value
	 *            {@link String} to digest
	 * @return {@link String} digest
	 */
	public static String digest(final Algorithm algorithm, final String value) {
		return digest(algorithm, value, EMPTY);
	}

	/**
	 * Create digest for a {@link String}.
	 * 
	 * @param algorithm
	 *            algorithm
	 * @param value
	 *            {@link String} to digest
	 * @param salt
	 *            secure salt, optional
	 * @return {@link String} digest
	 */
	public static String digest(final Algorithm algorithm, final String value, final String salt) {
		if (algorithm == null)
			throw new IllegalArgumentException("algorithm argument is null.");
		if (StringUtils.isEmpty(value))
			return CipherUtils.EMPTY_STRING;

		final byte[] digest = algorithm.digest(value.getBytes(CipherUtils.DEFAULT_CHARSET), salt);
		return HexUtils.encodeHexString(digest);
	}

	/**
	 * Supported algorithms.
	 * 
	 * @author Alexandr Bolbat
	 */
	public enum Algorithm {

		/**
		 * The MD2 message digest algorithm as defined in RFC 1319.
		 */
		MD2("MD2"),

		/**
		 * The MD5 message digest algorithm as defined in RFC 1321.
		 */
		MD5("MD5"),

		/**
		 * Hash algorithms defined in the FIPS PUB 180-2.
		 */
		SHA_1("SHA-1"),

		/**
		 * Hash algorithms defined in the FIPS PUB 180-2.
		 */
		SHA_256("SHA-256"),

		/**
		 * Hash algorithms defined in the FIPS PUB 180-2.
		 */
		SHA_384("SHA-384"),

		/**
		 * Hash algorithms defined in the FIPS PUB 180-2.
		 */
		SHA_512("SHA-512");

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
		 * Create digest.
		 * 
		 * @param value
		 *            original value
		 * @return digest value
		 */
		public byte[] digest(final byte[] value) {
			return digest(value, EMPTY);
		}

		/**
		 * Create digest.
		 * 
		 * @param value
		 *            original value
		 * @param salt
		 *            secure salt, optional
		 * @return digest value
		 */
		public byte[] digest(final byte[] value, final String salt) {
			if (value == null || value.length == 0)
				return CipherUtils.EMPTY_BYTE_ARRAY;

			try {
				final byte[] toDigest = isNotEmpty(salt) ? ArrayUtils.addAll(value, salt.getBytes(CipherUtils.DEFAULT_CHARSET)) : value;
				return MessageDigest.getInstance(getAlgorithmName()).digest(toDigest);
			} catch (final NoSuchAlgorithmException e) {
				throw new DigestRuntimeException(e);
			}
		}

		/**
		 * Get {@link Algorithm} by it name.<br>
		 * {@link IllegalArgumentException} will be thrown if algorithm with given key is not supported or key is empty.
		 * 
		 * @param aAlgorithmName
		 *            algorithm name, is not case-sensitive
		 * @return {@link Algorithm}
		 */
		public static Algorithm get(final String aAlgorithmName) {
			if (StringUtils.isEmpty(aAlgorithmName))
				throw new IllegalArgumentException("aAlgorithmName argument is empty.");

			for (final Algorithm a : Algorithm.values())
				if (a.getAlgorithmName().equalsIgnoreCase(aAlgorithmName))
					return a;

			throw new DigestRuntimeException("algorithm[" + aAlgorithmName + "] not supported.");
		}

	}

}
