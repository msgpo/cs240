package shared.communication;

import java.util.*;
import java.io.*;
import shared.model.field;

/**
 * fieldLists are containers for fields, which are sent
 * from the server to the client as a response to a request
 * for fields
 */
public class fieldList{
	
	private List<field> field;
	private boolean failure;

	/**
	*	Constructs a new field list
	*/
	public fieldList(){
	}
	
	/**
	 * add a field to the list (populate the container)
	*	@param f field to be added
	*/
	public void addField(field f){
		// do stuff
	}
	
	/**
	*	sets failure flag
	*/
	public void setFailure(){
		failure = true;
	}

	/**
	 * reveal failure state
	*	@return true if failure, false otherwise
	*/
	public boolean failure(){
		return failure;
	}

	/**
	 * spits out a formatted string, containing all the fields
	*	@return string representation of fields
	*/
	@Override
	public String toString(){
		// do stuff
		return "";
	}
}
