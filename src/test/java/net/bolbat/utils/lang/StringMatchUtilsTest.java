package net.bolbat.utils.lang;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * {@link StringMatchUtils} test.
 *
 * @author rkapushchak
 */
public class StringMatchUtilsTest {

	/**
	 * Is empty test.
	 */
	@Test
	public void testWildCardMatch() {
		assertTrue(StringMatchUtils.wildcardMatch(null, null));
		assertTrue(StringMatchUtils.wildcardMatch("", ""));

		assertFalse(StringMatchUtils.wildcardMatch("", "test*"));
		assertFalse(StringMatchUtils.wildcardMatch(null, "test*"));
		assertFalse(StringMatchUtils.wildcardMatch("test", null));

		assertTrue(StringMatchUtils.wildcardMatch("test", "test*"));
		assertTrue(StringMatchUtils.wildcardMatch("test1", "test*"));
		assertTrue(StringMatchUtils.wildcardMatch("test_2", "test*"));
		assertTrue(StringMatchUtils.wildcardMatch("testTEST", "test*"));
		assertTrue(StringMatchUtils.wildcardMatch("test-content", "test*"));
		assertTrue(StringMatchUtils.wildcardMatch("test.content", "test*"));
		assertTrue(StringMatchUtils.wildcardMatch("test-content.txt", "test*"));
		assertTrue(StringMatchUtils.wildcardMatch("test content.txt", "test*"));

		assertFalse(StringMatchUtils.wildcardMatch("2test", "test*"));
		assertFalse(StringMatchUtils.wildcardMatch("TEST", "test*"));
		assertFalse(StringMatchUtils.wildcardMatch("TEST2", "test*"));
		assertFalse(StringMatchUtils.wildcardMatch("test(content", "test*"));
		assertFalse(StringMatchUtils.wildcardMatch("test@content", "test*"));
		assertFalse(StringMatchUtils.wildcardMatch("test!content", "test*"));
		assertFalse(StringMatchUtils.wildcardMatch(" test", "test*"));
		assertFalse(StringMatchUtils.wildcardMatch("tes t", "test*"));

		assertTrue(StringMatchUtils.wildcardMatch("test", "*test*"));
		assertTrue(StringMatchUtils.wildcardMatch("test1", "*test*"));
		assertTrue(StringMatchUtils.wildcardMatch("test_2", "*test*"));
		assertTrue(StringMatchUtils.wildcardMatch("testTEST", "*test*"));
		assertTrue(StringMatchUtils.wildcardMatch("test-content", "*test*"));
		assertTrue(StringMatchUtils.wildcardMatch("test.content", "*test*"));
		assertTrue(StringMatchUtils.wildcardMatch("test-content.txt", "*test*"));
		assertTrue(StringMatchUtils.wildcardMatch("test content.txt", "*test*"));
		assertTrue(StringMatchUtils.wildcardMatch("2test", "*test*"));
		assertTrue(StringMatchUtils.wildcardMatch(" test", "*test*"));
		assertTrue(StringMatchUtils.wildcardMatch("1212testwasda", "*test*"));
		assertTrue(StringMatchUtils.wildcardMatch("_test.wasda", "*test*"));

		assertFalse(StringMatchUtils.wildcardMatch("TEST", "*test*"));
		assertFalse(StringMatchUtils.wildcardMatch("TEST2", "*test*"));
		assertFalse(StringMatchUtils.wildcardMatch("test(content", "*test*"));
		assertFalse(StringMatchUtils.wildcardMatch("content&test", "*test*"));
		assertFalse(StringMatchUtils.wildcardMatch("content+test", "*test*"));
		assertFalse(StringMatchUtils.wildcardMatch("test!content", "*test*"));
		assertFalse(StringMatchUtils.wildcardMatch("tes t", "*test*"));

		assertTrue(StringMatchUtils.wildcardMatch("test", "*test"));
		assertTrue(StringMatchUtils.wildcardMatch("1test", "*test"));
		assertTrue(StringMatchUtils.wildcardMatch("2_test", "*test"));
		assertTrue(StringMatchUtils.wildcardMatch("TESTtest", "*test"));
		assertTrue(StringMatchUtils.wildcardMatch("content-test", "*test"));
		assertTrue(StringMatchUtils.wildcardMatch("content.test", "*test"));
		assertTrue(StringMatchUtils.wildcardMatch("txt--content.test", "*test"));
		assertTrue(StringMatchUtils.wildcardMatch("txt content.test", "*test"));
		assertTrue(StringMatchUtils.wildcardMatch("2test", "*test"));
		assertTrue(StringMatchUtils.wildcardMatch(" test", "*test"));

		assertFalse(StringMatchUtils.wildcardMatch("test1", "*test"));
		assertFalse(StringMatchUtils.wildcardMatch("test_2", "*test"));
		assertFalse(StringMatchUtils.wildcardMatch("TEST", "*test"));
		assertFalse(StringMatchUtils.wildcardMatch("TEST2", "*test"));
		assertFalse(StringMatchUtils.wildcardMatch("test(content", "*test"));
		assertFalse(StringMatchUtils.wildcardMatch("content&test", "*test"));
		assertFalse(StringMatchUtils.wildcardMatch("content+test", "*test"));
		assertFalse(StringMatchUtils.wildcardMatch("test!content", "*test"));
		assertFalse(StringMatchUtils.wildcardMatch("tes t", "*test"));

		assertTrue(StringMatchUtils.wildcardMatch("1test", "?test"));
		assertTrue(StringMatchUtils.wildcardMatch("_test", "?test"));
		assertTrue(StringMatchUtils.wildcardMatch(".test", "?test"));
		assertTrue(StringMatchUtils.wildcardMatch("ctest", "?test"));

		assertFalse(StringMatchUtils.wildcardMatch("test", "?test"));
		assertFalse(StringMatchUtils.wildcardMatch("test1", "?test"));
		assertFalse(StringMatchUtils.wildcardMatch("test_2", "?test"));
		assertFalse(StringMatchUtils.wildcardMatch("TEST", "?test"));
		assertFalse(StringMatchUtils.wildcardMatch("(test", "?test"));
		assertFalse(StringMatchUtils.wildcardMatch("&test", "?test"));
		assertFalse(StringMatchUtils.wildcardMatch("+test", "?test"));
		assertFalse(StringMatchUtils.wildcardMatch("1tes t", "?test"));

		assertTrue(StringMatchUtils.wildcardMatch("test", "tes?"));
		assertTrue(StringMatchUtils.wildcardMatch("tess", "tes?"));
		assertTrue(StringMatchUtils.wildcardMatch("tes1", "tes?"));
		assertTrue(StringMatchUtils.wildcardMatch("tes_", "tes?"));
		assertTrue(StringMatchUtils.wildcardMatch("tes.", "tes?"));
		assertTrue(StringMatchUtils.wildcardMatch("tes ", "tes?"));

		assertFalse(StringMatchUtils.wildcardMatch("te.t", "tes?"));
		assertFalse(StringMatchUtils.wildcardMatch("te11", "tes?"));
		assertFalse(StringMatchUtils.wildcardMatch("te_2", "tes?"));
		assertFalse(StringMatchUtils.wildcardMatch("TEST", "tes?"));
		assertFalse(StringMatchUtils.wildcardMatch("(est", "tes?"));
		assertFalse(StringMatchUtils.wildcardMatch("tes+", "tes?"));
		assertFalse(StringMatchUtils.wildcardMatch("te t", "tes?"));

		assertTrue(StringMatchUtils.wildcardMatch("test", "te?t"));
		assertTrue(StringMatchUtils.wildcardMatch("tett", "te?t"));
		assertTrue(StringMatchUtils.wildcardMatch("te1t", "te?t"));
		assertTrue(StringMatchUtils.wildcardMatch("te_t", "te?t"));
		assertTrue(StringMatchUtils.wildcardMatch("te.t", "te?t"));
		assertTrue(StringMatchUtils.wildcardMatch("te t", "te?t"));

		assertFalse(StringMatchUtils.wildcardMatch("tes.", "te?t"));
		assertFalse(StringMatchUtils.wildcardMatch("tes1", "te?t"));
		assertFalse(StringMatchUtils.wildcardMatch("tes_", "te?t"));
		assertFalse(StringMatchUtils.wildcardMatch("TEST", "te?t"));
		assertFalse(StringMatchUtils.wildcardMatch("te(t", "te?t"));
		assertFalse(StringMatchUtils.wildcardMatch("te+t", "te?t"));
		assertFalse(StringMatchUtils.wildcardMatch("t st", "te?t"));

		assertTrue(StringMatchUtils.wildcardMatch("test.txt", "*.*"));
		assertTrue(StringMatchUtils.wildcardMatch("tett.", "*.*"));
		assertTrue(StringMatchUtils.wildcardMatch(".te1t", "*.*"));
		assertTrue(StringMatchUtils.wildcardMatch("te_t.nbm", "*.*"));
		assertTrue(StringMatchUtils.wildcardMatch("te.t.", "*.*"));
		assertTrue(StringMatchUtils.wildcardMatch("t.e t", "*.*"));

		assertFalse(StringMatchUtils.wildcardMatch("test", "*.*"));
		assertFalse(StringMatchUtils.wildcardMatch("tes1", "*.*"));
		assertFalse(StringMatchUtils.wildcardMatch("TEST", "*.*"));
		assertFalse(StringMatchUtils.wildcardMatch("te(t.hg", "*.*"));
		assertFalse(StringMatchUtils.wildcardMatch("vcx.te+t", "*.*"));
		assertFalse(StringMatchUtils.wildcardMatch("t st", "*.*"));

		assertTrue(StringMatchUtils.wildcardMatch("t.t", "?.?"));
		assertTrue(StringMatchUtils.wildcardMatch("t.x", "?.?"));
		assertTrue(StringMatchUtils.wildcardMatch(" .t", "?.?"));
		assertTrue(StringMatchUtils.wildcardMatch("t. ", "?.?"));

		assertFalse(StringMatchUtils.wildcardMatch("t.ts", "?.?"));
		assertFalse(StringMatchUtils.wildcardMatch("et.t", "?.?"));
		assertFalse(StringMatchUtils.wildcardMatch("(.t", "?.?"));
		assertFalse(StringMatchUtils.wildcardMatch("ttt", "?.?"));
	}

	/**
	 * Is not empty test.
	 */
	@Test
	public void testRegExpMatch() {
		assertTrue(StringMatchUtils.regexMatch(null, null));
		assertTrue(StringMatchUtils.regexMatch("", ""));

		assertFalse(StringMatchUtils.regexMatch("", "test*"));
		assertFalse(StringMatchUtils.regexMatch(null, "test*"));
		assertFalse(StringMatchUtils.regexMatch("test", null));

		assertTrue(StringMatchUtils.regexMatch("test", "^test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("test1", "^test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("test_2", "^test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("testTEST", "^test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("test-content", "^test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("test.content", "^test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("test-content.txt", "^test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("test content.txt", "^test[\\w\\s-\\.]*$"));

		assertFalse(StringMatchUtils.regexMatch("2test", "^test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regexMatch("TEST", "^test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regexMatch("TEST2", "^test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regexMatch("test(content", "^test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regexMatch("test@content", "^test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regexMatch("test!content", "^test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regexMatch(" test", "^test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regexMatch("tes t", "^test[\\w\\s-\\.]*$"));

		assertTrue(StringMatchUtils.regexMatch("test", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("test1", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("test_2", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("testTEST", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("test-content", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("test.content", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("test-content.txt", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("test content.txt", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("2test", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch(" test", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("1212testwasda", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("_test.wasda", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));

		assertFalse(StringMatchUtils.regexMatch("TEST", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regexMatch("TEST2", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regexMatch("test(content", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regexMatch("content&test", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regexMatch("content+test", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regexMatch("test!content", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regexMatch("tes t", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));

		assertTrue(StringMatchUtils.regexMatch("test", "^[\\w\\s-\\.]*test$"));
		assertTrue(StringMatchUtils.regexMatch("1test", "^[\\w\\s-\\.]*test$"));
		assertTrue(StringMatchUtils.regexMatch("2_test", "^[\\w\\s-\\.]*test$"));
		assertTrue(StringMatchUtils.regexMatch("TESTtest", "^[\\w\\s-\\.]*test$"));
		assertTrue(StringMatchUtils.regexMatch("content-test", "^[\\w\\s-\\.]*test$"));
		assertTrue(StringMatchUtils.regexMatch("content.test", "^[\\w\\s-\\.]*test$"));
		assertTrue(StringMatchUtils.regexMatch("txt--content.test", "^[\\w\\s-\\.]*test$"));
		assertTrue(StringMatchUtils.regexMatch("txt content.test", "^[\\w\\s-\\.]*test$"));
		assertTrue(StringMatchUtils.regexMatch("2test", "^[\\w\\s-\\.]*test$"));
		assertTrue(StringMatchUtils.regexMatch(" test", "^[\\w\\s-\\.]*test$"));

		assertFalse(StringMatchUtils.regexMatch("test1", "^[\\w\\s-\\.]*test$"));
		assertFalse(StringMatchUtils.regexMatch("test_2", "^[\\w\\s-\\.]*test$"));
		assertFalse(StringMatchUtils.regexMatch("TEST", "^[\\w\\s-\\.]*test$"));
		assertFalse(StringMatchUtils.regexMatch("TEST2", "^[\\w\\s-\\.]*test$"));
		assertFalse(StringMatchUtils.regexMatch("test(content", "^[\\w\\s-\\.]*test$"));
		assertFalse(StringMatchUtils.regexMatch("content&test", "^[\\w\\s-\\.]*test$"));
		assertFalse(StringMatchUtils.regexMatch("content+test", "^[\\w\\s-\\.]*test$"));
		assertFalse(StringMatchUtils.regexMatch("test!content", "^[\\w\\s-\\.]*test$"));
		assertFalse(StringMatchUtils.regexMatch("tes t", "^[\\w\\s-\\.]*test$"));

		assertTrue(StringMatchUtils.regexMatch("1test", "^[\\w\\s-\\.]test$"));
		assertTrue(StringMatchUtils.regexMatch("_test", "^[\\w\\s-\\.]test$"));
		assertTrue(StringMatchUtils.regexMatch(".test", "^[\\w\\s-\\.]test$"));
		assertTrue(StringMatchUtils.regexMatch("ctest", "^[\\w\\s-\\.]test$"));

		assertFalse(StringMatchUtils.regexMatch("test", "^[\\w\\s-\\.]test$"));
		assertFalse(StringMatchUtils.regexMatch("test1", "^[\\w\\s-\\.]test$"));
		assertFalse(StringMatchUtils.regexMatch("test_2", "^[\\w\\s-\\.]test$"));
		assertFalse(StringMatchUtils.regexMatch("TEST", "^[\\w\\s-\\.]test$"));
		assertFalse(StringMatchUtils.regexMatch("(test", "^[\\w\\s-\\.]test$"));
		assertFalse(StringMatchUtils.regexMatch("&test", "^[\\w\\s-\\.]test$"));
		assertFalse(StringMatchUtils.regexMatch("+test", "^[\\w\\s-\\.]test$"));
		assertFalse(StringMatchUtils.regexMatch("1tes t", "^[\\w\\s-\\.]test$"));

		assertTrue(StringMatchUtils.regexMatch("test", "^tes[\\w\\s-\\.]$"));
		assertTrue(StringMatchUtils.regexMatch("tess", "^tes[\\w\\s-\\.]$"));
		assertTrue(StringMatchUtils.regexMatch("tes1", "^tes[\\w\\s-\\.]$"));
		assertTrue(StringMatchUtils.regexMatch("tes_", "^tes[\\w\\s-\\.]$"));
		assertTrue(StringMatchUtils.regexMatch("tes.", "^tes[\\w\\s-\\.]$"));
		assertTrue(StringMatchUtils.regexMatch("tes ", "^tes[\\w\\s-\\.]$"));

		assertFalse(StringMatchUtils.regexMatch("te.t", "^tes[\\w\\s-\\.]$"));
		assertFalse(StringMatchUtils.regexMatch("te11", "^tes[\\w\\s-\\.]$"));
		assertFalse(StringMatchUtils.regexMatch("te_2", "^tes[\\w\\s-\\.]$"));
		assertFalse(StringMatchUtils.regexMatch("TEST", "^tes[\\w\\s-\\.]$"));
		assertFalse(StringMatchUtils.regexMatch("(est", "^tes[\\w\\s-\\.]$"));
		assertFalse(StringMatchUtils.regexMatch("tes+", "^tes[\\w\\s-\\.]$"));
		assertFalse(StringMatchUtils.regexMatch("te t", "^tes[\\w\\s-\\.]$"));

		assertTrue(StringMatchUtils.regexMatch("test", "^te[\\w\\s-\\.]t$"));
		assertTrue(StringMatchUtils.regexMatch("tett", "^te[\\w\\s-\\.]t$"));
		assertTrue(StringMatchUtils.regexMatch("te1t", "^te[\\w\\s-\\.]t$"));
		assertTrue(StringMatchUtils.regexMatch("te_t", "^te[\\w\\s-\\.]t$"));
		assertTrue(StringMatchUtils.regexMatch("te.t", "^te[\\w\\s-\\.]t$"));
		assertTrue(StringMatchUtils.regexMatch("te t", "^te[\\w\\s-\\.]t$"));

		assertFalse(StringMatchUtils.regexMatch("tes.", "^te[\\w\\s-\\.]t$"));
		assertFalse(StringMatchUtils.regexMatch("tes1", "^te[\\w\\s-\\.]t$"));
		assertFalse(StringMatchUtils.regexMatch("tes_", "^te[\\w\\s-\\.]t$"));
		assertFalse(StringMatchUtils.regexMatch("TEST", "^te[\\w\\s-\\.]t$"));
		assertFalse(StringMatchUtils.regexMatch("te(t", "^te[\\w\\s-\\.]t$"));
		assertFalse(StringMatchUtils.regexMatch("te+t", "^te[\\w\\s-\\.]t$"));
		assertFalse(StringMatchUtils.regexMatch("t st", "^te[\\w\\s-\\.]t$"));

		assertTrue(StringMatchUtils.regexMatch("test.txt", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("tett.", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch(".te1t", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("te_t.nbm", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("te.t.", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regexMatch("t.e t", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));

		assertFalse(StringMatchUtils.regexMatch("test", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regexMatch("tes1", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regexMatch("TEST", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regexMatch("te(t.hg", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regexMatch("vcx.te+t", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regexMatch("t st", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));

		assertTrue(StringMatchUtils.regexMatch("t.t", "^[\\w\\s-\\.]\\.[\\w\\s-\\.]$"));
		assertTrue(StringMatchUtils.regexMatch("t.x", "^[\\w\\s-\\.]\\.[\\w\\s-\\.]$"));
		assertTrue(StringMatchUtils.regexMatch(" .t", "^[\\w\\s-\\.]\\.[\\w\\s-\\.]$"));
		assertTrue(StringMatchUtils.regexMatch("t. ", "^[\\w\\s-\\.]\\.[\\w\\s-\\.]$"));

		assertFalse(StringMatchUtils.regexMatch("t.ts", "^[\\w\\s-\\.]\\.[\\w\\s-\\.]$"));
		assertFalse(StringMatchUtils.regexMatch("et.t", "^[\\w\\s-\\.]\\.[\\w\\s-\\.]$"));
		assertFalse(StringMatchUtils.regexMatch("(.t", "^[\\w\\s-\\.]\\.[\\w\\s-\\.]$"));
		assertFalse(StringMatchUtils.regexMatch("ttt", "^[\\w\\s-\\.]\\.[\\w\\s-\\.]$"));
	}

}
