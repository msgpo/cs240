package server.database.dao;

import server.database.Database;
import server.database.DBException;
import java.util.logging.*;

/**
*	abstract DAO class 
*/

abstract public class dao {
	
	private static Logger logger;
	static {
		logger = Logger.getLogger("big ol' indexing server");
	}

	protected Database db;

	/**
	* constructs a new dao object with the given DB
	* @param db an SQL database
	*/
	public dao(Database db){
		this.db = db;
	}

	abstract Object get() throws DBException;
	abstract void add(Object o) throws DBException;
	abstract void update(Object o) throws DBException;
	abstract void delete(Object o) throws DBException;
}
