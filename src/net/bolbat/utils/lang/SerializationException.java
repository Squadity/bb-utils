package net.bolbat.utils.lang;

/**
 * General {@link SerializationUtils} exception.
 * 
 * @author Alexandr Bolbat
 */
public class SerializationException extends RuntimeException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 5512402046295669738L;

	/**
	 * Default constructor.
	 */
	public SerializationException() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            exception message
	 */
	public SerializationException(final String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            exception cause
	 */
	public SerializationException(final Throwable cause) {
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
	public SerializationException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
