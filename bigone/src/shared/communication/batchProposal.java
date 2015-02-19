package shared.communication;

import java.io.*;
import java.util.*;

/**
 * a batchProposal is the means whereby clients attempt
 * to submit indexed batches to the server
 */

public class batchProposal {
	
	private int batchID;
	private String submission;
	
	/**
	 * Construct a new batchProposal
	*	@param id the requested batch ID
	*	@param proposal the proposed records
	*/
	public batchProposal(int id, String proposal){
		// do stuff
	}
	
	/**
	 * reveal which batch is being indexed
	*	@return the batch ID
	*/
	public int getID(){
		return batchID;
	}
	
	/**
	 * reveal the indexed records
	*	@return the submission, as a string
	*/
	public String getSubmission(){
		return submission;
	}
}
