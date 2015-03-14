package client;

import java.io.*;
import java.net.*;
import java.util.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import client.ClientException;
import shared.model.*;
import shared.communication.*;



/**
 * this is the object responsible for letting the server know
 * what the client wants to do and letting the client know
 * how it went with the server.
 */
public class communicator {
	
	private String urlBase;
	private XStream xml;

	/**
	*	get current URL 
	* 	@return url as string
	*/
	public String getURL(){
		return urlBase + "/";
	}
	
	/**
	*	starts the communicator class, which can then communicate 
	*	with a server using communication tokens
	*	@param server URL of the server
	* 	@param port the server's listening port
	*/
	public communicator(String server, int port){
		urlBase = "http://" + server + ":" + port;
		xml = new XStream(new DomDriver());
	}

	/**
	*	authenticate a user
	*	@param t a token representing a user's credentials
	*	@return an authToken representing a pass/fail
	*	@throws ClientException if error
	*/
	public authToken validateUser(userToken t)
			throws ClientException{
		Object[] a = {t};
		return (authToken)doPost("/validateUser", a);
	}

	/**
	*	get a list of all available projects
	*	@param t a token representing a user's credentials
	*	@return a projList containing projects or a failure flag
	*	@throws ClientException if error
	*/
	public projList getProjects(userToken t)
			throws ClientException{
		Object[] a = {t};
		return (projList)doPost("/getProjects", a);
	}

	/**
	*	get the sample image from a project
	*	@param t a token representing a user's credentials
	*	@param id ID of the project
	*	@return URL of the image
	*	@throws ClientException if error
	*/
	public String getSampleImage(userToken t, int id)
			throws ClientException{
		Object[] a = {t, id};
		return (String)doPost("/getSampleImage", a);
	}

	/**
	*	gets a batch for the user from the project
	*	@param t a token representing a user's credentials
	*	@param id ID of the project
	*	@return a batchBlob containing the batch you need
	*	@throws ClientException if error
	*/
	public batchBlob downloadBatch(userToken t, int id)
			throws ClientException{
		Object[] a = {t, id};
		batchBlob bb = (batchBlob)doPost("/downloadBatch", a);
		bb.setPrefix(getURL());
		return bb;
	}

	/**
	*	submits a batch
	*	@param t a token representing a user's credentials
	*	@param bp a batchProposal with the new records
	*	@return pass/fail contingent on submission
	*	@throws ClientException if error
	*/
	public boolean submitBatch(userToken t, batchProposal bp)
			throws ClientException{
		Object[] a = {t, bp};
		return (boolean)doPost("/submitBatch", a);
	}

	/**
	*	gets all available fields
	*	@param t a token representing a user's credentials
	*	@return a fieldList containing every field
	*	@throws ClientException if error
	*/
	public fieldList getFields(userToken t)
			throws ClientException{
		Object[] a = {t};
		return (fieldList)doPost("/getFields", a);
	}

	/**
	*	gets all fields from a project
	*	@param t a token representing a user's credentials
	*	@param id ID of the project
	*	@return a fieldList containing every field
	*	@throws ClientException if error
	*/
	public fieldList getFields(userToken t, int id)
			throws ClientException{
		Object[] a = {t, id};
		return (fieldList)doPost("/getFields", a);
	}

	/**
	*	searches for a certain term
	*	@param t a token representing a user's credentials
	*	@param sp a searchProposal representing our search terms
	*	@return a list of searchBlobs representing results
	*	@throws ClientException if error
	*/
	public ArrayList<searchBlob> search(userToken t, searchProposal sp)
			throws ClientException{
		Object[] a = {t, sp};
		return (ArrayList<searchBlob>)doPost("/search", a);
	}
	
	/**
	*	downloads a file
	*	@param loc URL of the wanted file
	*	@return an object with the file in it
	*	@throws ClientException if error
	*/
	public Object downloadFile(String loc) throws ClientException{
		
		Object res;
		
		try{
			URL url = new URL(urlBase + loc);
			HttpURLConnection connection =
					(HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(false);
			connection.connect();
			if(connection.getResponseCode() !=
					HttpURLConnection.HTTP_OK){
				throw new ClientException(String.format(
					"cannot get file: %s (http error) %d",
					loc, connection.getResponseCode()));
			}
			else {
				res = connection.getContent();
			}
		}
		catch(IOException e){
			throw new ClientException(String.format("DL failure: $s",
					e.getMessage()), e);
		}
		
		return res;
			
	}
	
	/**
	*	does a HTTP POST
	*	@param urlPath the url to post to
	*	@param o the object to put in the request
	*	@return an object from the server
	*	@throws ClientException if some problem communicating or w/e
	*/
	private Object doPost(String urlPath, Object[] o) 
			throws ClientException {
				
		Object res;
		
		try{		
			URL url = new URL(urlBase + urlPath);
			HttpURLConnection connection = 
					(HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.connect();
			xml.toXML(o, connection.getOutputStream());
			connection.getOutputStream().close();
			if(connection.getResponseCode() !=
					HttpURLConnection.HTTP_OK){
				throw new ClientException(String.format(
						"post failed: %s (http error) %d",
						urlPath, connection.getResponseCode()));
			}
			else {
				res = xml.fromXML(connection.
						getInputStream());
			}			
		}
		catch(IOException e){
			throw new ClientException(String.format("post failure: $s",
					e.getMessage()), e);
		}
		
		return res;
	}
		
}





