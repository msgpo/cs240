package bigone.server;

import java.io.*;
import java.util.*;
import java.sql.*;
import bigone.shared.model.*;

public class dao {
	
	/**
	*	create a new dao (database access object)
	*	@param dbURL URL of the database file
	*/
	public dao(String dbURL){
	}

	/**
	*	adds a user to the DB
	*	@param u user to be added
	*	@return true if works, false if not
	*/
	public boolean void addUser(user u){
		return false;
	}

	/**
	*	adds a batch to the DB
	*	@param b batch to be added
	*	@return true if works, false if not
	*/
	public boolean void addBatch(batch b){
		return false;
	}

	/**
	*	adds a project to the DB
	*	@param p project to be added
	*	@return true if works, false if not
	*/
	public boolean void addProject(project p){
		return false;
	}

	/**
	*	adds a field to the DB
	*	@param f field to be added
	*	@return true if works, false if not
	*/
	public boolean void addField(field f){
		return false;
	}

	/**
	*	adds a record to the DB
	*	@param r record to be added
	*	@return true if works, false if not
	*/
	public boolean void addRecord(String r){
		return false;
	}
}
