package net.bb.utils.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility with some helper functionality related to numbers.
 * 
 * @author Alexandr Bolbat
 */
public final class NumberUtils {

	/**
	 * Compare two instances of {@link Number} between each other.
	 * 
	 * @param first
	 *            first {@link Number}, can't be <code>null</code>
	 * @param second
	 *            second {@link Number}, can't be <code>null</code>
	 * @return -1, 0, or 1 as first {@link Number} is less than, equal to, or greater than second {@link Number}
	 */
	public static int compare(final Number first, final Number second) {
		if (first == null)
			throw new IllegalArgumentException("first argument is null.");
		if (second == null)
			throw new IllegalArgumentException("second argument is null.");

		// if first and second numbers not the same type we use BigDecimal type to compare it's
		if (!first.getClass().equals(second.getClass()))
			return new BigDecimal(first.doubleValue()).compareTo(new BigDecimal(second.doubleValue()));

		// if both numbers are the same type
		// byte
		if (first instanceof Byte && second instanceof Byte)
			return ((Byte) first).compareTo((Byte) second);
		// short
		if (first instanceof Short && second instanceof Short)
			return ((Short) first).compareTo((Short) second);
		// integer
		if (first instanceof Integer && second instanceof Integer)
			return ((Integer) first).compareTo((Integer) second);
		// atomic integer
		if (first instanceof AtomicInteger && second instanceof AtomicInteger)
			return ((Integer) first.intValue()).compareTo(second.intValue());
		// big integer
		if (first instanceof BigInteger && second instanceof BigInteger)
			return ((BigInteger) first).compareTo((BigInteger) second);
		// long
		if (first instanceof Long && second instanceof Long)
			return ((Long) first).compareTo((Long) second);
		// atomic long
		if (first instanceof AtomicLong && second instanceof AtomicLong)
			return ((Long) first.longValue()).compareTo(second.longValue());
		// float
		if (first instanceof Float && second instanceof Float)
			return org.apache.commons.lang.math.NumberUtils.compare(first.floatValue(), second.floatValue());
		// double
		if (first instanceof Double && second instanceof Double)
			return org.apache.commons.lang.math.NumberUtils.compare(first.doubleValue(), second.doubleValue());
		// big decimal
		if (first instanceof BigDecimal && second instanceof BigDecimal)
			return ((BigDecimal) first).compareTo((BigDecimal) second);

		// if other sub-types of Number we use BigDecimal type to compare it's
		return new BigDecimal(first.doubleValue()).compareTo(new BigDecimal(second.doubleValue()));
	}

}
