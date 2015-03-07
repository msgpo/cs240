package server.database.dao;

import server.database.Database;
import server.database.DBException;
import shared.model.*;
import java.util.*;

/**
*	user data access object
*/
public class userDAO extends dao{

	public userDAO(Database db){
		super(db);
	}
	
	@Override
	public user get() throws DBException {
		// do stuff
		return null;
	}

	public List<user> getAll() throws DBException {
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
