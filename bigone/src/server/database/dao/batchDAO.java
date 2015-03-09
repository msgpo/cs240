package server.database.dao;

import server.database.Database;
import server.database.DBException;
import shared.model.*;
import java.util.*;
import java.util.logging.*;
import java.sql.*;

/**
*	batch data access object
*/
public class batchDAO extends dao{

	private static Logger logger;
	
	static {
		logger = Logger.getLogger("indexing");
	}
	
	/**
	*	creates a new batchDAO
	*	@param db the database to get connections from
	*/
	public batchDAO(Database db){
		super(db);
	}
	
	/**
	*	gets a List of all the batches in the DB
	*	@return List<batch> of all the batches
	*	@throws DBException if impossible
	*/
	public List<batch> getAll() throws DBException {
		ArrayList<batch> result = new ArrayList<batch>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select image_url, field_quantity, ";
			query += "proj_key, owned_by, id from batches";
			stmt = db.getConnection().prepareStatement(query);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String iu = rs.getString(1);
				int fq = rs.getInt(2);
				int pk = rs.getInt(3);
				int ob = rs.getInt(4);
				int id = rs.getInt(5);
				batch b = new batch(iu, id, pk);
				b.setFields(fq);
				if(ob != 9999999){
					b.updateUser(ob);
				}
				result.add(b);
			}
		}
		catch(SQLException e){
			DBException ee = new DBException(e.getMessage(), e);
			logger.throwing("batchDAO", "getAll", ee);
			throw ee;
		}
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
		logger.exiting("batchDAO", "getAll");

		return result;
	}
	
	
	/**
	*	adds a new batch to the database, sets ID
	*	@param b batch to be put in
	*	@throws DBException if unable to add batch
	*/
	public void add(batch b) throws DBException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "insert into batches (image_url, ";
			query += "field_quantity, proj_key, owned_by) ";
			query += "values (?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, b.getImage());
			stmt.setInt(2, b.getFields());
			stmt.setInt(3, b.getProject());
			if(b.isOwned()){
				stmt.setInt(4, b.whichUser());
			}
			else {
				stmt.setInt(4, 9999999);
			}
			if(stmt.executeUpdate() == 1){
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				b.setID(keyRS.getInt(1));
			}
			else {
				throw new DBException("Can't insert batch");
			}
		}
		catch(SQLException e) {
			throw new DBException("Can't insert batch", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	/**
	*	syncs the DB's batch table with the batch in memory
	*	@param b batch that needs to sync to DB
	*	@throws DBException if can't be done
	*/
	public void update(batch b) throws DBException {
		PreparedStatement stmt = null;
		try {
			String query = "update batches set image_url = ?, ";
			query += "field_quantity = ?, proj_key = ?, ";
			query += "owned_by = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, b.getImage());
			stmt.setInt(2, b.getFields());
			stmt.setInt(3, b.getProject());
			if(b.isOwned()){
				stmt.setInt(4, b.whichUser());
			}
			else {
				stmt.setInt(4, 9999999);
			}
			stmt.setInt(5, b.getID());
			if(stmt.executeUpdate() != 1){
				//more or less than one update done
				throw new DBException("Could not update batch");
			}
		}
		catch(SQLException e){
			throw new DBException("Could not update batch", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}

	/**
	*	deletes a batch from the DB
	*	@param b batch you want to lose forever
	*	@throws DBException if can't be done
	*/
	public void delete(batch b) throws DBException {
		PreparedStatement stmt = null;
		try {
			String query = "delete from batchs where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, b.getID());
			if(stmt.executeUpdate() != 1){
				throw new DBException("Could not delete batch.");
			}
		}
		catch(SQLException e){
			throw new DBException("Could not delete batch", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
}
	
	

