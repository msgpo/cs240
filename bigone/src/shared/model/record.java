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
	* 	@param rID the ID of this record (zero before put in DB)
	*/
	public record(int bID, int fID, int rID){
		batchID = bID;
		fieldID = fID;
		ID = rID;
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
	
	/**
	 * set the record's DB-generated ID
	 * @param i new unique ID
	 */
	public void setID(int i){
		ID = i;
	}
	
	/**
	 * get reference to the batch we need
	 * @return batch ID (it's an int, duh)
	 */
	public int getBatch(){
		return batchID;
	}
	
	/**
	 * get reference to the field we need
	 * @return field ID (again, an int.  as ever)
	 */
	public int getField(){
		return fieldID;
	}
}
