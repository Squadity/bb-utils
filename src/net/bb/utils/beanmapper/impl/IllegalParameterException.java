package net.bb.utils.beanmapper.impl;

/**
 * General runtime exception. Mapper throw it if someone try to map wrong parameters.
 * 
 * @author Alexandr Bolbat
 */
public class IllegalParameterException extends IllegalArgumentException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -24686347723660285L;

	/**
	 * Default constructor.
	 * 
	 * @param prameter
	 *            parameter name
	 * @param value
	 *            value
	 */
	public IllegalParameterException(final String prameter, final Object value) {
		super("The parameter[" + prameter + "] have a wrong value[" + value + "]");
	}

}
