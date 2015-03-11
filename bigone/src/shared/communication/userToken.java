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

public class userToken implements Serializable{

	private String username;
	private String pw;
	
	/**
	 * Construct a userToken
	*	@param uname the username
	*	@param password the password
	*/

	public userToken(String uname, String password){
		username = uname;
		pw = password;
	}
	
	/**
	 * check if the username and password on the server
	 * match what the client has submitted
	*	@param password the supposed password
	*	@return true if you've matched, false otherwise
	*/
	public boolean auth(String password){
		return (pw.equals(password));
	}
	
	/**
	 * gets the username
	 * @return username
	 */
	public String getUsername(){
		return username;
	}

}
