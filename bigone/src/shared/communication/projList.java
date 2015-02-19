package shared.communication;

import java.util.*;
import java.io.*;
import shared.model.project;

/**
 * projLists are sent from the server to the client as a response
 * to a request to list projects which are available for indexing
 */
public class projList{
	
	private List<project> proj;
	private boolean failure;

	/**
	*	Constructs a new project list
	*/
	public projList(){
	}
	
	/**
	 * adds a project to the container
	*	@param p project to be added
	*/
	public void addProject(project p){
		// do stuff
	}
	
	/**
	*	sets failure flag
	*/
	public void setFailure(){
		failure = true;
	}

	/**
	 * reveals failure state of request/response
	*	@return true if failure, false otherwise
	*/
	public boolean failure(){
		return failure;
	}

	/**
	 * spits out a specially-formatted string, listing all the
	 * projects which can be indexed
	*	@return string representation of projects
	*/
	@Override
	public String toString(){
		// do stuff
		return "";
	}
}
