package net.bolbat.utils.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotations to inform users of a package, class or methods intended audience.
 * 
 * @author Alexandr Bolbat
 */
@Audience.Public
@Stability.Stable
public final class Audience {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private Audience() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Intended for use by any project or application.
	 */
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Public {

	};

	/**
	 * Intended only for the project(s) specified in the annotation.
	 */
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	public @interface LimitedTo {

		/**
		 * Project(s) list.
		 * 
		 * @return {@link String} array
		 */
		String[] value();

	};

	/**
	 * Intended for use only within project.
	 */
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Private {

	};

}
