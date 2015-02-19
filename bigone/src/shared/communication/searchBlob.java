package shared.communication;

import java.io.*;
import java.util.*;

/**
 * searchBlobs contains ONE result of a keyword search in the
 * records which have been indexed
 */

public class searchBlob {
	
	private int batchID;
	private String image;
	private int rNum;
	private int fNum;
	private boolean failure;

	/**
	 * Constructs a new searchBlob
	*	@param batch the ID of the batch
	*	@param imageURL URL of batch image
	*	@param recordNumber the record number
	*	@param fieldNumber the field number
	*/
	public searchBlob(int batch, String imageURL, int recordNumber, int fieldNumber){
		// do stuff
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
		//do stuff
		return "";
	}
}

