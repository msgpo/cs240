
package spellcheck;

import org.junit.* ;
import static org.junit.Assert.* ;

import java.io.*;


public class DictionaryTest {

	private Dictionary dict;

	@Before
	public void setUp() throws IOException {
		dict = new Dictionary("dict.txt");
	}

	@After
	public void tearDown() {
		return;
	}

	@Test
	public void testValidWords() {
		assertTrue(dict.isValidWord("computer"));
		assertTrue(dict.isValidWord("programming"));
		assertTrue(dict.isValidWord("is"));
		assertTrue(dict.isValidWord("fun"));
	}

	@Test
	public void testInvalidWords() {
		assertFalse(dict.isValidWord(""));
		assertFalse(dict.isValidWord("google"));
		assertFalse(dict.isValidWord("gonculator"));
		assertFalse(dict.isValidWord("webmaster"));
	}
	
}

