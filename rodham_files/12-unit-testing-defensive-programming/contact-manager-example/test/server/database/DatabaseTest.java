package server.database;

import static org.junit.Assert.*;

import java.util.List;
import org.junit.*;

import server.*;
import shared.model.*;

public class DatabaseTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		// Load database driver		
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}

	@Before
	public void setUp() throws Exception {

		// Delete all contacts from the database
		Database db = new Database();		
		db.startTransaction();
		
		List<Contact> contacts = db.getContacts().getAll();		
		for (Contact c : contacts) {
			db.getContacts().delete(c);
		}
		
		db.endTransaction(true);		
	}

	@After
	public void tearDown() throws Exception {
		return;
	}

	@Test
	public void testCommit() throws ServerException {
		
		// Add two contacts
		addTwoContacts(true);
		
		// Start new transaction
		Database db = new Database();
		Contacts dbContacts = db.getContacts();
		db.startTransaction();
		
		// Make sure the previous transaction was properly committed
		List<Contact> all = dbContacts.getAll();
		assertEquals(2, all.size());
		
		db.endTransaction(true);
	}

	@Test
	public void testRollback() throws ServerException {
		
		// Add two contacts
		addTwoContacts(false);
		
		// Start new transaction
		Database db = new Database();
		Contacts dbContacts = db.getContacts();
		db.startTransaction();
		
		// Make sure the previous transaction was properly rolled back
		List<Contact> all = dbContacts.getAll();
		assertEquals(0, all.size());
		
		db.endTransaction(true);
	}
	
	private void addTwoContacts(boolean commit) throws ServerException {
		
		// Start transaction	
		Database db = new Database();
		Contacts dbContacts = db.getContacts();
		db.startTransaction();
		
		List<Contact> all = dbContacts.getAll();
		assertEquals(0, all.size());
		
		// Add two contacts
		Contact bob = new Contact(-1, "Bob White", "801-999-9999", "1234 State Street", 
									"bob@white.org", "http://www.white.org/bob");
		Contact amy = new Contact(-1, "Amy White", "777-777-7777", "77 Industrial Boulevard",
									"amy@white.org", "http://www.white.org/amy");
		
		dbContacts.add(bob);
		dbContacts.add(amy);
		
		all = dbContacts.getAll();
		assertEquals(2, all.size());

		// End transaction	
		db.endTransaction(commit);	
	}

}
