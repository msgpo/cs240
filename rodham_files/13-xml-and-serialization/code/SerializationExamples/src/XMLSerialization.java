
import java.io.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XMLSerialization {

	public static void main(String[] args) throws Exception {
		
		Catalog catalog = new Catalog();
		
		catalog.add(new CD("Hide your heart", "Bonnie Tyler", "UK",
							"CBS Records", "9.90", "1988"));
		catalog.add(new CD("Greatest Hits", "Dolly Parton", "USA",
							"RCA", "9.90", "1982"));
		catalog.add(new CD("Still got the blues", "Gary Moore", "UK",
							"Virgin records", "10.20", "1990"));

		XStream xStream = new XStream(new DomDriver());
		
		OutputStream outFile = new BufferedOutputStream(new FileOutputStream("xstream.xml"));
		xStream.toXML(catalog, outFile);
		outFile.close();
		
		InputStream inFile = new BufferedInputStream(new FileInputStream("xstream.xml"));
		Catalog catalogCopy = (Catalog)xStream.fromXML(inFile);
		inFile.close();
		
		if (catalog.getItems().size() == catalogCopy.getItems().size()) {
			System.out.println("YES!");
		}
		else {
			System.out.println("NO!");
		}
	}

}
