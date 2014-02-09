package net.bolbat.utils.beanmapper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.bolbat.utils.beanmapper.BeanMapperConfiguration.ErrorStrategy;
import net.bolbat.utils.lang.StringUtils;

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
			if (mapAsMapType(parameters, field, bean, value, scopePrefix, configuration))
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
	 * Map parameters {@link Map} from bean.
	 *
	 * @param bean
	 * 		{@link Object} instance that should be stored
	 * @param configuration
	 * 		mapping configuration
	 * @return {@link Map} of properties and values
	 */
	public static <T extends Object> Map<String, Object> map(final T bean, final BeanMapperConfiguration configuration) {
		if (bean == null)
			throw new IllegalArgumentException("beam argument are null");
		if (configuration == null)
			throw new IllegalArgumentException("configuration argument are null");

		return map(bean, configuration.getRootScope(),configuration);
	}

	/**
	 * Map parameters {@link Map} from bean.
	 *
	 * @param bean
	 * 		{@link Object} instance that should be stored
	 * @param configuration
	 * 		mapping configuration
	 * @return {@link Map} of properties and values
	 */
	private static <T extends Object> Map<String, Object> map(final T bean, final String scope, final BeanMapperConfiguration configuration) {
		Class<?> clazz = bean.getClass();

		Map<String, Object> propertyMap = new HashMap<String, Object>();
		for (Field field : clazz.getDeclaredFields()) {
			try {
				field.setAccessible(true);
				final String fieldName = field.getName(); // field name
				final Object fieldValue = field.get(bean);

				//create current scope
				StringBuilder currScope = makeStringFromArgs(!StringUtils.isEmpty(scope) ? scope : null, !StringUtils.isEmpty(scope) ? configuration.getScopeDelimiter() : null, fieldName);
				if (isBasicType(fieldValue)) {
					propertyMap.put(currScope.toString(), fieldValue);

					continue;
				}

				if (isArrayType(fieldValue)) {
					propertyMap.putAll(mapFromArrayType(field, fieldValue, scope, configuration));

					continue;
				}

				if (isListType(fieldValue)) {
					propertyMap.putAll(mapFromListType(field, fieldValue, scope, configuration));

					continue;
				}

				if (isSetType(fieldValue)) {
					propertyMap.putAll(mapFromSetType(field, fieldValue, scope, configuration));

					continue;
				}

				if (isMapType(fieldValue)) {
					propertyMap.putAll(mapFromMapType(field, fieldValue, scope, configuration));

					continue;
				}

				if (fieldValue != null)
					propertyMap.putAll(map(fieldValue, currScope.toString(), configuration));

			} catch (IllegalAccessException e) {
				//can't read field skipp
			}
		}

		return propertyMap;
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

		//TODO: add correct check of null for not custom type and correct custom type field name check

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
	 * Map as Map.
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
	private static boolean mapAsMapType(final Map<String, Object> parameters, final Field field, final Object bean, final Object value,
										final String scopePrefix, final BeanMapperConfiguration conf) {
		// TODO: change this by correct mapping for custom map key/value objects
		Class<?> fieldClass = field.getType(); // field type
		if (!Map.class.isAssignableFrom(fieldClass)) // if this field not map we are skipping this mapping
			return false;

		String[] strArray = String.valueOf(value).split(conf.getMapElementsDelimiter());
		if (strArray == null || strArray.length == 0) // if source array are null or empty we are saying about completed mapping
			return true;

		Map map = null;
		if (fieldClass.isInterface())
			map =  createInstance(HashMap.class);

		if (map == null)
			map = Map.class.cast(createInstance(fieldClass));

		ParameterizedType genericType = null;
		if(!(field.getGenericType() instanceof ParameterizedType))
			return false;

		genericType = ParameterizedType.class.cast(field.getGenericType());

		// Map as basic types collection
		for(String elements: strArray){
			if(elements == null || !elements.contains(conf.getMapKeyValueDelimiter()))
				continue; //if element is null or do not contain delimiter we are skipping this mapping

			String[] entry = elements.split(conf.getMapKeyValueDelimiter());
			if(entry.length > 2)
				continue; //if there more than one key and one value elements we are skipping this mapping

			map.put(toBasicTypeFromValue(Class.class.cast(genericType.getActualTypeArguments()[0]), field.getName(), entry[0], conf),
					toBasicTypeFromValue(Class.class.cast(genericType.getActualTypeArguments()[1]), field.getName(), entry[1], conf));
		}

		if(map.entrySet().size() < 1)
			return true; //if empty map we should consider as already mapped

		setFieldValue(field, bean, map, conf);
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

	/**
	 * Method for mapping array map from field value.
	 *
	 * @param field
	 * 		{@link Field} that should be converted
	 * @param fieldValue
	 * 		{@link Object} field value
	 * @param scope
	 * 		{@link String} current field scope
	 * @param configuration
	 * 		{@link BeanMapperConfiguration} instance
	 * @return {@link java.util.Map} converted from field
	 */
	private static Map<String, Object> mapFromArrayType(final Field field, final Object fieldValue, final String scope, final BeanMapperConfiguration configuration) {
		final String fieldName = field.getName(); // field name
		Map<String, Object> propertyMap = new HashMap<String, Object>();

		if (fieldValue == null)
			return propertyMap;

		if (Array.getLength(fieldValue) > 0)
			if (isBasicType(Array.get(fieldValue, 0))) {
				StringBuilder arrayRepr = new StringBuilder();
				for (int i = 0; i < Array.getLength(fieldValue); i++) {
					if (i != 0)
						arrayRepr.append(configuration.getArrayDelimiter());

					arrayRepr.append(Array.get(fieldValue, i));
				}

				//create current scope
				StringBuilder currScope = makeStringFromArgs(!StringUtils.isEmpty(scope) ? scope : null, !StringUtils.isEmpty(scope) ? configuration.getScopeDelimiter() : null, fieldName);
				propertyMap.put(currScope.toString(), arrayRepr.toString());

				return propertyMap;
			}

		for (int i = 0; i < Array.getLength(fieldValue); i++) {
			String currScope = StringUtils.isEmpty(scope) ? fieldName : scope + configuration.getScopeDelimiter() + fieldName;
			propertyMap.putAll(map(Array.get(fieldValue, i), currScope + configuration.getBeanArrayDelimiter() + i, configuration));
		}

		return propertyMap;
	}

	/**
	 * Method for list mapping to map from field value.
	 *
	 * @param field
	 * 		{@link Field} that should be converted
	 * @param fieldValue
	 * 		{@link Object} field value
	 * @param scope
	 * 		{@link String} current field scope
	 * @param configuration
	 * 		{@link BeanMapperConfiguration} instance
	 * @return {@link java.util.Map} converted from field
	 */
	private static Map<String, Object> mapFromListType(final Field field, final Object fieldValue, final String scope, final BeanMapperConfiguration configuration) {
		final String fieldName = field.getName(); // field name
		Map<String, Object> propertyMap = new HashMap<String, Object>();
		List valueList = List.class.cast(fieldValue);

		if (fieldValue == null)
			return propertyMap;

		if (isBasicType(valueList.get(0))) {
			StringBuilder arrayRepr = new StringBuilder();
			for (int i = 0; i < valueList.size(); i++) {
				if (i != 0)
					arrayRepr.append(configuration.getListDelimiter());

				arrayRepr.append(valueList.get(i));
			}

			//create current scope
			StringBuilder currScope = makeStringFromArgs(!StringUtils.isEmpty(scope) ? scope : null, !StringUtils.isEmpty(scope) ? configuration.getScopeDelimiter() : null, fieldName);
			propertyMap.put(currScope.toString(), arrayRepr.toString());

			return propertyMap;
		}

		for (int i = 0; i < valueList.toArray().length; i++) {
			//create current scope
			StringBuilder currScope = makeStringFromArgs(!StringUtils.isEmpty(scope) ? scope : null, !StringUtils.isEmpty(scope) ? configuration.getScopeDelimiter() : null,
					fieldName, configuration.getBeanListDelimiter(), i + 1);

			propertyMap.putAll(map(valueList.get(i), currScope.toString(), configuration));
		}

		return propertyMap;
	}

	/**
	 * Method for set mapping to map from field value.
	 *
	 * @param field
	 * 		{@link Field} that should be converted
	 * @param fieldValue
	 * 		{@link Object} field value
	 * @param scope
	 * 		{@link String} current field scope
	 * @param configuration
	 * 		{@link BeanMapperConfiguration} instance
	 * @return {@link java.util.Map} converted from field
	 */
	private static Map<String, Object> mapFromSetType(final Field field, final Object fieldValue, final String scope, final BeanMapperConfiguration configuration) {
		final String fieldName = field.getName(); // field name
		Map<String, Object> propertyMap = new HashMap<String, Object>();
		Set valueSet = Set.class.cast(fieldValue);

		if (fieldValue == null)
			return propertyMap;

		if (isBasicType(valueSet.toArray()[0])) {
			StringBuilder arrayRepr = new StringBuilder();
			for (int i = 0; i < valueSet.toArray().length; i++) {
				if (i != 0)
					arrayRepr.append(configuration.getListDelimiter());

				arrayRepr.append(valueSet.toArray()[i]);
			}

			StringBuilder currScope = makeStringFromArgs(!StringUtils.isEmpty(scope) ? scope : null, !StringUtils.isEmpty(scope) ? configuration.getScopeDelimiter() : null, fieldName);
			propertyMap.put(currScope.toString(), arrayRepr);

			return propertyMap;
		}

		for (int i = 0; i < valueSet.toArray().length; i++) {
			StringBuilder currScope = makeStringFromArgs(!StringUtils.isEmpty(scope) ? scope : null, !StringUtils.isEmpty(scope) ? configuration.getScopeDelimiter() : null,
					fieldName, configuration.getBeanListDelimiter(), i + 1);
			propertyMap.putAll(map(valueSet.toArray()[i], currScope.toString(), configuration));
		}

		return propertyMap;
	}

	/**
	 * Method for map mapping to map from field value.
	 *
	 * @param field
	 * 		{@link Field} that should be converted
	 * @param fieldValue
	 * 		{@link Object} field value
	 * @param scope
	 * 		{@link String} current field scope
	 * @param configuration
	 * 		{@link BeanMapperConfiguration} instance
	 * @return {@link java.util.Map} converted from field
	 */
	private static Map<String, Object> mapFromMapType(final Field field, final Object fieldValue, final String scope, final BeanMapperConfiguration configuration) {
		final String fieldName = field.getName(); // field name
		Map<String, Object> propertyMap = new HashMap<String, Object>();
		Map valueMap = Map.class.cast(fieldValue);

		if (fieldValue == null)
			return propertyMap;

		StringBuilder mapRepr = new StringBuilder();
		for (int i = 0; i < valueMap.entrySet().toArray().length; i++) {
			Map.Entry mapElement = Map.Entry.class.cast(valueMap.entrySet().toArray()[i]);
			if (i != 0)
				mapRepr.append(configuration.getMapElementsDelimiter());

			mapRepr.append(mapElement.getKey());
			mapRepr.append(configuration.getMapKeyValueDelimiter());
			mapRepr.append(mapElement.getValue());
		}

		StringBuilder currScope = makeStringFromArgs(!StringUtils.isEmpty(scope) ? scope : null, !StringUtils.isEmpty(scope) ? configuration.getScopeDelimiter() : null, fieldName);
		propertyMap.put(currScope.toString(), mapRepr.toString());

		return propertyMap;
	}

	/**
	 * Prepare {@link StringBuilder} from multi parameters.
	 *
	 * @param params
	 * 		{@link Object}'s array
	 * @return {@link StringBuilder} instance
	 */
	private static StringBuilder makeStringFromArgs(final Object... params) {
		final StringBuilder sb = new StringBuilder();

		for (Object parameter : params)
			if (parameter != null)
				sb.append(parameter);

		return sb;
	}

}
