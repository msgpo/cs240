package parsing;

import java.io.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;

public class DOMExample {

	public static void main(String[] args) throws Exception {

		DocumentBuilder builder = DocumentBuilderFactory.
									newInstance().newDocumentBuilder();
		
		File file = new File("cd_catalog.xml");
		Document doc = builder.parse(file);
				
		NodeList cdList = doc.getElementsByTagName("CD");
		for (int i = 0; i < cdList.getLength(); ++i) {
			
			Element cdElem = (Element)cdList.item(i);
			
			Element titleElem = (Element)cdElem.getElementsByTagName("TITLE").item(0);
			Element artistElem = (Element)cdElem.getElementsByTagName("ARTIST").item(0);
			
			String title = titleElem.getTextContent();
			String artist = artistElem.getTextContent();
			
			System.out.println(title + ", " + artist);
		}		
	}
	
}
