package shared.model;

import java.util.*;
import java.io.*;
/**
 * the record holds the actual data an indexer wants to associate
 * with a field in a batch
 */

public class record implements Serializable{
	
	private int batchID;
	private int fieldID;
	private int ID;
	private String val;
	private int num;
	private int fnumber;

	/**
	 * Construct a new record
	*	@param bID the batch this record will belong to
	* 	@param fID the field this record "completes"
	* 	@param rID the ID of this record (zero before put in DB)
	* 	@param rNum the number of this record in a batch
	*/
	public record(int bID, int fID, int rID, int rNum){
		batchID = bID;
		fieldID = fID;
		ID = rID;
		num = rNum;
	}

	/** 
	 * alt constructor: contains field number, not field ID
	 *	@param bID the batch this record is from
	 *	@param fNum the field this record completes
	 *	@param rNum the record number
	 *	@param value the value
	 */
	public record(int bID, int fNum, int rNum, String value){
		batchID = bID;
		num = rNum;
		val = value;
		fnumber = fNum;
		ID = 0;
	}

	/**
	*	reveal the field number
	*	@return field number
	*/
	public int getFieldNumber(){
		return fnumber;
		// not stored in database, saved for convenience while updating a batch
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

	/**
	*	set the field ID
	*	@param i the field's ID
	*/
	public void setField(int i){
		fieldID = i;
	}
	
	/**
	 * get the number of this record in the batch
	 * @return int which is the "row" in a batch
	 */
	public int getNumber(){
		return num;
	}
	
	/**
	 * equality test
	 * @param o the object to compare
	 * @return true if same, false otherwise
	 */
	@Override
	public boolean equals(Object o){
		if(this == o){
			return true;
		}
		if(!(o instanceof record)){
			return false;
		}
		
		record c = (record) o;
		
		return
		(	(batchID == c.getBatch())	&&
			(fieldID == c.getField())	&&
			(ID ==	c.getID())			&&
			(val.equals(c.getValue()))
		);
	}
}
