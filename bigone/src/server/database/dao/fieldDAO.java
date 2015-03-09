package server.database.dao;

import server.database.Database;
import server.database.DBException;
import shared.model.*;
import java.util.*;
import java.util.logging.*;
import java.sql.*;

/**
*	field data access object
*/
public class fieldDAO extends dao{

	private static Logger logger;
	
	static {
		logger = Logger.getLogger("indexing");
	}
	
	/**
	*	creates a new fieldDAO
	*	@param db the database to get connections from
	*/
	public fieldDAO(Database db){
		super(db);
	}
	
	/**
	*	gets a List of all the fields in the DB
	*	@return List<field> of all the fields
	*	@throws DBException if impossible
	*/
	public List<field> getAll() throws DBException {
		ArrayList<field> result = new ArrayList<field>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select proj_key, title, x_coord, ";
			query += "width, help_url, known_value_url, ";
			query += "field_number, id from fields";
			stmt = db.getConnection().prepareStatement(query);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int pk= rs.getInt(1);
				String ti = rs.getString(2);
				int xc = rs.getInt(3);
				int fw = rs.getInt(4);
				String hu = rs.getString(5);
				String kv = rs.getString(6);
				int fn = rs.getInt(7);
				int id = rs.getInt(8);
				field f = new field(ti, id, fn, fw, xc, kv, hu, pk);
				result.add(f);
			}
		}
		catch(SQLException e){
			DBException ee = new DBException(e.getMessage(), e);
			logger.throwing("fieldDAO", "getAll", ee);
			throw ee;
		}
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
		logger.exiting("fieldDAO", "getAll");

		return result;
	}
	
	
	/**
	*	adds a new field to the database, sets ID
	*	@param f field to be put in
	*	@throws DBException if unable to add field
	*/
	public void add(field f) throws DBException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "insert into fields (proj_key, ";
			query += "title, x_coord, width, help_url, ";
			query += "known_value_url, field_number) values ";
			query += "(?, ?, ?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, f.getProject());
			stmt.setString(2, f.getTitle());
			stmt.setInt(3, f.getXCoord());
			stmt.setInt(4, f.getWidth());
			stmt.setString(5, f.getHelp());
			stmt.setString(6, f.getVals());
			stmt.setInt(7, f.getNumber());
			if(stmt.executeUpdate() == 1){
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				f.setID(keyRS.getInt(1));
			}
			else {
				throw new DBException("Can't insert field");
			}
		}
		catch(SQLException e) {
			throw new DBException("Can't insert field", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	/**
	*	syncs the DB's field table with the field in memory
	*	@param f field that needs to sync to DB
	*	@throws DBException if can't be done
	*/
	public void update(field f) throws DBException {
		PreparedStatement stmt = null;
		try {
			String query = "update fields set proj_key = ?, ";
			query += "title = ?, x_coord = ?, width = ?, ";
			query += "help_url = ?, known_value_url = ?, ";
			query += "field_number = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, f.getProject());
			stmt.setString(2, f.getTitle());
			stmt.setInt(3, f.getXCoord());
			stmt.setInt(4, f.getWidth());
			stmt.setString(5, f.getHelp());
			stmt.setString(6, f.getVals());
			stmt.setInt(7, f.getNumber());
			stmt.setInt(8, f.getID());
			if(stmt.executeUpdate() != 1){
				//more or less than one update done
				throw new DBException("Could not update field");
			}
		}
		catch(SQLException e){
			throw new DBException("Could not update field", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}

	/**
	*	deletes a field from the DB
	*	@param f field you want to lose forever
	*	@throws DBException if can't be done
	*/
	public void delete(field f) throws DBException {
		PreparedStatement stmt = null;
		try {
			String query = "delete from fields where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, f.getID());
			if(stmt.executeUpdate() != 1){
				throw new DBException("Could not delete field.");
			}
		}
		catch(SQLException e){
			throw new DBException("Could not delete field", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
}
	
	

