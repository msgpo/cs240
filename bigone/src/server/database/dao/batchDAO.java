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
	public ArrayList<batch> getAll() throws DBException {
		ArrayList<batch> result = new ArrayList<batch>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select image_url, field_quantity, ";
			query += "proj_key, owned_by, indexed, id from batches";
			stmt = db.getConnection().prepareStatement(query);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String iu = rs.getString(1);
				int fq = rs.getInt(2);
				int pk = rs.getInt(3);
				int ob = rs.getInt(4);
				int in = rs.getInt(5);
				int id = rs.getInt(6);
				batch b = new batch(iu, id, pk);
				b.setFields(fq);
				if(ob != -1){
					b.updateUser(ob);
				}
				b.updateRecords(in);
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
	 * get an unlocked batch from a specific project,
	 * use update to update the user ID after getting a batch
	 * @param pID = project ID
	 * @return batch that isn't owned already
	 * @throws DBException if there's a problem, including no batch
	 */
	public batch get(int pID) throws DBException {
		batch result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int indexed = 0;
		try { 
			String query = "select record_quantity from projects ";
			query += "where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, pID);
			rs = stmt.executeQuery();
			if(rs.next()){
				indexed = rs.getInt(1);
			}
			else {
				throw new DBException("no such project etc");
			}
			rs.close();
			stmt.close();
		}
		catch(SQLException e){
			throw new DBException("can't get free batch", e);
		}
		try {
			String query = "select image_url, field_quantity, ";
			query += " indexed, ";
			query += "id from batches where proj_key = ? and ";
			query += "owned_by = ? and indexed != ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, pID);
			stmt.setInt(2, -1);
			stmt.setInt(3, indexed);
			rs = stmt.executeQuery();
			if(rs.next()){
				//rs.next();
				String img = rs.getString(1);
				int fq = rs.getInt(2);
				int in = rs.getInt(3);
				int id = rs.getInt(4);
				result = new batch(img, id, pID);
				result.setFields(fq);
				result.updateRecords(in);
			}
			else {
			//	db.safeClose(rs);
			//	db.safeClose(stmt);
				throw new DBException("no batches available");
			}
			rs.close();
		}
		catch(SQLException e){
			throw new DBException("can't get free batch", e);
		}
		return result;
	}
	
	/**
	 * get information about a specific batch
	 * @param bID = batchID
	 * @return that specific batch
	 * @throws DBException in case of emergency
	 */
	public batch getInfo(int bID) throws DBException {
		batch result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select image_url, field_quantity, ";
			query += "proj_key, owned_by, indexed, from batches ";
			query += "where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, bID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String iu = rs.getString(1);
				int fq = rs.getInt(2);
				int pk = rs.getInt(3);
				int ob = rs.getInt(4);
				int in = rs.getInt(5);
				result = new batch(iu, bID, pk);
				result.setFields(fq);
				if(ob != -1){
					result.updateUser(ob);
				}
				result.updateRecords(in);
			}
		}
		catch(SQLException e){
			DBException ee = new DBException(e.getMessage(), e);
			logger.throwing("batchDAO", "getInfo", ee);
			throw ee;
		}
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
		logger.exiting("batchDAO", "getInfo");

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
			query += "indexed, ";
			query += "field_quantity, proj_key, owned_by) ";
			query += "values (?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, b.getImage());
			stmt.setInt(2, b.getIndexed());
			stmt.setInt(3, b.getFields());
			stmt.setInt(4, b.getProject());
			if(b.isOwned()){
				stmt.setInt(5, b.whichUser());
			}
			else {
				stmt.setInt(5, -1);
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
			query += "indexed = ?, ";
			query += "owned_by = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, b.getImage());
			stmt.setInt(2, b.getFields());
			stmt.setInt(3, b.getProject());
			stmt.setInt(4, b.getIndexed());
			if(b.isOwned()){
				stmt.setInt(5, b.whichUser());
			}
			else {
				stmt.setInt(5, -1);
			}
			stmt.setInt(6, b.getID());
			if(stmt.executeUpdate() != 1){
				//more or less than one update done
				System.out.println("whoops");
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
			String query = "delete from batches where id = ?";
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
	
	

