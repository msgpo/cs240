package bigone.client

import java.io.*;
import java.util.*;
import bigone.shared.model.project;
import bigone.shared.communication.*;
import javax.xml.ws.spi.http.HttpExchange;

public class communicator {
	
	/**
	*	starts the communicator class, which can then communicate 
	*	with a server using communication tokens
	*	@param server URL of the server
	*/
	public communicator(String server){
	}

	/**
	*	authenticate a user
	*	@param t a token representing a user's credentials
	*	@return an authToken representing a pass/fail
	*/
	public authToken validateUser(userToken t){
		return new authToken(1);
	}

	/**
	*	get a list of all available projects
	*	@param t a token representing a user's credentials
	*	@return a projList containing projects or a failure flag
	*/
	public projList getProjects(userToken t){
		return new projList();
	}

	/**
	*	get the sample image from a project
	*	@param t a token representing a user's credentials
	*	@param id ID of the project
	*	@return URL of the image
	*/
	public String getSampleImage(userToken t, int id){
		return "";
	}

	/**
	*	gets a batch for the user from the project
	*	@param t a token representing a user's credentials
	*	@param id ID of the project
	*	@return a batchBlob containing the batch you need
	*/
	public batchBlob downloadBatch(userToken t, int id){
		return new batchBlob();
	}

	/**
	*	submits a batch
	*	@param t a token representing a user's credentials
	*	@param bp a batchProposal with the new records
	*	@return pass/fail contingent on submission
	*/
	public boolean submitBatch(userToken t, batchProposal bp){
		return false;
	}

	/**
	*	gets all available fields
	*	@param t a token representing a user's credentials
	*	@return a fieldList containing every field
	*/
	public fieldList getFields(userToken t){
		return new fieldList();
	}

	/**
	*	gets all fields from a project
	*	@param t a token representing a user's credentials
	*	@param id ID of the project
	*	@return a fieldList containing every field
	*/
	public fieldList getFields(userToken t, int id){
		return new fieldList();
	}

	/**
	*	searches for a certain term
	*	@param t a token representing a user's credentials
	*	@param sp a searchProposal representing our search terms
	*	@return a list of searchBlobs representing results
	*/
	public List<searchBlob> search(userToken t, searchProposal sp){
		return new List<searchBlob>();
	}
	
	/**
	*	downloads a file
	*	@param loc URL of the wanted file
	*	@return an HTTP exchange with the file in it
	*/
	public HttpExchange downloadFile(String loc){
		return new HttpExchange();
	}
}





