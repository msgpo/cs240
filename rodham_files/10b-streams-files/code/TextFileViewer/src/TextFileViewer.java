
import java.io.*;


public class TextFileViewer {

	public static void main(String[] args) {

		new TextFileViewer().run(args);
	}
	
	private void run(String[] args) {
		
		if (args.length == 1) {
			try {
				
				File file = new File(args[0]);
				
				Reader input = null;
				Writer output = null;
				
				try {
					input = new InputStreamReader(
								new BufferedInputStream(
									new FileInputStream(file)));
	
					output = new OutputStreamWriter(System.out);
					
					char[] chunk = new char[512];
					
					int charsRead;
					while ((charsRead = input.read(chunk)) > 0) {
						output.write(chunk, 0, charsRead);
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
				System.out.println(e.getMessage());
				usage();
			}
		}
		else {
			usage();
		}
	}
	
	private void usage() {
		System.out.println("\nUSAGE: java TextFileViewer <text-file>");
	}

}
