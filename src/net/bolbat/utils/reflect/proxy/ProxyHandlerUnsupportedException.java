package net.bolbat.utils.reflect.proxy;

/**
 * Exception for {@code ProxyUtils.unwrapProxy(proxy)} functionality.
 * 
 * @author Alexandr Bolbat
 */
public class ProxyHandlerUnsupportedException extends RuntimeException {

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
	public ProxyHandlerUnsupportedException(final Class<?> clazz) {
		super("Proxy invocation handler[" + clazz + "] is unsupported");
	}

}
