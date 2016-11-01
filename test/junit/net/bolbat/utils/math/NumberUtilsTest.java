package net.bolbat.utils.math;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static java.lang.Integer.valueOf;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.mutable.MutableInt;
import org.apache.commons.lang.mutable.MutableLong;
import org.apache.commons.lang.mutable.MutableShort;
import org.junit.Assert;
import org.junit.Test;

/**
 * {@link NumberUtils} test.
 *
 * @author Alexandr Bolbat
 */
public class NumberUtilsTest {

	/**
	 * Complex testing for numbers of same types.
	 */
	@Test
	public void testOnSameTypesComplex() {
		// byte negative
		Assert.assertEquals(0, NumberUtils.compare(Byte.MIN_VALUE, Byte.MIN_VALUE));
		Assert.assertEquals(-1, NumberUtils.compare(Byte.MIN_VALUE, Byte.MIN_VALUE + 1));
		Assert.assertEquals(1, NumberUtils.compare(Byte.MIN_VALUE + 1, Byte.MIN_VALUE));
		// byte positive
		Assert.assertEquals(0, NumberUtils.compare(Byte.MAX_VALUE, Byte.MAX_VALUE));
		Assert.assertEquals(-1, NumberUtils.compare(Byte.MAX_VALUE - 1, Byte.MAX_VALUE));
		Assert.assertEquals(1, NumberUtils.compare(Byte.MAX_VALUE, Byte.MAX_VALUE - 1));

		// short negative
		Assert.assertEquals(0, NumberUtils.compare(Short.MIN_VALUE, Short.MIN_VALUE));
		Assert.assertEquals(-1, NumberUtils.compare(Short.MIN_VALUE, Short.MIN_VALUE + 1));
		Assert.assertEquals(1, NumberUtils.compare(Short.MIN_VALUE + 1, Short.MIN_VALUE));
		// short positive
		Assert.assertEquals(0, NumberUtils.compare(Short.MAX_VALUE, Short.MAX_VALUE));
		Assert.assertEquals(-1, NumberUtils.compare(Short.MAX_VALUE - 1, Short.MAX_VALUE));
		Assert.assertEquals(1, NumberUtils.compare(Short.MAX_VALUE, Short.MAX_VALUE - 1));

		// integer negative
		Assert.assertEquals(0, NumberUtils.compare(MIN_VALUE, MIN_VALUE));
		Assert.assertEquals(-1, NumberUtils.compare(MIN_VALUE, MIN_VALUE + 1));
		Assert.assertEquals(1, NumberUtils.compare(MIN_VALUE + 1, MIN_VALUE));
		// integer positive
		Assert.assertEquals(0, NumberUtils.compare(MAX_VALUE, MAX_VALUE));
		Assert.assertEquals(-1, NumberUtils.compare(MAX_VALUE - 1, MAX_VALUE));
		Assert.assertEquals(1, NumberUtils.compare(MAX_VALUE, MAX_VALUE - 1));

		// big integer negative
		Assert.assertEquals(0, NumberUtils.compare(BigInteger.valueOf(Long.MIN_VALUE), BigInteger.valueOf(Long.MIN_VALUE)));
		Assert.assertEquals(-1, NumberUtils.compare(BigInteger.valueOf(Long.MIN_VALUE), BigInteger.valueOf(Long.MIN_VALUE + 1)));
		Assert.assertEquals(1, NumberUtils.compare(BigInteger.valueOf(Long.MIN_VALUE + 1), BigInteger.valueOf(Long.MIN_VALUE)));
		// big integer positive
		Assert.assertEquals(0, NumberUtils.compare(BigInteger.valueOf(Long.MAX_VALUE), BigInteger.valueOf(Long.MAX_VALUE)));
		Assert.assertEquals(-1, NumberUtils.compare(BigInteger.valueOf(Long.MAX_VALUE - 1), BigInteger.valueOf(Long.MAX_VALUE)));
		Assert.assertEquals(1, NumberUtils.compare(BigInteger.valueOf(Long.MAX_VALUE), BigInteger.valueOf(Long.MAX_VALUE - 1)));

		// long negative
		Assert.assertEquals(0, NumberUtils.compare(Long.MIN_VALUE, Long.MIN_VALUE));
		Assert.assertEquals(-1, NumberUtils.compare(Long.MIN_VALUE, Long.MIN_VALUE + 1));
		Assert.assertEquals(1, NumberUtils.compare(Long.MIN_VALUE + 1, Long.MIN_VALUE));
		// long positive
		Assert.assertEquals(0, NumberUtils.compare(Long.MAX_VALUE, Long.MAX_VALUE));
		Assert.assertEquals(-1, NumberUtils.compare(Long.MAX_VALUE - 1, Long.MAX_VALUE));
		Assert.assertEquals(1, NumberUtils.compare(Long.MAX_VALUE, Long.MAX_VALUE - 1));

		// float negative
		Assert.assertEquals(0, NumberUtils.compare(Float.MIN_VALUE, Float.MIN_VALUE));
		Assert.assertEquals(-1, NumberUtils.compare(Float.MIN_VALUE, Float.MIN_VALUE + 1));
		Assert.assertEquals(1, NumberUtils.compare(Float.MIN_VALUE + 1, Float.MIN_VALUE));
		// float positive
		Assert.assertEquals(0, NumberUtils.compare(Float.MAX_VALUE, Float.MAX_VALUE));
		Assert.assertEquals(-1, NumberUtils.compare(Float.MAX_VALUE / 2, Float.MAX_VALUE));
		Assert.assertEquals(1, NumberUtils.compare(Float.MAX_VALUE, Float.MAX_VALUE / 2));

		// double negative
		Assert.assertEquals(0, NumberUtils.compare(Double.MIN_VALUE, Double.MIN_VALUE));
		Assert.assertEquals(-1, NumberUtils.compare(Double.MIN_VALUE, Double.MIN_VALUE + 1));
		Assert.assertEquals(1, NumberUtils.compare(Double.MIN_VALUE + 1, Double.MIN_VALUE));
		// double positive
		Assert.assertEquals(0, NumberUtils.compare(Double.MAX_VALUE, Double.MAX_VALUE));
		Assert.assertEquals(-1, NumberUtils.compare(Double.MAX_VALUE / 2, Double.MAX_VALUE));
		Assert.assertEquals(1, NumberUtils.compare(Double.MAX_VALUE, Double.MAX_VALUE / 2));

		// big decimal negative
		Assert.assertEquals(0, NumberUtils.compare(BigDecimal.valueOf(Double.MIN_VALUE), BigDecimal.valueOf(Double.MIN_VALUE)));
		Assert.assertEquals(-1, NumberUtils.compare(BigDecimal.valueOf(Double.MIN_VALUE), BigDecimal.valueOf(Double.MIN_VALUE + 1)));
		Assert.assertEquals(1, NumberUtils.compare(BigDecimal.valueOf(Double.MIN_VALUE + 1), BigDecimal.valueOf(Double.MIN_VALUE)));
		// big decimal positive
		Assert.assertEquals(0, NumberUtils.compare(BigDecimal.valueOf(Double.MAX_VALUE), BigDecimal.valueOf(Double.MAX_VALUE)));
		Assert.assertEquals(-1, NumberUtils.compare(BigDecimal.valueOf(Double.MAX_VALUE).subtract(BigDecimal.ONE), BigDecimal.valueOf(Double.MAX_VALUE)));
		Assert.assertEquals(1, NumberUtils.compare(BigDecimal.valueOf(Double.MAX_VALUE), BigDecimal.valueOf(Double.MAX_VALUE).subtract(BigDecimal.ONE)));
	}

	/**
	 * Complex testing for numbers of different types.
	 */
	@Test
	public void testOnDifferentTypesComplex() {
		// byte with short
		Assert.assertEquals(0, NumberUtils.compare(Byte.MIN_VALUE, Short.valueOf(Byte.MIN_VALUE)));
		Assert.assertEquals(-1, NumberUtils.compare(Byte.MIN_VALUE, Short.valueOf(Byte.MIN_VALUE) + 1));
		Assert.assertEquals(1, NumberUtils.compare(Byte.MIN_VALUE + 1, Short.valueOf(Byte.MIN_VALUE)));
		// short with byte
		Assert.assertEquals(0, NumberUtils.compare(Short.valueOf(Byte.MIN_VALUE), Byte.MIN_VALUE));
		Assert.assertEquals(-1, NumberUtils.compare(Short.valueOf(Byte.MIN_VALUE), Byte.MIN_VALUE + 1));
		Assert.assertEquals(1, NumberUtils.compare(Short.valueOf(Byte.MIN_VALUE) + 1, Byte.MIN_VALUE));

		// long with integer
		Assert.assertEquals(0, NumberUtils.compare(Long.valueOf(MAX_VALUE), MAX_VALUE));
		Assert.assertEquals(-1, NumberUtils.compare(Long.valueOf(MAX_VALUE) - 1, MAX_VALUE));
		Assert.assertEquals(1, NumberUtils.compare(Long.valueOf(MAX_VALUE), MAX_VALUE - 1));

		// float with double
		Assert.assertEquals(-1, NumberUtils.compare(Float.MAX_VALUE, Double.MAX_VALUE));
		Assert.assertEquals(1, NumberUtils.compare(Double.MAX_VALUE, Float.MAX_VALUE));

		// BigInteger with BigDecimal
		Assert.assertEquals(-1, NumberUtils.compare(BigInteger.valueOf(Long.MAX_VALUE), BigDecimal.valueOf(Double.MAX_VALUE)));
		Assert.assertEquals(1, NumberUtils.compare(BigInteger.valueOf(MIN_VALUE), BigDecimal.valueOf(Long.MIN_VALUE)));

		// BigDecimal with BigDecimal
		Assert.assertEquals(1, NumberUtils.compare(BigDecimal.valueOf(Float.MIN_VALUE), BigDecimal.valueOf(Double.MIN_VALUE)));
		Assert.assertEquals(-1, NumberUtils.compare(BigDecimal.valueOf(Float.MAX_VALUE), BigDecimal.valueOf(Double.MAX_VALUE)));

		// atomic types
		Assert.assertEquals(0, NumberUtils.compare(new AtomicInteger(MAX_VALUE), new AtomicInteger(MAX_VALUE)));
		Assert.assertEquals(0, NumberUtils.compare(new AtomicLong(Long.MIN_VALUE), new AtomicLong(Long.MIN_VALUE)));
		Assert.assertEquals(0, NumberUtils.compare(new AtomicInteger(0), new AtomicLong(0)));
		Assert.assertEquals(1, NumberUtils.compare(new AtomicLong(1), new AtomicInteger(-1)));
		Assert.assertEquals(-1, NumberUtils.compare(new AtomicLong(-1), new AtomicInteger(1)));

		// other types
		Assert.assertEquals(0, NumberUtils.compare(new MutableInt(MAX_VALUE), new MutableInt(MAX_VALUE)));
		Assert.assertEquals(-1, NumberUtils.compare(new MutableLong(Long.MIN_VALUE), new MutableLong(Long.MAX_VALUE)));
		Assert.assertEquals(1, NumberUtils.compare(new MutableShort(Short.MAX_VALUE), new MutableShort(Short.MIN_VALUE)));

		// there can be any other combinations
	}

	/**
	 * Test error cases.
	 */
	@Test
	public void testErrorCases() {
		try {
			NumberUtils.compare(null, 0);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().contains("first"));
		}
		try {
			NumberUtils.compare(0, null);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e.getMessage().contains("second"));
		}
	}

	/**
	 * Test add cases.
	 */
	@Test
	public void testAddDifferentArgs() {
		// Big decimal
		Assert.assertEquals(new BigDecimal(0d), NumberUtils.add(new BigDecimal(-1), 1L));
		// vice versa
		Assert.assertEquals(new BigDecimal(0d), NumberUtils.add(1L, new BigDecimal(-1)));

		// Big int
		Assert.assertEquals(BigDecimal.valueOf(0L), NumberUtils.add(new BigInteger("-1"), 1L));
		// vice versa
		Assert.assertEquals(BigDecimal.valueOf(0L), NumberUtils.add(new BigInteger("-1"), 1L));

		// double
		Assert.assertEquals(BigDecimal.valueOf(0L), NumberUtils.add(-1d, 1L));
		// vice versa
		Assert.assertEquals(BigDecimal.valueOf(0L), NumberUtils.add(1L, -1d));


		// custom cases

		Assert.assertEquals(BigDecimal.valueOf(0L), NumberUtils.add(new Number() {
			private static final long serialVersionUID = -7234874693244521786L;

			@Override
			public int intValue() {
				return -1;
			}

			@Override
			public long longValue() {
				return -1;
			}

			@Override
			public float floatValue() {
				return -1f;
			}

			@Override
			public double doubleValue() {
				return -1D;
			}
		}, new Number() {
			private static final long serialVersionUID = 9074621109639847995L;

			@Override
			public int intValue() {
				return 1;
			}

			@Override
			public long longValue() {
				return 1L;
			}

			@Override
			public float floatValue() {
				return 1f;
			}

			@Override
			public double doubleValue() {
				return 1d;
			}
		}));
	}

	@Test
	public void testAddSameArgs(){
		// Big decimal
		Assert.assertEquals(new BigDecimal(0), NumberUtils.add(new BigDecimal(-1), new BigDecimal(1)));
		//Double
		Assert.assertEquals(0d, NumberUtils.add(1d, -1d));
		//float
		Assert.assertEquals(0f, NumberUtils.add(1f, -1f));
		//Long
		Assert.assertEquals(0L, NumberUtils.add(1L, -1L));
		//BigInt
		Assert.assertEquals(BigInteger.valueOf(0L), NumberUtils.add(BigInteger.valueOf(1L), BigInteger.valueOf(-1L)));
		// Int
		Assert.assertEquals(0, NumberUtils.add(1, -1));
		//short
		Assert.assertEquals(0, NumberUtils.add((short)1, (short)-1));
		//byte
		Assert.assertEquals(0, NumberUtils.add((byte)1, (byte)-1));

		//atomics
		Assert.assertEquals(0L, NumberUtils.add(new AtomicLong(-1), new AtomicLong(1)));
		//AtomicInt
		Assert.assertEquals(0, NumberUtils.add(new AtomicInteger(-1), new AtomicInteger(1)));
	}


	@Test
	public void testDefaultAddCases() {
		Assert.assertEquals(0, NumberUtils.add(null, null));
		Assert.assertEquals(valueOf(0), NumberUtils.add(0, null));
		Assert.assertEquals(valueOf(0), NumberUtils.add(null, valueOf(0)));
	}

}
