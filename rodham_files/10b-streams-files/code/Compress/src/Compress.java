
import java.io.*;
import java.util.zip.*;


public class Compress {

	public static void main(String[] args) {
		
		new Compress().run(args);
	}
	
	private void run(String[] args) {

		if (args.length == 2) {
			try {
				File inputFile = new File(args[0]);
				File outputFile = new File(args[1]);
				
				InputStream input = null;
				OutputStream output = null;
	
				try {
					input = new BufferedInputStream(
								new FileInputStream(inputFile));
					
					output = new GZIPOutputStream(
								new BufferedOutputStream(
									new FileOutputStream(outputFile)));
					
					byte[] chunk = new byte[512];
					
					int bytesRead;
					while ((bytesRead = input.read(chunk)) > 0) {
						output.write(chunk, 0, bytesRead);
					}				
				}
				finally {
					if (input != null) {
						input.close();
					}
					if (output != null) {
						output.close();
					}
				}
			}
			catch (IOException e) {
				System.out.println("ERROR: " + e.getMessage());
				usage();
			}
		}
		else {
			usage();
		}
	}
	
	private void usage() {
		System.out.println("\nUSAGE: java Compress <input-file> <output-file>");
	}

}
