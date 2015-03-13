package server;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import server.database.*;
import server.database.dao.*;
import shared.model.project;

public class projectDAOTest {
	
	private server.database.Database db = 
			new server.database.Database
			("/Users/rt/workspaces/cs240/bigone/stowage.db");
		// funny bug here:
		// I did sed s/User/Project/g to clean up a few
		// typos I missed, but that also screwed up my
		// pathname, and I couldn't figure out why that didn't happen 
		// with my userDAO test too.
	
	@BeforeClass
	public static void setUp() throws DBException{
		Database.initialize();
	}
	
	@After
	public void teardown(){
		db.endTX(false);
	}
	
	@Test
	public void addGetTest() throws DBException{
		project p = new project("img", "title", 0, 1, 2, 3);
		
		db.startTX();
		db.getProjectDAO().add(p);
		ArrayList<project> ret = db.getProjectDAO().getAll();
		assertEquals("added project should be alone in DB", p,
				ret.get(0));
		assertEquals("get by ID", p, 
				db.getProjectDAO().get(p.getID()));
	}
	
	@Test
	public void deleteTest() throws DBException{
		project p1 = new project("img", "title", 0, 1, 2, 3);
		project p2 = new project("pic", "named", 0, 7, 8, 9);
		
		db.startTX();
		db.getProjectDAO().add(p1);
		db.getProjectDAO().add(p2);
				
		assertEquals("should be 2 in DB before deletion", 2,
				db.getProjectDAO().getAll().size());
		db.getProjectDAO().delete(p1);
		assertEquals("should be 1 in DB after 1 delete", 1,
				db.getProjectDAO().getAll().size());
		assertEquals("and it should be the one not deleted", p2,
				db.getProjectDAO().getAll().get(0));
		db.getProjectDAO().delete(p2);
		assertTrue("DB should be empty after 2 deletes", 
				db.getProjectDAO().getAll().isEmpty());
		
		db.endTX(false);
	}
	
	@Test
	public void updateTest() throws DBException{
		project p1 = new project("img", "title", 0, 1, 2, 3);
		project p2 = new project("pic", "named", 0, 7, 8, 9);
		
		db.startTX();
		db.getProjectDAO().add(p1);
		p2.setID(p1.getID());
		db.getProjectDAO().update(p2);
		assertEquals("original project should be new project", p2, 
				db.getProjectDAO().get(p2.getID()));
		assertEquals("should be only project in DB", 1,
				db.getProjectDAO().getAll().size());
		
		db.endTX(false);
	}
	
}
