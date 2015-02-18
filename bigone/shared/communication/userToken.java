package bigone.shared.communication

import java.util.*
import java.io.*

/** 
*	@author RT Hatfield
*/

public class userToken {

	private String name;
	private String pw;
	
	/**
	*	@param username the user's username
	*	@param password the password
	*/

	public userToken(String username, String password){
		name = username;
		pw = username;
	}
	/**
	*	@param username the proffered username
	*	@param password the supposed password
	*	@return true if you've matched, false otherwise
	*/

	public boolean auth(String username, String password){
		return (name == username && pw == password);
	}

}
