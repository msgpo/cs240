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
	private LinkedList<field> fields;
	private LinkedList<batch> batches;
	private int y;
	private int recs;
	private int h;
	
	/**
	 * Construct a new project
	*	@param image sample image URL
	*	@param title project name
	*	@param pID project ID
	* 	@param y_coord y-coordinate of first field
	* 	@param height height of each field
	*/
	public project(String image, String title, int pID,
			int y_coord, int height){
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
	public LinkedList<batch> getBatches(){
		return batches;
	}

	/**
	*	give the "next" field back
	*	@return a field from the project
	*/
	public LinkedList<field> getFields(){
		return fields;
	}
	
	/**
	 * update the number of records indexed in this project
	 * @param add the number of new records to add
	 */
	public void updateRecords(int add){
		recs = recs + add;
	}
	
	/**
	 * reveal how many records in this project have been indexed
	 * @return the number of records already indexed
	 */
	public int getRecordQuantity(){
		return recs;
	}	
}
