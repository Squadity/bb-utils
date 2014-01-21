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
		assertTrue(StringMatchUtils.wildCardMatch(null, null));
		assertTrue(StringMatchUtils.wildCardMatch("", ""));

		assertFalse(StringMatchUtils.wildCardMatch("", "test*"));
		assertFalse(StringMatchUtils.wildCardMatch(null, "test*"));
		assertFalse(StringMatchUtils.wildCardMatch("test", null));

		assertTrue(StringMatchUtils.wildCardMatch("test", "test*"));
		assertTrue(StringMatchUtils.wildCardMatch("test1", "test*"));
		assertTrue(StringMatchUtils.wildCardMatch("test_2", "test*"));
		assertTrue(StringMatchUtils.wildCardMatch("testTEST", "test*"));
		assertTrue(StringMatchUtils.wildCardMatch("test-content", "test*"));
		assertTrue(StringMatchUtils.wildCardMatch("test.content", "test*"));
		assertTrue(StringMatchUtils.wildCardMatch("test-content.txt", "test*"));
		assertTrue(StringMatchUtils.wildCardMatch("test content.txt", "test*"));

		assertFalse(StringMatchUtils.wildCardMatch("2test", "test*"));
		assertFalse(StringMatchUtils.wildCardMatch("TEST", "test*"));
		assertFalse(StringMatchUtils.wildCardMatch("TEST2", "test*"));
		assertFalse(StringMatchUtils.wildCardMatch("test(content", "test*"));
		assertFalse(StringMatchUtils.wildCardMatch("test@content", "test*"));
		assertFalse(StringMatchUtils.wildCardMatch("test!content", "test*"));
		assertFalse(StringMatchUtils.wildCardMatch(" test", "test*"));
		assertFalse(StringMatchUtils.wildCardMatch("tes t", "test*"));

		assertTrue(StringMatchUtils.wildCardMatch("test", "*test*"));
		assertTrue(StringMatchUtils.wildCardMatch("test1", "*test*"));
		assertTrue(StringMatchUtils.wildCardMatch("test_2", "*test*"));
		assertTrue(StringMatchUtils.wildCardMatch("testTEST", "*test*"));
		assertTrue(StringMatchUtils.wildCardMatch("test-content", "*test*"));
		assertTrue(StringMatchUtils.wildCardMatch("test.content", "*test*"));
		assertTrue(StringMatchUtils.wildCardMatch("test-content.txt", "*test*"));
		assertTrue(StringMatchUtils.wildCardMatch("test content.txt", "*test*"));
		assertTrue(StringMatchUtils.wildCardMatch("2test", "*test*"));
		assertTrue(StringMatchUtils.wildCardMatch(" test", "*test*"));
		assertTrue(StringMatchUtils.wildCardMatch("1212testwasda", "*test*"));
		assertTrue(StringMatchUtils.wildCardMatch("_test.wasda", "*test*"));

		assertFalse(StringMatchUtils.wildCardMatch("TEST", "*test*"));
		assertFalse(StringMatchUtils.wildCardMatch("TEST2", "*test*"));
		assertFalse(StringMatchUtils.wildCardMatch("test(content", "*test*"));
		assertFalse(StringMatchUtils.wildCardMatch("content&test", "*test*"));
		assertFalse(StringMatchUtils.wildCardMatch("content+test", "*test*"));
		assertFalse(StringMatchUtils.wildCardMatch("test!content", "*test*"));
		assertFalse(StringMatchUtils.wildCardMatch("tes t", "*test*"));

		assertTrue(StringMatchUtils.wildCardMatch("test", "*test"));
		assertTrue(StringMatchUtils.wildCardMatch("1test", "*test"));
		assertTrue(StringMatchUtils.wildCardMatch("2_test", "*test"));
		assertTrue(StringMatchUtils.wildCardMatch("TESTtest", "*test"));
		assertTrue(StringMatchUtils.wildCardMatch("content-test", "*test"));
		assertTrue(StringMatchUtils.wildCardMatch("content.test", "*test"));
		assertTrue(StringMatchUtils.wildCardMatch("txt--content.test", "*test"));
		assertTrue(StringMatchUtils.wildCardMatch("txt content.test", "*test"));
		assertTrue(StringMatchUtils.wildCardMatch("2test", "*test"));
		assertTrue(StringMatchUtils.wildCardMatch(" test", "*test"));

		assertFalse(StringMatchUtils.wildCardMatch("test1", "*test"));
		assertFalse(StringMatchUtils.wildCardMatch("test_2", "*test"));
		assertFalse(StringMatchUtils.wildCardMatch("TEST", "*test"));
		assertFalse(StringMatchUtils.wildCardMatch("TEST2", "*test"));
		assertFalse(StringMatchUtils.wildCardMatch("test(content", "*test"));
		assertFalse(StringMatchUtils.wildCardMatch("content&test", "*test"));
		assertFalse(StringMatchUtils.wildCardMatch("content+test", "*test"));
		assertFalse(StringMatchUtils.wildCardMatch("test!content", "*test"));
		assertFalse(StringMatchUtils.wildCardMatch("tes t", "*test"));

		assertTrue(StringMatchUtils.wildCardMatch("1test", "?test"));
		assertTrue(StringMatchUtils.wildCardMatch("_test", "?test"));
		assertTrue(StringMatchUtils.wildCardMatch(".test", "?test"));
		assertTrue(StringMatchUtils.wildCardMatch("ctest", "?test"));

		assertFalse(StringMatchUtils.wildCardMatch("test", "?test"));
		assertFalse(StringMatchUtils.wildCardMatch("test1", "?test"));
		assertFalse(StringMatchUtils.wildCardMatch("test_2", "?test"));
		assertFalse(StringMatchUtils.wildCardMatch("TEST", "?test"));
		assertFalse(StringMatchUtils.wildCardMatch("(test", "?test"));
		assertFalse(StringMatchUtils.wildCardMatch("&test", "?test"));
		assertFalse(StringMatchUtils.wildCardMatch("+test", "?test"));
		assertFalse(StringMatchUtils.wildCardMatch("1tes t", "?test"));

		assertTrue(StringMatchUtils.wildCardMatch("test", "tes?"));
		assertTrue(StringMatchUtils.wildCardMatch("tess", "tes?"));
		assertTrue(StringMatchUtils.wildCardMatch("tes1", "tes?"));
		assertTrue(StringMatchUtils.wildCardMatch("tes_", "tes?"));
		assertTrue(StringMatchUtils.wildCardMatch("tes.", "tes?"));
		assertTrue(StringMatchUtils.wildCardMatch("tes ", "tes?"));

		assertFalse(StringMatchUtils.wildCardMatch("te.t", "tes?"));
		assertFalse(StringMatchUtils.wildCardMatch("te11", "tes?"));
		assertFalse(StringMatchUtils.wildCardMatch("te_2", "tes?"));
		assertFalse(StringMatchUtils.wildCardMatch("TEST", "tes?"));
		assertFalse(StringMatchUtils.wildCardMatch("(est", "tes?"));
		assertFalse(StringMatchUtils.wildCardMatch("tes+", "tes?"));
		assertFalse(StringMatchUtils.wildCardMatch("te t", "tes?"));

		assertTrue(StringMatchUtils.wildCardMatch("test", "te?t"));
		assertTrue(StringMatchUtils.wildCardMatch("tett", "te?t"));
		assertTrue(StringMatchUtils.wildCardMatch("te1t", "te?t"));
		assertTrue(StringMatchUtils.wildCardMatch("te_t", "te?t"));
		assertTrue(StringMatchUtils.wildCardMatch("te.t", "te?t"));
		assertTrue(StringMatchUtils.wildCardMatch("te t", "te?t"));

		assertFalse(StringMatchUtils.wildCardMatch("tes.", "te?t"));
		assertFalse(StringMatchUtils.wildCardMatch("tes1", "te?t"));
		assertFalse(StringMatchUtils.wildCardMatch("tes_", "te?t"));
		assertFalse(StringMatchUtils.wildCardMatch("TEST", "te?t"));
		assertFalse(StringMatchUtils.wildCardMatch("te(t", "te?t"));
		assertFalse(StringMatchUtils.wildCardMatch("te+t", "te?t"));
		assertFalse(StringMatchUtils.wildCardMatch("t st", "te?t"));

		assertTrue(StringMatchUtils.wildCardMatch("test.txt", "*.*"));
		assertTrue(StringMatchUtils.wildCardMatch("tett.", "*.*"));
		assertTrue(StringMatchUtils.wildCardMatch(".te1t", "*.*"));
		assertTrue(StringMatchUtils.wildCardMatch("te_t.nbm", "*.*"));
		assertTrue(StringMatchUtils.wildCardMatch("te.t.", "*.*"));
		assertTrue(StringMatchUtils.wildCardMatch("t.e t", "*.*"));

		assertFalse(StringMatchUtils.wildCardMatch("test", "*.*"));
		assertFalse(StringMatchUtils.wildCardMatch("tes1", "*.*"));
		assertFalse(StringMatchUtils.wildCardMatch("TEST", "*.*"));
		assertFalse(StringMatchUtils.wildCardMatch("te(t.hg", "*.*"));
		assertFalse(StringMatchUtils.wildCardMatch("vcx.te+t", "*.*"));
		assertFalse(StringMatchUtils.wildCardMatch("t st", "*.*"));

		assertTrue(StringMatchUtils.wildCardMatch("t.t", "?.?"));
		assertTrue(StringMatchUtils.wildCardMatch("t.x", "?.?"));
		assertTrue(StringMatchUtils.wildCardMatch(" .t", "?.?"));
		assertTrue(StringMatchUtils.wildCardMatch("t. ", "?.?"));

		assertFalse(StringMatchUtils.wildCardMatch("t.ts", "?.?"));
		assertFalse(StringMatchUtils.wildCardMatch("et.t", "?.?"));
		assertFalse(StringMatchUtils.wildCardMatch("(.t", "?.?"));
		assertFalse(StringMatchUtils.wildCardMatch("ttt", "?.?"));
	}

	/**
	 * Is not empty test.
	 */
	@Test
	public void testRegExpMatch() {
		assertTrue(StringMatchUtils.regExpMatch(null, null));
		assertTrue(StringMatchUtils.regExpMatch("", ""));

		assertFalse(StringMatchUtils.regExpMatch("", "test*"));
		assertFalse(StringMatchUtils.regExpMatch(null, "test*"));
		assertFalse(StringMatchUtils.regExpMatch("test", null));

		assertTrue(StringMatchUtils.regExpMatch("test", "^test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("test1", "^test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("test_2", "^test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("testTEST", "^test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("test-content", "^test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("test.content", "^test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("test-content.txt", "^test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("test content.txt", "^test[\\w\\s-\\.]*$"));

		assertFalse(StringMatchUtils.regExpMatch("2test", "^test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regExpMatch("TEST", "^test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regExpMatch("TEST2", "^test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regExpMatch("test(content", "^test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regExpMatch("test@content", "^test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regExpMatch("test!content", "^test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regExpMatch(" test", "^test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regExpMatch("tes t", "^test[\\w\\s-\\.]*$"));

		assertTrue(StringMatchUtils.regExpMatch("test", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("test1", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("test_2", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("testTEST", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("test-content", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("test.content", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("test-content.txt", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("test content.txt", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("2test", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch(" test", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("1212testwasda", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("_test.wasda", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));

		assertFalse(StringMatchUtils.regExpMatch("TEST", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regExpMatch("TEST2", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regExpMatch("test(content", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regExpMatch("content&test", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regExpMatch("content+test", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regExpMatch("test!content", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regExpMatch("tes t", "^[\\w\\s-\\.]*test[\\w\\s-\\.]*$"));

		assertTrue(StringMatchUtils.regExpMatch("test", "^[\\w\\s-\\.]*test$"));
		assertTrue(StringMatchUtils.regExpMatch("1test", "^[\\w\\s-\\.]*test$"));
		assertTrue(StringMatchUtils.regExpMatch("2_test", "^[\\w\\s-\\.]*test$"));
		assertTrue(StringMatchUtils.regExpMatch("TESTtest", "^[\\w\\s-\\.]*test$"));
		assertTrue(StringMatchUtils.regExpMatch("content-test", "^[\\w\\s-\\.]*test$"));
		assertTrue(StringMatchUtils.regExpMatch("content.test", "^[\\w\\s-\\.]*test$"));
		assertTrue(StringMatchUtils.regExpMatch("txt--content.test", "^[\\w\\s-\\.]*test$"));
		assertTrue(StringMatchUtils.regExpMatch("txt content.test", "^[\\w\\s-\\.]*test$"));
		assertTrue(StringMatchUtils.regExpMatch("2test", "^[\\w\\s-\\.]*test$"));
		assertTrue(StringMatchUtils.regExpMatch(" test", "^[\\w\\s-\\.]*test$"));

		assertFalse(StringMatchUtils.regExpMatch("test1", "^[\\w\\s-\\.]*test$"));
		assertFalse(StringMatchUtils.regExpMatch("test_2", "^[\\w\\s-\\.]*test$"));
		assertFalse(StringMatchUtils.regExpMatch("TEST", "^[\\w\\s-\\.]*test$"));
		assertFalse(StringMatchUtils.regExpMatch("TEST2", "^[\\w\\s-\\.]*test$"));
		assertFalse(StringMatchUtils.regExpMatch("test(content", "^[\\w\\s-\\.]*test$"));
		assertFalse(StringMatchUtils.regExpMatch("content&test", "^[\\w\\s-\\.]*test$"));
		assertFalse(StringMatchUtils.regExpMatch("content+test", "^[\\w\\s-\\.]*test$"));
		assertFalse(StringMatchUtils.regExpMatch("test!content", "^[\\w\\s-\\.]*test$"));
		assertFalse(StringMatchUtils.regExpMatch("tes t", "^[\\w\\s-\\.]*test$"));

		assertTrue(StringMatchUtils.regExpMatch("1test", "^[\\w\\s-\\.]test$"));
		assertTrue(StringMatchUtils.regExpMatch("_test", "^[\\w\\s-\\.]test$"));
		assertTrue(StringMatchUtils.regExpMatch(".test", "^[\\w\\s-\\.]test$"));
		assertTrue(StringMatchUtils.regExpMatch("ctest", "^[\\w\\s-\\.]test$"));

		assertFalse(StringMatchUtils.regExpMatch("test", "^[\\w\\s-\\.]test$"));
		assertFalse(StringMatchUtils.regExpMatch("test1", "^[\\w\\s-\\.]test$"));
		assertFalse(StringMatchUtils.regExpMatch("test_2", "^[\\w\\s-\\.]test$"));
		assertFalse(StringMatchUtils.regExpMatch("TEST", "^[\\w\\s-\\.]test$"));
		assertFalse(StringMatchUtils.regExpMatch("(test", "^[\\w\\s-\\.]test$"));
		assertFalse(StringMatchUtils.regExpMatch("&test", "^[\\w\\s-\\.]test$"));
		assertFalse(StringMatchUtils.regExpMatch("+test", "^[\\w\\s-\\.]test$"));
		assertFalse(StringMatchUtils.regExpMatch("1tes t", "^[\\w\\s-\\.]test$"));

		assertTrue(StringMatchUtils.regExpMatch("test", "^tes[\\w\\s-\\.]$"));
		assertTrue(StringMatchUtils.regExpMatch("tess", "^tes[\\w\\s-\\.]$"));
		assertTrue(StringMatchUtils.regExpMatch("tes1", "^tes[\\w\\s-\\.]$"));
		assertTrue(StringMatchUtils.regExpMatch("tes_", "^tes[\\w\\s-\\.]$"));
		assertTrue(StringMatchUtils.regExpMatch("tes.", "^tes[\\w\\s-\\.]$"));
		assertTrue(StringMatchUtils.regExpMatch("tes ", "^tes[\\w\\s-\\.]$"));

		assertFalse(StringMatchUtils.regExpMatch("te.t", "^tes[\\w\\s-\\.]$"));
		assertFalse(StringMatchUtils.regExpMatch("te11", "^tes[\\w\\s-\\.]$"));
		assertFalse(StringMatchUtils.regExpMatch("te_2", "^tes[\\w\\s-\\.]$"));
		assertFalse(StringMatchUtils.regExpMatch("TEST", "^tes[\\w\\s-\\.]$"));
		assertFalse(StringMatchUtils.regExpMatch("(est", "^tes[\\w\\s-\\.]$"));
		assertFalse(StringMatchUtils.regExpMatch("tes+", "^tes[\\w\\s-\\.]$"));
		assertFalse(StringMatchUtils.regExpMatch("te t", "^tes[\\w\\s-\\.]$"));

		assertTrue(StringMatchUtils.regExpMatch("test", "^te[\\w\\s-\\.]t$"));
		assertTrue(StringMatchUtils.regExpMatch("tett", "^te[\\w\\s-\\.]t$"));
		assertTrue(StringMatchUtils.regExpMatch("te1t", "^te[\\w\\s-\\.]t$"));
		assertTrue(StringMatchUtils.regExpMatch("te_t", "^te[\\w\\s-\\.]t$"));
		assertTrue(StringMatchUtils.regExpMatch("te.t", "^te[\\w\\s-\\.]t$"));
		assertTrue(StringMatchUtils.regExpMatch("te t", "^te[\\w\\s-\\.]t$"));

		assertFalse(StringMatchUtils.regExpMatch("tes.", "^te[\\w\\s-\\.]t$"));
		assertFalse(StringMatchUtils.regExpMatch("tes1", "^te[\\w\\s-\\.]t$"));
		assertFalse(StringMatchUtils.regExpMatch("tes_", "^te[\\w\\s-\\.]t$"));
		assertFalse(StringMatchUtils.regExpMatch("TEST", "^te[\\w\\s-\\.]t$"));
		assertFalse(StringMatchUtils.regExpMatch("te(t", "^te[\\w\\s-\\.]t$"));
		assertFalse(StringMatchUtils.regExpMatch("te+t", "^te[\\w\\s-\\.]t$"));
		assertFalse(StringMatchUtils.regExpMatch("t st", "^te[\\w\\s-\\.]t$"));

		assertTrue(StringMatchUtils.regExpMatch("test.txt", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("tett.", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch(".te1t", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("te_t.nbm", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("te.t.", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));
		assertTrue(StringMatchUtils.regExpMatch("t.e t", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));

		assertFalse(StringMatchUtils.regExpMatch("test", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regExpMatch("tes1", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regExpMatch("TEST", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regExpMatch("te(t.hg", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regExpMatch("vcx.te+t", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));
		assertFalse(StringMatchUtils.regExpMatch("t st", "^[\\w\\s-\\.]*\\.[\\w\\s-\\.]*$"));

		assertTrue(StringMatchUtils.regExpMatch("t.t", "^[\\w\\s-\\.]\\.[\\w\\s-\\.]$"));
		assertTrue(StringMatchUtils.regExpMatch("t.x", "^[\\w\\s-\\.]\\.[\\w\\s-\\.]$"));
		assertTrue(StringMatchUtils.regExpMatch(" .t", "^[\\w\\s-\\.]\\.[\\w\\s-\\.]$"));
		assertTrue(StringMatchUtils.regExpMatch("t. ", "^[\\w\\s-\\.]\\.[\\w\\s-\\.]$"));

		assertFalse(StringMatchUtils.regExpMatch("t.ts", "^[\\w\\s-\\.]\\.[\\w\\s-\\.]$"));
		assertFalse(StringMatchUtils.regExpMatch("et.t", "^[\\w\\s-\\.]\\.[\\w\\s-\\.]$"));
		assertFalse(StringMatchUtils.regExpMatch("(.t", "^[\\w\\s-\\.]\\.[\\w\\s-\\.]$"));
		assertFalse(StringMatchUtils.regExpMatch("ttt", "^[\\w\\s-\\.]\\.[\\w\\s-\\.]$"));
	}

}
