package server.database;

import java.util.*;
import java.util.logging.*;

import server.*;
import shared.model.*;


public class Contacts {

	private static Logger logger;
	
	static {
		logger = Logger.getLogger("contactmanager");
	}

	private Database db;
	
	Contacts(Database db) {
		this.db = db;
	}
	
	public List<Contact> getAll() throws ServerException {	
		
		logger.entering("server.database.Contacts", "getAll");
	
		// TODO: Use db's connection to query all contacts from the database and return them	
		logger.fine("TODO: Use db's connection to query all contacts from the database and return them");
		
		logger.exiting("server.database.Contacts", "getAll");
		
		return null;
	}
	
	public void add(Contact contact) throws ServerException {

		logger.entering("server.database.Contacts", "add");
	
		// TODO: Use db's connection to add a new contact to the database
		logger.fine("TODO: Use db's connection to add a new contact to the database");
		
		logger.exiting("server.database.Contacts", "add");
	}
	
	public void update(Contact contact) throws ServerException {

		logger.entering("server.database.Contacts", "update");
	
		// TODO: Use db's connection to update contact in the database
		logger.fine("TODO: Use db's connection to update contact in the database");
		
		logger.exiting("server.database.Contacts", "update");
	}
	
	public void delete(Contact contact) throws ServerException {

		logger.entering("server.database.Contacts", "update");
	
		// TODO: Use db's connection to delete contact from the database
		logger.fine("TODO: Use db's connection to delete contact from the database");
		
		logger.exiting("server.database.Contacts", "update");
	}

}
