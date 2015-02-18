package bigone.shared.model;

import java.util.*;
import java.io.*;
import bigone.shared.communication.userToken;

public class user {
	
	private String fName;
	private String lName;
	private String un;
	private String pw;
	private int rec;
	private int id;

	/**
	*	@param firstName user's first name
	*	@param lastName user's last name
	*	@param uName user's username
	*	@param password	user's password
	*	@param records	how many records have been indexed
	*	@param userID	user's ID
	*/
	public user(String firstName, String lastName, String uName,
			String password, int records, int userID){
		// stuff
	}

	/**
	*	@param add how many new records have been done
	*/
	public void updateRecords(int add){
		// asdfasdf
	}

	/**
	*	@return a matching userToken object
	*/
	public userToken token(){
		return new bigone.shared.communication.userToken(un, pw);
	}
}
