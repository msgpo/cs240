package server.database.dao;

import server.database.Database;
import server.database.DBException;
import shared.model.*;
import java.util.*;
import java.util.logging.*;
import java.sql.*;

/**
*	user data access object
*/
public class userDAO extends dao{

	private static Logger logger;
	
	static {
		logger = Logger.getLogger("indexing");
	}
	
	/**
	*	creates a new userDAO
	*	@param db the database to get connections from
	*/
	public userDAO(Database db){
		super(db);
	}
	
	/**
	*	gets a List of all the users in the DB
	*	@return List<user> of all the users
	*	@throws DBException if impossible
	*/
	public List<user> getAll() throws DBException {
		ArrayList<user> result = new ArrayList<user>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select first_name, last_name,";
			query += " password, id, records_indexed,";
			query += " assigned_batch from users";
			stmt = db.getConnection().prepareStatement(query);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String fn = rs.getString(1);
				String ln = rs.getString(2);
				String pw = rs.getString(3);
				int id = rs.getInt(4);
				int ri = rs.getInt(5);
				int ab = rs.getInt(6);
				user u = new user(fn, ln, pw, ri, id);
				u.updateRecords(ri);
				if(ab != 9999999){
					u.assignBatch(ab);
				}
				
				result.add(u);
			}
		}
		catch(SQLException e){
			DBException ee = new DBException(e.getMessage(), e);
			logger.throwing("userDAO", "getAll", ee);
			throw ee;
		}
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
		logger.exiting("userDAO", "getAll");

		return result;
	}
	

	/**
	*	get user password.
	*	@param fName user's first name
	*	@param lName user's last name
	*	@return string password
	*	@throws DBException when no such user exists, probably
	*/
	public String get(String fName, String lName) throws DBException {
		String result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select password from users where ";
			query += "first_name = ? AND last_name = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, fName);
			stmt.setString(2, lName);
			rs = stmt.executeQuery();
			if(rs.next()){
				result = rs.getString(1);
			}
			else {
				throw new DBException("no such user");
			}
		}
		catch(SQLException e){
			throw new DBException("get userpass error", e);
		}
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
		return result;
	}
	
	/**
	*	adds a new user to the database, sets user's new ID
	*	@param u user to be put in
	*	@throws DBException if unable to add user
	*/

	
	public void add(user u) throws DBException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "insert into users (first_name, ";
			query += "last_name, password, records_indexed, ";
			query += "assigned_batch) values (?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, u.getFName());
			stmt.setString(2, u.getLName());
			stmt.setString(3, u.getPW());
			stmt.setInt(4, u.getRecords());
			if(u.hasBatch()){
				stmt.setInt(5, u.whichBatch());
			}
			else {
				stmt.setInt(5, 9999999);
			}
			if(stmt.executeUpdate() == 1){
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				u.setID(keyRS.getInt(1));
			}
			else {
				throw new DBException("Can't insert user");
			}
		}
		catch(SQLException e) {
			throw new DBException("Can't insert user", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	/**
	*	syncs the DB's user table with the user in memory
	*	@param u user that needs to sync to DB
	*	@throws DBException if can't be done
	*/
	public void update(user u) throws DBException {
		PreparedStatement stmt = null;
		try {
			String query = "update users set first_name = ?, ";
			query += "last_name = ?, password = ?, records_indexed = ?, ";
			query += "assigned_batch = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, u.getFName());
			stmt.setString(2, u.getLName());
			stmt.setString(3, u.getPW());
			stmt.setInt(4, u.getRecords());
			stmt.setInt(5, u.whichBatch());
			stmt.setInt(6, u.getID());
			if(stmt.executeUpdate() != 1){
				//more or less than one update done
				throw new DBException("Could not update user");
			}
		}
		catch(SQLException e){
			throw new DBException("Could not update user", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}

	/**
	*	deletes a user from the DB
	*	@param u user you want to lose forever
	*	@throws DBException if can't be done
	*/
	public void delete(user u) throws DBException {
		PreparedStatement stmt = null;
		try {
			String query = "delete from users where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, u.getID());
			if(stmt.executeUpdate() != 1){
				throw new DBException("Could not delete user.");
			}
		}
		catch(SQLException e){
			throw new DBException("Could not delete user", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
}
	
	

