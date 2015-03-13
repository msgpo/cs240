package client;

import org.junit.*;
import static org.junit.Assert.*;

import client.*;

public class communicatorTests {
	
	@Test
	public void urlDownloadTest() throws ClientException{
		communicator c = new communicator("i.imgur.com",80);
		Object o = c.downloadFile("/u36kg.jpg");
		assertEquals("hash must be certain number", 662822946,
				o.hashCode());
	}
}
