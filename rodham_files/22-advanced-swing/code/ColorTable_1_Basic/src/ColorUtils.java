
import java.awt.*;


public class ColorUtils {
	
	public static String toString(Color c) {
		return String.format("%d %d %d", c.getRed(), c.getGreen(), c.getBlue());
	}
	
	public static Color fromString(String s) {
		
		String[] values = s.split("\\s+");
		
		try {
			int red = Integer.parseInt(values[0]);
			int green = Integer.parseInt(values[1]);
			int blue = Integer.parseInt(values[2]);
			
			return new Color(red, green, blue);
		}
		catch (NumberFormatException e) {
			return null;
		}
	}

}
