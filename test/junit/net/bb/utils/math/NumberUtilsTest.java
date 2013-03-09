package net.bb.utils.math;

import java.math.BigDecimal;
import java.math.BigInteger;

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
		Assert.assertEquals(0, NumberUtils.compare(Integer.MIN_VALUE, Integer.MIN_VALUE));
		Assert.assertEquals(-1, NumberUtils.compare(Integer.MIN_VALUE, Integer.MIN_VALUE + 1));
		Assert.assertEquals(1, NumberUtils.compare(Integer.MIN_VALUE + 1, Integer.MIN_VALUE));
		// integer positive
		Assert.assertEquals(0, NumberUtils.compare(Integer.MAX_VALUE, Integer.MAX_VALUE));
		Assert.assertEquals(-1, NumberUtils.compare(Integer.MAX_VALUE - 1, Integer.MAX_VALUE));
		Assert.assertEquals(1, NumberUtils.compare(Integer.MAX_VALUE, Integer.MAX_VALUE - 1));

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
		Assert.assertEquals(0, NumberUtils.compare(Long.valueOf(Integer.MAX_VALUE), Integer.MAX_VALUE));
		Assert.assertEquals(-1, NumberUtils.compare(Long.valueOf(Integer.MAX_VALUE) - 1, Integer.MAX_VALUE));
		Assert.assertEquals(1, NumberUtils.compare(Long.valueOf(Integer.MAX_VALUE), Integer.MAX_VALUE - 1));

		// float with double
		Assert.assertEquals(-1, NumberUtils.compare(Float.MAX_VALUE, Double.MAX_VALUE));
		Assert.assertEquals(1, NumberUtils.compare(Double.MAX_VALUE, Float.MAX_VALUE));

		// BigInteger with BigDecimal
		Assert.assertEquals(-1, NumberUtils.compare(BigInteger.valueOf(Long.MAX_VALUE), BigDecimal.valueOf(Double.MAX_VALUE)));
		Assert.assertEquals(1, NumberUtils.compare(BigInteger.valueOf(Integer.MIN_VALUE), BigDecimal.valueOf(Long.MIN_VALUE)));

		// BigDecimal with BigDecimal
		Assert.assertEquals(1, NumberUtils.compare(BigDecimal.valueOf(Float.MIN_VALUE), BigDecimal.valueOf(Double.MIN_VALUE)));
		Assert.assertEquals(-1, NumberUtils.compare(BigDecimal.valueOf(Float.MAX_VALUE), BigDecimal.valueOf(Double.MAX_VALUE)));

		// there can be any other combinations
	}

}
