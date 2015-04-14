package shared.communication;

import java.util.*;
import java.io.*;
import shared.model.field;

/**
 * fieldLists are containers for fields, which are sent
 * from the server to the client as a response to a request
 * for fields
 */
public class fieldList implements Serializable {
	
	private LinkedList<field> fields;
	private boolean failure;

	/**
	*	Constructs a new field list
	*/
	public fieldList(){
		failure = false;
		fields = new LinkedList<field>();
	}
	
	/**
	 * add a field to the list (populate the container)
	*	@param f field to be added
	*/
	public void addField(field f){
		fields.add(f);
	}

	/**
	* get list of fields
	* @return list of fields
	*/
	public LinkedList<field> getFields(){
		return fields;
	}
	
	/**
	*	sets failure flag
	*/
	public void setFailure(){
		failure = true;
	}

	/**
	 * reveal failure state
	*	@return true if failure, false otherwise
	*/
	public boolean failure(){
		return failure;
	}

	/**
	 * spits out a formatted string, containing all the fields
	*	@return string representation of fields
	*/
	@Override
	public String toString(){
		if(failure){
			return "FAILED\n";
		}
		
		StringBuilder sb = new StringBuilder();
		for(field t : fields){
			sb.append(t.getProject());
			sb.append("\n");
			sb.append(t.getID());
			sb.append("\n");
			sb.append(t.getTitle());
			sb.append("\n");
		}
		return sb.toString();
	}
}
