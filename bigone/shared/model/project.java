package bigone.shared.model;

import java.util.*;
import java.io.*;
import bigone.shared.model.batch;
import bigone.shared.model.field;

public class project {
	
	private String sImg;
	private String name;
	private int ID;
	private List<field> fields;
	private List<batch> batches;
	/**
	*	@param image sample image URL
	*	@param title project name
	*	@param pID project ID
	*/
	public project(String image, String title, int pID){
		// stuff
	}

	/**
	*	add fields
	*	@param f field to add
	*/
	public void addField(field f){
		// stasdfa
	}

	/**
	*	add batches
	*	@param b batch to add
	*/
	public void addBatch(batch b){
		//asdfasdf
	}
	
	/**
	* 	@return image url
	*/
	public String getImage(){
		return sImg;
	}

	/**
	*	@return title
	*/
	public String getTitle(){
		return name;
	}
	
	/**
	*	@return project ID
	*/
	public int getID(){
		return ID;
	}

	/**
	*	give the "next" batch back
	*	@return a batch from the project
	*/
	public batch nextBatch(){
		return new batch();
	}

	/**
	*	give the "next" field back
	*	@return a field from the project
	*/
	public field nextField(){
		return new field();
	}
	
}
