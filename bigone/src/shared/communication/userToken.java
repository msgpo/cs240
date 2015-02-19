package shared.communication;

import java.util.*;
import java.io.*;

/** 
*	a userToken encapsulates the user data relevant to 
* 	authentication.  it keeps the password and username
* 	secret during transmission from client to server,
* 	and the server can only check if a username/password
* 	combination is correct.  the password submitted is hidden
* 	from the server.
*/

public class userToken {

	private String name;
	private String pw;
	
	/**
	 * Construct a userToken
	*	@param username the user's username
	*	@param password the password
	*/

	public userToken(String username, String password){
		name = username;
		pw = username;
	}
	
	/**
	 * check if the username and password on the server
	 * match what the client has submitted
	*	@param username the proffered username
	*	@param password the supposed password
	*	@return true if you've matched, false otherwise
	*/
	public boolean auth(String username, String password){
		return (name == username && pw == password);
	}
	
	/**
	 * get the username the server needs to authenticate.
	 * if this is not in the database, server fails immediately
	 * and sends back an authToken with the "false" flag set.
	 * otherwise, password lookup and check proceeds.
	 * @return the username
	 */
	public String getUser(){
		return name;
	}

}
