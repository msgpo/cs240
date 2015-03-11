package server;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import server.database.*;
import server.database.dao.*;
import shared.model.*;

public class userDAOTest {
	
	private server.database.Database db = 
			new server.database.Database
			("/Users/rt/workspaces/cs240/bigone/stowage.db");
	
	@BeforeClass
	public void setUp() throws DBException{
		Database.initialize();
	}
	
	@After
	public void teardown(){
		//no idea
	}
	
	@Test
	public void addGetTest() throws DBException{
		user u = new user("jj", "john", "jormson", "123", 3, 0);
		
		db.startTX();
		db.getUserDAO().add(u);
		ArrayList<user> ret = db.getUserDAO().getAll();
		assertTrue("added user should be alone in DB", ret.contains(u));
		assertEquals("get user by id", u, db.getUserDAO().get("jj"));
	
		db.endTX(false);
	}
	
	@Test
	public void deleteTest() throws DBException{
		user u1 = new user("jj", "john", "jormson", "123", 3, 0);
		user u2 = new user("kk", "kim", "kardashian", "pw", 0, 0);
		
		db.startTX();
		db.getUserDAO().add(u1);
		db.getUserDAO().add(u2);
				
		assertEquals("should be 2 in DB before deletion", 2,
				db.getUserDAO().getAll().size());
		db.getUserDAO().delete(u1);
		assertEquals("should be 1 in DB after 1 delete", 1,
				db.getUserDAO().getAll().size());
		db.getUserDAO().delete(u2);
		assertTrue("DB should be empty after 2 deletes", 
				db.getUserDAO().getAll().isEmpty());
		
		db.endTX(false);
	}
	
	@Test
	public void updateTest() throws DBException{
		user u1 = new user("a", "b", "c", "PW", 7, 0);
		
		db.startTX();
		db.getUserDAO().add(u1);
		user u2 = new user("k", "k", "k", "un", 0, u1.getID());
		db.getUserDAO().update(u2);
		assertEquals("original user should equal modified user", u2, 
				db.getUserDAO().get("k"));
		
		db.endTX(false);
	}
	
}
