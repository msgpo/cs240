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
	public ArrayList<record> getAll() throws DBException {
		ArrayList<record> result = new ArrayList<record>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select batch_key, field_key, value, ";
			query += "id, number from records";
			stmt = db.getConnection().prepareStatement(query);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String val = rs.getString(3);
				int bk = rs.getInt(1);
				int fk = rs.getInt(2);
				int id = rs.getInt(4);
				int nu = rs.getInt(5);
				record r = new record(bk, fk, id, nu);
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
	*	gets a List of all the records in the DB matching search criteria
	*	@param f the field ID
	*	@param v the string that should "complete" that field
	*	@return List<record> of all the records we find
	*	@throws DBException if there's a screwup
	*/
	public ArrayList<record> getAll(int f, String v) throws DBException {
		ArrayList<record> result = new ArrayList<record>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select batch_key, ";
			query += "id, number from records where ";
			query += "field_key = ? AND value = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(4, f);
			stmt.setString(5, v);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int bk = rs.getInt(1);
				int id = rs.getInt(2);
				int nu = rs.getInt(3);
				record r = new record(bk, f, id, nu);
				r.setValue(v);
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
	*	tells if a certain record is in the DB
	*	@param bID batch ID
	*	@param fNum field number
	*	@param rNum record number
	*	@return -1 if absent, rID if in DB 
	* 	@throws DBException if faults
	*/
	public int checkPresence(int bID, int fNum, int rNum) throws DBException {
		int ret = -1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// get field key first
		int pID = -1;
		int fKey = -1;
		try {
			String query = "select proj_key from batches ";
			query += "where	id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, bID);
			rs = stmt.executeQuery();
			if(rs.next()){
				pID = rs.getInt(1);
			}
			else {
				//do nothing, pID stays -1
			}
		}
		catch(SQLException e){
			DBException ee = new DBException(e.getMessage(), e);
			logger.throwing("recordDAO", "checkPresence", ee);
			throw ee;
		}
		try {
			String query = "select id from fields ";
			query += "where	proj_key = ? and field_number = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, pID);
			stmt.setInt(2, fNum);
			rs = stmt.executeQuery();
			if(rs.next()){
				fKey = rs.getInt(1);
			}
			else {
				//do nothing, fKey stays -1
			}
		}
		catch(SQLException e){
			DBException ee = new DBException(e.getMessage(), e);
			logger.throwing("recordDAO", "checkPresence", ee);
			throw ee;
		}
		try {
			String query = "select id ";
			query += "from records where ";
			query += "field_key = ? AND batch_key = ? AND number = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, fKey);
			stmt.setInt(2, bID);
			stmt.setInt(3, rNum);
			rs = stmt.executeQuery();
			if(rs.next()){
				ret = rs.getInt(1);
			}
			else {
				// do nothing, ret stays -1
			}
		}
		catch(SQLException e){
			DBException ee = new DBException(e.getMessage(), e);
			logger.throwing("recordDAO", "checkPresence", ee);
			throw ee;
		}
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
		logger.exiting("recordDAO", "getAll");

		return ret;
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
			query += "(batch_key, field_key, value, number) ";
			query += "values (?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, r.getBatch());
			stmt.setInt(2, r.getField());
			stmt.setString(3, r.getValue());
			stmt.setInt(4, r.getNumber());
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
			query += "number = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, r.getBatch());
			stmt.setInt(2, r.getField());
			stmt.setString(3, r.getValue());
			stmt.setInt(4, r.getNumber());
			stmt.setInt(5, r.getID());
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
	
	

