package shared.model;

import java.util.*;
import java.io.*;
import shared.communication.*;

/**
 * A user, which has some personal data and authentication
 * data, and the number of records indexed by that user.
  */

public class user {
	
	private String fName;
	private String lName;
	private String un;
	private String pw;
	private int rec;
	private int id;
	private int batchID;
	private boolean hasBatch = false;

	/**
	 * Construct a new user
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
	 * update the count of records indexed by this user
	*	@param add how many new records have been done
	*/
	public void updateRecords(int add){
		// asdfasdf
	}
	
	/**
	 * reveal how many records this user has indexed
	 * @return the number of records already indexed
	 */
	public int getRecords(){
		return rec;
	}
	
	/**
	 * assign a batch to this user
	 * @param assign ID of batch to assign
	 */
	public void assignBatch(int assign){
		batchID = assign;
		hasBatch = true;
	}
	
	/**
	 * reveal whether a batch is assigned
	 * @return true if batch assigned, false otherwise
	 */
	public boolean hasBatch(){
		return hasBatch;
	}
	
	/**
	 * reveal which batch has been assigned
	 * @return int ID of assigned batch
	 */
	public int whichBatch(){
		return batchID;
	}	

	/**
	 * get a userToken, representing this user,
	 * which can be used for authentication during
	 * server operations
	*	@return a matching userToken object
	*/
	public userToken token(){
		return new shared.communication.userToken(un, pw);
	}
}
