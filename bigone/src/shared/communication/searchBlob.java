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

	/**
	 * Constructs a new searchBlob
	*	@param bid the ID of the batch
	*	@param imageURL URL of batch image
	*	@param recordNumber the record number
	*	@param fieldNumber the field number
	*/
	public searchBlob(int bid, String imageURL, int recordNumber, int fieldNumber){
		batchID = bid;
		image = imageURL;
		rNum = recordNumber;
		fNum = fieldNumber;
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
	 * fetch a string, which contains the results of the search
	*	@return the string representation
	*/
	@Override
	public String toString(){
		if(failure){
			return "FAILED/n";
		}
		
		StringBuilder sb = new StringBuilder();
		
		return "   BROKEN!  FIGURE OUT HOW FIELDS AND RECORDS WORK  ";
		// whoops
	}
}

