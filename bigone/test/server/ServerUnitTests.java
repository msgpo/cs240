package server;

import org.junit.* ;
import org.junit.runner.JUnitCore;
import static org.junit.Assert.* ;

public class ServerUnitTests {
	
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}
	
	
	//  later, stick some code in here to verify the main server
	//  bits, i guess
	@Test
	public void test_1() {
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}

	public static void main(String[] args) {
		
		String[] testClasses = new String[] {
				"server.ServerUnitTests",
				"server.userDAOTest",
				"server.projectDAOTest",
				"server.batchDAOTest",
				"server.fieldDAOTest",
				"server.recordDAOTest",
				"server.communicatorTest"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}

