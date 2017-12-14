package net.bolbat.utils.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotations to inform users of how much to rely on a particular package, class or method not changing over time.
 * 
 * @author Alexandr Bolbat
 */
@Audience.Public
@Stability.Stable
public final class Stability {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private Stability() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Can evolve while retaining compatibility for minor release boundaries, can break compatibility only at major release.
	 */
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Stable {

	};

	/**
	 * Evolving, but can break compatibility at minor release.
	 */
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Evolving {

	};

	/**
	 * No guarantee is provided as to reliability or stability across any level of release granularity.
	 */
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Unstable {

	};

}
