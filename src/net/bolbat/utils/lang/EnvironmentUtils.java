package net.bolbat.utils.lang;

import static net.bolbat.utils.lang.StringUtils.EMPTY;
import static net.bolbat.utils.lang.StringUtils.isEmpty;
import static net.bolbat.utils.lang.StringUtils.isNotEmpty;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Environment utilities.
 *
 * @author Alexandr Bolbat
 */
public final class EnvironmentUtils {

	/**
	 * Variables cache.
	 */
	private static final Map<String, Variable> VARIABLES_CACHE = new ConcurrentHashMap<>();

	/**
	 * Variables cache initialization lock.
	 */
	private static final Object VARIABLES_CACHE_LOCK = new Object();

	/**
	 * Total variables amount from all sources, not the same amount what in variables cache.
	 */
	private static final AtomicInteger VARIABLES_TOTAL = new AtomicInteger();

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private EnvironmentUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Get variables names.
	 * 
	 * @return {@link Set} of {@link String}
	 */
	public static Set<String> getVariablesNames() {
		ensureVariablesCacheState();
		return new HashSet<>(VARIABLES_CACHE.keySet());
	}

	/**
	 * Get variable.
	 * 
	 * @param name
	 *            variable name
	 * @return {@link Variable} or <code>null</code>
	 */
	public static Variable getVariable(final String name) {
		ensureVariablesCacheState();
		final Variable value = VARIABLES_CACHE.get(name);
		return value != null ? value.clone() : null;
	}

	/**
	 * Get actual variable value.<br>
	 * Resolving priority:<br>
	 * - system property;<br>
	 * - system user environment variable (not implemented);<br>
	 * - system environment variable.<br>
	 * Empty {@link String} will be returned if 'name' argument is empty.
	 * 
	 * @param name
	 *            variable name
	 * @return resolved value as {@link String} or empty {@link String}
	 */
	public static String getActualVariableValue(final String name) {
		return getActualVariableValue(name, null);
	}

	/**
	 * Get actual variable value.<br>
	 * Resolving priority:<br>
	 * - system property;<br>
	 * - system user environment variable (not implemented);<br>
	 * - system environment variable.<br>
	 * Empty {@link String} will be returned if 'name' argument is empty.<br>
	 * Empty {@link String} will be returned if variable is not resolved and 'def' argument is <code>null</code>.
	 * 
	 * @param name
	 *            variable name
	 * @param def
	 *            default value
	 * @return resolved value as {@link String} or empty {@link String}
	 */
	public static String getActualVariableValue(final String name, final String def) {
		if (isEmpty(name))
			return isNotEmpty(def) ? def : EMPTY;

		// trying to resolve from cache
		ensureVariablesCacheState();
		final Variable value = VARIABLES_CACHE.get(name);
		if (value == null)
			return isNotEmpty(def) ? def : EMPTY;

		if (value.getSystemPropValue() != null)
			return value.getSystemPropValue();

		if (value.getSystemEnvVarValue() != null)
			return value.getSystemEnvVarValue();

		return isNotEmpty(def) ? def : EMPTY;
	}

	/**
	 * Ensure variables cache state.
	 */
	private static void ensureVariablesCacheState() {
		int rawTotal = System.getenv().size();
		rawTotal += System.getProperties().stringPropertyNames().size();
		if (rawTotal == VARIABLES_TOTAL.get())
			return;

		synchronized (VARIABLES_CACHE_LOCK) {
			rawTotal = System.getenv().size();
			rawTotal += System.getProperties().stringPropertyNames().size();
			if (rawTotal == VARIABLES_TOTAL.get()) // double check
				return;

			int processedVars = 0;

			// initializing from system environment variables
			for (final Entry<String, String> envVarEntry : System.getenv().entrySet()) {
				final Variable var = new Variable(envVarEntry.getKey());
				var.setSystemEnvVarValue(envVarEntry.getValue());
				VARIABLES_CACHE.put(var.getName(), var);
				processedVars++;
			}

			// initializing from system properties
			for (final String propKey : System.getProperties().stringPropertyNames()) {
				Variable var = VARIABLES_CACHE.get(propKey);
				if (var == null)
					var = new Variable(propKey);

				var.setSystemPropValue(System.getProperty(propKey));

				VARIABLES_CACHE.put(propKey, var);
				processedVars++;
			}

			VARIABLES_TOTAL.set(processedVars);
		}
	}

	/**
	 * Clear cache.
	 */
	public void tearDown() {
		synchronized (VARIABLES_CACHE_LOCK) {
			VARIABLES_CACHE.clear();
			VARIABLES_TOTAL.set(0);
		}
	}

	/**
	 * Variable data.
	 * 
	 * @author Alexandr Bolbat
	 */
	public static class Variable implements Serializable, Cloneable {

		/**
		 * Generated SerialVersionUID.
		 */
		private static final long serialVersionUID = 331854019084782738L;

		/**
		 * Variable name.
		 */
		private final String name;

		/**
		 * System environment variable value.
		 */
		private String systemEnvVarValue;

		/**
		 * System property value.
		 */
		private String systemPropValue;

		/**
		 * Default constructor.
		 * 
		 * @param aName
		 *            variable name
		 */
		protected Variable(final String aName) {
			this.name = aName;
		}

		public String getName() {
			return name;
		}

		public String getSystemEnvVarValue() {
			return systemEnvVarValue;
		}

		public void setSystemEnvVarValue(final String aSystemEnvVarValue) {
			this.systemEnvVarValue = aSystemEnvVarValue;
		}

		public String getSystemPropValue() {
			return systemPropValue;
		}

		public void setSystemPropValue(final String aSystemPropValue) {
			this.systemPropValue = aSystemPropValue;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof Variable))
				return false;
			final Variable other = (Variable) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
			builder.append("[name=").append(name);
			builder.append(", systemEnvVarValue=").append(systemEnvVarValue);
			builder.append(", systemPropValue=").append(systemPropValue);
			builder.append("]");
			return builder.toString();
		}

		@Override
		public Variable clone() {
			try {
				return Variable.class.cast(super.clone());
			} catch (final CloneNotSupportedException e) {
				throw new AssertionError("Can't clone [" + this + "]");
			}
		}

	}

}
