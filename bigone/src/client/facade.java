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

public class facade{

	
	static communicator comm;
	static String address;
	static int port;
	static private userToken uToken;
	static BatchState bs;
	static ArrayList<FacadeListener> flisteners;

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
		flisteners = new ArrayList<FacadeListener>();

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
				System.out.println("Restoring: " + "local/" + un);
				ObjectInputStream is = 
					new ObjectInputStream(
					new FileInputStream(storedState));
				System.out.println("Restoring object...");
				bs = (BatchState) is.readObject();
				System.out.println("Getting image URL");
				URL url = new URL(comm.getURL() + bs.getURL());
				System.out.println("getting image from: " + bs.getURL());
				bs.setImg(ImageIO.read(url));

				fireNewBatch(bs);

			}
		}
		catch(Exception e){
			System.out.println("Problem with restore: " + e.getMessage());
			System.out.println(e);
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
			System.out.println("problem getting batch");
			throw new ClientException("Failed getting batch");
		}
		// elsewise we know we can make a new state
		// build batch state
		try{
			fieldList fl = comm.getFields(uToken, id);
			batch b = bb.getBatch();
			projList pl = comm.getProjects(uToken);
			project p = null;
			for(project t : pl.getProjects()){
				if(t.getID() == id){
					p = t;
				}
			}			
			bs = new BatchState(uToken.getUsername());
			// set up all the batch stats: coords etc.
			bs.batchID = b.getID();
			bs.fields = fl.getFields();
			bs.firsty = p.getYCoord();
			bs.fieldHeight = p.getHeight();
			bs.rows = p.getRecordQuantity();
			bs.firstx = bs.fields.getFirst().getXCoord();

			for(int rNum = 1; rNum <= bs.rows; rNum++){
				for(int fNum = 1; fNum <= bs.fields.size(); fNum++){
					record rec = new record(bs.batchID,
								fNum,
								rNum,
								"");
					bs.records.add(rec);
				}
			}
			
			URL url = new URL(comm.getURL() + b.getImage());
			BufferedImage img = ImageIO.read(url);
			bs.setImg(img);
			bs.setURL(b.getImage());
			System.out.println("set image to: " + bs.getURL());
			fireNewBatch(bs);
		}
		catch(Exception e){
			e.printStackTrace();
			throw new ClientException("problem making batch: ", e);
		}
	}

	/*
	* save a batch to disk
	*/
	public static void save(){
		try{
			File destLocation = new File("local/" + bs.getUser());
			ObjectOutputStream os = 
				new ObjectOutputStream(		
				new FileOutputStream(
				destLocation));
			os.writeObject(bs);
		}
		catch(Exception e){
			System.out.println("Problem saving batch" + e.getMessage());
		}
	}

	public static void submit()
			throws ClientException{
		try{
			StringBuilder submission = new StringBuilder();
			for(int row = 1; row <= bs.rows; row++){
				for(int col = 1; col <= bs.fields.size(); col++){
					for(record r : bs.records){
						if(r.getNumber() == row && r.getFieldNumber() == col){
							submission.append(r.getValue());
							if(col != bs.fields.size()){
								submission.append(",");
							}
						}
					}
				}

				submission.append(";");
			}
			String sub = submission.substring(0, submission.length()-1);
			//System.out.println(sub);
			batchProposal bp = new batchProposal(bs.batchID, sub);
			if(!comm.submitBatch(uToken, bp)){
				throw new ClientException("can't submit");
			}
			bs = new BatchState(uToken.getUsername());
			File storedState = new File("local/" + uToken.getUsername());
			if(storedState.exists()){
				storedState.delete();
			}
			fireNewBatch(bs);

		}
		catch(Exception e){
			throw new ClientException("can't submit", e);
		}
	}
			
		
	public static void addFacadeListener(FacadeListener fl){
		flisteners.add(fl);
	}
	protected static void fireImage(BufferedImage i){
		for(FacadeListener t : flisteners){
			t.imageChanged(i);
		}
	}
	protected static void fireNewBatch(BatchState bState){
		for(FacadeListener t : flisteners){
			t.newBatch(bState);
		}
	}
}


