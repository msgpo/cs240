package server;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import server.database.*;
import server.database.dao.*;
import shared.model.field;
import shared.model.project;

public class fieldDAOTest {
	
	private server.database.Database db = 
			new server.database.Database
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
		field f = new field("Title", 0, 1, 2, 3, "kval", "hel", pID);
		

		db.getFieldDAO().add(f);
		ArrayList<field> ret = db.getFieldDAO().getAll();
		assertEquals("added field should be alone in DB", f,
				ret.get(0));
	//	assertEquals("get by ID", b, 
	//			db.getFieldDAO().get(b.getID()));
	//		none such for fieldes, keeping in case tho
	}
	
	@Test
	public void deleteTest() throws DBException{
		field f1 = new field("Title", 0, 1, 2, 3, "kval", "hel", pID);
		field f2 = new field("Named", 0, 7, 8, 9, "lavk", "leh", pID);
		

		db.getFieldDAO().add(f1);
		db.getFieldDAO().add(f2);
				
		assertEquals("should be 2 in DB before deletion", 2,
				db.getFieldDAO().getAll().size());
		db.getFieldDAO().delete(f2);
		assertEquals("should be 1 in DB after 1 delete", 1,
				db.getFieldDAO().getAll().size());
		assertEquals("and it should be the one not deleted", f1,
				db.getFieldDAO().getAll().get(0));
		db.getFieldDAO().delete(f1);
		assertTrue("DB should be empty after 2 deletes", 
				db.getFieldDAO().getAll().isEmpty());
		
		db.endTX(false);
	}
	
	@Test
	public void updateTest() throws DBException{
		field f1 = new field("Title", 0, 1, 2, 3, "kval", "hel", pID);
		field f2 = new field("Named", 0, 7, 8, 9, "lavk", "leh", pID);
		

		db.getFieldDAO().add(f1);
		f2.setID(f1.getID());
		db.getFieldDAO().update(f2);
		assertEquals("original field should be new field", f2, 
				db.getFieldDAO().getAll().get(0));
		assertEquals("should be only field in DB", 1,
				db.getFieldDAO().getAll().size());
		
		db.endTX(false);
	}
	
}
