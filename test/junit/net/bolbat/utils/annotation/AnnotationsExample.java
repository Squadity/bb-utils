package net.bolbat.utils.annotation;

/**
 * Annotations usage example.
 * 
 * @author Alexandr Bolbat
 */
@Audience.LimitedTo(value = { "bb-utils" })
@Stability.Stable
@Mark.ToDo("Finish me with good examples for all annotations from net.bolbat.utils.annotation package")
public class AnnotationsExample {

	@Stability.Stable
	@Concurrency.ThreadSafe
	public static class EvolvingThreadSafeBean {
	}

	@Stability.Unstable
	@Concurrency.NotThreadSafe
	@Mark.FixMe("Make this bean stable")
	public static class UnstableBean {
	}

	@Stability.Evolving
	@Mark.RefactorMe("This functionality should be improved")
	public static void someComplicatedFunctionality() {
	}

}
