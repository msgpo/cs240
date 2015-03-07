package server.database;

import java.io.*;
import java.util.*;

public class DBException extends Exception {
	
	/**
	* constructs a DBException with a particular message
	* @param s custom exception message
	*/
	public DBException(String s){
		super(s);
	// do nothing, i don't think this exception needs 
	// much beyond a name
	}
	
	/**
	* constructs a DBException with a particular message and cause
	* @param s custom message
	* @param e custom cause
	*/
	public DBException(String s, Throwable e){
		super(s,e);
	}

}
