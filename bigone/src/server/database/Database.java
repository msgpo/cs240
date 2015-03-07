package server.database;

import server.database.dao.*;
import java.io.*;
import java.sql.*;
import java.util.logging.*;

/**
*	this class is responsible for finding the database file, setting up
*	the driver, and doing all the other database-managey stuff
*/

public class Database {

	private static Logger logger;
	private String dbURL;

	static {
		logger = Logger.getLogger("big ol' indexing server");
	}
	
	/**
	*	sets up the database driver with no particular file attached
	*	@throws DBException if the driver is unloadable
	*/
	public static void initialize() throws DBException {
		try {
			String driver = "org.sqlite.JDBC";
			Class.forName(driver);
			// this is identifying which driver we want
			// and instantiating one of those
		}
		catch(ClassNotFoundException e) {
			DBException ee = new DBException("bad database driver");
			logger.throwing("server.database.Database", "initialize", e);
			throw ee;
			// this makes a custom exception for missing JDBC,
			// logs the problem, and propagates the exception
		}
	}

	private userDAO udao;
	private recordDAO rdao;
	private projectDAO pdao;
	private batchDAO bdao;
	private fieldDAO fdao;

	private Connection connection;

	/**
	*	constructs a new Database with a given db file
	*	@param url location of the database file relative to project root
	*/
	public Database(String url){
		dbURL = "jdbc:sqlite:" + url;
		udao = new userDAO(this);
		rdao = new recordDAO(this);
		pdao = new projectDAO(this);
		bdao = new batchDAO(this);
		fdao = new fieldDAO(this);
		connection = null;
	}
	
	/**
	*	exposes the user DAO
	*	@return this database's userDAO
	*/
	public userDAO getUserDAO(){
		return udao;
	}

	/**
	*	exposes the record DAO
	*	@return this database's recordDAO
	*/
	public recordDAO getRecordDAO(){
		return rdao;
	}

	/**
	*	exposes the project DAO
	*	@return this database's projectDAO
	*/
	public projectDAO getProjectDAO(){
		return pdao;
	}

	/**
	*	exposes the batch DAO
	*	@return this database's batchDAO
	*/
	public batchDAO getBatchDAO(){
		return bdao;
	}

	/**
	*	exposes the field DAO
	*	@return this database's fieldDAO
	*/
	public fieldDAO getFieldDAO(){
		return fdao;
	}

	/**
	*	exposes a connection to the underlying DB
	*	@return a Connection to an SQL database
	*/
	public Connection getConnection(){
		return connection;
	}

	/**
	*	starts a transaction with the underlying DB
	*	@throws DBException if connection impossible or whatever
	*/
	public void startTX() throws DBException {
		try {
			assert(connection == null);
			connection = DriverManager.getConnection(dbURL);
			connection.setAutoCommit(false);
		}
		catch(SQLException e){
			throw new DBException("Connection impossible.  Bad file?", e);
		}
	}

	/**
	*	ends a transaction sequence
	* 	@param commit true if no rollback, false if rollback
	*/
	public void endTX(boolean commit){
		if(connection != null){
			try {
				if(commit){
					connection.commit();
				}
				else {
					connection.rollback();
				}
			}
			catch(SQLException e){
				System.out.println("Cannot end transaction sequence");
				e.printStackTrace();
			}
			finally {
				safeClose(connection);
				connection = null;
			}
		}
	}

	/**
	*	closes a DB connection gracefully
	*	@param c connection to be closed
	*/
	public static void safeClose(Connection c){
		if(c != null){
			try{c.close();}
			catch(SQLException e){
				System.out.println("Cannot gracefully close DB connection");
				e.printStackTrace();
			}
		}
	}
	
	/**
	*	closes a statement gracefully
	*	@param s SQL statement to be closed
	*/
	public static void safeClose(Statement s){
		if(s != null){
			try{s.close();}
			catch(SQLException e){
				System.out.println("Cannot gracefully close SQL statement");
				e.printStackTrace();
			}
		}
	}

	/**
	*	closes a prepared statement gracefully
	*	@param p SQL prepared statement to be closed
	*/
	public static void safeClose(PreparedStatement p){
		if(p != null){
			try{p.close();}
			catch(SQLException e){
				System.out.println("Cannot gracefully close SQL prepared statement");
				e.printStackTrace();
			}
		}
	}

	/**
	*	closes a result set gracefully
	*	@param r SQL result set to be closed
	*/
	public static void safeClose(ResultSet r){
		if(r != null){
			try{r.close();}
			catch(SQLException e){
				System.out.println("Cannot gracefully close SQL result set");
				e.printStackTrace();
			}
		}
	}
}

				
