package shared.model;

import java.util.*;
import java.io.*;
import shared.model.batch;
import shared.model.field;

/**
 * a project, which has fields and batches, organizes
 * different types of indexable records.
 */
public class project {
	
	private String sImg;
	private String name;
	private int ID;
	private List<field> fields;
	private List<batch> batches;
	/**
	 * Construct a new project
	*	@param image sample image URL
	*	@param title project name
	*	@param pID project ID
	*/
	public project(String image, String title, int pID){
		// stuff
	}

	/**
	*	add fields
	*	@param f field to add
	*/
	public void addField(field f){
		// stasdfa
	}

	/**
	*	add batches
	*	@param b batch to add
	*/
	public void addBatch(batch b){
		//asdfasdf
	}
	
	/**
	 * reveal an image, which is representative of the batches
	 * in this project.
	* 	@return image url
	*/
	public String getImage(){
		return sImg;
	}

	/**
	 * reveal this project's name (or title)
	*	@return title
	*/
	public String getTitle(){
		return name;
	}
	
	/**
	 * reveal the project's globally-unique ID
	*	@return project ID
	*/
	public int getID(){
		return ID;
	}

	/**
	*	give the "next" batch back
	*	@return a batch from the project
	*/
	public batch nextBatch(){
		return new batch("", 0, 0, 0, 0);
	}

	/**
	*	give the "next" field back
	*	@return a field from the project
	*/
	public field nextField(){
		return new field("", 0, 0, 0, 0, "", "");
		
	}
	
}
