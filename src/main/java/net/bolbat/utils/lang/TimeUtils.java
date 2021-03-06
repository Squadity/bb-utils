package net.bolbat.utils.lang;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Utilities for manipulating with time.
 *
 * @author Vasyl Zarva
 */
public final class TimeUtils {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private TimeUtils() {
		throw new IllegalAccessError("Can't be instantiated.");
	}

	/**
	 * Allow to add/remove specified amount of time, defined by {@link TimeUnit} to/from current time, without further rounding call.
	 *
	 * @param amount
	 *            value/amount specified
	 * @param unit
	 *            {@link TimeUnit}
	 * @return timeStamp in millis
	 */
	public static long fromNow(final int amount, final TimeUnit unit) {
		return fromNow(amount, unit, Rounding.NONE);
	}

	/**
	 * Allow to add/remove specified amount of time, defined by {@link TimeUnit} to/from current time, with further rounding call. See {@link #round}.
	 *
	 * @param amount
	 *            value/amount specified
	 * @param unit
	 *            {@link TimeUnit}
	 * @param roundingMode
	 *            {@link Rounding}
	 * @return timeStamp in millis
	 */
	public static long fromNow(final int amount, final TimeUnit unit, final Rounding roundingMode) {
		if (unit == null)
			throw new IllegalArgumentException("'unit' argument is null");
		final Calendar cal = Calendar.getInstance();
		// there is no need to roll in case if we have 0-amount
		if (amount != 0)
			cal.add(unit.getMappedCalendarField(), amount);
		if (roundingMode == null || Rounding.NONE == roundingMode)
			return cal.getTimeInMillis();
		round(cal, unit, roundingMode);
		return cal.getTimeInMillis();
	}

	/**
	 * Allow to round incoming 'timeStamp' in context of selected {@link TimeUnit}, using selected {@link Rounding}.<br>
	 * In case if incoming 'unit' will be {@code null}, {@link IllegalArgumentException} will be thrown.<br>
	 * In case if 'roundingMode' will be set to {@code null} or will be equals to {@link Rounding#NONE} - operation won't be performed at all.
	 *
	 * @param timeStamp
	 *            incoming timestamp in millis
	 * @param unit
	 *            {@link TimeUnit}
	 * @param roundingMode
	 *            {@link Rounding}
	 * @return time rounded using selected {@link Rounding}, in context of {@link TimeUnit}
	 */
	public static long round(final long timeStamp, final TimeUnit unit, final Rounding roundingMode) {
		if (unit == null)
			throw new IllegalArgumentException("'unit' argument is null.");
		if (roundingMode == null || Rounding.NONE == roundingMode)
			return timeStamp;
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeStamp);
		round(cal, unit, roundingMode);
		return cal.getTimeInMillis();
	}

	/**
	 * Allow to round incoming 'calendar' instance, in context of selected {@link TimeUnit}, using selected {@link Rounding}.<br>
	 * In case if incoming 'unit' or 'calendar' will be {@code null}, {@link IllegalArgumentException} will be thrown.<br>
	 * In case if 'roundingMode' will be set to {@code null} or will be equals to {@link Rounding#NONE} - operation won't be performed at all.
	 *
	 * @param calendar
	 *            {@link Calendar} instance
	 * @param unit
	 *            {@link TimeUnit}
	 * @param roundingMode
	 *            {@link Rounding}
	 */
	public static void round(final Calendar calendar, final TimeUnit unit, final Rounding roundingMode) {
		if (calendar == null)
			throw new IllegalArgumentException("'calendar' argument is null.");
		if (unit == null)
			throw new IllegalArgumentException("'unit' argument is null.");

		if (roundingMode == null || Rounding.NONE == roundingMode)
			return;
		for (final int field : unit.getDependingCalendarFields()) {
			switch (roundingMode) {
				case DOWN:
					calendar.set(field, calendar.getActualMinimum(field));
					break;
				case UP:
					calendar.set(field, calendar.getActualMaximum(field));
					break;
				case NONE:
				default:
					break;
			}
		}
	}

	/**
	 * Rounding mode.
	 *
	 * @author Alexandr Bolbat
	 */
	public enum Rounding {

		/**
		 * No rounding.
		 */
		NONE,

		/**
		 * Round to max time.
		 */
		UP,

		/**
		 * Round to min time.
		 */
		DOWN;
	}

	/**
	 * Time unit measure.
	 */
	public enum TimeUnit {
		/**
		 * Represent 'second'.
		 */
		SECOND(Calendar.MILLISECOND) {
			@Override
			public int getMappedCalendarField() {
				return Calendar.SECOND;
			}
		},
		/**
		 * Represent 'minute'.
		 */
		MINUTE(Calendar.SECOND, TimeUnit.SECOND) {
			@Override
			public int getMappedCalendarField() {
				return Calendar.MINUTE;
			}
		},
		/**
		 * Represent 'hour'.
		 */
		HOUR(Calendar.MINUTE, TimeUnit.MINUTE) {
			@Override
			public int getMappedCalendarField() {
				return Calendar.HOUR_OF_DAY;
			}
		},
		/**
		 * Represent 'day'.
		 */
		DAY(Calendar.HOUR_OF_DAY, TimeUnit.HOUR) {
			@Override
			public int getMappedCalendarField() {
				return Calendar.DAY_OF_MONTH;
			}
		},
		/**
		 * Represent 'month'.
		 */
		MONTH(Calendar.DAY_OF_MONTH, TimeUnit.DAY) {
			@Override
			public int getMappedCalendarField() {
				return Calendar.MONTH;
			}
		},
		/**
		 * Represent 'year'.
		 */
		YEAR(Calendar.MONTH, TimeUnit.MONTH) {
			@Override
			public int getMappedCalendarField() {
				return Calendar.YEAR;
			}
		};
		/**
		 * TimeUnit 'dependingCalendarFields'.
		 */
		private final List<Integer> dependingCalendarFields;

		/**
		 * Constructor.
		 *
		 * @param biggestDependingField
		 *            biggest calendar field, on which current entry has dependency
		 */
		TimeUnit(final int biggestDependingField) {
			this(biggestDependingField, null);
		}

		/**
		 * Constructor.
		 *
		 * @param biggestDependingField
		 *            biggest calendar field, on which current entry has dependency
		 * @param dependingUnit
		 *            {@link TimeUnit} linked depending unit
		 */
		TimeUnit(final int biggestDependingField, final TimeUnit dependingUnit) {
			dependingCalendarFields = new ArrayList<>();
			dependingCalendarFields.add(biggestDependingField);
			if (dependingUnit != null)
				dependingCalendarFields.addAll(dependingUnit.getDependingCalendarFields());
		}

		public List<Integer> getDependingCalendarFields() {
			return dependingCalendarFields;
		}

		/**
		 * Return {@link Calendar} field, to which current entity is mapped.
		 *
		 * @return field <code>int</code> value
		 */
		public abstract int getMappedCalendarField();

	}

}
