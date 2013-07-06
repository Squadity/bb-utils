package net.bolbat.utils.beanmapper;


/**
 * Bean mapper utility configuration.
 * 
 * @author Alexandr Bolbat
 */
public final class BeanMapperConfiguration {

	/**
	 * Default root scope prefix.
	 */
	public static final String DEFAULT_ROOT_SCOPE = "";

	/**
	 * Default scope delimiter.
	 */
	public static final String DEFAULT_SCOPE_DELIMITER = ".";

	/**
	 * Default bean array delimiter.
	 */
	public static final String DEFAULT_BEAN_ARRAY_DELIMITER = "_";

	/**
	 * Default bean list delimiter.
	 */
	public static final String DEFAULT_BEAN_LIST_DELIMITER = "_";

	/**
	 * Default array delimiter.
	 */
	public static final String DEFAULT_ARRAY_DELIMITER = ",";

	/**
	 * Default list delimiter.
	 */
	public static final String DEFAULT_LIST_DELIMITER = ",";

	/**
	 * Default map elements delimiter.
	 */
	public static final String DEFAULT_MAP_ELEMENTS_DELIMITER = ",";

	/**
	 * Default map key/value delimiter.
	 */
	public static final String DEFAULT_MAP_KEY_VALUE_DELIMITER = "=";

	/**
	 * Configured root scope.
	 */
	private String rootScope;

	/**
	 * Configured scope delimiter.
	 */
	private String scopeDelimiter;

	/**
	 * Configured bean array delimiter.
	 */
	private String beanArrayDelimiter;

	/**
	 * Configured bean list delimiter.
	 */
	private String beanListDelimiter;

	/**
	 * Configured array delimiter.
	 */
	private String arrayDelimiter;

	/**
	 * Configured list delimiter.
	 */
	private String listDelimiter;

	/**
	 * Configured map elements delimiter.
	 */
	private String mapElementsDelimiter;

	/**
	 * Configured map key/value delimiter.
	 */
	private String mapKeyValueDelimiter;

	/**
	 * Error strategy.
	 */
	private ErrorStrategy errorStrategy;

	/**
	 * Default private constructor.
	 */
	public BeanMapperConfiguration() {
		this.rootScope = DEFAULT_ROOT_SCOPE;
		this.scopeDelimiter = DEFAULT_SCOPE_DELIMITER;
		this.beanArrayDelimiter = DEFAULT_BEAN_ARRAY_DELIMITER;
		this.beanListDelimiter = DEFAULT_BEAN_LIST_DELIMITER;
		this.arrayDelimiter = DEFAULT_ARRAY_DELIMITER;
		this.listDelimiter = DEFAULT_LIST_DELIMITER;
		this.mapElementsDelimiter = DEFAULT_MAP_ELEMENTS_DELIMITER;
		this.mapKeyValueDelimiter = DEFAULT_MAP_KEY_VALUE_DELIMITER;

		this.errorStrategy = ErrorStrategy.DEFAULT;
	}

	public String getRootScope() {
		return rootScope;
	}

	public void setRootScope(final String aRootScope) {
		this.rootScope = aRootScope;
	}

	public String getScopeDelimiter() {
		return scopeDelimiter;
	}

	public void setScopeDelimiter(final String aScopeDelimiter) {
		this.scopeDelimiter = aScopeDelimiter;
	}

	public String getBeanArrayDelimiter() {
		return beanArrayDelimiter;
	}

	public void setBeanArrayDelimiter(final String aBeanArrayDelimiter) {
		this.beanArrayDelimiter = aBeanArrayDelimiter;
	}

	public String getBeanListDelimiter() {
		return beanListDelimiter;
	}

	public void setBeanListDelimiter(final String aBeanListDelimiter) {
		this.beanListDelimiter = aBeanListDelimiter;
	}

	public String getArrayDelimiter() {
		return arrayDelimiter;
	}

	public void setArrayDelimiter(final String aArrayDelimiter) {
		this.arrayDelimiter = aArrayDelimiter;
	}

	public String getListDelimiter() {
		return listDelimiter;
	}

	public void setListDelimiter(final String aListDelimiter) {
		this.listDelimiter = aListDelimiter;
	}

	public String getMapElementsDelimiter() {
		return mapElementsDelimiter;
	}

	public void setMapElementsDelimiter(final String aMapElementsDelimiter) {
		this.mapElementsDelimiter = aMapElementsDelimiter;
	}

	public String getMapKeyValueDelimiter() {
		return mapKeyValueDelimiter;
	}

	public void setMapKeyValueDelimiter(final String aMapKeyValueDelimiter) {
		this.mapKeyValueDelimiter = aMapKeyValueDelimiter;
	}

	public ErrorStrategy getErrorStrategy() {
		return errorStrategy;
	}

	public void setErrorStrategy(final ErrorStrategy aErrorStrategy) {
		this.errorStrategy = aErrorStrategy;
	}

	/**
	 * Error strategy for mapping process.
	 * 
	 * @author Alexandr Bolbat
	 */
	public enum ErrorStrategy {

		/**
		 * Skipping value validation error and continuing with next one.
		 */
		SKIP_EXCEPTIONS,

		/**
		 * Throwing {@link IllegalParameterException} on first validation error.
		 */
		THROW_EXCEPTIONS;

		/**
		 * Default {@link ERROR_STRATEGY}.
		 */
		public static final ErrorStrategy DEFAULT = SKIP_EXCEPTIONS;

	}

}
