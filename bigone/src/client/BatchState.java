package client;

import shared.communication.*;
import shared.model.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;

/*
*	The BatchState contains batch image, record values, window
*	position, window size, panel divider locations, zoom level,
*	scroll position, highlight setting, and inversion setting
*/

public class BatchState implements Serializable {
	// don't serialize everything:
	// use @Transient

	private String user;
	// image characteristics:
	public double zoom;
	public int offsetX;
	public int offsetY;
	public boolean highlight;
	public boolean invert;
	// window characteristics:
	public int windowX;
	public int windowY;
	public int windowW;
	public int windowH;
	public int hsplit;
	public int vsplit;
	// batch characteristics
	public int batchID;
	public LinkedList<field> fields;
	public int rows; // number of records
	public int firstx;
	public int firsty;
	public int fieldHeight;
	
	// because B.I. is non-serializable (?!)
	public String imgURL;
	public transient BufferedImage img;
	
	// where the magic happens
	public ArrayList<record> records;
	
	/*
	* set up a BatchState belonging to a user
	* @param u the name of the user
	*/
	public BatchState(String u){
		user = u;
		zoom = 1.0;
		offsetX = 0;
		offsetY = 0;
		invert = false;
		highlight = false;
		img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		batchID = 0;
		fields = new LinkedList<field>();
		rows = 0;
		firstx = 0;
		firsty = 0;
		fieldHeight = 0;
		windowX = 0;
		windowY = 0;
		windowW = 0;
		windowH = 0;
		hsplit = -1;
		vsplit = -1;
		records = new ArrayList<record>();
	}
	
	/*
	* get name of batch owner
	* @return the username
	*/
	public String getUser(){
		return user;
	}

	/*
	* set image url
	* @param URL of the image
	*/
	public void setURL(String u){
		imgURL = u;
	}

	/*
	* get image url
	* @return URL of the image
	*/
	public String getURL(){
		return imgURL;
	}

	/*
	* set the image
	* @param image the new image
	*/
	public void setImg(BufferedImage image){
		img = image;
	}

	/*
	* get image from the batch
	* @return the batch's image
	*/
	public BufferedImage getImg(){
		return img;
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

