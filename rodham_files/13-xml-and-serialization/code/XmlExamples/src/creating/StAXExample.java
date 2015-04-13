package creating;

import java.io.*;
import javax.xml.stream.*;

public class StAXExample {

	public static void main(String[] args) throws Exception {

		Catalog catalog = new Catalog();
		
		catalog.add(new CD("Hide your heart", "Bonnie Tyler", "UK",
							"CBS Records", "9.90", "1988"));
		catalog.add(new CD("Greatest Hits", "Dolly Parton", "USA",
							"RCA", "9.90", "1982"));
		catalog.add(new CD("Still got the blues", "Gary Moore", "UK",
							"Virgin records", "10.20", "1990"));
		
		OutputStream output = new BufferedOutputStream(new FileOutputStream("stax_cd_catalog.xml"));
		
		XMLStreamWriter writer = XMLOutputFactory.newInstance().createXMLStreamWriter(output);

		saveCatalog(catalog, writer);
		
		writer.close();
		output.close();
	}
	
	private static void saveCatalog(Catalog catalog, XMLStreamWriter writer) throws Exception {
		
		writer.writeStartElement("CATALOG");
		
		for (CD cd : catalog.getItems()) {
			saveCD(cd, writer);
		}
		
		writer.writeEndElement();
	}
	
	private static void saveCD(CD cd, XMLStreamWriter writer) throws Exception {
		writer.writeStartElement("CD");
		
		saveTextElement("TITLE", cd.getTitle(), writer);
		saveTextElement("ARTIST", cd.getArtist(), writer);
		saveTextElement("COUNTRY", cd.getCountry(), writer);
		saveTextElement("COMPANY", cd.getCompany(), writer);
		saveTextElement("PRICE", cd.getPrice(), writer);
		saveTextElement("YEAR", cd.getYear(), writer);
		
		writer.writeEndElement();
	}
	
	private static void saveTextElement(String elemName, String elemText, XMLStreamWriter writer)
		throws Exception {
		
		writer.writeStartElement(elemName);
		writer.writeCharacters(elemText);
		writer.writeEndElement();
	}

}
