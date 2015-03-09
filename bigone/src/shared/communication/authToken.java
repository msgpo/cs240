package shared.communication;

import java.util.*;
import java.io.*;

/** 
*	An authToken is used to tell a client whether a
* 	server operation was able to continue or not, based
* 	on provided user credentials. 
*/

public class authToken implements Serializable {

	private String fName = "";
	private String lName = "";
	private int recs = 0;
	private boolean failure = false;
	private boolean invalid = false;
	
	/**
	*	Constructs a normal user
	*	@param firstName the user's first name
	*	@param lastName the user's last name
	*	@param records	how many records have been indexed
	*/
	public authToken(String firstName, String lastName, int records){
		fName = firstName;
		lName = lastName;
		recs = records;
	}
	
	/**
	*	Constructs a "failed" user
	*	@param errorLevel 1 for invalid user, 2 for failure
	*/
	public authToken(int errorLevel){
		if(errorLevel ==1){
			invalid = true;
		}
		else {
			failure = true;
		}
	}
	
	/**
	 * Reveals the possible cause of failure
	*	@return true if invalid input, false otherwise
	*/
	public boolean invalid(){
		return invalid;
	}
	
	/**
	 * Reveals the possible cause of failure
	*	@return true if some failure occurred, false otherwise
	*/
	public boolean failure(){
		return failure;
	}

	/**
	 * Returns a formatted string, which tells about
	 * the authentication result.
	*	@return the "result," as directed in the spec.
	*/
	@Override
	public String toString(){
		if(failure){
			return "FAILED\n";
		}
		if(invalid){
			return "FALSE\n";
		}
		return "TRUE\n" + fName + "\n" + lName + "\n" + recs + "\n";
	}
	


}
