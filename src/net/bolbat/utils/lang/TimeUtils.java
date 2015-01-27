package net.bolbat.utils.lang;

import java.util.Calendar;

/**
 * Time utils.
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
	 * Get timestamp for X years ago from now.
	 *
	 * @param years
	 * 		years, can't be <code>null</code>
	 * @param mode
	 * 		rounding mode
	 * @return timestamp
	 */
	public static long getYearsFromNow(final int years, final Rounding mode) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, years);
		if (mode == null || Rounding.NONE == mode)
			return cal.getTimeInMillis();
		roundYearTime(cal, mode);
		return cal.getTimeInMillis();
	}

	/**
	 * Get timestamp from now for given months.
	 *
	 * @param months
	 * 		days count
	 * @param mode
	 * 		rounding mode
	 * @return timestamp
	 */
	public static long getMonthFromNow(final int months, final Rounding mode) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, months);
		if (mode == null || Rounding.NONE == mode)
			return cal.getTimeInMillis();
		roundMonthTime(cal, mode);
		return cal.getTimeInMillis();
	}

	/**
	 * Get timestamp from now for given days.
	 *
	 * @param days
	 * 		days count
	 * @param mode
	 * 		rounding mode
	 * @return timestamp
	 */
	public static long getDaysFromNow(final int days, final Rounding mode) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days);
		if (mode == null || Rounding.NONE == mode)
			return cal.getTimeInMillis();
		roundDayTime(cal, mode);
		return cal.getTimeInMillis();
	}

	/**
	 * Get timestamp from now for given hours.
	 *
	 * @param hours
	 * 		days count
	 * @param mode
	 * 		rounding mode
	 * @return timestamp
	 */
	public static long getHoursFromNow(final int hours, final Rounding mode) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, hours);
		if (mode == null || Rounding.NONE == mode)
			return cal.getTimeInMillis();
		roundHourTime(cal, mode);
		return cal.getTimeInMillis();
	}

	/**
	 * Rounding incoming time to seconds, up/down rounding defined by {@link Rounding}.
	 *
	 * @param time
	 * 		time in millis
	 * @param mode
	 * 		rounding mode
	 * @return rounded time in millis
	 */
	public static long roundTimeToSecondTime(final long time, final Rounding mode) {
		if (mode == null || Rounding.NONE == mode)
			return time;
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		roundSecondTime(cal, mode);
		return cal.getTimeInMillis();
	}

	/**
	 * Round date to maximum or minimum in second, depending of rounding mode.
	 *
	 * @param cal
	 * 		{@link Calendar} with configured date
	 * @param mode
	 * 		rounding mode
	 */
	public static void roundSecondTime(final Calendar cal, final Rounding mode) {
		if (cal == null)
			throw new IllegalArgumentException("cal argument is null.");
		if (mode == null || Rounding.NONE == mode)
			return;
		switch (mode) {
			case MAX:
				cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
				break;
			case MIN:
				cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
				break;
			case NONE:
			default:
				break;
		}
	}

	/**
	 * Rounding incoming time to minutes, up/down rounding defined by {@link Rounding}.
	 *
	 * @param time
	 * 		time in millis
	 * @param mode
	 * 		rounding mode
	 * @return rounded time in millis
	 */
	public static long roundTimeToMinuteTime(final long time, final Rounding mode) {
		if (mode == null || Rounding.NONE == mode)
			return time;
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		roundMinuteTime(cal, mode);
		return cal.getTimeInMillis();
	}

	/**
	 * Round date to maximum or minimum in minute, depending of rounding mode.
	 *
	 * @param cal
	 * 		{@link Calendar} with configured date
	 * @param mode
	 * 		rounding mode
	 */
	public static void roundMinuteTime(final Calendar cal, final Rounding mode) {
		if (cal == null)
			throw new IllegalArgumentException("cal argument is null.");
		if (mode == null || Rounding.NONE == mode)
			return;
		switch (mode) {
			case MAX:
				cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
				cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
				break;
			case MIN:
				cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
				cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
				break;
			case NONE:
			default:
				break;
		}
	}

	/**
	 * Rounding incoming time to hour, up/down rounding defined by {@link Rounding}.
	 *
	 * @param time
	 * 		time in millis
	 * @param mode
	 * 		rounding mode
	 * @return rounded time in millis
	 */
	public static long roundTimeToHourTime(final long time, final Rounding mode) {
		if (mode == null || Rounding.NONE == mode)
			return time;
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		roundHourTime(cal, mode);
		return cal.getTimeInMillis();
	}

	/**
	 * Round date to maximum or minimum in hour, depending of rounding mode.
	 *
	 * @param cal
	 * 		{@link Calendar} with configured date
	 * @param mode
	 * 		rounding mode
	 */
	public static void roundHourTime(final Calendar cal, final Rounding mode) {
		if (cal == null)
			throw new IllegalArgumentException("cal argument is null.");
		if (mode == null || Rounding.NONE == mode)
			return;
		switch (mode) {
			case MAX:
				cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
				cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
				cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
				break;
			case MIN:
				cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
				cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
				cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
				break;
			case NONE:
			default:
				break;
		}
	}

	/**
	 * Rounding incoming time to day, up/down rounding defined by {@link Rounding}.
	 *
	 * @param time
	 * 		time in millis
	 * @param mode
	 * 		rounding mode
	 * @return rounded time in millis
	 */
	public static long roundTimeToDayTime(final long time, final Rounding mode) {
		if (mode == null || Rounding.NONE == mode)
			return time;
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		roundDayTime(cal, mode);
		return cal.getTimeInMillis();
	}

	/**
	 * Round date to maximum or minimum in day, depending of rounding mode.
	 *
	 * @param cal
	 * 		{@link Calendar} with configured date
	 * @param mode
	 * 		rounding mode
	 */
	public static void roundDayTime(final Calendar cal, final Rounding mode) {
		if (cal == null)
			throw new IllegalArgumentException("cal argument is null.");
		if (mode == null)
			return;
		switch (mode) {
			case MAX:
				cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
				cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
				cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
				cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
				return;
			case MIN:
				cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
				cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
				cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
				cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
				return;
			case NONE:
			default:
				break;
		}
	}


	/**
	 * Rounding incoming time to Month, up/down rounding defined by {@link Rounding}.
	 *
	 * @param time
	 * 		time in millis
	 * @param mode
	 * 		rounding mode
	 * @return rounded time in millis
	 */
	public static long roundTimeToMonthTime(final long time, final Rounding mode) {
		if (mode == null || Rounding.NONE == mode)
			return time;
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		roundMonthTime(cal, mode);
		return cal.getTimeInMillis();
	}

	/**
	 * Round date to maximum or minimum in month, depending of rounding mode.
	 *
	 * @param cal
	 * 		{@link Calendar} with configured date
	 * @param mode
	 * 		rounding mode
	 */
	public static void roundMonthTime(final Calendar cal, final Rounding mode) {
		if (cal == null)
			throw new IllegalArgumentException("cal argument is null.");
		if (mode == null || Rounding.NONE == mode)
			return;
		switch (mode) {
			case MAX:
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
				cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
				cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
				cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
				break;
			case MIN:
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
				cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
				cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
				cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
				cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
				break;
			case NONE:
			default:
				break;
		}
	}

	/**
	 * Rounding incoming time to Year, up/down rounding defined by {@link Rounding}.
	 *
	 * @param time
	 * 		time in millis
	 * @param mode
	 * 		rounding mode
	 * @return rounded time in millis
	 */
	public static long roundTimeToYearTime(final long time, final Rounding mode) {
		if (mode == null || Rounding.NONE == mode)
			return time;
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		roundYearTime(cal, mode);
		return cal.getTimeInMillis();
	}

	/**
	 * Round date to maximum or minimum in year, depending of rounding mode.
	 *
	 * @param cal
	 * 		{@link Calendar} with configured date
	 * @param mode
	 * 		rounding mode
	 */
	public static void roundYearTime(final Calendar cal, final Rounding mode) {
		if (cal == null)
			throw new IllegalArgumentException("cal argument is null.");
		if (mode == null || Rounding.NONE == mode)
			return;

		switch (mode) {
			case MAX:
				cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
				cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
				cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
				cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
				break;
			case MIN:
				cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
				cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
				cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
				cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
				cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
				break;
			case NONE:
			default:
				break;
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
		 * Round to min time.
		 */
		MAX,

		/**
		 * Round to max time.
		 */
		MIN;

		/**
		 * Default {@link Rounding}.
		 */
		public static final Rounding DEFAULT = NONE;

	}

}
