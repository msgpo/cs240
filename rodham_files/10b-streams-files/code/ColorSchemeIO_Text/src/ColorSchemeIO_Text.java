
import java.awt.*;
import java.io.*;
import java.util.*;


public class ColorSchemeIO_Text {

	private static final String DELIMITER = "|";
	
	public static void main(String[] args) {

		new ColorSchemeIO_Text().run(args);
		
	}
	
	private void run(String[] args) {
		try {
			ArrayList<ColorScheme> schemes = generateSchemes();
			
			File file = new File("color-schemes.txt");
			
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
	
		PrintWriter output = null;
		
		try {
			output = new PrintWriter(
						new BufferedOutputStream(
							new FileOutputStream(file)));

			output.print(schemes.size());
			output.append(DELIMITER);
			
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
	
	private void saveScheme(ColorScheme scheme, PrintWriter output)
		throws IOException {
		
		output.print(scheme.getName());
		output.append(DELIMITER);
		
		saveColor(scheme.getBackground(), output);
		saveColor(scheme.getForeground(), output);
		saveColor(scheme.getHighlight(), output);
		saveColor(scheme.getShadow(), output);
	}
	
	private void saveColor(Color color, PrintWriter output) 
		throws IOException {
		
		output.print(color.getRed());
		output.append(DELIMITER);
		output.print(color.getGreen());
		output.append(DELIMITER);
		output.print(color.getBlue());
		output.append(DELIMITER);
		output.print(color.getAlpha());
		output.append(DELIMITER);
	}
	
	private ArrayList<ColorScheme> loadSchemes(File file) 
		throws IOException {
	
		ArrayList<ColorScheme> schemes = new ArrayList<ColorScheme>();
		
		Scanner input = null;
		
		try {
			input = new Scanner(
						new BufferedInputStream(
							new FileInputStream(file)));
			input.useDelimiter("\\|");
			
			int count = input.nextInt();
			
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
	
	private ColorScheme loadScheme(Scanner input)
		throws IOException {
		
		ColorScheme scheme = new ColorScheme();
		
		scheme.setName(input.next());
		
		scheme.setBackground(loadColor(input));
		scheme.setForeground(loadColor(input));
		scheme.setHighlight(loadColor(input));
		scheme.setShadow(loadColor(input));
		
		return scheme;
	}
	
	private Color loadColor(Scanner input) 
		throws IOException {
		 
		int red = input.nextInt();
		int green = input.nextInt();
		int blue = input.nextInt();
		int alpha = input.nextInt();
		
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
