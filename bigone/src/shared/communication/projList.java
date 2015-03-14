package shared.communication;

import java.util.*;
import java.io.*;
import shared.model.project;

/**
 * projLists are sent from the server to the client as a response
 * to a request to list projects which are available for indexing
 */
public class projList implements Serializable{
	
	private ArrayList<project> proj = new ArrayList<project>();
	private boolean failure = false;

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
		proj.add(p);
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
		if(failure){
			return "FAILED\n";
		}
		StringBuilder sb = new StringBuilder();
		for(project t : proj){
			sb.append(t.getID());
			sb.append("\n");
			sb.append(t.getTitle());
			sb.append("\n");
		}
		return sb.toString();
	}
}
