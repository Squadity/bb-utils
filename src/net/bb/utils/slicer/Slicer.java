package net.bb.utils.slicer;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility for slicing original collection by given parameters.
 * 
 * @author Alexandr Bolbat
 */
public final class Slicer {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private Slicer() {
		throw new IllegalAccessError();
	}

	/**
	 * Return elements slice from original list. If original list are <code>null</code> or empty - empty {@link ArrayList} will be returned.
	 * 
	 * @param list
	 *            - original list
	 * @param offset
	 *            - offset
	 * @param amount
	 *            - result elements size
	 * @return {@link List} of {@link T}
	 */
	public static <T> List<T> slice(final List<T> list, final int offset, final int amount) {
		if (list == null || list.isEmpty())
			return new ArrayList<T>();

		int aSize = list.size();
		int aOffset = offset >= 0 ? offset : 0;
		int aAmount = amount >= 0 ? amount : 0;

		if (aOffset > aSize || aAmount == 0)
			return new ArrayList<T>();

		int startIndex = aOffset == 0 ? aOffset : aOffset - 1;
		int endIndex = Math.min(aSize, startIndex + aAmount);

		return list.subList(startIndex, endIndex);
	}

	/**
	 * Return elements slice from original list. If original list are <code>null</code> or empty - empty {@link ArrayList} will be returned.
	 * 
	 * @param list
	 *            - original list
	 * @param page
	 *            - page number
	 * @param elements
	 *            - element on page
	 * @return {@link List} of {@link T}
	 */
	public static <T> List<T> sliceTo(final List<T> list, final int page, final int elements) {
		if (page > 1)
			return slice(list, (page * elements) - 1, elements);

		return slice(list, page, elements);
	}

}
