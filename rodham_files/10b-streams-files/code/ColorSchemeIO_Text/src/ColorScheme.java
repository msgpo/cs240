
import java.awt.*;


public class ColorScheme {

	private String name;
	private Color foreground;
	private Color background;
	private Color highlight;
	private Color shadow;
	
	public ColorScheme() {
		return;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		ColorScheme other = (ColorScheme)obj;
		
		return (getName().equals(other.getName()) &&
				getBackground().equals(other.getBackground()) &&
				getForeground().equals(other.getForeground()) &&
				getHighlight().equals(other.getHighlight()) &&
				getShadow().equals(other.getShadow()));
	}

	public ColorScheme(String name, Color foreground, Color background,
						Color highlight, Color shadow) {
		setName(name);
		setForeground(foreground);
		setBackground(background);
		setHighlight(highlight);
		setShadow(shadow);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Color getForeground() {
		return foreground;
	}
	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}
	
	public Color getBackground() {
		return background;
	}
	public void setBackground(Color background) {
		this.background = background;
	}
	
	public Color getHighlight() {
		return highlight;
	}
	public void setHighlight(Color highlight) {
		this.highlight = highlight;
	}
	
	public Color getShadow() {
		return shadow;
	}
	public void setShadow(Color shadow) {
		this.shadow = shadow;
	}
		
}
