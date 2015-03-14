package shared.communication;

import java.io.*;
import java.util.*;

/**
 * searchBlobs contains ONE result of a keyword search in the
 * records which have been indexed
 */

public class searchBlob implements Serializable {
	
	private int batchID;
	private String image;
	private int rNum;
	private int fNum;
	private boolean failure;
	private String prefix = "";

	/**
	 * Constructs a new searchBlob
	*	@param bid the ID of the batch
	*	@param imageURL URL of batch image
	*	@param recordNumber the record number
	*	@param fieldID the field ID
	*/
	public searchBlob(int bid, String imageURL, int recordNumber, int fieldID){
		batchID = bid;
		image = imageURL;
		rNum = recordNumber;
		fNum = fieldID;
		failure = false;
	}

	/**
	*	sets failure flag
	*/
	public void setFailure(){
		failure = true;
	}

	/**
	 * reveal failure state of transaction
	*	@return true if failure, false otherwise
	*/
	public boolean failure(){
		return failure;
	}

	/**
	 * set printing prefix
	 * @param s the string to set
	 */
	public void setPrefix(String s){
		prefix = s;
	}

	/**
	 * fetch a string, which contains the results of the search
	*	@return the string representation
	*/
	@Override
	public String toString(){
		if(failure){
			return "FAILED/n";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(batchID);
		sb.append("\n");
		sb.append(prefix + image);
		sb.append("\n");
		sb.append(rNum);
		sb.append("\n");
		sb.append(fNum);
		sb.append("\n");
		
		
		return sb.toString();
		// whoops, gotta go make changes in the DB...
	}
}

