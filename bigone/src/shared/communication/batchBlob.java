package shared.communication;

import java.io.*;
import java.util.*;
import shared.model.batch;
/** 
*	a batchBlob contains a batch which is being sent
* 	from a server to a client.
*/

public class batchBlob{
	
	private batch b;
	private int projID;
	private boolean failure;
	
	/**
	*	Constructs a new batchBlob, empty
	*/
	public batchBlob(){
	}
	
	/**
	 * Adds the batch to the blob
	*	@param toAdd the batch you want to send
	*/
	public void addBatch(batch toAdd){
		// do stuff
	}
	
	/**
	*	sets failure flag
	*/
	public void setFailed(){
		// do stuff
	}

	/**
	 * reveals if this batchBlob is useful or a failure
	*	@return returns true if failure, false otherwise
	*/
	public boolean failure(){
		return failure;
	}

	/**
	 * gives a formatted "result" string
	*	@return returns string representation of batch
	*/
	@Override
	public String toString(){
		//do stuff
		return "";
	}
		
}


