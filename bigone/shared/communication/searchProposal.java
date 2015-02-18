package bigone.shared.communication

import java.util.*;
import java.io.*;

public class searchProposal {
	
	private String fields;
	private String sought;

	/**
	*	@param inFields contains fields to search in
	*	@param lookFor	looking for this
	*/
	public searchProposal(String inFields, String lookFor){
		// do stuff
	}

	/**
	*	@return the fields we're looking in
	*/
	public String whichFields(){
		return fields;
	}

	/**
	*	@return our search term
	*/
	public String searchTerm(){
		return sought;
	}
}

