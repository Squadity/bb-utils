package net.bolbat.utils.crypt;

/**
 * General {@link CipherUtils} exception.
 * 
 * @author Alexandr Bolbat
 */
public class CipherRuntimeException extends RuntimeException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -8844908798889969747L;

	/**
	 * Default constructor.
	 */
	public CipherRuntimeException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public CipherRuntimeException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public CipherRuntimeException(final Throwable cause) {
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
	public CipherRuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
