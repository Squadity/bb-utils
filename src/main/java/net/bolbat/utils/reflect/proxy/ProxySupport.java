package net.bolbat.utils.reflect.proxy;

import java.lang.reflect.Proxy;

/**
 * {@link Proxy} support.
 * 
 * @author Alexandr Bolbat
 * 
 * @param <T>
 *            source type
 */
public interface ProxySupport<T> {

	/**
	 * Get supported type.
	 * 
	 * @return {@link Class}
	 */
	Class<?> getSupportedType();

	/**
	 * Get proxied target from proxy.
	 * 
	 * @param source
	 *            source type
	 * @return proxied target
	 */
	Object getTarget(T source);

}
