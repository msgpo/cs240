package parsing;

import java.io.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;

import org.w3c.dom.*;

public class XPathExample {

	public static void main(String[] args) throws Exception {
		
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
		File file = new File("cd_catalog.xml");
		Document doc = builder.parse(file);

		XPath xpath = XPathFactory.newInstance().newXPath();
		
		NodeList titleList = (NodeList)xpath.evaluate("/CATALOG/CD/TITLE", doc, XPathConstants.NODESET);
		NodeList artistList = (NodeList)xpath.evaluate("/CATALOG/CD/ARTIST", doc, XPathConstants.NODESET);
		
		for (int i = 0; i < titleList.getLength(); ++i) {
			String title = ((Element)titleList.item(i)).getTextContent();
			String artist = ((Element)artistList.item(i)).getTextContent();
			System.out.println(title + ", " + artist);
		}
	}

}
