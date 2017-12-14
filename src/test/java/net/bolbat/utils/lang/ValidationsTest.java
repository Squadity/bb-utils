package net.bolbat.utils.lang;

import static net.bolbat.utils.lang.Validations.checkArgument;
import static net.bolbat.utils.lang.Validations.checkNotNull;
import static net.bolbat.utils.lang.Validations.checkState;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link Validations} test.
 * 
 * @author Alexandr Bolbat
 */
public class ValidationsTest {

	@Test
	public void checkArgumentTest() {
		// basic
		checkArgument(1 < 2);

		try {
			checkArgument(1 > 2);
			Assert.fail();
		} catch (final IllegalArgumentException e) {
			// ignored
		}

		// with message
		checkArgument(1 < 2, "must be true");

		try {
			checkArgument(1 > 2, "must not be true");
			Assert.fail();
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("must not be true", e.getMessage());
		}

		// with message template and arguments
		checkArgument(1 < 2, "%s must be less than %s", 1, 2);

		try {
			checkArgument(1 > 2, "%s must not be more than %s", 1, 2);
			Assert.fail();
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("1 must not be more than 2", e.getMessage());
		}

		// other cases
		try {
			checkArgument(false, null);
			Assert.fail();
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("null", e.getMessage());
		}
		try {
			checkArgument(false, null, 1, 2);
			Assert.fail();
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("null", e.getMessage());
		}
	}

	@Test
	public void checkStateTest() {
		// basic
		checkState(1 < 2);

		try {
			checkState(1 > 2);
			Assert.fail();
		} catch (final IllegalStateException e) {
			// ignored
		}

		// with message
		checkState(1 < 2, "must be true");

		try {
			checkState(1 > 2, "must not be true");
			Assert.fail();
		} catch (final IllegalStateException e) {
			Assert.assertEquals("must not be true", e.getMessage());
		}

		// with message template and arguments
		checkState(1 < 2, "%s must be less than %s", 1, 2);

		try {
			checkState(1 > 2, "%s must not be more than %s", 1, 2);
			Assert.fail();
		} catch (final IllegalStateException e) {
			Assert.assertEquals("1 must not be more than 2", e.getMessage());
		}

		// other cases
		try {
			checkState(false, null);
			Assert.fail();
		} catch (final IllegalStateException e) {
			Assert.assertEquals("null", e.getMessage());
		}
		try {
			checkState(false, null, 1, 2);
			Assert.fail();
		} catch (final IllegalStateException e) {
			Assert.assertEquals("null", e.getMessage());
		}
	}

	@Test
	public void checkNotNullTest() {
		final Object notNull = new Object();
		// basic
		checkNotNull(notNull);

		try {
			checkNotNull(null);
			Assert.fail();
		} catch (final NullPointerException e) {
			// ignored
		}

		// with message
		checkNotNull(notNull, "not null");

		try {
			checkNotNull(null, "is null");
			Assert.fail();
		} catch (final NullPointerException e) {
			Assert.assertEquals("is null", e.getMessage());
		}

		// with message template and arguments
		checkNotNull(notNull, "%s not null", "argument");

		try {
			checkNotNull(null, "%s is null", "argument");
			Assert.fail();
		} catch (final NullPointerException e) {
			Assert.assertEquals("argument is null", e.getMessage());
		}

		// other cases
		try {
			checkNotNull(null, null);
			Assert.fail();
		} catch (final NullPointerException e) {
			Assert.assertEquals("null", e.getMessage());
		}
		try {
			checkNotNull(null, null, "argument");
			Assert.fail();
		} catch (final NullPointerException e) {
			Assert.assertEquals("null", e.getMessage());
		}
	}

}
