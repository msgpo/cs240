package parsing;

import java.io.*;

import javax.xml.stream.*;

public class StAXExample {

	public static void main(String[] args) throws Exception {
		
		// Use StAX parser to find list of CD titles and artists
		
		InputStream input = new BufferedInputStream(
								new FileInputStream("cd_catalog.xml"));
		XMLStreamReader parser = XMLInputFactory.newInstance().
									createXMLStreamReader(input);
		
		String title = "";
		String artist = "";
		
		while (parser.hasNext()) {
			
			int event = parser.next();
			
			if (event == XMLStreamConstants.START_ELEMENT) {
				
				String elemName = parser.getLocalName();
				
				if (elemName.equals("TITLE")) {
					title = parser.getElementText();
				}
				else if (elemName.equals("ARTIST")) {
					artist = parser.getElementText();
					System.out.println(title + ", " + artist);
				}
			}
		}
		
		parser.close();
		input.close();
	}
	
}
