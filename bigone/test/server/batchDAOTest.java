package server;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import server.database.*;
import server.database.dao.*;
import shared.model.batch;
import shared.model.project;

public class batchDAOTest {
	
	private Database db = 
			new Database
			("/Users/rt/workspaces/cs240/bigone/stowage.db");
	
	@BeforeClass
	public static void setUp() throws DBException{
		Database.initialize();
	}
	
	private int pID = 0;
	
	@Before
	public void addProj() throws DBException{
		db.startTX();
		project p = new project("i", "t", 0, 1,1,1);
		db.getProjectDAO().add(p);
		pID = p.getID();
	}
	
	@After
	public void teardown(){
		db.endTX(false);
	}
	
	@Test
	public void addGetTest() throws DBException{
		batch b = new batch("img", 0, pID);
		

		db.getBatchDAO().add(b);
		ArrayList<batch> ret = db.getBatchDAO().getAll();
		assertEquals("added batch should be alone in DB", b,
				ret.get(0));
	//	assertEquals("get by ID", b, 
	//			db.getBatchDAO().get(b.getID()));
	//		none such for batches, keeping in case tho
	}
	
	@Test
	public void getFreeTest() throws DBException{
		batch b = new batch("img", 0, pID);
		b.setFields(7);
		
		db.getBatchDAO().add(b);
		batch gotB = db.getBatchDAO().get(pID);
		assertEquals("no user yet", -1, gotB.whichUser());
		gotB.updateUser(4);
		db.getBatchDAO().update(gotB);
		//batch cant = db.getBatchDAO().get(pID);
	}
		
	
	@Test
	public void deleteTest() throws DBException{
		batch b1 = new batch("img", 0, pID);
		batch b2 = new batch("pic", 0, pID);
		

		db.getBatchDAO().add(b1);
		db.getBatchDAO().add(b2);
				
		assertEquals("should be 2 in DB before deletion", 2,
				db.getBatchDAO().getAll().size());
		db.getBatchDAO().delete(b2);
		assertEquals("should be 1 in DB after 1 delete", 1,
				db.getBatchDAO().getAll().size());
		assertEquals("and it should be the one not deleted", b1,
				db.getBatchDAO().getAll().get(0));
		db.getBatchDAO().delete(b1);
		assertTrue("DB should be empty after 2 deletes", 
				db.getBatchDAO().getAll().isEmpty());
		
		db.endTX(false);
	}
	
	@Test
	public void updateTest() throws DBException{
		batch b1 = new batch("img", 0, pID);
		batch b2 = new batch("pic", 0, pID);
		

		db.getBatchDAO().add(b1);
		b2.setID(b1.getID());
		db.getBatchDAO().update(b2);
		assertEquals("original batch should be new batch", b2, 
				db.getBatchDAO().getAll().get(0));
		assertEquals("should be only batch in DB", 1,
				db.getBatchDAO().getAll().size());
		
		db.endTX(false);
	}
	
}
