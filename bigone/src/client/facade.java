package client;

import shared.communication.*;
import shared.model.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;

public class facade{
	
	static communicator comm;
	static String address;
	static int port;
	static private userToken uToken;
	static BatchState bs;

	/*
	* initializes your facade
	* @param srv the server address
	* @param p the port on the server
	*/
	public static void initialize(String srv, int p){
		address = srv;
		port = p;
		System.out.println("Server address: " + address);
		System.out.println("Server port:    " + port);
		comm = new communicator(srv, p);

	}
	
	/*
	* handles a login
	* @param un the user name
	* @param pw the password
	* @throws ClientException if any problem including bad auth
	*/
	public static authToken login(String un, String pw)
			throws ClientException {
		// attempt login
		uToken = new userToken(un, pw);
		authToken aToken = comm.validateUser(uToken);
		if(aToken.invalid() || aToken.failure()){
			throw new ClientException("bad authentication");
		}
		
		// now set up the batch state etc
		// if file with user's un is in folder ./local/, 
		// it's the existing batch state and load it.
		// otherwise we can continue naively assuming there
		// is no state to restore
		try{
			File storedState = new File("local/" + un);
			if(storedState.exists()){
				ObjectInputStream is = 
					new ObjectInputStream(
					new BufferedInputStream(
					new FileInputStream(storedState)));
				bs = (BatchState) is.readObject();
			}
		}
		catch(Exception e){
			// don't worry about it yo
		}

		return aToken;
	}

	/*
	* prep download of batch
	*/
	public static ArrayList<project> getBatchInfo(){
		ArrayList<project> ret = new ArrayList<project>();
		try{
			projList projects = comm.getProjects(uToken);
			ret = projects.getProjects();
		}
		catch(ClientException e){
	/**		JOptionPane.showMessageDialog(this, 
					"Error getting projects",
					"Problem!",
					JOptionPane.ERROR_MESSAGE);
	**/
		}
		return ret;
	}

	/*
	* get a sample image from the server
	* @param id project ID
	* @return the image data
	* @throws ClientException if an error happens
	*/
	public static Image getSample(int id)
			throws ClientException{
		Image ret = null;		
		projList projects = comm.getProjects(uToken);
		for(project t : projects.getProjects()){
			if(t.getID() == id){
				try{
					System.out.println("image located at: " + t.getImage());
					URL url = new URL(comm.getURL() + t.getImage());
					ret = ImageIO.read(url);
				}
				catch(Exception e){
					throw new ClientException(
						"Image problem: " + e.getMessage());
				}

			}
		}
		return ret;
	}

	/*
	* get a batch from the server for the current user
	* @param id the project ID to get a batch from
	* @throws ClientException for problems, including user has batch
	*/
	public static void getBatch(int id)
			throws ClientException{
		// do stuff to set up batch
		batchBlob bb = comm.downloadBatch(uToken, id);
		if(bb.failure()){
			throw new ClientException("Failed getting batch");
		}
		// elsewise we know we can make a new state
		// build batch state
		fieldList fl = comm.getFields(uToken, id);
		
		bs = new BatchState(uToken.getUsername());
		
	}

	/*
	* save a batch to disk
	*/
	public static void save(){
		try{
			String destLocation = "local/" + bs.getUser();
			ObjectOutputStream os = 
				new ObjectOutputStream(
				new BufferedOutputStream(
				new FileOutputStream(
				destLocation)));
			os.writeObject(bs);
		}
		catch(Exception e){
			// don't sweat it
		}
	}
		

		
}
