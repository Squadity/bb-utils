package net.bolbat.utils.reflect.proxy;

/**
 * Interface to be implemented by classes that hold the proxy configuration.
 * 
 * @author Alexandr Bolbat
 */
public interface Advised {

	/**
	 * Get proxied target.
	 * 
	 * @return proxied target
	 */
	Object getProxiedTarget();

	/**
	 * Get proxied interfaces.
	 * 
	 * @return interfaces array
	 */
	Class<?>[] getProxiedInterfaces();

}
