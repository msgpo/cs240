package bigone.shared.communication

import java.util.*;
import java.io.*;
import bigone.shared.model.project;

public class projList{
	
	private List<project> proj;
	private boolean failure;

	/**
	*	@return a new project list
	*/
	public projList(){
	}
	
	/**
	*	@param p project to be added
	*/
	public void addProject(project p){
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
	*	@return string representation of projects
	*/
	@Override
	public String toString(){
		// do stuff
		return "";
	}
}
