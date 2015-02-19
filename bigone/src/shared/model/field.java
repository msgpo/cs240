package shared.model;

import java.util.*;
import java.io.*;
/**
 * a field, which is had by batches and projects,
 * lets you know what kind of information needs to 
 * be indexed by a user
 */

public class field {
	
	private String name;
	private int ID;
	private int n;
	private String h;
	private int x;
	private int w;
	private String vals;

	/**
	 * Construct a new field
	*	@param title name of the field
	*	@param fID field ID
	*	@param fNum field number
	*	@param width field width 
	*	@param x_coord x-coordinate
	*	@param known_vals URL of known values file
	*	@param help URL of help file
	*/
	public field(String title, int fID, int fNum, int width, 
			int x_coord, String known_vals,
			String help){
		// stuff
	}

	/**
	 * reveal this field's title
	*	@return title
	*/
	public String getTitle(){
		return name;
	}
	
	/**
	 * reveal the field's globally-unique ID
	*	@return field ID
	*/
	public int getID(){
		return ID;
	}
	
	/**
	 * reveal the field's number within a project
	*	@return number of field
	*/
	public int getNumber(){
		return n;
	}

	/**
	 * reveal the help file's URL.  this is where indexing 
	 * instructions live.
	*	@return the URL of the help file
	*/
	public String getHelp(){
		return h;
	}

	/**
	 * reveal the URL of the known values file.  this is used
	 * to provide suggestions to the indexer.
	*	@return the URL of the known values file
	*/
	public String getVals(){
		return vals;
	}

	/**
	 * reveal the x-coordinate of the field in a batch image
	*	@return the x-coordinate of the field
	*/
	public int getX(){
		return x;
	}

	/**
	 * reveal the width of the field on the batch image
	*	@return field width
	*/
	public int getWidth(){
		return w;
	}
}
