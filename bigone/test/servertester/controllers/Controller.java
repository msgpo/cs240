package servertester.controllers;

import java.util.*;

import servertester.views.*;
import client.communicator;
import shared.communication.*;
import shared.model.*;

public class Controller implements IController {

	private IView _view;
	private communicator comm;
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("39640");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		
		comm = new communicator(getView().getHost(), 
				Integer.parseInt(getView().getPort()));

		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser() {
		String[] input = getView().getParameterValues();
		userToken t = new userToken(input[0], input[1]);
		getView().setRequest(t.toString());
		try{
			authToken a = comm.validateUser(t);
			getView().setResponse(a.toString());
		}
		catch(Exception e){
			getView().setResponse("FAILED\n");
		}
	}
	
	private void getProjects() {
		String[] input = getView().getParameterValues();
		userToken t = new userToken(input[0], input[1]);
		getView().setRequest(t.toString());
		try{
			projList pl = comm.getProjects(t);
			getView().setResponse(pl.toString());
		}
		catch(Exception  e){
			getView().setResponse("FAILED\n");
		}
	}
	
	private void getSampleImage() {
		String[] input = getView().getParameterValues();
		userToken t = new userToken(input[0], input[1]);
		int id = Integer.parseInt(input[2]);
		getView().setRequest(t.toString() + id + "\n");
		try{
			String s = comm.getSampleImage(t,id);
			getView().setResponse(s + "\n");
		}
		catch(Exception  e){
			getView().setResponse("FAILED\n");
		}
	}
	
	private void downloadBatch() {
		String[] input = getView().getParameterValues();
		userToken t = new userToken(input[0], input[1]);
		int id = Integer.parseInt(input[2]);
		getView().setRequest(t.toString() + id + "\n");
		try{
			batchBlob bb = comm.downloadBatch(t,id);
			getView().setResponse(bb.toString());
		}
		catch(Exception  e){
			getView().setResponse("FAILED\n");
		}
	}
	
	private void getFields() {
		String[] input = getView().getParameterValues();
		userToken t = new userToken(input[0], input[1]);
		fieldList fl;
		try{
			if(!(input[2].equals(""))){
				int id = Integer.parseInt(input[2]);
				getView().setRequest(t.toString() + id + "\n");
				fl = comm.getFields(t, id);
				getView().setResponse(fl.toString());
			}
			else{			
				getView().setRequest(t.toString());
				fl = comm.getFields(t);
				getView().setResponse(fl.toString());
			}
		}
		catch(Exception e){
			getView().setResponse("FAILED\n");
		}
	}
	
	private void submitBatch() {
		String[] input = getView().getParameterValues();
		userToken t = new userToken(input[0], input[1]);
		int id = Integer.parseInt(input[2]);
		String p = input[3];
		batchProposal bp = new batchProposal(id, p);
		getView().setRequest(t.toString() + id + "\n" + p + "\n");
		try{
			boolean res = comm.submitBatch(t, bp);
			if(res){
				getView().setResponse("TRUE\n");
			}
			else{
				getView().setResponse("FAILED\n");
			}
		}
		catch(Exception  e){
			getView().setResponse("FAILED\n");
		}
	}
	
	private void search() {
		String[] input = getView().getParameterValues();
		userToken t = new userToken(input[0], input[1]);
		searchProposal sp = new searchProposal(input[2], input[3]);
		getView().setRequest(t.toString() + input[2] + "\n" + input[3] + "\n");
		try{
			ArrayList<searchBlob> lsb = comm.search(t, sp);
			StringBuilder result = new StringBuilder();
			for(searchBlob sb : lsb){
				result.append(sb.toString());
			}
			getView().setResponse(result.toString());
		}
		catch(Exception  e){
			getView().setResponse("FAILED\n");
		}

	}

}

