package net.bolbat.utils.crypt;

/**
 * General {@link DigestUtils} exception.
 * 
 * @author Alexandr Bolbat
 */
public class DigestRuntimeException extends RuntimeException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -5552494871605015134L;

	/**
	 * Default constructor.
	 */
	public DigestRuntimeException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public DigestRuntimeException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public DigestRuntimeException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 * @param cause
	 *            exception cause
	 */
	public DigestRuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
