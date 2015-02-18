package bigone.shared.model;

import java.util.*;
import java.io.*;

public class field {
	
	private String name;
	private int ID;
	private int n;
	private String h;
	private int x;
	private int w;
	private String vals;

	/**
	*	@param title name of the field
	*	@param fID field ID
	*	@param fNum field number
	*	@param width field width 
	*	@param x_coord x-coordinate
	*	@param known_vals URL of known values file
	*	@param help URL of help file
	*/
	public field(String title, int fID, int fNum, int width, 
			int x_coord, String known_vals,
			String help){
		// stuff
	}

	/**
	*	@return title
	*/
	public String getTitle(){
		return name;
	}
	
	/**
	*	@return field ID
	*/
	public int getID(){
		return ID;
	}
	
	/**
	*	@return number of field
	*/
	public int getNumber(){
		return n;
	}

	/**
	*	@return the URL of the help file
	*/
	public String getHelp(){
		return h;
	}

	/**
	*	@return the URL of the known values file
	*/
	public String getVals(){
		return vals;
	}

	/**
	*	@return the x-coordinate of the field
	*/
	public int getX(){
		return x;
	}

	/**
	*	@return field width
	*/
	public int getWidth(){
		return w;
	}
}
