package shared.communication;

import java.util.*;
import java.io.*;

/** 
 * searchProposals are the means by which the client
 * asks the server to perform a keyword search in the 
 * records of the database
 */
public class searchProposal implements Serializable {
	
	private String fields;
	private String sought;

	/**
	 * Construct a new searchProposal
	*	@param inFields contains fields to search in
	*	@param lookFor	looking for this
	*/
	public searchProposal(String inFields, String lookFor){
		fields = inFields;
		sought = lookFor;
	}

	/**
	 * reveal the search scope
	*	@return the fields we're looking in
	*/
	public String whichFields(){
		return fields;
	}

	/**
	 * reveal the search keyword
	*	@return our search term
	*/
	public String searchTerm(){
		return sought;
	}
}

