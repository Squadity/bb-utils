package net.bolbat.utils.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotations to inform users of code concurrency support.
 * 
 * @author Alexandr Bolbat
 */
@Audience.Public
@Stability.Stable
public final class Concurrency {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private Concurrency() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Indicates that annotated subject is <b>thread safe</b>.
	 * 
	 * @author Alexandr Bolbat
	 */
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	public @interface ThreadSafe {

	}

	/**
	 * Indicates that annotated subject is <b>not thread safe</b>.
	 * 
	 * @author Alexandr Bolbat
	 */
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	public @interface NotThreadSafe {

	}

}
