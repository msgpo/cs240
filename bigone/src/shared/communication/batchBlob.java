package shared.communication;

import java.io.*;
import java.util.*;
import shared.model.batch;
import shared.model.project;

/** 
*	a batchBlob contains a batch which is being sent
* 	from a server to a client.
*/

public class batchBlob implements Serializable {
	
	private project p;
	private batch b;
	private int projID;
	private boolean failure = false;
	
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
		b = toAdd;
	}
	
	/**
	 * adds the associated project data
	 * @param toAdd the project
	 */
	public void addProject(project toAdd){
		p = toAdd;
	}
	
	/**
	 * "reconstitutes" the batch
	 * @return batch that we sent
	*/
	public batch getBatch(){
		return b;
	}
	
	/**
	*	sets failure flag
	*/
	public void setFailed(){
		failure = true;
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
	public String toString(){
		if(failure){
			return "FAILED\n";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(b.getID());
		sb.append("\n");
		sb.append(b.getProject());
		sb.append("\n");
		sb.append(b.getImage());
		sb.append("\n");
		sb.append(p.getYCoord());
		sb.append("\n");
		sb.append(p.getHeight());
		sb.append("\n");
		sb.append(" BAD PROBLEM: RECORD NUMBER! ");
		sb.append("\n");
		sb.append(b.getFields());
		sb.append("  NOT FINISHED!!!!   ");
		// FINISH THIS.
		return "";
	}
		
}


