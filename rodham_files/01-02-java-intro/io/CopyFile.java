
import java.io.*;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.PrintWriter;

import java.util.*;
//import java.util.Scanner;


// Copy one text file to another
// (this is not the best way to copy a text file, but it shows how to
// do simple file i/o without dealing with exceptions)

public class CopyFile {
	
	public static void main(String[] args) throws FileNotFoundException {

		// args[0] is the source file
		// args[1] is the destination file
		
		File srcFile = new File(args[0]);
		File destFile = new File(args[1]);
		
		FileCopier copier = new FileCopier(srcFile, destFile);
		copier.copy();
	}
	
}


class FileCopier {

	private File srcFile;
	private File destFile;
	
	public FileCopier(File srcFile, File destFile) {
		this.srcFile = srcFile;
		this.destFile = destFile;
	}
	
	public void copy() throws FileNotFoundException {
		Scanner scanner = new Scanner(srcFile);
		PrintWriter writer = new PrintWriter(destFile);
		
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			writer.println(line);
		}
		
		scanner.close();
		writer.close();
	}
	
}
