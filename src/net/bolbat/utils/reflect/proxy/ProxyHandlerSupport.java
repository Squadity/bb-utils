package net.bolbat.utils.reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * {@link Proxy} {@link InvocationHandler} support.
 * 
 * @author Alexandr Bolbat
 */
public interface ProxyHandlerSupport {

	/**
	 * Get supported {@link InvocationHandler} class.
	 * 
	 * @return {@link Class}
	 */
	Class<?> getHandlerClass();

	/**
	 * Get proxied target from supported {@link InvocationHandler}.
	 * 
	 * @param handler
	 *            {@link InvocationHandler}
	 * @return proxied target
	 */
	Object getTarget(InvocationHandler handler);

}
