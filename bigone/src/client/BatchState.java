package client;

import java.util.*;
import java.io.*;

/*
*	The BatchState contains batch image, record values, window
*	position, window size, panel divider locations, zoom level,
*	scroll position, highlight setting, and inversion setting
*/

public class BatchState implements Serializable {
	// don't serialize everything:
	// use @Transient

	private String user;
	public int zoom;
	public int scroll;
	public boolean highlight;
	public boolean invert;
	public int windowX;
	public int windowY;
	public int hsplit;
	public int vsplit;

//	public ArrayList
	
	/*
	* set up a BatchState belonging to a user
	* @param u the name of the user
	*/
	public BatchState(String u){
		user = u;
	}
	
	/*
	* get name of batch owner
	* @return the username
	*/
	public String getUser(){
		return user;
	}
	

	@Override
	public boolean equals(Object o){
		if(this==o){
			return true;
		}
		if(o==null){
			return false;
		}
		if(!(o instanceof BatchState)){
			return false;
		}
		BatchState c = (BatchState) o;

		if(c.getUser().equals(user)){
			return true;
		}
		return false;
	}


}

