package bigone.shared.communication

import java.util.*;
import java.io.*;
import bigone.shared.model.field;

public class fieldList{
	
	private List<field> field;
	private boolean failure;

	/**
	*	@return a new field list
	*/
	public fieldList(){
	}
	
	/**
	*	@param f field to be added
	*/
	public void addField(field f){
		// do stuff
	}
	
	/**
	*	@return sets failure flag
	*/
	public void setFailure(){
		failure = true;
	}

	/**
	*	@return true if failure, false otherwise
	*/
	public boolean failure(){
		return failure;
	}

	/**
	*	@return string representation of fields
	*/
	@Override
	public String toString(){
		// do stuff
		return "";
	}
}
