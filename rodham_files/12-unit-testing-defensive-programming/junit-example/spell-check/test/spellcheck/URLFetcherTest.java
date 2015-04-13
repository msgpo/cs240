
package spellcheck;

import org.junit.* ;
import static org.junit.Assert.* ;

import java.io.*;
import java.net.*;


public class URLFetcherTest {

	private URLFetcher fetcher;

	@Before
	public void setUp() {
		fetcher = new URLFetcher();
	}

	@After
	public void tearDown() {
		return;
	}

	@Test
	public void testValidURL()
		throws IOException, MalformedURLException {
		
		URL validURL = new URL("http://faculty.cs.byu.edu/~rodham/" +
							"cs240/design-document-examples/" +
							"spell-check/DataStructureExample.txt");

		String actual = fetcher.fetch(validURL);
		
		String expected = readFileAsString("DataStructureExample.txt");
		
		assertEquals(expected, actual);
	}

	private String readFileAsString(String fileName) throws IOException {
		
		StringBuffer buffer = new StringBuffer();
	
		FileInputStream input = new FileInputStream(fileName);
		try {
			int c;
			while ((c = input.read()) >= 0) {
				buffer.append((char)c);
			}
		}
		finally {
			input.close();
		}
		
		return buffer.toString();
	}

	@Test
	public void testInvalidURL()
		throws IOException, MalformedURLException{

		URL invalidURL = new URL("http://www.adflkjhadj.com/fkls.html");

		boolean failed = false;
		try {
			fetcher.fetch(invalidURL);
		}
		catch (IOException e) {
			failed = true;
		}

		assertTrue(failed);
	}
	
}


