package version4;

import java.io.*;
import java.util.*;


public class Exceptions {

	public static void main(String[] args) {
		
		File file = new File(args[0]);
		
		processFile(file);
	}
	
	private static void processFile(File file) {
		
		try (Scanner scan = new Scanner(file)) {
		
			// ... use Scanner for something useful
		}
		catch (FileNotFoundException ex) {
			System.out.println("Could not find file: " + file);
			ex.printStackTrace();
		}
		catch (IllegalArgumentException ex) {
			System.out.println("Illegal argument: Fix your bug!");
			ex.printStackTrace();
		}
		catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
}
