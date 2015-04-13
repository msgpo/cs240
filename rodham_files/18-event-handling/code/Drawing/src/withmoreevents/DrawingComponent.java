package withmoreevents;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.font.*;
import java.awt.event.*;

import javax.imageio.*;
import javax.swing.*;

import java.util.*;
import java.io.*;


@SuppressWarnings("serial")
public class DrawingComponent extends JComponent {

	private static Image NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
	
	private ArrayList<DrawingShape> shapes;
	private ArrayList<DrawingShape> dragShapes;
	private Point2D lastPoint;
	private Font font;
	
	public DrawingComponent() {
		shapes = new ArrayList<DrawingShape>();
		dragShapes = new ArrayList<DrawingShape>();
		
		font = new Font("SansSerif", Font.PLAIN, 72);
		
		this.setBackground(new Color(178, 223, 210));
		this.setPreferredSize(new Dimension(700, 700));
		this.setMinimumSize(new Dimension(100, 100));
		this.setMaximumSize(new Dimension(1000, 1000));
		
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.addMouseWheelListener(mouseAdapter);
		this.addComponentListener(componentAdapter);
		this.addKeyListener(keyAdapter);
		
		Image mario = loadImage("mario.jpg");
		shapes.add(new DrawingImage(mario, new Rectangle2D.Double(350, 50, mario.getWidth(null), mario.getHeight(null))));
		
		Image spongebob = loadImage("spongebob.jpg");
		shapes.add(new DrawingImage(spongebob, new Rectangle2D.Double(50, 250, spongebob.getWidth(null) * 2, spongebob.getHeight(null) * 2)));
		
		shapes.add(new DrawingRect(new Rectangle2D.Double(20, 20, 150, 200), new Color(210, 180, 140, 192)));
		shapes.add(new DrawingLine(new Line2D.Double(400, 400, 600, 600), new Color(255, 0, 0, 64)));
		
		createTextShapes();
	}
	
	private void createTextShapes() {
		String text1 = "Width: " + this.getWidth();
		shapes.add(new DrawingText(text1, font, Color.BLACK, new Point2D.Float(200, 200)));
		
		String text2 = "Height: " + this.getHeight();
		shapes.add(new DrawingText(text2, font, Color.BLACK, new Point2D.Float(200, 400)));
	}
	
	private void updateTextShapes() {
		for (DrawingShape shape : shapes) {
			if (shape instanceof DrawingText) {
				DrawingText textShape = (DrawingText)shape;
				if (textShape.getText().startsWith("Width:")) {
					textShape.setText("Width: " + this.getWidth());
				}
				else if (textShape.getText().startsWith("Height:")) {
					textShape.setText("Height: " + this.getHeight());
				}
			}
		}
	}
	
	private void adjustShapePositions(double dx, double dy) {
		for (DrawingShape shape : shapes) {
			shape.adjustPosition(dx, dy);
		}
		this.repaint();
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
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mouseDragged(MouseEvent e) {
			
			int dx = e.getX() - (int)lastPoint.getX();
			int dy = e.getY() - (int)lastPoint.getY();
			
			for (DrawingShape s : dragShapes) {
				s.adjustPosition(dx, dy);
			}
			
			lastPoint = new Point2D.Double(e.getX(), e.getY());
			
			DrawingComponent.this.repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
			dragShapes.clear();
			
			for (DrawingShape s : shapes) {
				if (s.contains((Graphics2D)DrawingComponent.this.getGraphics(), e.getX(), e.getY())) {
					dragShapes.add(s);
				}
			}
			
			lastPoint = new Point2D.Double(e.getX(), e.getY());
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			dragShapes.clear();
			lastPoint = null;
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			int scrollAmount = 0;
			if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
				scrollAmount = e.getUnitsToScroll();
			} else {
				scrollAmount = e.getWheelRotation();
			}
			
			int dx = 0;
			int dy = 0;
			if (e.isShiftDown()) {
				dx = scrollAmount;
				dy = 0;
			}
			else {
				dx = 0;
				dy = scrollAmount;
			}
			
			adjustShapePositions(dx, dy);
		}	
	};
	
	private ComponentAdapter componentAdapter = new ComponentAdapter() {

		@Override
		public void componentHidden(ComponentEvent e) {
			return;
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			return;
		}

		@Override
		public void componentResized(ComponentEvent e) {
			updateTextShapes();
		}

		@Override
		public void componentShown(ComponentEvent e) {
			return;
		}	
	};

	private KeyAdapter keyAdapter = new KeyAdapter() {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				adjustShapePositions(-1, 0);
			}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				adjustShapePositions(1, 0);
			}
			else if (e.getKeyCode() == KeyEvent.VK_UP) {
				adjustShapePositions(0, -1);
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				adjustShapePositions(0, 1);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			return;
		}

		@Override
		public void keyTyped(KeyEvent e) {
			return;
		}	
	};

}


interface DrawingShape {
	boolean contains(Graphics2D g2, double x, double y);
	void adjustPosition(double dx, double dy);
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
	public boolean contains(Graphics2D g2, double x, double y) {
		return rect.contains(x, y);
	}

	@Override
	public void adjustPosition(double dx, double dy) {
		rect.setRect(rect.getX() + dx, rect.getY() + dy, rect.getWidth(), rect.getHeight());	
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
	public boolean contains(Graphics2D g2, double x, double y) {

		final double TOLERANCE = 5.0;
		
		Point2D p1 = line.getP1();
		Point2D p2 = line.getP2();
		Point2D p3 = new Point2D.Double(x, y);
		
		double numerator = (p3.getX() - p1.getX()) * (p2.getX() - p1.getX()) + (p3.getY() - p1.getY()) * (p2.getY() - p1.getY());
		double denominator =  p2.distance(p1) * p2.distance(p1);
		double u = numerator / denominator;
		
		if (u >= 0.0 && u <= 1.0) {
			Point2D pIntersection = new Point2D.Double(	p1.getX() + u * (p2.getX() - p1.getX()),
														p1.getY() + u * (p2.getY() - p1.getY()));
			
			double distance = pIntersection.distance(p3);
			
			return (distance <= TOLERANCE);
		}
		
		return false;
	}

	@Override
	public void adjustPosition(double dx, double dy) {
		line.setLine(line.getX1() + dx, line.getY1() + dy, line.getX2() + dx, line.getY2() + dy);	
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
	public boolean contains(Graphics2D g2, double x, double y) {
		return rect.contains(x, y);
	}

	@Override
	public void adjustPosition(double dx, double dy) {
		rect.setRect(rect.getX() + dx, rect.getY() + dy, rect.getWidth(), rect.getHeight());	
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
	public boolean contains(Graphics2D g2, double x, double y) {
		FontRenderContext context = g2.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(text, context);
		bounds.setRect(location.getX(), location.getY() + bounds.getY(), 
						bounds.getWidth(), bounds.getHeight());
		return bounds.contains(x, y);
	}

	@Override
	public void adjustPosition(double dx, double dy) {
		double newX = location.getX() + dx;
		double newY = location.getY() + dy;
		location.setLocation(newX, newY);
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(color);
		g2.setFont(font);
		g2.drawString(text, (int)location.getX(), (int)location.getY());
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String value) {
		text = value;
	}
}

