
package spellcheck;

import org.junit.* ;
import static org.junit.Assert.* ;

import java.io.*;
import java.net.*;
import java.util.*;


public class SpellingCheckerTest {

	private static final String URL_BASE = 
		"http://faculty.cs.byu.edu/~rodham/test_data/";
	
	private SpellingChecker checker;

	@Before
	public void setUp() {
		checker = new SpellingChecker();
	}

	@After
	public void tearDown() {
		return;
	}

	@Test
	public void testOne() throws IOException, MalformedURLException {
		URL url = new URL(URL_BASE + "one.txt");
		
		SortedMap<String, Integer> expected = new TreeMap<String, Integer>();
		expected.put("forrrrr", (Integer)2);
		expected.put("scorrrrre", (Integer)2);
		expected.put("annnnnd", (Integer)1);
		expected.put("sevvvvin", (Integer)3);

		SortedMap<String, Integer> actual = checker.check(url);
		assertEquals(expected, actual);
	}

}



