package net.bolbat.utils.lang;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link TimeUtilsTest} test.
 *
 * @author Alexandr Bolbat
 */

public class TimeUtilsTest {

	@Test
	public void generalTest() {

		// check TimeUnit values

		Assert.assertEquals("Should be equal.", Calendar.YEAR, TimeUtils.TimeUnit.YEAR.getMappedCalendarField());
		Assert.assertEquals("Should be equal.", Calendar.MONTH, TimeUtils.TimeUnit.MONTH.getMappedCalendarField());
		Assert.assertEquals("Should be equal.", Calendar.DAY_OF_MONTH, TimeUtils.TimeUnit.DAY.getMappedCalendarField());
		Assert.assertEquals("Should be equal.", Calendar.HOUR_OF_DAY, TimeUtils.TimeUnit.HOUR.getMappedCalendarField());
		Assert.assertEquals("Should be equal.", Calendar.MINUTE, TimeUtils.TimeUnit.MINUTE.getMappedCalendarField());
		Assert.assertEquals("Should be equal.", Calendar.SECOND, TimeUtils.TimeUnit.SECOND.getMappedCalendarField());

		// check rounding

		checkRound(TimeUtils.TimeUnit.SECOND, TimeUtils.Rounding.DOWN);
		checkRound(TimeUtils.TimeUnit.SECOND, TimeUtils.Rounding.UP);

		checkRound(TimeUtils.TimeUnit.MINUTE, TimeUtils.Rounding.DOWN);
		checkRound(TimeUtils.TimeUnit.MINUTE, TimeUtils.Rounding.UP);

		checkRound(TimeUtils.TimeUnit.HOUR, TimeUtils.Rounding.DOWN);
		checkRound(TimeUtils.TimeUnit.HOUR, TimeUtils.Rounding.UP);

		checkRound(TimeUtils.TimeUnit.DAY, TimeUtils.Rounding.DOWN);
		checkRound(TimeUtils.TimeUnit.DAY, TimeUtils.Rounding.UP);

		checkRound(TimeUtils.TimeUnit.MONTH, TimeUtils.Rounding.DOWN);
		checkRound(TimeUtils.TimeUnit.MONTH, TimeUtils.Rounding.UP);

		checkRound(TimeUtils.TimeUnit.YEAR, TimeUtils.Rounding.DOWN);
		checkRound(TimeUtils.TimeUnit.YEAR, TimeUtils.Rounding.UP);

		// check 'from now'

		checkFromNow(1, TimeUtils.TimeUnit.YEAR);
		checkFromNow(-1, TimeUtils.TimeUnit.YEAR);

		checkFromNow(1, TimeUtils.TimeUnit.MONTH);
		checkFromNow(-1, TimeUtils.TimeUnit.MONTH);

		checkFromNow(1, TimeUtils.TimeUnit.DAY);
		checkFromNow(-1, TimeUtils.TimeUnit.DAY);

		checkFromNow(1, TimeUtils.TimeUnit.HOUR);
		checkFromNow(-1, TimeUtils.TimeUnit.HOUR);

		checkFromNow(1, TimeUtils.TimeUnit.MINUTE);
		checkFromNow(-1, TimeUtils.TimeUnit.MINUTE);

		checkFromNow(1, TimeUtils.TimeUnit.SECOND);
		checkFromNow(-1, TimeUtils.TimeUnit.SECOND);

		// check 'from now' with no rounding param

		int fromNow = 1;
		TimeUtils.TimeUnit timeUnit = TimeUtils.TimeUnit.DAY;
		Calendar calendar = Calendar.getInstance();
		calendar.add(timeUnit.getMappedCalendarField(), fromNow);

		// manual calculation
		roundCalendarManually(calendar, timeUnit, TimeUtils.Rounding.UP);
		long fromNowRoundUpByCalendar = calendar.getTimeInMillis();

		// util calculation
		long fromNowRoundUpByUtil = TimeUtils.fromNow(fromNow, timeUnit);
		calendar.setTimeInMillis(fromNowRoundUpByUtil);
		roundCalendarManually(calendar, timeUnit, TimeUtils.Rounding.UP);

		Assert.assertEquals("Test time unit[" + timeUnit + "]. Values should be equal", fromNowRoundUpByCalendar, calendar.getTimeInMillis());
	}

	@Test
	public void errorCaseTests() {
		try {
			TimeUtils.fromNow(0, null, TimeUtils.Rounding.DOWN);
			Assert.fail("Should fail cause param is wrong.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e instanceof IllegalArgumentException);
		}
		try {
			TimeUtils.fromNow(0, null);
			Assert.fail("Should fail cause param is wrong.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e instanceof IllegalArgumentException);
		}
		try {
			TimeUtils.round(null, TimeUtils.TimeUnit.SECOND, TimeUtils.Rounding.DOWN);
			Assert.fail("Should fail cause param is wrong.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e instanceof IllegalArgumentException);
		}
		try {
			TimeUtils.round(Calendar.getInstance(), null, TimeUtils.Rounding.DOWN);
			Assert.fail("Should fail cause param is wrong.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e instanceof IllegalArgumentException);
		}
		try {
			TimeUtils.round(Calendar.getInstance().getTimeInMillis(), null, TimeUtils.Rounding.DOWN);
			Assert.fail("Should fail cause param is wrong.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e instanceof IllegalArgumentException);
		}
	}

	/**
	 * Check method 'round'.
	 *
	 * @param timeUnit
	 *            {@link TimeUtils.TimeUnit}
	 * @param rounding
	 *            {@link TimeUtils.Rounding}
	 */
	private void checkRound(TimeUtils.TimeUnit timeUnit, TimeUtils.Rounding rounding) {
		Calendar calendar = Calendar.getInstance();
		roundCalendarManually(calendar, timeUnit, rounding);
		Calendar utilCal = Calendar.getInstance();
		TimeUtils.round(utilCal, timeUnit, rounding);
		Assert.assertEquals("Round value should be same", calendar.getTimeInMillis(), utilCal.getTimeInMillis());
	}

	/**
	 * Check method 'from now'.
	 *
	 * @param amount
	 *            amount of time units from now
	 * @param timeUnit
	 *            {@link TimeUtils.TimeUnit}
	 */
	private void checkFromNow(int amount, TimeUtils.TimeUnit timeUnit) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(timeUnit.getMappedCalendarField(), amount);

		// manual calculation
		roundCalendarManually(calendar, timeUnit, TimeUtils.Rounding.UP);
		long fromNowRoundUpByCalendar = calendar.getTimeInMillis();
		roundCalendarManually(calendar, timeUnit, TimeUtils.Rounding.DOWN);
		long fromNowRoundDownByCalendar = calendar.getTimeInMillis();

		// util calculation
		long fromNowRoundUpByUtil = TimeUtils.fromNow(amount, timeUnit, TimeUtils.Rounding.UP);
		long fromNowRoundDownByUtil = TimeUtils.fromNow(amount, timeUnit, TimeUtils.Rounding.DOWN);

		Assert.assertEquals("Test time unit[" + timeUnit + "]. Values should be equal", fromNowRoundUpByCalendar, fromNowRoundUpByUtil);
		Assert.assertEquals("Test time unit[" + timeUnit + "]. Values should be equal", fromNowRoundDownByCalendar, fromNowRoundDownByUtil);
	}

	/**
	 * Round calendar manually.
	 *
	 * @param cal
	 *            {@link Calendar}
	 * @param timeUnit
	 *            {@link TimeUtils.TimeUnit}
	 * @param rounding
	 *            {@link TimeUtils.Rounding}
	 */
	private void roundCalendarManually(Calendar cal, TimeUtils.TimeUnit timeUnit, TimeUtils.Rounding rounding) {
		if (rounding == null || TimeUtils.Rounding.NONE.equals(rounding))
			return;

		boolean roundDown = TimeUtils.Rounding.DOWN.equals(rounding);

		switch (timeUnit) {
			case SECOND:
				cal.set(Calendar.MILLISECOND, roundDown ? cal.getActualMinimum(Calendar.MILLISECOND) : cal.getActualMaximum(Calendar.MILLISECOND));
				break;
			case MINUTE:
				cal.set(Calendar.MILLISECOND, roundDown ? cal.getActualMinimum(Calendar.MILLISECOND) : cal.getActualMaximum(Calendar.MILLISECOND));
				cal.set(Calendar.SECOND, roundDown ? cal.getActualMinimum(Calendar.SECOND) : cal.getActualMaximum(Calendar.SECOND));
				break;
			case HOUR:
				cal.set(Calendar.MILLISECOND, roundDown ? cal.getActualMinimum(Calendar.MILLISECOND) : cal.getActualMaximum(Calendar.MILLISECOND));
				cal.set(Calendar.SECOND, roundDown ? cal.getActualMinimum(Calendar.SECOND) : cal.getActualMaximum(Calendar.SECOND));
				cal.set(Calendar.MINUTE, roundDown ? cal.getActualMinimum(Calendar.MINUTE) : cal.getActualMaximum(Calendar.MINUTE));
				break;
			case DAY:
				cal.set(Calendar.MILLISECOND, roundDown ? cal.getActualMinimum(Calendar.MILLISECOND) : cal.getActualMaximum(Calendar.MILLISECOND));
				cal.set(Calendar.SECOND, roundDown ? cal.getActualMinimum(Calendar.SECOND) : cal.getActualMaximum(Calendar.SECOND));
				cal.set(Calendar.MINUTE, roundDown ? cal.getActualMinimum(Calendar.MINUTE) : cal.getActualMaximum(Calendar.MINUTE));
				cal.set(Calendar.HOUR_OF_DAY, roundDown ? cal.getActualMinimum(Calendar.HOUR_OF_DAY) : cal.getActualMaximum(Calendar.HOUR_OF_DAY));
				break;
			case MONTH:
				cal.set(Calendar.MILLISECOND, roundDown ? cal.getActualMinimum(Calendar.MILLISECOND) : cal.getActualMaximum(Calendar.MILLISECOND));
				cal.set(Calendar.SECOND, roundDown ? cal.getActualMinimum(Calendar.SECOND) : cal.getActualMaximum(Calendar.SECOND));
				cal.set(Calendar.MINUTE, roundDown ? cal.getActualMinimum(Calendar.MINUTE) : cal.getActualMaximum(Calendar.MINUTE));
				cal.set(Calendar.HOUR_OF_DAY, roundDown ? cal.getActualMinimum(Calendar.HOUR_OF_DAY) : cal.getActualMaximum(Calendar.HOUR_OF_DAY));
				cal.set(Calendar.DAY_OF_MONTH, roundDown ? cal.getActualMinimum(Calendar.DAY_OF_MONTH) : cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				break;
			case YEAR:
				cal.set(Calendar.MILLISECOND, roundDown ? cal.getActualMinimum(Calendar.MILLISECOND) : cal.getActualMaximum(Calendar.MILLISECOND));
				cal.set(Calendar.SECOND, roundDown ? cal.getActualMinimum(Calendar.SECOND) : cal.getActualMaximum(Calendar.SECOND));
				cal.set(Calendar.MINUTE, roundDown ? cal.getActualMinimum(Calendar.MINUTE) : cal.getActualMaximum(Calendar.MINUTE));
				cal.set(Calendar.HOUR_OF_DAY, roundDown ? cal.getActualMinimum(Calendar.HOUR_OF_DAY) : cal.getActualMaximum(Calendar.HOUR_OF_DAY));
				cal.set(Calendar.DAY_OF_MONTH, roundDown ? cal.getActualMinimum(Calendar.DAY_OF_MONTH) : cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				cal.set(Calendar.MONTH, roundDown ? cal.getActualMinimum(Calendar.MONTH) : cal.getActualMaximum(Calendar.MONTH));
				break;
		}
	}
}
