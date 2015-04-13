package version5;

import java.io.*;
import java.util.*;
import java.net.MalformedURLException;

public class Exceptions {

	public static void main(String[] args) {
		
		File file = new File(args[0]);
		
		try {
			processFile(file);
		}
		
		catch (MalformedURLException | SomethingBadHappenedException ex) {
			for (StackTraceElement ste : ex.getStackTrace()) {
				System.out.printf("Class: %s, Method: %s, File: %s, Line: %d\n", 
									ste.getClassName(), ste.getMethodName(),
									ste.getFileName(), ste.getLineNumber());
			}
		}
	}
	
	private static void processFile(File file) throws java.net.MalformedURLException, SomethingBadHappenedException {
		
		Scanner scan = null;

		try {
			scan = new Scanner(file);
			
			// ... use Scanner for something useful
			
			boolean invalidURL = true;
			if (invalidURL) {
				throw new MalformedURLException();
			}
			
			boolean somethingBad = true;
			if (somethingBad) {
				throw new SomethingBadHappenedException("The Sun went out!");
			}
		}
		catch (FileNotFoundException ex) {
			System.out.println("Could not find file: " + file);
			ex.printStackTrace();
		}
		catch (IllegalArgumentException ex) {
			System.out.println("Illegal argument: Fix your bug!");
			ex.printStackTrace();
		}
//		catch (Exception ex) {
//			System.out.println("Error: " + ex.getMessage());
//			ex.printStackTrace();
//		}
		finally {
			if (scan != null) {
				scan.close();
			}
		}
	
	}
	
}
