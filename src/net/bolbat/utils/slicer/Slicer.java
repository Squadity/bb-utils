package net.bolbat.utils.slicer;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Return elements slice from original list. If original list are <code>null</code> or empty - empty {@link ArrayList} will be returned.
	 * 
	 * @param list
	 *            original list
	 * @param offset
	 *            offset, minimum offset is '0', if offset less than '0' default will be used
	 * @param amount
	 *            result elements size
	 * @return {@link List} of <T>
	 */
	public static <T> List<T> slice(final List<T> list, final int offset, final int amount) {
		if (list == null || list.isEmpty())
			return new ArrayList<T>();

		int aSize = list.size();
		int aOffset = offset < 0 ? 0 : offset;
		int aAmount = amount < 0 ? 0 : amount;

		if (aAmount == 0 || aOffset > aSize - 1)
			return new ArrayList<T>();

		int endIndex = Math.min(aSize, aOffset + aAmount);
		return new ArrayList<T>(list.subList(aOffset, endIndex));
	}

	/**
	 * Return elements slice from original list. If original list are <code>null</code> or empty - empty {@link ArrayList} will be returned.
	 * 
	 * @param list
	 *            original list
	 * @param page
	 *            page number, minimum page is '0', if page less than '0' default will be used
	 * @param elements
	 *            element on page
	 * @return {@link List} of <T>
	 */
	public static <T> List<T> sliceTo(final List<T> list, final int page, final int elements) {
		if (list == null || list.isEmpty() || elements < 1)
			return new ArrayList<T>();
		if (list.size() <= elements && page > 0)
			return new ArrayList<T>();

		final int startIndex = page == 0 ? 0 : elements * page;
		return slice(list, startIndex, elements);
	}

	/**
	 * Divide original list to sub-lists with elements limit.
	 * 
	 * @param list
	 *            original list
	 * @param elements
	 *            elements per sub-list
	 * @return {@link List} of {@link List} of <T>
	 */
	public static <T> List<List<T>> divide(final List<T> list, final int elements) {
		if (list == null || list.isEmpty() || elements < 1)
			return new ArrayList<List<T>>();

		final int pages = new BigDecimal(list.size()).divide(new BigDecimal(elements), RoundingMode.UP).intValue();
		final List<List<T>> result = new ArrayList<List<T>>();
		for (int current = 0; current < pages; current++)
			result.add(sliceTo(list, current, elements));

		return result;
	}

}
