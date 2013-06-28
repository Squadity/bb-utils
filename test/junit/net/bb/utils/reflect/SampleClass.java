package net.bb.utils.reflect;

/**
 * Sample class for testing {@link Instantiator} utility.
 * 
 * @author Alexandr Bolbat
 */
@SampleTypeAnnotation
public class SampleClass {

	/**
	 * Some default value.
	 */
	public static final String DEFAULT_VALUE = "DEFAULT";

	/**
	 * Some value.
	 */
	private final String value;

	/**
	 * Default constructor.
	 */
	public SampleClass() {
		this.value = DEFAULT_VALUE;
	}

	/**
	 * Public constructor.
	 */
	public SampleClass(final String aValue) {
		this.value = aValue;
	}

	public String getValue() {
		return value;
	}

}
