package server;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import server.*;
import client.*;
import shared.model.*;
import shared.communication.*;

public class communicatorTest {

	// most of the comparisons in this class will be done with 
	// string literals.  why?  because it's extremely 
	// difficult to make these communication objects by hand, 
	// and because asking the façade to do it will only test
	// that i can send an object, not that the façade is making
	// it right or that the client is decoding it right.
	
	// server should already be running on localhost:9090
	private communicator comm = new communicator("localhost", 9090);

	userToken utoken = new userToken("test1", "test1");
	String failed = "FAILED\n";

	@BeforeClass
	public static void setUp() throws ServerException {
		// this works because ant erases the database before
		// running any tests, and the DAO tests all rollback their TX
		System.out.println("Setting up client-server tests.");
		System.out.println("May take around 30-60 seconds...");
		try{
			String[] importfile = {"tdata/Records.xml"};
			importer.main(importfile);
		}
		catch(Exception e){
			throw new ServerException("unable to set up server tests", e);
		}
		System.out.println("Setup done.");
	}

	@Test
	public void validateTest() throws ClientException, ServerException{
		authToken atoken = comm.validateUser(utoken);
		// comparison tokens:
		authToken ctoken1 = new authToken("Test","One",0);
		authToken ctoken2 = new authToken(1);

		assertEquals("get good user", ctoken1, atoken);
		assertEquals("user failure", ctoken2, 
				comm.validateUser(new userToken(
					"fake", "fake")));
	}

	@Test
	public void getProjectTest() throws ClientException, ServerException{
		projList plist = comm.getProjects(utoken);
		// comparison lists:
		String goodList = "1\n1890 Census\n2\n1900 Census\n3\nDraft Records\n";
		
		assertEquals("get good list", goodList, plist.toString());
	}

	@Test
	public void getSampleTest() throws ClientException, ServerException{
		String sImg = comm.getSampleImage(utoken,1);
		// comparison URL:
		String goodImg = "http://localhost:9090/images/1890_image0.png";

		assertEquals("get sample image", goodImg, sImg);
	}

	@Test
	public void batchTest() throws ClientException, ServerException{
		// first, get a batch:
		batchBlob bb = comm.downloadBatch(utoken, 1);
		String gotBatch = "1\n1\nhttp://localhost:9090/images/1890_image0.png\n";
		gotBatch += "199\n60\n8\n4\n1\n1\nLast Name\nhttp://localhost:9090/field";
		gotBatch += "help/last_name.html\n60\n300\nhttp://localhost:9090/knownda";
		gotBatch += "ta/1890_last_names.txt\n2\n2\nFirst Name\nhttp://localhost:";
		gotBatch += "9090/fieldhelp/first_name.html\n360\n280\nhttp://localhost:";
		gotBatch += "9090/knowndata/1890_first_names.txt\n3\n3\nGender\nhttp://l";
		gotBatch += "ocalhost:9090/fieldhelp/gender.html\n640\n205\nhttp://local";
		gotBatch += "host:9090/knowndata/genders.txt\n4\n4\nAge\nhttp://localhos";
		gotBatch += "t:9090/fieldhelp/age.html\n845\n120\n";

		assertEquals("downloading batch", gotBatch, bb.toString());

		// prove that we can't double-download

		batchBlob badbb = comm.downloadBatch(utoken, 1);
		
		assertEquals("downloading batch", "FAILED\n", badbb.toString());

		// submit the batch
		// create submission: 8 rows, 4 fields
		// empty spaces were an issue earlier, try those
		String submission = "John,Jongle,5,7;,,,;,,,;,,,;";
		submission += ",,,;,,,;,,,;George,Bush,Barack,Obama";
		batchProposal bp = new batchProposal(1, submission);

		assertEquals("succesfully submit batch", true, 
				comm.submitBatch(utoken, bp));

		// prove that we can't double-submit
		assertEquals("fail to double-submit", false, 
				comm.submitBatch(utoken, bp));
		
	}
	
	@Test
	public void getFieldsTest() throws ClientException, ServerException{
		// try to get all fields

		fieldList fl = comm.getFields(utoken);
		// comparison:
		String clist = "1\n1\nLast Name\n1\n2\nFirst Name\n1\n3\nGender\n";
		clist += "1\n4\nAge\n2\n5\nGender\n2\n6\nAge\n2\n7\nLast Name\n2";
		clist += "\n8\nFirst Name\n2\n9\nEthnicity\n3\n10\nLast Name\n3\n";
		clist += "11\nFirst Name\n3\n12\nAge\n3\n13\nEthnicity\n";

		assertEquals("get all the fields", clist, fl.toString());
		
		// get by project (project id 1);

		fl = comm.getFields(utoken, 1);
		//comparison:
		clist = "1\n1\nLast Name\n1\n2\nFirst Name\n1\n3\nGender\n1\n4\nAge\n";
		
		assertEquals("get proj. 1 fields", clist, fl.toString());
	}

	@Test
	public void searchTest() throws ClientException, ServerException{
		searchProposal sp = new searchProposal("2,4,1", "Barack,Bush,John");
		ArrayList<searchBlob> sb = comm.search(utoken, sp);
		
		//comparisons:
		String res1 = "1\nhttp://localhost:9090/images/1890_image0.png\n8\n2\n";
		String res2 = "1\nhttp://localhost:9090/images/1890_image0.png\n1\n1\n";

		assertEquals("first search result", res1, sb.get(0).toString());
		assertEquals("last search result", res2, sb.get(1).toString());
	}	
}
