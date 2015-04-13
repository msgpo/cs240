
import java.io.*;

public class BinarySerialization {

	public static void main(String[] args) throws Exception {
		
		Catalog catalog = new Catalog();
		
		catalog.add(new CD("Hide your heart", "Bonnie Tyler", "UK",
							"CBS Records", "9.90", "1988"));
		catalog.add(new CD("Greatest Hits", "Dolly Parton", "USA",
							"RCA", "9.90", "1982"));
		catalog.add(new CD("Still got the blues", "Gary Moore", "UK",
							"Virgin records", "10.20", "1990"));

		OutputStream outFile = new BufferedOutputStream(new FileOutputStream("java.ser"));
		ObjectOutputStream output = new ObjectOutputStream(outFile);
		output.writeObject(catalog);
		output.close();
		
		InputStream inFile = new BufferedInputStream(new FileInputStream("java.ser"));
		ObjectInputStream input = new ObjectInputStream(inFile);
		Catalog catalogCopy = (Catalog)input.readObject();
		input.close();
		
		if (catalog.getItems().size() == catalogCopy.getItems().size()) {
			System.out.println("YES!");
		}
		else {
			System.out.println("NO!");
		}
	}

}
