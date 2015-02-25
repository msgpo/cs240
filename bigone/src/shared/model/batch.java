package shared.model;

import java.util.*;
import java.io.*;
/**
 * A batch of records to be indexed by a user
 */
public class batch {
	
	private String sImg;
	private int ID;
	private int uid;
	private boolean owned = false;
	private LinkedList<record> records;
	
	/**
	 * Constructs a new batch.
	*	@param image image URL
	* 	@param id the batch's unique ID
	*/
	public batch(String image, int id){
		sImg = image;
		ID = id;
	}
	
	/**
	*	set the user id associated with batch
	*	@param newID new user id
	*/
	public void updateUser(int newID){
		uid = newID;
		owned = true;
	}
	
	/**
	 * reveal if batch is owned
	 * @return true if owned, false if free
	 */
	public boolean isOwned(){
		return owned;
	}

	/**
	 * reveal who owns the batch
	*	@return user id associated with batch
	*/
	public int whichUser(){
		return uid;
	}

	/**
	*	add records
	*	@param r record to add
	*/
	public void addRecord(record r){
		// stasdfa
	}
	
	/**
	 * get list of records that have been indexed
	 * @return a LinkedList of record objects
	 */
	public LinkedList<record> getRecords(){
		return records;
	}

	/**
	 * get the image for the user to read
	* 	@return image url
	*/
	public String getImage(){
		return sImg;
	}
}
