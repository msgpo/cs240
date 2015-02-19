package shared.model;

import java.util.*;
import java.io.*;
/**
 * A batch of records to be indexed by a user
 */
public class batch {
	
	private String sImg;	private int ID;
	private int y;
	private int h;
	private int recs;
	private int fs;
	private int uid;
	private List<String> records;
	/**
	 * Constructs a new batch.
	*	@param image image URL
	*	@param y_coord y-coordinate of first field
	*	@param height height of fields
	*	@param records number of records
	*	@param fieldQuant number of fields
	*/
	public batch(String image, int y_coord, int height, int records, int fieldQuant){
		// stuff
	}
	
	/**
	*	set the user id associated with batch
	*	@param newID new user id
	*/
	public void updateUser(int newID){
		//...
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
	public void addRecord(String r){
		// stasdfa
	}

	/**
	 * get the image for the user to read
	* 	@return image url
	*/
	public String getImage(){
		return sImg;
	}

	/** 
	 * get the y-coordinate of the first record
	*	@return the y-coordinate
	*/
	public int ycoord(){
		return y;
	}

	/** 
	 * reveal how many records have been indexed
	*	@return the number of records
	*/
	public int records(){
		return recs;
	}

	/** 
	 * reveal how many fields this type of batch has
	*	@return the number of fields
	*/
	public int fields(){
		return fs;
	}

	/** 
	 * reveal the height of each field
	*	@return the height
	*/
	public int height(){
		return h;
	}

	/**
	*	give the "next" field back
	*	@return a field from the batch
	*/
	public field nextField(){
		return new field("", 0, 0, 0, 0, "", "");
	}
	
}
