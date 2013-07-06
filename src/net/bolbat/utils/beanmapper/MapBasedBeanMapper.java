package net.bolbat.utils.beanmapper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.bolbat.utils.beanmapper.BeanMapperConfiguration.ErrorStrategy;

/**
 * Mapper implementation for map properties from {@link Map} to bean.
 * 
 * @author Alexandr Bolbat
 */
public class MapBasedBeanMapper extends AbstractBeanMapper {

	/**
	 * Map bean with parameters from {@link Map}.
	 * 
	 * @param parameters
	 *            parameters
	 * @param bean
	 *            bean
	 * @param configuration
	 *            mapping configuration
	 * @return mapped bean
	 */
	public static <T extends Object> T map(final Map<String, Object> parameters, final T bean, final BeanMapperConfiguration configuration) {
		if (parameters == null)
			throw new IllegalArgumentException("parameters argument are null");
		if (bean == null)
			throw new IllegalArgumentException("bean argument are null");
		if (configuration == null)
			throw new IllegalArgumentException("configuration argument are null");

		return map(parameters, bean, configuration.getRootScope(), configuration);
	}

	/**
	 * Map bean with parameters from {@link Map}.
	 * 
	 * @param parameters
	 *            parameters
	 * @param bean
	 *            bean
	 * @param scope
	 *            scope
	 * @param configuration
	 *            mapping configuration
	 * @return mapped bean
	 */
	private static <T extends Object> T map(final Map<String, Object> parameters, final T bean, final String scope, final BeanMapperConfiguration configuration) {
		Field[] fields = bean.getClass().getDeclaredFields(); // reading bean fields
		for (Field field : fields) {
			String fieldName = field.getName(); // field name

			int modifier = field.getModifiers();
			if (Modifier.isAbstract(modifier) || Modifier.isFinal(modifier)) // skipping not needed fields
				continue;

			String scopePrefix = scope.equals(configuration.getRootScope()) ? configuration.getRootScope() : scope + configuration.getScopeDelimiter();
			Object value = parameters.get(scopePrefix + fieldName);

			// Map as collection
			if (mapAsCollection(parameters, field, bean, value, scopePrefix, configuration))
				continue;

			// Map as map, should be implemented in next releases
			if (Map.class.isAssignableFrom(field.getType()))
				continue;

			// Map as array
			if (mapAsArrayType(parameters, field, bean, value, scopePrefix, configuration))
				continue;

			// Map as basic type
			if (mapAsBasicType(field, bean, value, configuration))
				continue;

			// Map as bean
			mapAsBean(parameters, field, bean, scopePrefix, configuration);
		}

		return bean;
	}

	/**
	 * Map as collection.
	 * 
	 * @param parameters
	 *            parameters
	 * @param field
	 *            field
	 * @param bean
	 *            bean
	 * @param value
	 *            value
	 * @param scopePrefix
	 *            scope prefix
	 * @param conf
	 *            mapping configuration
	 * @return <code>true</code> if mapped or <code>false</code>
	 */
	private static boolean mapAsCollection(final Map<String, Object> parameters, final Field field, final Object bean, final Object value,
			final String scopePrefix, final BeanMapperConfiguration conf) {
		Class<?> fieldClass = field.getType(); // field type
		if (!Collection.class.isAssignableFrom(fieldClass)) // if this field not collection we are skipping this mapping
			return false;

		if (value == null) // if value are null we are saying about completed mapping
			return true;

		String[] strArray = String.valueOf(value).split(conf.getListDelimiter());
		if (strArray == null || strArray.length == 0) // if source array are null or empty we are saying about completed mapping
			return true;

		Collection<Object> collection = null;
		if (fieldClass.isInterface()) {
			if (Set.class.isAssignableFrom(fieldClass))
				collection = createCollectionInstance(HashSet.class);

			if (List.class.isAssignableFrom(fieldClass))
				collection = createCollectionInstance(ArrayList.class);

			if (collection == null) {
				if (conf.getErrorStrategy() == ErrorStrategy.THROW_EXCEPTIONS)
					throw new UnsupportedTypeException(fieldClass);

				return true; // if not created because of unsupported interface we are saying about completed mapping
			}
		}

		if (collection == null)
			collection = createCollectionInstance(fieldClass);

		// Map as basic types collection
		Object[] array = toBasicTypesArrayFromValue(getFieldGenericType(field), field.getName(), value, strArray, conf);
		if (array != null) {
			for (Object element : array)
				collection.add(element);

			setFieldValue(field, bean, collection, conf);
			return true;
		}

		// Map as bean's collection
		array = toBeanArrayFromValue(parameters, field, getFieldGenericType(field), scopePrefix, conf);
		if (array == null)
			return true;

		for (Object element : array)
			collection.add(element);

		setFieldValue(field, bean, collection, conf);
		return true;
	}

	/**
	 * Map as array.
	 * 
	 * @param parameters
	 *            parameters
	 * @param field
	 *            field
	 * @param bean
	 *            bean
	 * @param value
	 *            value
	 * @param scopePrefix
	 *            scope prefix
	 * @param conf
	 *            mapping configuration
	 * @return <code>true</code> if mapped or <code>false</code>
	 */
	private static boolean mapAsArrayType(final Map<String, Object> parameters, final Field field, final Object bean, final Object value,
			final String scopePrefix, final BeanMapperConfiguration conf) {
		Class<?> fieldClass = field.getType();
		String fieldName = field.getName();

		if (!fieldClass.isArray())
			return false;

		// Map as array of primitive types
		String[] strArray = String.valueOf(value).split(conf.getArrayDelimiter());
		if (fieldClass.getComponentType() == byte.class) {
			byte[] arrayValue = toBytePrimitiveArray(fieldName, value, strArray, conf);
			setFieldValue(field, bean, arrayValue, conf);
			return true;
		}

		if (fieldClass.getComponentType() == short.class) {
			short[] arrayValue = toShortPrimitiveArray(fieldName, value, strArray, conf);
			setFieldValue(field, bean, arrayValue, conf);
			return true;
		}

		if (fieldClass.getComponentType() == int.class) {
			int[] arrayValue = toIntegerPrimitiveArray(fieldName, value, strArray, conf);
			setFieldValue(field, bean, arrayValue, conf);
			return true;
		}

		if (fieldClass.getComponentType() == long.class) {
			long[] arrayValue = toLongPrimitiveArray(fieldName, value, strArray, conf);
			setFieldValue(field, bean, arrayValue, conf);
			return true;
		}

		if (fieldClass.getComponentType() == float.class) {
			float[] arrayValue = toFloatPrimitiveArray(fieldName, value, strArray, conf);
			setFieldValue(field, bean, arrayValue, conf);
			return true;
		}

		if (fieldClass.getComponentType() == double.class) {
			double[] arrayValue = toDoublePrimitiveArray(fieldName, value, strArray, conf);
			setFieldValue(field, bean, arrayValue, conf);
			return true;
		}

		if (fieldClass.getComponentType() == boolean.class) {
			boolean[] arrayValue = toBooleanPrimitiveArray(fieldName, value, strArray, conf);
			setFieldValue(field, bean, arrayValue, conf);
			return true;
		}

		// Map as array of basic types
		Object[] arrayValue = toBasicTypesArrayFromValue(fieldClass.getComponentType(), fieldName, value, strArray, conf);
		if (arrayValue != null) {
			setFieldValue(field, bean, arrayValue, conf);
			return true;
		}

		arrayValue = toBeanArrayFromValue(parameters, field, fieldClass.getComponentType(), scopePrefix, conf);
		setFieldValue(field, bean, arrayValue, conf);
		return true;
	}

	/**
	 * Map as basic type.
	 * 
	 * @param field
	 *            field
	 * @param bean
	 *            bean
	 * @param value
	 *            value
	 * @param conf
	 *            mapping configuration
	 * @return <code>true</code> if mapped or <code>false</code>
	 */
	private static boolean mapAsBasicType(final Field field, final Object bean, final Object value, final BeanMapperConfiguration conf) {
		Object basicValue = toBasicTypeFromValue(field.getType(), field.getName(), value, conf);
		if (basicValue == null)
			return false;

		// Map as basic type
		setFieldValue(field, bean, basicValue, conf);
		return true;
	}

	/**
	 * Map as bean.
	 * 
	 * @param parameters
	 *            parameters
	 * @param field
	 *            field
	 * @param bean
	 *            bean
	 * @param scopePrefix
	 *            scope prefix
	 * @param conf
	 *            mapping configuration
	 * @return <code>true</code> if mapped or <code>false</code>
	 */
	private static <T extends Object> boolean mapAsBean(final Map<String, Object> parameters, final Field field, final Object bean, final String scopePrefix,
			final BeanMapperConfiguration conf) {
		String beanScope = scopePrefix + field.getName();
		if (!isValuesInBeanScope(parameters.keySet(), beanScope, conf))
			return false;

		// Map as bean
		@SuppressWarnings("unchecked")
		T innerBean = (T) toBeanFromValue(parameters, field.getType(), beanScope, conf);
		setFieldValue(field, bean, innerBean, conf); // setting field value with mapped nested bean instance
		return true;
	}

	/**
	 * Prepare basic type object from parameter value.
	 * 
	 * @param arrayType
	 *            array component type
	 * @param fieldName
	 *            field name
	 * @param value
	 *            original parameter value
	 * @param strArray
	 *            parameter array, constructed from the original parameter value
	 * @param conf
	 *            mapping configuration
	 * @return {@link Object} array
	 */
	private static Object[] toBasicTypesArrayFromValue(final Class<?> arrayType, final String fieldName, final Object value, final String[] strArray,
			final BeanMapperConfiguration conf) {
		if (arrayType == Byte.class)
			return toByteArray(fieldName, value, strArray, conf);

		if (arrayType == Short.class)
			return toShortArray(fieldName, value, strArray, conf);

		if (arrayType == Integer.class)
			return toIntegerArray(fieldName, value, strArray, conf);

		if (arrayType == Long.class)
			return toLongArray(fieldName, value, strArray, conf);

		if (arrayType == Float.class)
			return toFloatArray(fieldName, value, strArray, conf);

		if (arrayType == Double.class)
			return toDoubleArray(fieldName, value, strArray, conf);

		if (arrayType == Boolean.class)
			return toBooleanArray(fieldName, value, strArray, conf);

		if (arrayType == String.class)
			return strArray;

		return null;
	}

	/**
	 * Prepare beans array from parameters.
	 * 
	 * @param parameters
	 *            parameters
	 * @param field
	 *            field
	 * @param scope
	 *            bean scope
	 * @param conf
	 *            mapping configuration
	 * @return mapped array of beans
	 */
	private static <T extends Object> T[] toBeanArrayFromValue(final Map<String, Object> parameters, final Field field, final Class<T> arrayType,
			final String scope, final BeanMapperConfiguration conf) {

		Set<String> beansInSet = new TreeSet<String>();
		String arrayScopePrefix = scope + field.getName() + conf.getBeanArrayDelimiter();
		for (String key : parameters.keySet())
			if (key.startsWith(arrayScopePrefix)) {
				int beanScopeEndIndex = key.indexOf(conf.getScopeDelimiter(), arrayScopePrefix.length());
				beansInSet.add(key.substring(0, beanScopeEndIndex));
			}

		if (beansInSet.size() == 0)
			return null;

		List<String> beansInList = new ArrayList<String>(beansInSet);
		@SuppressWarnings("unchecked")
		T[] result = (T[]) Array.newInstance(arrayType, beansInSet.size());
		for (int i = 0; i < beansInSet.size(); i++)
			result[i] = toBeanFromValue(parameters, arrayType, beansInList.get(i), conf);

		return result;
	}

	/**
	 * Prepare bean object from parameters.
	 * 
	 * @param parameters
	 *            parameters
	 * @param type
	 *            bean type
	 * @param scope
	 *            bean scope
	 * @param conf
	 *            mapping configuration
	 * @return mapped bean
	 */
	private static <T extends Object> T toBeanFromValue(final Map<String, Object> parameters, final Class<T> type, final String scope,
			final BeanMapperConfiguration conf) {
		T bean = createInstance(type);
		bean = map(parameters, bean, scope, conf);
		return bean;
	}

}
