package noevents;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

import javax.imageio.*;
import javax.swing.*;

import java.util.*;
import java.io.*;


@SuppressWarnings("serial")
public class DrawingComponent extends JComponent {

	private static Image NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
	
	private ArrayList<DrawingShape> shapes;
	private Font font;
	
	public DrawingComponent() {
		shapes = new ArrayList<DrawingShape>();
		
		font = new Font("SansSerif", Font.PLAIN, 72);
		
		this.setBackground(new Color(178, 223, 210));
		this.setPreferredSize(new Dimension(700, 700));
		this.setMinimumSize(new Dimension(100, 100));
		this.setMaximumSize(new Dimension(1000, 1000));
		
		shapes.add(new DrawingRect(new Rectangle2D.Double(20, 20, 150, 200), new Color(210, 180, 140, 192)));
		shapes.add(new DrawingLine(new Line2D.Double(400, 400, 600, 600), new Color(255, 0, 0, 64)));
		
		Image mario = loadImage("mario.jpg");
		shapes.add(new DrawingImage(mario, new Rectangle2D.Double(350, 50, mario.getWidth(null), mario.getHeight(null))));
		
		Image spongebob = loadImage("spongebob.jpg");
		shapes.add(new DrawingImage(spongebob, new Rectangle2D.Double(50, 250, spongebob.getWidth(null) * 2, spongebob.getHeight(null) * 2)));
	
		String text1 = "Width: " + this.getWidth();
		shapes.add(new DrawingText(text1, font, Color.BLACK, new Point2D.Float(200, 200)));
		
		String text2 = "Height: " + this.getHeight();
		shapes.add(new DrawingText(text2, font, Color.BLACK, new Point2D.Float(200, 400)));
	}
	
	private Image loadImage(String imageFile) {
		try {
			return ImageIO.read(new File(imageFile));
		}
		catch (IOException e) {
			return NULL_IMAGE;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		drawBackground(g2);
		drawShapes(g2);
	}
	
	private void drawBackground(Graphics2D g2) {
		g2.setColor(getBackground());
		g2.fillRect(0,  0, getWidth(), getHeight());
	}

	private void drawShapes(Graphics2D g2) {
		for (DrawingShape shape : shapes) {
			shape.draw(g2);
		}
	}
}


interface DrawingShape {
	void draw(Graphics2D g2);
}


class DrawingRect implements DrawingShape {

	private Rectangle2D rect;
	private Color color;
	
	public DrawingRect(Rectangle2D rect, Color color) {
		this.rect = rect;
		this.color = color;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(color);
		g2.fill(rect);
		// OR g2.fillRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
	}	
}


class DrawingLine implements DrawingShape {

	private Line2D line;
	private Color color;
	
	public DrawingLine(Line2D rect, Color color) {
		this.line = rect;
		this.color = color;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(color);
		g2.setStroke(new BasicStroke(5));
		g2.draw(line);
		// OR g2.drawLine((int)line.getX1(), (int)line.getY1(), (int)line.getX2(), (int)line.getY2());
	}	
}


class DrawingImage implements DrawingShape {

	private Image image;
	private Rectangle2D rect;
	
	public DrawingImage(Image image, Rectangle2D rect) {
		this.image = image;
		this.rect = rect;
	}

	@Override
	public void draw(Graphics2D g2) {
		Rectangle2D bounds = rect.getBounds2D();
		g2.drawImage(image, (int)bounds.getMinX(), (int)bounds.getMinY(), (int)bounds.getMaxX(), (int)bounds.getMaxY(),
						0, 0, image.getWidth(null), image.getHeight(null), null);
	}	
}


class DrawingText implements DrawingShape {

	private String text;
	private Font font;
	private Color color;
	private Point2D location;
	
	public DrawingText(String text, Font font, Color color, Point2D location) {
		this.text = text;
		this.font = font;
		this.color = color;
		this.location = location;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(color);
		g2.setFont(font);
		g2.drawString(text, (int)location.getX(), (int)location.getY());
	}	
}

