package net.bolbat.utils.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to inform users with <i>to do</i>, <i>fix me</i>, etc.
 * 
 * @author Alexandr Bolbat
 */
@Audience.Public
@Stability.Stable
public final class Mark {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private Mark() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * To do message.
	 */
	@Documented
	@Retention(RetentionPolicy.SOURCE)
	public @interface ToDo {

		/**
		 * Description.
		 * 
		 * @return {@link String}
		 */
		String[] value() default "";

	};

	/**
	 * Fix me message.
	 */
	@Documented
	@Retention(RetentionPolicy.SOURCE)
	public @interface FixMe {

		/**
		 * Description.
		 * 
		 * @return {@link String}
		 */
		String[] value() default "";

	};

	/**
	 * Indicates that annotated functionality should be improved.
	 */
	@Documented
	@Retention(RetentionPolicy.SOURCE)
	public @interface RefactorMe {

		/**
		 * Description.
		 * 
		 * @return {@link String}
		 */
		String[] value() default "";

	};

	/**
	 * Indicates that annotated functionality should be tested.
	 */
	@Documented
	@Retention(RetentionPolicy.SOURCE)
	public @interface TestMe {

		/**
		 * Description.
		 * 
		 * @return {@link String}
		 */
		String[] value() default "";

	};

}
