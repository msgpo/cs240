package server.database.dao;

import server.database.Database;
import server.database.DBException;
import shared.model.*;
import java.util.*;
import java.util.logging.*;
import java.sql.*;

/**
*	project data access object
*/
public class projectDAO extends dao{

	private static Logger logger;
	
	static {
		logger = Logger.getLogger("indexing");
	}
	
	/**
	*	creates a new projectDAO
	*	@param db the database to get connections from
	*/
	public projectDAO(Database db){
		super(db);
	}
	
	/**
	*	gets a List of all the projects in the DB
	*	@return List<project> of all the projects
	*	@throws DBException if impossible
	*/
	public ArrayList<project> getAll() throws DBException {
		ArrayList<project> result = new ArrayList<project>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select sample_image, title, y_coord, ";
			query += "rec_height, record_quantity, id from projects";
			stmt = db.getConnection().prepareStatement(query);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String si = rs.getString(1);
				String ti = rs.getString(2);
				int yc = rs.getInt(3);
				int rh = rs.getInt(4);
				int rq = rs.getInt(5);
				int id = rs.getInt(6);
				project p = new project(si, ti, id, yc, rh, rq);
				result.add(p);
			}
		}
		catch(SQLException e){
			DBException ee = new DBException(e.getMessage(), e);
			logger.throwing("projectDAO", "getAll", ee);
			throw ee;
		}
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
		logger.exiting("projectDAO", "getAll");

		return result;
	}

	/**
	*	gets a project, by the ID number
	*	@param p project ID
	*	@return project
	*	@throws DBException if impossible
	*/
	public project get(int p) throws DBException {
		project result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select sample_image, title, y_coord, ";
			query += "rec_height, record_quantity ";
			query += "from projects where id = ? ";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, p);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String si = rs.getString(1);
				String ti = rs.getString(2);
				int yc = rs.getInt(3);
				int rh = rs.getInt(4);
				int rq = rs.getInt(5);
				result = new project(si, ti, p, yc, rh, rq);
			}
		}
		catch(SQLException e){
			DBException ee = new DBException(e.getMessage(), e);
			logger.throwing("projectDAO", "get", ee);
			throw ee;
		}
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
		logger.exiting("projectDAO", "get");

		return result;
	}

	
	/**
	*	adds a new project to the database, sets ID
	*	@param p project to be put in
	*	@throws DBException if unable to add project
	*/
	public void add(project p) throws DBException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "insert into projects (sample_image, ";
			query += "title, y_coord, rec_height, record_quantity) ";
			query += "values (?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, p.getImage());
			stmt.setString(2, p.getTitle());
			stmt.setInt(3, p.getYCoord());
			stmt.setInt(4, p.getHeight());
			stmt.setInt(5, p.getRecordQuantity());
			if(stmt.executeUpdate() == 1){
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				p.setID(keyRS.getInt(1));
			}
			else {
				throw new DBException("Can't insert project");
			}
		}
		catch(SQLException e) {
			throw new DBException("Can't insert project", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	/**
	*	syncs the DB's project table with the project in memory
	*	@param p project that needs to sync to DB
	*	@throws DBException if can't be done
	*/
	public void update(project p) throws DBException {
		PreparedStatement stmt = null;
		try {
			String query = "update projects set sample_image = ?, ";
			query += "title = ?, y_coord = ?, rec_height = ?, ";
			query += "record_quantity = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, p.getImage());
			stmt.setString(2, p.getTitle());
			stmt.setInt(3, p.getYCoord());
			stmt.setInt(4, p.getHeight());
			stmt.setInt(5, p.getRecordQuantity());
			stmt.setInt(6, p.getID());
			if(stmt.executeUpdate() != 1){
				//more or less than one update done
				throw new DBException("Could not update project");
			}
		}
		catch(SQLException e){
			throw new DBException("Could not update project", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}

	/**
	*	deletes a project from the DB
	*	@param p project you want to lose forever
	*	@throws DBException if can't be done
	*/
	public void delete(project p) throws DBException {
		PreparedStatement stmt = null;
		try {
			String query = "delete from projects where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, p.getID());
			if(stmt.executeUpdate() != 1){
				throw new DBException("Could not delete project.");
			}
		}
		catch(SQLException e){
			throw new DBException("Could not delete project", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
}
	
	

