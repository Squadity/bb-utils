package net.bolbat.utils.crypt;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Hex encoding and decoding utilities.
 * 
 * @author Alexandr Bolbat
 */
public final class HexUtils {

	/**
	 * Default {@link Charset}.
	 */
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	/**
	 * Used to build output as Hex.
	 */
	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * Used to build output as Hex.
	 */
	private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private HexUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Converts a String representing hexadecimal values into an array of bytes of those same values.<br>
	 * The returned array will be half the length of the passed String, as it takes two characters to represent any given byte.<br>
	 * An exception is thrown if the passed String has an odd number of elements.
	 *
	 * @param data
	 *            a String containing hexadecimal digits
	 * @return a byte array containing binary data decoded from the supplied char array
	 * @throws IllegalArgumentException
	 *             thrown if an odd number or illegal of characters is supplied
	 */
	public static byte[] decodeHex(final String data) {
		return decodeHex(data.toCharArray());
	}

	/**
	 * Converts an array of characters representing hexadecimal values into an array of bytes of those same values.<br>
	 * The returned array will be half the length of the passed array, as it takes two characters to represent any given byte.<br>
	 * An exception is thrown if the passed char array has an odd number of elements.
	 *
	 * @param data
	 *            an array of characters containing hexadecimal digits
	 * @return a byte array containing binary data decoded from the supplied char array
	 * @throws IllegalArgumentException
	 *             thrown if an odd number or illegal of characters is supplied
	 */
	public static byte[] decodeHex(final char[] data) {
		final int len = data.length;
		if ((len & 0x01) != 0)
			throw new IllegalArgumentException("Odd number of characters.");

		final byte[] out = new byte[len >> 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f = f | toDigit(data[j], j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}

		return out;
	}

	/**
	 * Converts a hexadecimal character to an integer.
	 *
	 * @param character
	 *            a character to convert to an integer digit
	 * @param index
	 *            the index of the character in the source
	 * @return an integer
	 * @throws IllegalArgumentException
	 *             thrown if character is an illegal hex character
	 */
	protected static int toDigit(final char character, final int index) {
		final int digit = Character.digit(character, 16);
		if (digit == -1)
			throw new IllegalArgumentException("Illegal hexadecimal character " + character + " at index " + index);

		return digit;
	}

	/**
	 * Converts an array of bytes into an array of characters representing the hexadecimal values of each byte in order.<br>
	 * The returned array will be double the length of the passed array, as it takes two characters to represent any given byte.
	 *
	 * @param data
	 *            a byte[] to convert to Hex characters
	 * @return a char[] containing lower-case hexadecimal characters
	 */
	public static char[] encodeHex(final byte[] data) {
		return encodeHex(data, true);
	}

	/**
	 * Converts an array of bytes into an array of characters representing the hexadecimal values of each byte in order.<br>
	 * The returned array will be double the length of the passed array, as it takes two characters to represent any given byte.
	 *
	 * @param data
	 *            a byte[] to convert to Hex characters
	 * @param toLowerCase
	 *            <code>true</code> converts to lowercase, <code>false</code> to uppercase
	 * @return a char[] containing hexadecimal characters in the selected case
	 */
	public static char[] encodeHex(final byte[] data, final boolean toLowerCase) {
		return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}

	/**
	 * Converts a byte buffer into an array of characters representing the hexadecimal values of each byte in order.<br>
	 * The returned array will be double the length of the passed array, as it takes two characters to represent any given byte.
	 *
	 * @param data
	 *            a byte buffer to convert to Hex characters
	 * @return a char[] containing lower-case hexadecimal characters
	 */
	public static char[] encodeHex(final ByteBuffer data) {
		return encodeHex(data, true);
	}

	/**
	 * Converts a byte buffer into an array of characters representing the hexadecimal values of each byte in order.<br>
	 * The returned array will be double the length of the passed array, as it takes two characters to represent any given byte.
	 *
	 * @param data
	 *            a byte buffer to convert to Hex characters
	 * @param toLowerCase
	 *            <code>true</code> converts to lowercase, <code>false</code> to uppercase
	 * @return a char[] containing hexadecimal characters in the selected case
	 */
	public static char[] encodeHex(final ByteBuffer data, final boolean toLowerCase) {
		return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}

	/**
	 * Converts a byte buffer into an array of characters representing the hexadecimal values of each byte in order.<br>
	 * The returned array will be double the length of the passed array, as it takes two characters to represent any given byte.
	 *
	 * @param data
	 *            a byte buffer to convert to Hex characters
	 * @param toDigits
	 *            the output alphabet (must be at least 16 characters)
	 * @return a char[] containing the appropriate characters from the alphabet For best results, this should be either upper- or lower-case hex
	 */
	protected static char[] encodeHex(final ByteBuffer data, final char[] toDigits) {
		return encodeHex(data.array(), toDigits);
	}

	/**
	 * Converts an array of bytes into an array of characters representing the hexadecimal values of each byte in order.<br>
	 * The returned array will be double the length of the passed array, as it takes two characters to represent any given byte.
	 *
	 * @param data
	 *            a byte[] to convert to Hex characters
	 * @param toDigits
	 *            the output alphabet (must contain at least 16 chars)
	 * @return a char[] containing the appropriate characters from the alphabet For best results, this should be either upper- or lower-case hex
	 */
	protected static char[] encodeHex(final byte[] data, final char[] toDigits) {
		final int l = data.length;
		final char[] out = new char[l << 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
			out[j++] = toDigits[0x0F & data[i]];
		}
		return out;
	}

	/**
	 * Converts an array of bytes into a String representing the hexadecimal values of each byte in order.<br>
	 * The returned String will be double the length of the passed array, as it takes two characters to represent any given byte.
	 *
	 * @param data
	 *            a byte[] to convert to Hex characters
	 * @return a String containing lower-case hexadecimal characters
	 */
	public static String encodeHexString(final byte[] data) {
		return new String(encodeHex(data));
	}

	/**
	 * Converts an array of bytes into a String representing the hexadecimal values of each byte in order.<br>
	 * The returned String will be double the length of the passed array, as it takes two characters to represent any given byte.
	 *
	 * @param data
	 *            a byte[] to convert to Hex characters
	 * @param toLowerCase
	 *            <code>true</code> converts to lowercase, <code>false</code> to uppercase
	 * @return a String containing lower-case hexadecimal characters
	 */
	public static String encodeHexString(final byte[] data, final boolean toLowerCase) {
		return new String(encodeHex(data, toLowerCase));
	}

	/**
	 * Converts a byte buffer into a String representing the hexadecimal values of each byte in order.<br>
	 * The returned String will be double the length of the passed array, as it takes two characters to represent any given byte.
	 *
	 * @param data
	 *            a byte buffer to convert to Hex characters
	 * @return a String containing lower-case hexadecimal characters
	 */
	public static String encodeHexString(final ByteBuffer data) {
		return new String(encodeHex(data));
	}

	/**
	 * Converts a byte buffer into a String representing the hexadecimal values of each byte in order.<br>
	 * The returned String will be double the length of the passed array, as it takes two characters to represent any given byte.
	 *
	 * @param data
	 *            a byte buffer to convert to Hex characters
	 * @param toLowerCase
	 *            <code>true</code> converts to lowercase, <code>false</code> to uppercase
	 * @return a String containing lower-case hexadecimal characters
	 */
	public static String encodeHexString(final ByteBuffer data, final boolean toLowerCase) {
		return new String(encodeHex(data, toLowerCase));
	}

}
