package net.bolbat.utils.beanmapper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.bolbat.utils.beanmapper.BeanMapperConfiguration.ErrorStrategy;

/**
 * Abstract mapper functional.
 * 
 * @author Alexandr Bolbat
 */
public abstract class AbstractBeanMapper {

	/**
	 * Create bean instance from a given type.
	 * 
	 * @param beanClass
	 *            bean type
	 * @return bean instance
	 */
	public static final <T extends Object> T createInstance(final Class<T> beanClass) {
		try {
			return beanClass.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Create bean instance from a given type.
	 * 
	 * @param collectionClass
	 *            collection type
	 * @return collection instance
	 */
	public static final Collection<Object> createCollectionInstance(final Class<?> collectionClass) {
		@SuppressWarnings("unchecked")
		Collection<Object> created = (Collection<Object>) createInstance(collectionClass);
		return created;
	}

	/**
	 * Get field generic type. If generic type undefined {@link Object} type will be returned.
	 * 
	 * @param field
	 *            original field type
	 * @return generic type
	 */
	protected static final Class<?> getFieldGenericType(final Field field) {
		if (!ParameterizedType.class.isAssignableFrom(field.getGenericType().getClass()))
			return Object.class;

		ParameterizedType genericType = (ParameterizedType) field.getGenericType();
		if (genericType.getActualTypeArguments() == null || genericType.getActualTypeArguments().length == 0)
			return Object.class;

		return (Class<?>) genericType.getActualTypeArguments()[0];
	}

	/**
	 * Setting field value.
	 * 
	 * @param field
	 *            field
	 * @param bean
	 *            bean
	 * @param value
	 *            value
	 * @param conf
	 *            mapping configuration
	 */
	protected static final void setFieldValue(final Field field, final Object bean, final Object value, final BeanMapperConfiguration conf) {
		if (field == null || bean == null || value == null || conf == null)
			return;

		try {
			field.setAccessible(true);
			field.set(bean, value);
		} catch (IllegalArgumentException e) {
			if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
				throw new IllegalParameterException(field.getName(), value); // throwing custom runtime exception
		} catch (IllegalAccessException e) {
			if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
				throw new RuntimeException(e);
		}
	}

	/**
	 * Is parameters contains values for given bean scope.
	 * 
	 * @param parameters
	 *            parameters
	 * @param scope
	 *            scope
	 * @param conf
	 *            mapping configuration
	 * @return <code>true</code> if contains or <code>false</code>
	 */
	protected static final boolean isValuesInBeanScope(final Set<String> parameters, final String scope, final BeanMapperConfiguration conf) {
		if (parameters == null)
			throw new IllegalArgumentException("parameters aggument are null");
		if (scope == null)
			throw new IllegalArgumentException("scope aggument are null");

		String scopePrefix = scope + conf.getScopeDelimiter();
		for (String parameterName : parameters)
			if (parameterName.startsWith(scopePrefix))
				return true;

		return false;
	}

	/**
	 * Convert value to {@link Byte}.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param conf
	 *            configuration
	 * @return {@link Byte}
	 */
	protected static final Byte toByte(final String fieldName, final Object value, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || conf == null)
			return null;

		try {
			return Byte.valueOf(String.valueOf(value));
		} catch (NumberFormatException e) {
			if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
				throw new IllegalParameterException(fieldName, value);
		}

		return null;
	}

	/**
	 * Convert value to {@link Short}.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param conf
	 *            configuration
	 * @return {@link Short}
	 */
	protected static final Short toShort(final String fieldName, final Object value, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || conf == null)
			return null;

		try {
			return Short.valueOf(String.valueOf(value));
		} catch (NumberFormatException e) {
			if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
				throw new IllegalParameterException(fieldName, value);
		}

		return null;
	}

	/**
	 * Convert value to {@link Integer}.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param conf
	 *            configuration
	 * @return {@link Integer}
	 */
	protected static final Integer toInteger(final String fieldName, final Object value, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || conf == null)
			return null;

		try {
			return Integer.valueOf(String.valueOf(value));
		} catch (NumberFormatException e) {
			if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
				throw new IllegalParameterException(fieldName, value);
		}

		return null;
	}

	/**
	 * Convert value to {@link Long}.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param conf
	 *            configuration
	 * @return {@link Long}
	 */
	protected static final Long toLong(final String fieldName, final Object value, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || conf == null)
			return null;

		try {
			return Long.valueOf(String.valueOf(value));
		} catch (NumberFormatException e) {
			if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
				throw new IllegalParameterException(fieldName, value);
		}

		return null;
	}

	/**
	 * Convert value to {@link Float}.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param conf
	 *            configuration
	 * @return {@link Float}
	 */
	protected static final Float toFloat(final String fieldName, final Object value, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || conf == null)
			return null;

		try {
			return Float.valueOf(String.valueOf(value));
		} catch (NumberFormatException e) {
			if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
				throw new IllegalParameterException(fieldName, value);
		}

		return null;
	}

	/**
	 * Convert value to {@link Double}.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param conf
	 *            configuration
	 * @return {@link Double}
	 */
	protected static final Double toDouble(final String fieldName, final Object value, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || conf == null)
			return null;

		try {
			return Double.valueOf(String.valueOf(value));
		} catch (NumberFormatException e) {
			if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
				throw new IllegalParameterException(fieldName, value);
		}

		return null;
	}

	/**
	 * Convert value to {@link Boolean}.
	 * 
	 * @param value
	 *            value
	 * @return {@link Boolean}
	 */
	protected static final Boolean toBoolean(final Object value) {
		return Boolean.valueOf(String.valueOf(value));
	}

	/**
	 * Convert value to {@link Character}.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param conf
	 *            configuration
	 * @return {@link Character}
	 */
	protected static final Character toCharacter(final String fieldName, final Object value, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || conf == null)
			return null;

		String str = String.valueOf(value);
		if (str.length() == 0)
			return null;

		return Character.valueOf(str.charAt(0));
	}

	/**
	 * Convert value to {@link String}.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param conf
	 *            configuration
	 * @return {@link String}
	 */
	protected static final String toString(final String fieldName, final Object value, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || conf == null)
			return null;

		return String.valueOf(value);
	}

	/**
	 * Convert {@link String} array to {@link Byte} array.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param array
	 *            string array
	 * @param conf
	 *            configuration
	 * @return {@link Byte} array
	 */
	protected static final Byte[] toByteArray(final String fieldName, final Object value, final String[] array, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || array == null || conf == null)
			return null;

		Byte[] resultArray = new Byte[array.length];
		for (int i = 0; i < array.length; i++) {
			try {
				resultArray[i] = Byte.valueOf(array[i]);
			} catch (NumberFormatException e) {
				if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
					throw new IllegalParameterException(fieldName, value);

				return null;
			}
		}

		return resultArray;
	}

	/**
	 * Convert {@link String} array to {@link Short} array.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param array
	 *            string array
	 * @param conf
	 *            configuration
	 * @return {@link Short} array
	 */
	protected static final Short[] toShortArray(final String fieldName, final Object value, final String[] array, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || array == null || conf == null)
			return null;

		Short[] resultArray = new Short[array.length];
		for (int i = 0; i < array.length; i++) {
			try {
				resultArray[i] = Short.valueOf(array[i]);
			} catch (NumberFormatException e) {
				if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
					throw new IllegalParameterException(fieldName, value);

				return null;
			}
		}

		return resultArray;
	}

	/**
	 * Convert {@link String} array to {@link Integer} array.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param array
	 *            string array
	 * @param conf
	 *            configuration
	 * @return {@link Integer} array
	 */
	protected static final Integer[] toIntegerArray(final String fieldName, final Object value, final String[] array, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || array == null || conf == null)
			return null;

		Integer[] resultArray = new Integer[array.length];
		for (int i = 0; i < array.length; i++) {
			try {
				resultArray[i] = Integer.valueOf(array[i]);
			} catch (NumberFormatException e) {
				if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
					throw new IllegalParameterException(fieldName, value);

				return null;
			}
		}

		return resultArray;
	}

	/**
	 * Convert {@link String} array to {@link Long} array.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param array
	 *            string array
	 * @param conf
	 *            configuration
	 * @return {@link Long} array
	 */
	protected static final Long[] toLongArray(final String fieldName, final Object value, final String[] array, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || array == null || conf == null)
			return null;

		Long[] resultArray = new Long[array.length];
		for (int i = 0; i < array.length; i++) {
			try {
				resultArray[i] = Long.valueOf(array[i]);
			} catch (NumberFormatException e) {
				if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
					throw new IllegalParameterException(fieldName, value);

				return null;
			}
		}

		return resultArray;
	}

	/**
	 * Convert {@link String} array to {@link Float} array.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param array
	 *            string array
	 * @param conf
	 *            configuration
	 * @return {@link Float} array
	 */
	protected static final Float[] toFloatArray(final String fieldName, final Object value, final String[] array, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || array == null || conf == null)
			return null;

		Float[] resultArray = new Float[array.length];
		for (int i = 0; i < array.length; i++) {
			try {
				resultArray[i] = Float.valueOf(array[i]);
			} catch (NumberFormatException e) {
				if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
					throw new IllegalParameterException(fieldName, value);

				return null;
			}
		}

		return resultArray;
	}

	/**
	 * Convert {@link String} array to {@link Double} array.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param array
	 *            string array
	 * @param conf
	 *            configuration
	 * @return {@link Double} array
	 */
	protected static final Double[] toDoubleArray(final String fieldName, final Object value, final String[] array, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || array == null || conf == null)
			return null;

		Double[] resultArray = new Double[array.length];
		for (int i = 0; i < array.length; i++) {
			try {
				resultArray[i] = Double.valueOf(array[i]);
			} catch (NumberFormatException e) {
				if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
					throw new IllegalParameterException(fieldName, value);

				return null;
			}
		}

		return resultArray;
	}

	/**
	 * Convert {@link String} array to {@link Boolean} array.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param array
	 *            string array
	 * @param conf
	 *            configuration
	 * @return {@link Boolean} array
	 */
	protected static final Boolean[] toBooleanArray(final String fieldName, final Object value, final String[] array, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || array == null || conf == null)
			return null;

		Boolean[] resultArray = new Boolean[array.length];
		for (int i = 0; i < array.length; i++)
			resultArray[i] = Boolean.valueOf(array[i]);

		return resultArray;
	}

	/**
	 * Convert {@link String} array to {@link Character} array.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param array
	 *            string array
	 * @param conf
	 *            configuration
	 * @return {@link Character} array
	 */
	protected static final Character[] toCharacterArray(final String fieldName, final Object value, final String[] array, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || array == null || conf == null)
			return null;

		Character[] resultArray = new Character[array.length];
		for (int i = 0; i < array.length; i++) {
			String v = array[i];
			if (v.length() > 0)
				resultArray[i] = Character.valueOf(v.charAt(0));
		}

		return resultArray;
	}

	/**
	 * Convert {@link String} array to byte array.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param array
	 *            string array
	 * @param conf
	 *            configuration
	 * @return byte array
	 */
	protected static final byte[] toBytePrimitiveArray(final String fieldName, final Object value, final String[] array, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || array == null || conf == null)
			return null;

		byte[] resultArray = new byte[array.length];
		for (int i = 0; i < array.length; i++) {
			try {
				resultArray[i] = Byte.valueOf(array[i]);
			} catch (NumberFormatException e) {
				if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
					throw new IllegalParameterException(fieldName, value);

				return null;
			}
		}

		return resultArray;
	}

	/**
	 * Convert {@link String} array to short array.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param array
	 *            string array
	 * @param conf
	 *            configuration
	 * @return short array
	 */
	protected static final short[] toShortPrimitiveArray(final String fieldName, final Object value, final String[] array, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || array == null || conf == null)
			return null;

		short[] resultArray = new short[array.length];
		for (int i = 0; i < array.length; i++) {
			try {
				resultArray[i] = Short.valueOf(array[i]);
			} catch (NumberFormatException e) {
				if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
					throw new IllegalParameterException(fieldName, value);

				return null;
			}
		}

		return resultArray;
	}

	/**
	 * Convert {@link String} array to int array.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param array
	 *            string array
	 * @param conf
	 *            configuration
	 * @return int array
	 */
	protected static final int[] toIntegerPrimitiveArray(final String fieldName, final Object value, final String[] array, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || array == null || conf == null)
			return null;

		int[] resultArray = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			try {
				resultArray[i] = Integer.valueOf(array[i]);
			} catch (NumberFormatException e) {
				if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
					throw new IllegalParameterException(fieldName, value);

				return null;
			}
		}

		return resultArray;
	}

	/**
	 * Convert {@link String} array to long array.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param array
	 *            string array
	 * @param conf
	 *            configuration
	 * @return long array
	 */
	protected static final long[] toLongPrimitiveArray(final String fieldName, final Object value, final String[] array, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || array == null || conf == null)
			return null;

		long[] resultArray = new long[array.length];
		for (int i = 0; i < array.length; i++) {
			try {
				resultArray[i] = Long.valueOf(array[i]);
			} catch (NumberFormatException e) {
				if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
					throw new IllegalParameterException(fieldName, value);

				return null;
			}
		}

		return resultArray;
	}

	/**
	 * Convert {@link String} array to float array.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param array
	 *            string array
	 * @param conf
	 *            configuration
	 * @return float array
	 */
	protected static final float[] toFloatPrimitiveArray(final String fieldName, final Object value, final String[] array, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || array == null || conf == null)
			return null;

		float[] resultArray = new float[array.length];
		for (int i = 0; i < array.length; i++) {
			try {
				resultArray[i] = Float.valueOf(array[i]);
			} catch (NumberFormatException e) {
				if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
					throw new IllegalParameterException(fieldName, value);

				return null;
			}
		}

		return resultArray;
	}

	/**
	 * Convert {@link String} array to double array.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param array
	 *            string array
	 * @param conf
	 *            configuration
	 * @return double array
	 */
	protected static final double[] toDoublePrimitiveArray(final String fieldName, final Object value, final String[] array, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || array == null || conf == null)
			return null;

		double[] resultArray = new double[array.length];
		for (int i = 0; i < array.length; i++) {
			try {
				resultArray[i] = Double.valueOf(array[i]);
			} catch (NumberFormatException e) {
				if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
					throw new IllegalParameterException(fieldName, value);

				return null;
			}
		}

		return resultArray;
	}

	/**
	 * Convert {@link String} array to boolean array.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param array
	 *            string array
	 * @param conf
	 *            configuration
	 * @return boolean array
	 */
	protected static final boolean[] toBooleanPrimitiveArray(final String fieldName, final Object value, final String[] array,
			final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || array == null || conf == null)
			return null;

		boolean[] resultArray = new boolean[array.length];
		for (int i = 0; i < array.length; i++)
			resultArray[i] = Boolean.valueOf(array[i]);

		return resultArray;
	}

	/**
	 * Convert {@link String} array to char array.
	 * 
	 * @param fieldName
	 *            field name
	 * @param value
	 *            value
	 * @param array
	 *            string array
	 * @param conf
	 *            configuration
	 * @return char array
	 */
	protected static final char[] toCharacterPrimitiveArray(final String fieldName, final Object value, final String[] array, final BeanMapperConfiguration conf) {
		if (fieldName == null || value == null || array == null || conf == null)
			return null;

		char[] resultArray = new char[array.length];
		for (int i = 0; i < array.length; i++) {
			String v = array[i];
			if (v.length() > 0)
				resultArray[i] = Character.valueOf(v.charAt(0));
		}

		return resultArray;
	}

	/**
	 * Prepare basic type object from parameter value.
	 * 
	 * @param fieldClass
	 *            field class
	 * @param fieldName
	 *            field name
	 * @param value
	 *            original parameter value
	 * @param conf
	 *            mapping configuration
	 * @return {@link Object}
	 */
	public static final Object toBasicTypeFromValue(final Class<?> fieldClass, final String fieldName, final Object value, final BeanMapperConfiguration conf) {
		if (fieldClass == byte.class || fieldClass == Byte.class)
			return toByte(fieldName, value, conf);

		if (fieldClass == short.class || fieldClass == Short.class)
			return toShort(fieldName, value, conf);

		if (fieldClass == int.class || fieldClass == Integer.class)
			return toInteger(fieldName, value, conf);

		if (fieldClass == long.class || fieldClass == Long.class)
			return toLong(fieldName, value, conf);

		if (fieldClass == float.class || fieldClass == Float.class)
			return toFloat(fieldName, value, conf);

		if (fieldClass == double.class || fieldClass == Double.class)
			return toDouble(fieldName, value, conf);

		if (fieldClass == boolean.class || fieldClass == Boolean.class)
			return toBoolean(value);

		if (fieldClass == char.class || fieldClass == Character.class)
			return toCharacter(fieldName, value, conf);

		if (fieldClass == String.class)
			return toString(fieldName, value, conf);

		return null;
	}

	/**
	 * Check is field of simple type.
	 *
	 * @param field
	 * 		{@link Object} field
	 * @return {@code true} if field of simple type
	 */
	public static boolean isBasicType(final Object field) {
		if (field == null)
			return false;

		Class<?> fieldClass = field.getClass();

		if (fieldClass == byte.class || fieldClass == Byte.class)
			return true;

		if (fieldClass == short.class || fieldClass == Short.class)
			return true;

		if (fieldClass == int.class || fieldClass == Integer.class)
			return true;

		if (fieldClass == long.class || fieldClass == Long.class)
			return true;

		if (fieldClass == float.class || fieldClass == Float.class)
			return true;

		if (fieldClass == double.class || fieldClass == Double.class)
			return true;

		if (fieldClass == boolean.class || fieldClass == Boolean.class)
			return true;

		if (fieldClass == char.class || fieldClass == Character.class)
			return true;

		if (fieldClass == String.class)
			return true;

		return false;
	}

	/**
	 * Check is field is array.
	 *
	 * @param field
	 * 		{@link Object} field
	 * @return {@code true} if field is array
	 */
	public static boolean isArrayType(final Object field) {
		return field != null && field.getClass().isArray();

	}

	/**
	 * Check is field is {@link java.util.List}.
	 *
	 * @param field
	 * 		{@link Field} field
	 * @return {@code true} if field is {@link java.util.List}
	 */
	public static boolean isListType(final Object field) {
		return field != null && field instanceof List;

	}

	/**
	 * Check is field is {@link Set}.
	 *
	 * @param field
	 * 		{@link Object} field
	 * @return {@code true} if field is {@link Set}
	 */
	public static boolean isSetType(final Object field) {
		return field != null && field instanceof Set;

	}

	/**
	 * Check is field is {@link java.util.Map}.
	 *
	 * @param field
	 * 		{@link Object} field
	 * @return {@code true} if field is {@link java.util.Map}
	 */
	public static boolean isMapType(final Object field) {
		return field != null && field instanceof Map;

	}

}
