package server;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import server.database.*;
import server.database.dao.*;
import shared.model.record;
import shared.model.batch;
import shared.model.field;
import shared.model.project;

public class recordDAOTest {
	
	private server.database.Database db = 
			new server.database.Database
			("/Users/rt/workspaces/cs240/bigone/stowage.db");
	
	@BeforeClass
	public static void setUp() throws DBException{
		Database.initialize();
	}
	
	private int pID = 0;
	private int bID = 0;
	private int fID = 0;
	//private int f2I = 0;
	
	@Before
	public void addProj() throws DBException{
		db.startTX();
		project p = new project("PIMG", "P", 0, 1, 1, 1);
		db.getProjectDAO().add(p);
		pID = p.getID();
		batch b = new batch("BIMG", 0, pID);
		db.getBatchDAO().add(b);
		bID = b.getID();
		field f = new field("FT", 0, 1, 1, 1, "KV", "FH", pID);
		db.getFieldDAO().add(f);
		fID = f.getID();
		//field f = new field("TF", 0, 2, 
	}
	
	@After
	public void teardown(){
		db.endTX(false);
	}
	
	@Test
	public void addGetTest() throws DBException{
		record r = new record(bID, fID, 0, 1);
		r.setValue("val");
		

		db.getRecordDAO().add(r);
		ArrayList<record> ret = db.getRecordDAO().getAll();
		assertEquals("added record should be alone in DB", r,
				ret.get(0));
	//	assertEquals("get by ID", b, 
	//			db.getRecordDAO().get(b.getID()));
	//		none such for records, keeping in case tho
	}
	
	@Test
	public void deleteTest() throws DBException{
		record r1 = new record(bID, fID, 0, 1);
		record r2 = new record(bID, fID, 0, 2);
		r1.setValue("val");
		r2.setValue("lav");
		

		db.getRecordDAO().add(r1);
		db.getRecordDAO().add(r2);
				
		assertEquals("should be 2 in DB before deletion", 2,
				db.getRecordDAO().getAll().size());
		db.getRecordDAO().delete(r2);
		assertEquals("should be 1 in DB after 1 delete", 1,
				db.getRecordDAO().getAll().size());
		assertEquals("and it should be the one not deleted", r1,
				db.getRecordDAO().getAll().get(0));
		db.getRecordDAO().delete(r1);
		assertTrue("DB should be empty after 2 deletes", 
				db.getRecordDAO().getAll().isEmpty());
		
		db.endTX(false);
	}
	
	@Test
	public void updateTest() throws DBException{
		record r1 = new record(bID, fID, 0, 1);
		record r2 = new record(bID, fID, 0, 2);
		r1.setValue("val");
		r2.setValue("lav");
		

		db.getRecordDAO().add(r1);
		r2.setID(r1.getID());
		db.getRecordDAO().update(r2);
		assertEquals("original record should be new record", r2, 
				db.getRecordDAO().getAll().get(0));
		assertEquals("should be only record in DB", 1,
				db.getRecordDAO().getAll().size());
		
		db.endTX(false);
	}
	
}
