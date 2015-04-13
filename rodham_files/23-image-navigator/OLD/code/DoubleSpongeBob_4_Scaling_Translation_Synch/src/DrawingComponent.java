

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
	
	private int w_originX;
	private int w_originY;
	private double scale;
	
	private boolean dragging;
	private int d_dragStartX;
	private int d_dragStartY;
	private int w_dragStartOriginX;
	private int w_dragStartOriginY;

	private ArrayList<DrawingShape> shapes;
	private Font font;
	private BasicStroke stroke;
	
	private ArrayList<DrawingListener> listeners;
	
	public DrawingComponent() {
		w_originX = 0;
		w_originY = 0;
		scale = 1.0;
		
		initDrag();

		shapes = new ArrayList<DrawingShape>();
		
		font = new Font("SansSerif", Font.PLAIN, 72);
		stroke = new BasicStroke(5);
		
		listeners = new ArrayList<DrawingListener>();
		
		this.setBackground(new Color(178, 223, 210));
		this.setPreferredSize(new Dimension(700, 700));
		this.setMinimumSize(new Dimension(100, 100));
		this.setMaximumSize(new Dimension(1000, 1000));
		
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.addComponentListener(componentAdapter);
		
		Image mario = loadImage("mario.jpg");
		shapes.add(new DrawingImage(mario, new Rectangle2D.Double(350, 50, mario.getWidth(null), mario.getHeight(null))));
		
		Image spongebob = loadImage("spongebob.jpg");
		shapes.add(new DrawingImage(spongebob, new Rectangle2D.Double(50, 250, spongebob.getWidth(null) * 2, spongebob.getHeight(null) * 2)));
		
		shapes.add(new DrawingRect(new Rectangle2D.Double(20, 20, 150, 200), new Color(210, 180, 140, 192)));
		shapes.add(new DrawingLine(new Line2D.Double(400, 400, 600, 600), new Color(255, 0, 0, 64)));
		
		createTextShapes();
	}
	
	private void initDrag() {
		dragging = false;
		d_dragStartX = 0;
		d_dragStartY = 0;
		w_dragStartOriginX = 0;
		w_dragStartOriginY = 0;
	}
	
	private void createTextShapes() {
		String text1 = "Width: " + this.getWidth();
		shapes.add(new DrawingText(text1, Color.BLACK, new Point2D.Float(200, 200)));
		
		String text2 = "Height: " + this.getHeight();
		shapes.add(new DrawingText(text2, Color.BLACK, new Point2D.Float(200, 400)));
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
	
	private Image loadImage(String imageFile) {
		try {
			return ImageIO.read(new File(imageFile));
		}
		catch (IOException e) {
			return NULL_IMAGE;
		}
	}
	
	public void setScale(double newScale) {
		scale = newScale;
		this.repaint();
	}
	
	public void setOrigin(int w_newOriginX, int w_newOriginY) {
		w_originX = w_newOriginX;
		w_originY = w_newOriginY;
		this.repaint();
	}
	
	public void addDrawingListener(DrawingListener listener) {
		listeners.add(listener);
	}
	
	private void notifyOriginChanged(int w_newOriginX, int w_newOriginY) {
		for (DrawingListener listener : listeners) {
			listener.originChanged(w_newOriginX, w_newOriginY);
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
			if (dragging) {
				int d_deltaX = (e.getX() - d_dragStartX);
				int d_deltaY = (e.getY() - d_dragStartY);
				
				int w_deltaX = (int)(d_deltaX / scale);
				int w_deltaY = (int)(d_deltaY / scale);
				
				w_originX = w_dragStartOriginX - w_deltaX;
				w_originY = w_dragStartOriginY - w_deltaY;
				
				notifyOriginChanged(w_originX, w_originY);
				
				repaint();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			int d_X = e.getX();
			int d_Y = e.getY();
			int w_X = deviceToWorldX(d_X);
			int w_Y = deviceToWorldY(d_Y);
			
			boolean hitShape = false;
			
			Graphics2D g2 = (Graphics2D)getGraphics();
			for (DrawingShape shape : shapes) {
				if (shape.contains(g2, w_X, w_Y)) {
					hitShape = true;
					break;
				}
			}
			
			if (hitShape) {
				dragging = true;		
				d_dragStartX = e.getX();
				d_dragStartY = e.getY();		
				w_dragStartOriginX = w_originX;
				w_dragStartOriginY = w_originY;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			initDrag();
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			return;
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
	
	private int worldToDeviceX(int w_X) {
		double d_X = w_X;
		d_X -= w_originX;
		d_X *= scale;
		return (int)d_X;
	}
	
	private int worldToDeviceY(int w_Y) {
		double d_Y = w_Y;
		d_Y -= w_originY;
		d_Y *= scale;
		return (int)d_Y;
	}
	
	private int deviceToWorldX(int d_X) {
		double w_X = d_X;
		w_X *= 1.0 / scale;
		w_X += w_originX;
		return (int)w_X;
	}
	
	private int deviceToWorldY(int d_Y) {
		double w_Y = d_Y;
		w_Y *= 1.0 / scale;
		w_Y += w_originY;
		return (int)w_Y;
	}

	////////////////
	// Drawing Shape
	////////////////


	interface DrawingShape {
		boolean contains(Graphics2D g2, double x, double y);
		void draw(Graphics2D g2);
		Rectangle2D getBounds(Graphics2D g2);
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
		public void draw(Graphics2D g2) {
			Rectangle2D transformedRect = new Rectangle2D.Double(worldToDeviceX((int)rect.getX()),
																	worldToDeviceY((int)rect.getY()),
																	(int)(rect.getWidth() * scale),
																	(int)(rect.getHeight() * scale));
			g2.setColor(color);
			g2.fill(transformedRect);
		}
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
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
		public void draw(Graphics2D g2) {
			g2.setColor(color);

			Stroke transformedStroke = new BasicStroke((float)(scale * stroke.getLineWidth()));
			g2.setStroke(transformedStroke);
			
			Line2D transformedLine = new Line2D.Double(worldToDeviceX((int)line.getX1()),
														worldToDeviceY((int)line.getY1()),
														worldToDeviceX((int)line.getX2()),
														worldToDeviceY((int)line.getY2()));
			g2.draw(transformedLine);
		}	
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return line.getBounds2D();
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
		public void draw(Graphics2D g2) {
			Rectangle2D transformedRect = new Rectangle2D.Double(worldToDeviceX((int)rect.getX()),
																	worldToDeviceY((int)rect.getY()),
																	(int)(rect.getWidth() * scale),
																	(int)(rect.getHeight() * scale));
			
			g2.drawImage(image, (int)transformedRect.getMinX(), (int)transformedRect.getMinY(), 
							(int)transformedRect.getMaxX(), (int)transformedRect.getMaxY(),
							0, 0, image.getWidth(null), image.getHeight(null), null);
		}	
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
		}
	}
	
	
	class DrawingText implements DrawingShape {
	
		private String text;
		private Color color;
		private Point2D location;
		
		public DrawingText(String text, Color color, Point2D location) {
			this.text = text;
			this.color = color;
			this.location = location;
		}
	
		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			Rectangle2D bounds = getBounds(g2);
			return bounds.contains(x, y);
		}
	
		@Override
		public void draw(Graphics2D g2) {
			g2.setColor(color);
			
			Font transformedFont = font.deriveFont((float)(scale * font.getSize()));
			g2.setFont(transformedFont);
			
			g2.drawString(text, worldToDeviceX((int)location.getX()), worldToDeviceY((int)location.getY()));
		}
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			FontRenderContext context = g2.getFontRenderContext();
			Rectangle2D bounds = font.getStringBounds(text, context);
			bounds.setRect(location.getX(), location.getY() + bounds.getY(), 
							bounds.getWidth(), bounds.getHeight());
			return bounds;
		}
		
		public String getText() {
			return text;
		}
		
		public void setText(String value) {
			text = value;
		}
	}

}


