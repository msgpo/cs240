package server.database.dao;

import server.database.Database;
import server.database.DBException;
import shared.model.*;
import java.util.*;

/**
*	record data access object
*/
public class recordDAO extends dao{

	public recordDAO(Database db){
		super(db);
	}
	
	@Override
	public record get() throws DBException {
		// do stuff
		return null;
	}

	public List<record> getAll() throws DBException {
		// do stuff
		return null;
	}

	@Override
	public void add(Object o) throws DBException {
		// do stuff
	}

	@Override
	public void update(Object o) throws DBException {
		// do stuff
	}

	@Override
	public void delete(Object o) throws DBException {
		// do stuff
	}

	public Database fake(){
		return db;
	}

	
}
