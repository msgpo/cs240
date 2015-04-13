
import java.awt.*;
import java.io.*;
import java.util.*;


public class ColorSchemeIO_Binary {

	public static void main(String[] args) {

		new ColorSchemeIO_Binary().run(args);
		
	}
	
	private void run(String[] args) {
		try {
			ArrayList<ColorScheme> schemes = generateSchemes();
			
			File file = new File("color-schemes.dat");
			
			saveSchemes(schemes, file);
			
			ArrayList<ColorScheme> copyOfSchemes = loadSchemes(file);
			
			if (schemes.equals(copyOfSchemes)) {
				System.out.println("IT WORKED!");
			}
			else {
				System.out.println("ERROR!");
			}		
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}	
	}
	
	private void saveSchemes(ArrayList<ColorScheme> schemes, File file) 
		throws IOException {
	
		DataOutputStream output = null;
		
		try {
			output = new DataOutputStream(
						new BufferedOutputStream(
							new FileOutputStream(file)));
			
			output.writeInt(schemes.size());
			
			for (ColorScheme cs : schemes) {
				saveScheme(cs, output);
			}
		}
		finally {
			if (output != null) {
				output.close();
			}
		}
	}
	
	private void saveScheme(ColorScheme scheme, DataOutputStream output)
		throws IOException {
		
		output.writeUTF(scheme.getName());
		
		saveColor(scheme.getBackground(), output);
		saveColor(scheme.getForeground(), output);
		saveColor(scheme.getHighlight(), output);
		saveColor(scheme.getShadow(), output);
	}
	
	private void saveColor(Color color, DataOutputStream output) 
		throws IOException {
		
		output.writeInt(color.getRed());
		output.writeInt(color.getGreen());
		output.writeInt(color.getBlue());
		output.writeInt(color.getAlpha());
	}
	
	private ArrayList<ColorScheme> loadSchemes(File file) 
		throws IOException {
	
		ArrayList<ColorScheme> schemes = new ArrayList<ColorScheme>();
		
		DataInputStream input = null;
		
		try {
			input = new DataInputStream(
						new BufferedInputStream(
							new FileInputStream(file)));
			
			int count = input.readInt();
			
			for (int i = 0; i < count; ++i) {
				schemes.add(loadScheme(input));
			}
		}
		finally {
			if (input != null) {
				input.close();
			}
		}
		
		return schemes;
	}
	
	private ColorScheme loadScheme(DataInputStream input)
		throws IOException {
		
		ColorScheme scheme = new ColorScheme();
		
		scheme.setName(input.readUTF());
		
		scheme.setBackground(loadColor(input));
		scheme.setForeground(loadColor(input));
		scheme.setHighlight(loadColor(input));
		scheme.setShadow(loadColor(input));
		
		return scheme;
	}
	
	private Color loadColor(DataInputStream input) 
		throws IOException {
		 
		int red = input.readInt();
		int green = input.readInt();
		int blue = input.readInt();
		int alpha = input.readInt();
		
		return new Color(red, green, blue, alpha);
	}
	
	private ArrayList<ColorScheme> generateSchemes() {
		
		final int NUM_SCHEMES = 20;
		
		ArrayList<ColorScheme> result = new ArrayList<ColorScheme>();
		Random rand = new Random();
		
		for (int i = 1; i <= NUM_SCHEMES; ++i) {
			
			ColorScheme scheme = new ColorScheme("Scheme " + i,
													generateColor(rand),
													generateColor(rand),
													generateColor(rand),
													generateColor(rand));
			result.add(scheme);
		}
		
		return result;
	}
	
	private Color generateColor(Random rand) {
		
		int r = rand.nextInt(256);
		int g = rand.nextInt(256);
		int b = rand.nextInt(256);
		
		return new Color(r, g, b);
	}
	
}
