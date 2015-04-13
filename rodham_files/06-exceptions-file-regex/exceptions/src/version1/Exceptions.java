package version1;

import java.io.*;
import java.util.*;


public class Exceptions {

	public static void main(String[] args) throws FileNotFoundException {
		
		File file = new File(args[0]);
		
		processFile(file);
	}
	
	private static void processFile(File file) throws FileNotFoundException {
		
		Scanner scan = null;

		scan = new Scanner(file);
		
		// ... use Scanner for something useful
		
		scan.close();
	}
	
}
