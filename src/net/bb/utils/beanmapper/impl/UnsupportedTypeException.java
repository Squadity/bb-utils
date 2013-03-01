package net.bb.utils.beanmapper.impl;

/**
 * General runtime exception. Mapper throw it if someone try to map unsupported type like some custom collection instance.
 * 
 * @author Alexandr Bolbat
 */
public class UnsupportedTypeException extends RuntimeException {

	/**
	 * Basic serialVersionUID variable.
	 */

	private static final long serialVersionUID = 3395840355790995823L;

	/**
	 * Default constructor.
	 * 
	 * @param type
	 *            - type
	 */
	public UnsupportedTypeException(final Class<?> type) {
		super("Type[" + type + "] is unsupported");
	}

}
