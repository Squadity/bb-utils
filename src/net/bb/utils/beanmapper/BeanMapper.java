package net.bb.utils.beanmapper;

import java.util.Map;

import net.bb.utils.beanmapper.impl.AbstractBeanMapper;
import net.bb.utils.beanmapper.impl.MapBasedBeanMapper;

/**
 * Utility for automatic bean mapping. It's supports simple property mapping, complex type mapping, recursive mapping, etc.
 * 
 * @author Alexandr Bolbat
 */
public final class BeanMapper {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private BeanMapper() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Default instance of the {@link BeanMapperConfiguration}.
	 */
	private static BeanMapperConfiguration DEFAULT_BEAN_MAPPER_CONFIGURATION = new BeanMapperConfiguration();

	/**
	 * Map bean or primitive (int, boolean, long, etc) or basic type (String, Long, Char, etc) with parameters from {@link Map}.
	 * 
	 * @param params
	 *            parameters
	 * @param type
	 *            class of bean or primitive (int, boolean, long, etc) or basic type (String, Long, Char, etc)
	 * @param key
	 *            value key, used for mapping parameter to primitive or basic type
	 * @return mapped bean
	 */
	public static <T extends Object> T map(final Map<String, Object> params, final Class<T> type, final String key) {
		if (params == null)
			throw new IllegalArgumentException("parameters argument are null");
		if (type == null)
			throw new IllegalArgumentException("beanClass argument are null");

		return map(params, type, key, DEFAULT_BEAN_MAPPER_CONFIGURATION);
	}

	/**
	 * Map bean or primitive (int, boolean, long, etc) or basic type (String, Long, Char, etc) with parameters from {@link Map}.
	 * 
	 * @param params
	 *            parameters
	 * @param type
	 *            class of bean or primitive (int, boolean, long, etc) or basic type (String, Long, Char, etc)
	 * @param key
	 *            value key, used for mapping parameter to primitive or basic type
	 * @param conf
	 *            mapping configuration
	 * @return mapped bean
	 */
	public static <T extends Object> T map(final Map<String, Object> params, final Class<T> type, final String key, final BeanMapperConfiguration conf) {
		if (params == null)
			throw new IllegalArgumentException("parameters argument are null");
		if (type == null)
			throw new IllegalArgumentException("beanClass argument are null");
		if (conf == null)
			throw new IllegalArgumentException("configuration argument are null");

		if (key != null && key.trim().length() > 0) {
			@SuppressWarnings("unchecked")
			T result = (T) AbstractBeanMapper.toBasicTypeFromValue(type, key, params.get(key), conf);
			return result;
		}

		return map(params, type, conf);
	}

	/**
	 * Map bean with parameters from {@link Map}.
	 * 
	 * @param params
	 *            parameters
	 * @param type
	 *            bean class
	 * @return mapped bean
	 */
	public static <T extends Object> T map(final Map<String, Object> params, final Class<T> type) {
		if (params == null)
			throw new IllegalArgumentException("parameters argument are null");
		if (type == null)
			throw new IllegalArgumentException("beanClass argument are null");

		return map(params, type, DEFAULT_BEAN_MAPPER_CONFIGURATION);
	}

	/**
	 * Map bean with parameters from {@link Map}.
	 * 
	 * @param params
	 *            parameters
	 * @param type
	 *            bean class
	 * @param conf
	 *            mapping configuration
	 * @return mapped bean
	 */
	public static <T extends Object> T map(final Map<String, Object> params, final Class<T> type, final BeanMapperConfiguration conf) {
		if (params == null)
			throw new IllegalArgumentException("parameters argument are null");
		if (type == null)
			throw new IllegalArgumentException("beanClass argument are null");
		if (conf == null)
			throw new IllegalArgumentException("configuration argument are null");

		T bean = AbstractBeanMapper.createInstance(type);
		return map(params, bean, conf);
	}

	/**
	 * Map bean with parameters from {@link Map}.
	 * 
	 * @param params
	 *            parameters
	 * @param bean
	 *            bean
	 * @return mapped bean
	 */
	public static <T extends Object> T map(final Map<String, Object> params, final T bean) {
		if (params == null)
			throw new IllegalArgumentException("parameters argument are null");
		if (bean == null)
			throw new IllegalArgumentException("bean argument are null");

		return map(params, bean, DEFAULT_BEAN_MAPPER_CONFIGURATION);
	}

	/**
	 * Map bean with parameters from {@link Map}.
	 * 
	 * @param params
	 *            parameters
	 * @param bean
	 *            bean
	 * @param conf
	 *            mapping configuration
	 * @return mapped bean
	 */
	public static <T extends Object> T map(final Map<String, Object> params, final T bean, final BeanMapperConfiguration conf) {
		if (params == null)
			throw new IllegalArgumentException("parameters argument are null");
		if (bean == null)
			throw new IllegalArgumentException("bean argument are null");
		if (conf == null)
			throw new IllegalArgumentException("configuration argument are null");

		return MapBasedBeanMapper.map(params, bean, conf);
	}

}
