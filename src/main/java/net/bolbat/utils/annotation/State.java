package net.bolbat.utils.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotations to inform users of object state.
 * 
 * @author Alexandr Bolbat
 */
@Audience.Public
@Stability.Stable
public final class State {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private State() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Indicates that annotated subject is <b>mutable</b>.
	 * 
	 * @author Alexandr Bolbat
	 */
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Mutable {

	}

	/**
	 * Indicates that annotated subject is <b>immutable</b>.
	 * 
	 * @author Alexandr Bolbat
	 */
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Immutable {

	}

}
