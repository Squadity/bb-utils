package net.bolbat.utils.reflect.proxy;

/**
 * Exception for {@code ProxyUtils.unwrapProxy(proxy)} functionality.
 * 
 * @author Alexandr Bolbat
 */
public class ProxyUnsupportedException extends RuntimeException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -3783985112967590131L;

	/**
	 * Default constructor.
	 * 
	 * @param clazz
	 *            handler class
	 */
	public ProxyUnsupportedException(final Class<?> clazz) {
		super("Proxy[" + clazz + "] is unsupported");
	}

}
