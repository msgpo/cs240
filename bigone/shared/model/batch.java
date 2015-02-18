package bigone.shared.model;

import java.util.*;
import java.io.*;

public class project {
	
	private String sImg;	private int ID;
	private int y;
	private int h;
	private int recs;
	private int fs;
	private int uid;
	private List<String> records;
	/**
	*	@param image image URL
	*	@param y_coord y-coordinate of first field
	*	@param height height of fields
	*	@param records number of records
	*	@param fieldQuant number of fields
	*/
	public project(String image, int y_coord, int height, int records, int fieldQuant){
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
	* 	@return image url
	*/
	public String getImage(){
		return sImg;
	}

	/** 
	*	@return the y-coordinate
	*/
	public int ycoord(){
		return y;
	}

	/** 
	*	@return the number of records
	*/
	public int records(){
		return recs;
	}

	/** 
	*	@return the number of fields
	*/
	public int fields(){
		return fs;
	}

	/** 
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
		return new field();
	}
	
}
