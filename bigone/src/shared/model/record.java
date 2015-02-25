package shared.model;

import java.util.*;
import java.io.*;
/**
 * the record holds the actual data an indexer wants to associate
 * with a field in a batch
 */

public class record {
	
	private int batchID;
	private int fieldID;
	private int ID;
	private String val;

	/**
	 * Construct a new record
	*	@param bID the batch this record will belong to
	* 	@param fID the field this record "completes"
	* 	@param rID the ID of this record
	*/
	public record(int bID, int fID, int rID){
		// stuff
	}

	/**
	 * reveal this record's indexed value
	*	@return string value
	*/
	public String getValue(){
		return val;
	}
	
	/**
	 * add / change data for this record
	 * @param s string of data to add
	 */
	public void setValue(String s){
		val = s;
	}
	
	/**
	 * reveal the record's globally-unique ID
	*	@return record ID
	*/
	public int getID(){
		return ID;
	}
}
