package net.bb.utils.reflect;

import java.lang.annotation.Annotation;

/**
 * {@link Annotation} utilities.
 * 
 * @author Alexandr Bolbat
 */
public final class AnnotationUtils {

	/**
	 * Default constructor with preventing instantiations of this class.
	 */
	private AnnotationUtils() {
		throw new IllegalAccessError("Shouldn't be instantiated.");
	}

	/**
	 * Get class annotation (including inheritance search).
	 * 
	 * @param annotationClass
	 *            annotation type, can't be <code>null</code>
	 * @param subject
	 *            class instance
	 * @return <A> instance or <code>null</code>
	 */
	public static <A extends Annotation> A getClassAnnotation(final Class<A> annotationClass, final Object subject) {
		return getClassAnnotation(annotationClass, subject, true);
	}

	/**
	 * Get class annotation.
	 * 
	 * @param annotationClass
	 *            annotation type, can't be <code>null</code>
	 * @param subject
	 *            class instance
	 * @param inheritance
	 *            <code>true</code> to apply inheritance search or <code>false</code>
	 * @return <A> instance or <code>null</code>
	 */
	public static <A extends Annotation> A getClassAnnotation(final Class<A> annotationClass, final Object subject, final boolean inheritance) {
		if (subject == null)
			return null;

		return getClassAnnotation(annotationClass, subject.getClass(), inheritance);
	}

	/**
	 * Get class annotation (including inheritance search).
	 * 
	 * @param annotationClass
	 *            annotation type, can't be <code>null</code>
	 * @param clazz
	 *            class
	 * @return <A> instance or <code>null</code>
	 */
	public static <A extends Annotation> A getClassAnnotation(final Class<A> annotationClass, final Class<?> clazz) {
		return getClassAnnotation(annotationClass, clazz, true);
	}

	/**
	 * Get class annotation.
	 * 
	 * @param annotationClass
	 *            annotation type, can't be <code>null</code>
	 * @param clazz
	 *            class
	 * @param inheritance
	 *            <code>true</code> to apply inheritance search or <code>false</code>
	 * @return <A> instance or <code>null</code>
	 */
	public static <A extends Annotation> A getClassAnnotation(final Class<A> annotationClass, final Class<?> clazz, final boolean inheritance) {
		if (annotationClass == null)
			throw new IllegalArgumentException("annotationClass argument is null.");
		if (clazz == null || clazz.equals(Object.class))
			return null;

		A annotation = clazz.getAnnotation(annotationClass);
		if (annotation != null)
			return annotation;

		if (!inheritance)
			return null;

		return getClassAnnotation(annotationClass, clazz.getSuperclass(), inheritance);
	}

}
