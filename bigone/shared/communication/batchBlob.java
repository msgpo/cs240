package bigone.shared.communication

import java.io.*
import java.util.*
import bigone.shared.model.batch
/** 
*	@author RT Hatfield
*/

public batchBlob{
	
	private batch b;
	private int projID;
	private boolean failure;
	
	/**
	*	@return returns a new batchBlob, empty
	*/
	public batchBlob(){
	}
	
	/**
	*	@param toAdd the batch you want to send
	*/
	public void addBatch(batch toAdd){
		// do stuff
	}
	
	/**
	*	@return returns void, but sets failure flag
	*/
	public void setFailed(){
		// do stuff
	}

	/**
	*	@return returns true if failure, false otherwise
	*/
	public boolean failure(){
		return failure;
	}

	/**
	*	@return returns string representation of batch
	*/
	@Override
	public String toString(){
		//do stuff
		return "";
	}
		
}


