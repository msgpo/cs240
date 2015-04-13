
import java.io.*;
import java.util.*;


public class ColorSchemeIO_RandomAccess {

	public static void main(String[] args) {

		new ColorSchemeIO_RandomAccess().run(args);
		
	}
	
	// Color scheme binary data file
	RandomAccessFile file;
	
	// Map that records the position of each color scheme in the binary data file
	private Map<String, Long> schemeIndex;
	
	public ColorSchemeIO_RandomAccess() {
		
		schemeIndex = new HashMap<String, Long>();
	}
	
	private void run(String[] args) {
		try {
			file = new RandomAccessFile(new File("color-schemes.dat"), "rw");
			
			try {
				buildSchemeIndex();
				
				Scanner input = new Scanner(System.in);
				input.useDelimiter("\\s+");
				
				while (true) {
					
					System.out.print("\n>>> ");
					
					String command = input.next();
					
					if (command.equals("quit")) {
						break;
					}
					else if (command.equals("help")) {
						help();
					}
					else if (command.equals("show")) {
						String schemeName = input.nextLine().trim();
						showScheme(schemeName);
					}
					else if (command.equals("edit")) {
						input.useDelimiter(",");
						String schemeName = input.next().trim();
						input.useDelimiter("\\s+");
						input.next();	// skip comma
						int r = input.nextInt();
						int g = input.nextInt();
						int b = input.nextInt();
						int a = input.nextInt();
						editScheme(schemeName, r, g, b, a);
					}
					else {
						System.out.println("Invalid Command");
					}
				}
			}
			finally {
				if (file != null) {
					file.close();
				}
			}
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}	
	}
	
	private void showScheme(String schemeName) throws IOException {
		
		if (schemeIndex.containsKey(schemeName)) {
			
			long schemePosition = schemeIndex.get(schemeName);
			file.seek(schemePosition);
			
			int r = file.readInt();
			int g = file.readInt();
			int b = file.readInt();
			int a = file.readInt();
			
			System.out.format("%d %d %d %d\n", r, g, b, a);
		}
		else {
			System.out.println("Invalid Scheme Name");
		}
	}
	
	private void editScheme(String schemeName, int r, int g, int b, int a)
		throws IOException {
		
		System.out.format("%s, %d %d %d %d\n", schemeName, r, g, b, a);
		
		if (schemeIndex.containsKey(schemeName)) {
			
			long schemePosition = schemeIndex.get(schemeName);
			file.seek(schemePosition);
			
			file.writeInt(r);
			file.writeInt(g);
			file.writeInt(b);
			file.writeInt(a);			
		}
		else {
			System.out.println("Invalid Scheme Name");
		}
	}
	
	private void help() {
		System.out.println("COMMANDS:");
		System.out.println("show <color-scheme-name>");
		System.out.println("edit <color-scheme-name>, <red> <green> <blue> <alpha>");
		System.out.println("help");
		System.out.println("quit");
	}
	
	private void buildSchemeIndex() throws IOException {

		final int COLORS_PER_SCHEME = 4;
		final int INTS_PER_COLOR = 4;
		final int BYTES_PER_INT = 4;
		final int BYTES_PER_SCHEME = COLORS_PER_SCHEME * 
										INTS_PER_COLOR * 
										BYTES_PER_INT;
	
		file.seek(0);
		
		int count = file.readInt();
		
		for (int i = 0; i < count; ++i) {
			
			String schemeName = file.readUTF();
			
			long schemePosition = file.getFilePointer();
			
			schemeIndex.put(schemeName, schemePosition);

			file.seek(schemePosition + BYTES_PER_SCHEME);
		}
	}

}
