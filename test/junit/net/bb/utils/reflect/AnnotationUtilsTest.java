package net.bb.utils.reflect;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link AnnotationUtils} test.
 * 
 * @author Alexandr Bolbat
 */
public class AnnotationUtilsTest {

	/**
	 * Complex test.
	 */
	@Test
	public void complexTest() {
		Assert.assertNotNull(AnnotationUtils.getClassAnnotation(SampleTypeAnnotation.class, SampleInheritedClass.class));
		Assert.assertNull(AnnotationUtils.getClassAnnotation(SampleTypeAnnotation.class, SampleInheritedClass.class, false));
		Assert.assertNotNull(AnnotationUtils.getClassAnnotation(SampleTypeAnnotation.class, SampleInheritedClass.class, true));

		Assert.assertNotNull(AnnotationUtils.getClassAnnotation(SampleTypeAnnotation.class, new SampleInheritedClass()));
		Assert.assertNull(AnnotationUtils.getClassAnnotation(SampleTypeAnnotation.class, new SampleInheritedClass(), false));
		Assert.assertNotNull(AnnotationUtils.getClassAnnotation(SampleTypeAnnotation.class, new SampleInheritedClass(), true));
	}

	/**
	 * Error cases test.
	 */
	@Test
	public void errorCasesTest() {
		try {
			AnnotationUtils.getClassAnnotation(null, null);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().contains("annotationClass"));
		}

		try {
			AnnotationUtils.getClassAnnotation(null, null, false);
			Assert.fail("Exception shold be thrown before this step.");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue("Exception should be there.", e.getMessage().contains("annotationClass"));
		}
	}

}
