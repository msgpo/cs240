
import java.io.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class JSONSerialization {

	public static void main(String[] args) throws Exception {
		
		Catalog catalog = new Catalog();
		
		catalog.add(new CD("Hide your heart", "Bonnie Tyler", "UK",
							"CBS Records", "9.90", "1988"));
		catalog.add(new CD("Greatest Hits", "Dolly Parton", "USA",
							"RCA", "9.90", "1982"));
		catalog.add(new CD("Still got the blues", "Gary Moore", "UK",
							"Virgin records", "10.20", "1990"));

		XStream xStream = new XStream(new JettisonMappedXmlDriver());
		
		OutputStream outFile = new BufferedOutputStream(new FileOutputStream("xstream.json"));
		xStream.toXML(catalog, outFile);
		outFile.close();
		
		InputStream inFile = new BufferedInputStream(new FileInputStream("xstream.json"));
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
