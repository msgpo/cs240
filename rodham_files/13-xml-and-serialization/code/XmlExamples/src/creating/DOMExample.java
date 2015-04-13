package creating;

import java.io.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;

public class DOMExample {

	public static void main(String[] args) throws Exception {

		Catalog catalog = new Catalog();
		
		catalog.add(new CD("Hide your heart", "Bonnie Tyler", "UK",
							"CBS Records", "9.90", "1988"));
		catalog.add(new CD("Greatest Hits", "Dolly Parton", "USA",
							"RCA", "9.90", "1982"));
		catalog.add(new CD("Still got the blues", "Gary Moore", "UK",
							"Virgin records", "10.20", "1990"));
		
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();	
		Document doc = builder.newDocument();
		doc.appendChild(buildCatalog(catalog, doc));

		File file = new File("dom_cd_catalog.xml");
		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
		t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(file)));
	}
	
	private static Element buildCatalog(Catalog catalog, Document doc) throws Exception {
		
		Element catalogElem = doc.createElement("CATALOG");
		
		for (CD cd : catalog.getItems()) {
			catalogElem.appendChild(buildCD(cd, doc));
		}
		
		return catalogElem;
	}
	
	private static Element buildCD(CD cd, Document doc) throws Exception {

		Element cdElem = doc.createElement("CD");
		
		cdElem.appendChild(buildTextElem("TITLE", cd.getTitle(), doc));
		cdElem.appendChild(buildTextElem("ARTIST", cd.getArtist(), doc));
		cdElem.appendChild(buildTextElem("COUNTRY", cd.getCountry(), doc));
		cdElem.appendChild(buildTextElem("COMPANY", cd.getCompany(), doc));
		cdElem.appendChild(buildTextElem("PRICE", cd.getPrice(), doc));
		cdElem.appendChild(buildTextElem("YEAR", cd.getYear(), doc));
		
		return cdElem;
	}
	
	private static Element buildTextElem(String elemName, String elemText, Document doc)
		throws Exception {
		
		Element textElem = doc.createElement(elemName);
		textElem.appendChild(doc.createTextNode(elemText));
		return textElem;
	}

}
