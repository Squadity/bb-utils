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

	/**
	 * Shallow clones an array and handling <code>null</code>.<br>
	 * The objects in the array are not cloned, thus there is no special handling for multi-dimensional arrays.
	 *
	 * @param <T>
	 *            the component type of the array
	 * @param array
	 *            the array to shallow clone, may be <code>null</code>
	 * @return the cloned array or <code>null</code>
	 */
	public static <T> T[] clone(final T[] array) {
		return array != null ? array.clone() : null;
	}

	/**
	 * Clones an array and handling <code>null</code>.
	 *
	 * @param array
	 *            the array to clone, may be <code>null</code>
	 * @return the cloned array or <code>null</code>
	 */
	public static long[] clone(final long[] array) {
		return array != null ? array.clone() : null;
	}

	/**
	 * Clones an array and handling <code>null</code>.
	 *
	 * @param array
	 *            the array to clone, may be <code>null</code>
	 * @return the cloned array or <code>null</code>
	 */
	public static int[] clone(final int[] array) {
		return array != null ? array.clone() : null;
	}

	/**
	 * Clones an array and handling <code>null</code>.
	 *
	 * @param array
	 *            the array to clone, may be <code>null</code>
	 * @return the cloned array or <code>null</code>
	 */
	public static short[] clone(final short[] array) {
		return array != null ? array.clone() : null;
	}

	/**
	 * Clones an array and handling <code>null</code>.
	 *
	 * @param array
	 *            the array to clone, may be <code>null</code>
	 * @return the cloned array or <code>null</code>
	 */
	public static char[] clone(final char[] array) {
		return array != null ? array.clone() : null;
	}

	/**
	 * Clones an array and handling <code>null</code>.
	 *
	 * @param array
	 *            the array to clone, may be <code>null</code>
	 * @return the cloned array or <code>null</code>
	 */
	public static byte[] clone(final byte[] array) {
		return array != null ? array.clone() : null;
	}

	/**
	 * Clones an array and handling <code>null</code>.
	 *
	 * @param array
	 *            the array to clone, may be <code>null</code>
	 * @return the cloned array or <code>null</code>
	 */
	public static double[] clone(final double[] array) {
		return array != null ? array.clone() : null;
	}

	/**
	 * Clones an array and handling <code>null</code>.
	 *
	 * @param array
	 *            the array to clone, may be <code>null</code>
	 * @return the cloned array or <code>null</code>
	 */
	public static float[] clone(final float[] array) {
		return array != null ? array.clone() : null;
	}

	/**
	 * Clones an array and handling <code>null</code>.
	 *
	 * @param array
	 *            the array to clone, may be <code>null</code>
	 * @return the cloned array or <code>null</code>
	 */
	public static boolean[] clone(final boolean[] array) {
		return array != null ? array.clone() : null;
	}

	/**
	 * Adds all the elements of the given arrays into a new array.<br>
	 * The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}.<br>
	 * When an array is returned, it is always a new array.
	 *
	 * <pre>
	 * ArrayUtils.addAll(null, null)     = null
	 * ArrayUtils.addAll(array1, null)   = cloned copy of array1
	 * ArrayUtils.addAll(null, array2)   = cloned copy of array2
	 * ArrayUtils.addAll([], [])         = []
	 * ArrayUtils.addAll([null], [null]) = [null, null]
	 * ArrayUtils.addAll(["a", "b", "c"], ["1", "2", "3"]) = ["a", "b", "c", "1", "2", "3"]
	 * </pre>
	 *
	 * @param <T>
	 *            the component type of the array
	 * @param array1
	 *            the first array whose elements are added to the new array, may be <code>null</code>
	 * @param array2
	 *            the second array whose elements are added to the new array, may be <code>null</code>
	 * @return new array
	 * @throws IllegalArgumentException
	 *             if the array types are incompatible
	 */
	@SafeVarargs
	public static <T> T[] addAll(final T[] array1, final T... array2) {
		if (array1 == null)
			return clone(array2);
		if (array2 == null)
			return clone(array1);

		final Class<?> type1 = array1.getClass().getComponentType();
		final T[] joined = CastUtils.cast(Array.newInstance(type1, array1.length + array2.length));

		System.arraycopy(array1, 0, joined, 0, array1.length);

		try {
			System.arraycopy(array2, 0, joined, array1.length, array2.length);
		} catch (final ArrayStoreException ase) {
			// check if problem was due to incompatible types
			final Class<?> type2 = array2.getClass().getComponentType();
			if (!type1.isAssignableFrom(type2))
				throw new IllegalArgumentException("Cannot store " + type2.getName() + " in an array of " + type1.getName(), ase);

			// re-throw original in other case
			throw ase;
		}

		return joined;
	}

	/**
	 * Adds all the elements of the given arrays into a new array.<br>
	 * The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}.<br>
	 * When an array is returned, it is always a new array.
	 *
	 * <pre>
	 * addAll(array1, null)   = cloned copy of array1
	 * addAll(null, array2)   = cloned copy of array2
	 * addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1
	 *            the first array whose elements are added to the new array, may be <code>null</code>
	 * @param array2
	 *            the second array whose elements are added to the new array, may be <code>null</code>
	 * @return new array
	 */
	public static boolean[] addAll(final boolean[] array1, final boolean... array2) {
		if (array1 == null)
			return clone(array2);
		if (array2 == null)
			return clone(array1);

		final boolean[] joined = new boolean[array1.length + array2.length];
		System.arraycopy(array1, 0, joined, 0, array1.length);
		System.arraycopy(array2, 0, joined, array1.length, array2.length);
		return joined;
	}

	/**
	 * Adds all the elements of the given arrays into a new array.<br>
	 * The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}.<br>
	 * When an array is returned, it is always a new array.
	 *
	 * <pre>
	 * addAll(array1, null)   = cloned copy of array1
	 * addAll(null, array2)   = cloned copy of array2
	 * addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1
	 *            the first array whose elements are added to the new array, may be <code>null</code>
	 * @param array2
	 *            the second array whose elements are added to the new array, may be <code>null</code>
	 * @return new array
	 */
	public static char[] addAll(final char[] array1, final char... array2) {
		if (array1 == null)
			return clone(array2);
		if (array2 == null)
			return clone(array1);

		final char[] joined = new char[array1.length + array2.length];
		System.arraycopy(array1, 0, joined, 0, array1.length);
		System.arraycopy(array2, 0, joined, array1.length, array2.length);
		return joined;
	}

	/**
	 * Adds all the elements of the given arrays into a new array.<br>
	 * The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}.<br>
	 * When an array is returned, it is always a new array.
	 *
	 * <pre>
	 * addAll(array1, null)   = cloned copy of array1
	 * addAll(null, array2)   = cloned copy of array2
	 * addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1
	 *            the first array whose elements are added to the new array, may be <code>null</code>
	 * @param array2
	 *            the second array whose elements are added to the new array, may be <code>null</code>
	 * @return new array
	 */
	public static byte[] addAll(final byte[] array1, final byte... array2) {
		if (array1 == null)
			return clone(array2);
		if (array2 == null)
			return clone(array1);

		final byte[] joined = new byte[array1.length + array2.length];
		System.arraycopy(array1, 0, joined, 0, array1.length);
		System.arraycopy(array2, 0, joined, array1.length, array2.length);
		return joined;
	}

	/**
	 * Adds all the elements of the given arrays into a new array.<br>
	 * The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}.<br>
	 * When an array is returned, it is always a new array.
	 *
	 * <pre>
	 * addAll(array1, null)   = cloned copy of array1
	 * addAll(null, array2)   = cloned copy of array2
	 * addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1
	 *            the first array whose elements are added to the new array, may be <code>null</code>
	 * @param array2
	 *            the second array whose elements are added to the new array, may be <code>null</code>
	 * @return new array
	 */
	public static short[] addAll(final short[] array1, final short... array2) {
		if (array1 == null)
			return clone(array2);
		if (array2 == null)
			return clone(array1);

		final short[] joined = new short[array1.length + array2.length];
		System.arraycopy(array1, 0, joined, 0, array1.length);
		System.arraycopy(array2, 0, joined, array1.length, array2.length);
		return joined;
	}

	/**
	 * Adds all the elements of the given arrays into a new array.<br>
	 * The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}.<br>
	 * When an array is returned, it is always a new array.
	 *
	 * <pre>
	 * addAll(array1, null)   = cloned copy of array1
	 * addAll(null, array2)   = cloned copy of array2
	 * addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1
	 *            the first array whose elements are added to the new array, may be <code>null</code>
	 * @param array2
	 *            the second array whose elements are added to the new array, may be <code>null</code>
	 * @return new array
	 */
	public static int[] addAll(final int[] array1, final int... array2) {
		if (array1 == null)
			return clone(array2);
		if (array2 == null)
			return clone(array1);

		final int[] joined = new int[array1.length + array2.length];
		System.arraycopy(array1, 0, joined, 0, array1.length);
		System.arraycopy(array2, 0, joined, array1.length, array2.length);
		return joined;
	}

	/**
	 * Adds all the elements of the given arrays into a new array.<br>
	 * The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}.<br>
	 * When an array is returned, it is always a new array.
	 *
	 * <pre>
	 * addAll(array1, null)   = cloned copy of array1
	 * addAll(null, array2)   = cloned copy of array2
	 * addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1
	 *            the first array whose elements are added to the new array, may be <code>null</code>
	 * @param array2
	 *            the second array whose elements are added to the new array, may be <code>null</code>
	 * @return new array
	 */
	public static long[] addAll(final long[] array1, final long... array2) {
		if (array1 == null)
			return clone(array2);
		if (array2 == null)
			return clone(array1);

		final long[] joined = new long[array1.length + array2.length];
		System.arraycopy(array1, 0, joined, 0, array1.length);
		System.arraycopy(array2, 0, joined, array1.length, array2.length);
		return joined;
	}

	/**
	 * Adds all the elements of the given arrays into a new array.<br>
	 * The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}.<br>
	 * When an array is returned, it is always a new array.
	 *
	 * <pre>
	 * addAll(array1, null)   = cloned copy of array1
	 * addAll(null, array2)   = cloned copy of array2
	 * addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1
	 *            the first array whose elements are added to the new array, may be <code>null</code>
	 * @param array2
	 *            the second array whose elements are added to the new array, may be <code>null</code>
	 * @return new array
	 */
	public static float[] addAll(final float[] array1, final float... array2) {
		if (array1 == null)
			return clone(array2);
		if (array2 == null)
			return clone(array1);

		final float[] joined = new float[array1.length + array2.length];
		System.arraycopy(array1, 0, joined, 0, array1.length);
		System.arraycopy(array2, 0, joined, array1.length, array2.length);
		return joined;
	}

	/**
	 * Adds all the elements of the given arrays into a new array.<br>
	 * The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}.<br>
	 * When an array is returned, it is always a new array.
	 *
	 * <pre>
	 * addAll(array1, null)   = cloned copy of array1
	 * addAll(null, array2)   = cloned copy of array2
	 * addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1
	 *            the first array whose elements are added to the new array, may be <code>null</code>
	 * @param array2
	 *            the second array whose elements are added to the new array, may be <code>null</code>
	 * @return new array
	 */
	public static double[] addAll(final double[] array1, final double... array2) {
		if (array1 == null)
			return clone(array2);
		if (array2 == null)
			return clone(array1);

		final double[] joined = new double[array1.length + array2.length];
		System.arraycopy(array1, 0, joined, 0, array1.length);
		System.arraycopy(array2, 0, joined, array1.length, array2.length);
		return joined;
	}

}
