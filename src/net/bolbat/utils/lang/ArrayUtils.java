package net.bolbat.utils.lang;

import java.lang.reflect.Array;
import java.util.Arrays;

import net.bolbat.utils.annotation.Audience;
import net.bolbat.utils.annotation.Stability;

/**
 * Array utilities.
 * 
 * @author Alexandr Bolbat
 */
@Audience.Public
@Stability.Evolving
public final class ArrayUtils {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private ArrayUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Create new array instance.<br>
	 * Throws:<br>
	 * - {@link NullPointerException} if the specified array type parameter is <code>null</code>;<br>
	 * - {@link NegativeArraySizeException} if the specified array length is negative.
	 * 
	 * @param type
	 *            array type
	 * @param length
	 *            array length
	 * @return result array
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] newInstance(final T type, final int length) {
		return (T[]) newInstance(type.getClass(), length);
	}

	/**
	 * Create new array instance.<br>
	 * Throws:<br>
	 * - {@link NullPointerException} if the specified array type parameter is <code>null</code>;<br>
	 * - {@link NegativeArraySizeException} if the specified array length is negative.
	 * 
	 * @param type
	 *            array type
	 * @param length
	 *            array length
	 * @return result array
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] newInstance(final Class<T> type, final int length) {
		return (T[]) Array.newInstance(type, length);
	}

	/**
	 * Convert element to array.
	 * 
	 * @param element
	 *            element
	 * @return result array or <code>null</code> if element is <code>null</code>
	 */
	public static <T> T[] toArray(final T element) {
		if (element == null)
			return null;

		final T[] result = newInstance(element, 1);
		result[0] = element;
		return result;
	}

	/**
	 * Clone array.
	 * 
	 * @param array
	 *            array
	 * @return result array or <code>null</code> if array is <code>null</code>
	 */
	public static <T> T[] clone(final T[] array) {
		return array != null ? array.clone() : null;
	}

	/**
	 * Concatenate element with array.
	 * 
	 * @param element
	 *            element
	 * @param array
	 *            array
	 * @return result array or <code>null</code> if element and array is <code>null</code>
	 */
	public static <T> T[] concat(final T element, final T[] array) {
		if (element == null)
			return clone(array);
		if (array == null)
			return toArray(element);

		final T[] result = newInstance(element, array.length + 1);
		result[0] = element;
		System.arraycopy(array, 0, result, 1, array.length);
		return result;
	}

	/**
	 * Concatenate array with element.
	 * 
	 * @param array
	 *            array
	 * @param element
	 *            element
	 * @return result array or <code>null</code> if array and element is <code>null</code>
	 */
	public static <T> T[] concat(final T[] array, final T element) {
		if (array == null)
			return toArray(element);
		if (element == null)
			return clone(array);

		final T[] result = Arrays.copyOf(array, array.length + 1);
		result[array.length] = element;
		return result;
	}

	/**
	 * Concatenate two arrays.
	 * 
	 * @param first
	 *            first array
	 * @param second
	 *            second array
	 * @return result array or <code>null</code> if arrays is <code>null</code>
	 */
	@SafeVarargs
	public static <T> T[] concat(final T[] first, final T... second) {
		if (first == null)
			return clone(second);
		if (second == null)
			return clone(first);

		final T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

}
