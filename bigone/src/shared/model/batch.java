package shared.model;

import java.util.*;
import java.io.*;
/**
 * A batch of records to be indexed by a user
 */
public class batch implements Serializable {
	
	private String sImg;
	private int ID;
	private int uid;
	private boolean owned = false;
	private LinkedList<record> records;
	private int numFields;
	private int pID;
	
	/**
	 * Constructs a new batch.
	*	@param image image URL
	* 	@param id the batch's unique ID: 0 if not in DB yet.
	* 	@param project the project ID
	*/
	public batch(String image, int id, int project){
		sImg = image;
		ID = id;
		pID = project;
	}
	
	/**
	 * reveal the project which owns this batch
	 * @return int ID of the project
	 */
	public int getProject(){
		return pID;
	}
	
	/**
	 * set the ID after the DB generates it
	 * @param i the new ID
	 */
	public void setID(int i){
		ID = i;
	}
	
	/**
	 * reveal the sooper-seekrit unique ID
	 * @return int batch ID
	 */
	public int getID(){
		return ID;
	}
	
	/**
	 * set number of fields
	 * @param f number of fields
	 */
	public void setFields(int f){
		numFields = f;
	}
	
	/**
	 * reveal number of fields
	 * @return int of fields
	 */
	public int getFields(){
		return numFields;
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
	 * unassign batch from user
	 */
	public void loseUser(){
		owned = false;
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
		records.add(r);
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
	
	/**
	 * tests for equality
	 * @param o object to compare
	 * @return true if the same, false otherwise
	 */
	@Override
	public boolean equals(Object o){
		if(this == o){
			return true;
		}
		if(!(o instanceof batch)){
			return false;
		}
		
		batch c = (batch) o;
		
		return
		(	sImg.equals(c.getImage()) 	&&
			(ID == c.getID())			&&
			(owned == c.isOwned())		&&
			(numFields == c.getFields())&&
			(pID == c.getProject())
		);
	}
}
