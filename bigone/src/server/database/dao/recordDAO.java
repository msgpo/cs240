package server.database.dao;

import server.database.Database;
import server.database.DBException;
import shared.model.*;
import java.util.*;
import java.util.logging.*;
import java.sql.*;

/**
*	record data access object
*/
public class recordDAO extends dao{

	private static Logger logger;
	
	static {
		logger = Logger.getLogger("indexing");
	}
	
	/**
	*	creates a new recordDAO
	*	@param db the database to get connections from
	*/
	public recordDAO(Database db){
		super(db);
	}
	
	/**
	*	gets a List of all the records in the DB
	*	@return List<record> of all the records
	*	@throws DBException if impossible
	*/
	public List<record> getAll() throws DBException {
		ArrayList<record> result = new ArrayList<record>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select batch_key, field_key, value, ";
			query += "id from records";
			stmt = db.getConnection().prepareStatement(query);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String val = rs.getString(3);
				int bk = rs.getInt(1);
				int fk = rs.getInt(2);
				int id = rs.getInt(4);
				record r = new record(bk, fk, id);
				r.setValue(val);
				result.add(r);
			}
		}
		catch(SQLException e){
			DBException ee = new DBException(e.getMessage(), e);
			logger.throwing("recordDAO", "getAll", ee);
			throw ee;
		}
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
		logger.exiting("recordDAO", "getAll");

		return result;
	}
	
	
	/**
	*	adds a new record to the database, sets ID
	*	@param r record to be put in
	*	@throws DBException if unable to add record
	*/
	public void add(record r) throws DBException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "insert into records ";
			query += "(batch_key, field_key, value) ";
			query += "values (?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, r.getBatch());
			stmt.setInt(2, r.getField());
			stmt.setString(3, r.getValue());
			if(stmt.executeUpdate() == 1){
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				r.setID(keyRS.getInt(1));
			}
			else {
				throw new DBException("Can't insert record");
			}
		}
		catch(SQLException e) {
			throw new DBException("Can't insert record", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	/**
	*	syncs the DB's record table with the record in memory
	*	@param r record that needs to sync to DB
	*	@throws DBException if can't be done
	*/
	public void update(record r) throws DBException {
		PreparedStatement stmt = null;
		try {
			String query = "update records set ";
			query += "batch_key = ?, field_key = ?, value = ?, ";
			query += "where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, r.getBatch());
			stmt.setInt(2, r.getField());
			stmt.setString(3, r.getValue());
			stmt.setInt(4, r.getID());
			if(stmt.executeUpdate() != 1){
				//more or less than one update done
				throw new DBException("Could not update record");
			}
		}
		catch(SQLException e){
			throw new DBException("Could not update record", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}

	/**
	*	deletes a record from the DB
	*	@param r record you want to lose forever
	*	@throws DBException if can't be done
	*/
	public void delete(record r) throws DBException {
		PreparedStatement stmt = null;
		try {
			String query = "delete from records where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, r.getID());
			if(stmt.executeUpdate() != 1){
				throw new DBException("Could not delete record.");
			}
		}
		catch(SQLException e){
			throw new DBException("Could not delete record", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
}
	
	

