package server.database;

import java.sql.*;
import java.util.logging.*;

import server.*;


public class Database {

	private static Logger logger;
	
	static {
		logger = Logger.getLogger("contactmanager");
	}
	
	public static void initialize() throws ServerException {
		
		logger.entering("server.database.Database", "initialize");
		
		// TODO: Load the SQLite database driver
		logger.fine("TODO: Load the SQLite database driver");
		
		logger.exiting("server.database.Database", "initialize");
	}

	private Contacts contacts;
	private Connection connection;
	
	public Database() {
		contacts = new Contacts(this);
		connection = null;
	}
	
	public Contacts getContacts() {
		return contacts;
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void startTransaction() throws ServerException {
		
		logger.entering("server.database.Database", "startTransaction");
		
		// TODO: Open a connection to the database and start a transaction
		logger.fine("TODO: Open a connection to the database and start a transaction");
		
		logger.exiting("server.database.Database", "startTransaction");
	}
	
	public void endTransaction(boolean commit) {
		
		logger.entering("server.database.Database", "endTransaction");
		
		// TODO: Commit or rollback the transaction and close the connection
		logger.fine("TODO: Commit or rollback the transaction and close the connection");
		
		logger.exiting("server.database.Database", "endTransaction");
	}
	
}
