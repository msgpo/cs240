package bigone.shared.communication

import java.util.*
import java.io.*

/** 
*	@author RT Hatfield
*/

public class authToken {

	private String firstName;
	private String lastName;
	private int recs;
	private boolean failure;
	private boolean invalid;
	
	/**
	*	@param firstName the user's first name
	*	@param lastName the user's last name
	*	@param recs	how many records have been indexed
	*	@return a normal user has been found
	*/
	public authToken(String firstName, String lastName, int recs){
		name = username;
		pw = username;
	}
	
	/**
	*	@param errorLevel 1 for invalid user, 2 for failure
	*	@return a "failed" user
	*/
	public authToken(int errorLevel){
		// do stuff!
	}
	
	/**
	*	@return true if invalid input, false otherwise
	*/
	public boolean invalid(){
		return true;
	}
	
	/**
	*	@return true if some failure occurred, false otherwise
	*/
	public boolean failure(){
		return true;
	}

	/**
	*	@return the "result," as directed in the spec.
	*/
	@Override
	public String toString(){
		//do stuff
		return "";
	}
	


}
