package client;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.font.*;
import java.awt.event.*;

import javax.imageio.*;
import javax.swing.*;

import java.util.*;
import java.io.*;

import shared.model.field;


@SuppressWarnings("serial")
public class DrawingComponent extends JComponent {

	private static Image NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
	private double scale;
	private int w_originX;
	private int w_originY;
	private boolean dragging;
	private int w_dragStartX;
	private int w_dragStartY;
	private int w_dragStartOriginX;
	private int w_dragStartOriginY;

	private ArrayList<DrawingShape> shapes;
	private ArrayList<Rectangle2D> rectangles;
	private DrawingRect highlight;
	private boolean doHighlight;
	private Font font;
	private BasicStroke stroke;

	private BufferedImage image;
	private IndexingListener il;
	
	public DrawingComponent() {

		shapes = new ArrayList<DrawingShape>();
		rectangles = new ArrayList<Rectangle2D>();
		doHighlight = false;
		
		font = new Font("SansSerif", Font.PLAIN, 72);
		stroke = new BasicStroke(5);

		scale = 1.0;
		w_originX = 0;
		w_originY = 0;
		
	//	this.setBackground(new Color(178, 223, 210));
		this.setPreferredSize(new Dimension(1000, 700));
		this.setMinimumSize(new Dimension(512, 100));
		this.setMaximumSize(new Dimension(1480, 1000));
		
		this.addComponentListener(componentAdapter);
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.addMouseWheelListener(mouseAdapter);
		initDrag();
		
	//			
	}

	public void addIndexingListener(IndexingListener IL){
		il = IL;
	}

	public void setImage(BufferedImage i){
		image = i;
		shapes.add(new DrawingImage(image, 
				new Rectangle2D.Double(0, 0, 
					image.getWidth(), image.getHeight())));
		this.repaint();
	}

	public void addGrid(int rows, int firstx, 
			int firsty, int height, 
			LinkedList<field> fields){
		doHighlight = true;
		// x and y here are grid squares, that's all.
		rectangles.clear();
		for(field f : fields){
			for(int y = 0; y < rows; y++){
				// now make a rectangle by the offsets
				Rectangle2D square =
						new Rectangle2D.Double(
								f.getXCoord(),
								firsty + (y * height),
								f.getWidth(),
								height - 1);
				rectangles.add(square);
			}
		}
		if(!rectangles.isEmpty()){
			// in case user clicks when no batch
			highlight = new DrawingRect(rectangles.get(0), 
					new Color(178, 208, 254, 64));
			this.repaint();
		}
	}

	public void noGrid(){
	//	rectangles.clear();
		doHighlight = false;
		this.repaint();
	}

	public void resetImage(){
		shapes.clear();
		this.repaint();
	}


	private void initDrag(){
		dragging = false;
		w_dragStartX = 0;
		w_dragStartY = 0;
		w_dragStartOriginX = 0;
		w_dragStartOriginY = 0;
	}
		
	
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;
		drawBackground(g2);

		// translate to origin
		g2.translate(getWidth()/2.0, getHeight()/2.0);
		// set new scale factor
		g2.scale(scale, scale);
		// translate
		g2.translate(-w_originX, -w_originY);
		
		drawShapes(g2);
	}

	public void setOrigin(int w_newOriginX, int w_newOriginY) {
		w_originX = w_newOriginX;
		w_originY = w_newOriginY;
		this.repaint();
	}

	
	private void drawBackground(Graphics2D g2) {
		g2.setColor(getBackground());
		g2.fillRect(0,  0, getWidth(), getHeight());
	}

	private void drawShapes(Graphics2D g2) {
		for (DrawingShape shape : shapes) {
			shape.draw(g2);
		}
		if(doHighlight && !rectangles.isEmpty()){
			// we can draw the highlight box
			highlight.draw(g2);
			for(Rectangle2D rect : rectangles){
			//	g2.draw(rect);
			}
		}
	}
	
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
		}

		@Override
		public void componentShown(ComponentEvent e) {
			return;
		}	
	};

	private MouseAdapter mouseAdapter = new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e){
			int d_X = e.getX();
			int d_Y = e.getY();

			AffineTransform transform = new AffineTransform();
			transform.scale(scale, scale);
			transform.translate(-w_originX, -w_originY);
			
			Point2D d_Pt = new Point2D.Double(d_X, d_Y);
			Point2D w_Pt = new Point2D.Double();
			try
			{
				transform.inverseTransform(d_Pt, w_Pt);
			}
			catch (NoninvertibleTransformException ex) {
				return;
			}
			int w_X = (int)w_Pt.getX();
			int w_Y = (int)w_Pt.getY();
			
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
				w_dragStartX = w_X;
				w_dragStartY = w_Y;		
				w_dragStartOriginX = w_originX;
				w_dragStartOriginY = w_originY;
			}	
		}	
		@Override
		public void mouseDragged(MouseEvent e) {		
			if (dragging) {
				int d_X = e.getX();
				int d_Y = e.getY();
				
				AffineTransform transform = new AffineTransform();
				transform.scale(scale, scale);
				transform.translate(-w_dragStartOriginX, -w_dragStartOriginY);
				
				Point2D d_Pt = new Point2D.Double(d_X, d_Y);
				Point2D w_Pt = new Point2D.Double();
				try
				{
					transform.inverseTransform(d_Pt, w_Pt);
				}
				catch (NoninvertibleTransformException ex) {
					return;
				}
				int w_X = (int)w_Pt.getX();
				int w_Y = (int)w_Pt.getY();
				
				int w_deltaX = w_X - w_dragStartX;
				int w_deltaY = w_Y - w_dragStartY;
				
				w_originX = w_dragStartOriginX - w_deltaX;
				w_originY = w_dragStartOriginY - w_deltaY;

				il.scrollChanged(w_originX, w_originY);
				
				repaint();
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			initDrag();
			// detect if clicked on grid
			// if grid empty, nothing is done.
			int d_X = e.getX();
			int d_Y = e.getY();

			AffineTransform transform = new AffineTransform();
			transform.translate(getWidth()/2.0, getHeight()/2.0);
			transform.scale(scale, scale);
			transform.translate(-w_originX, -w_originY);
			
			Point2D d_Pt = new Point2D.Double(d_X, d_Y);
			Point2D w_Pt = new Point2D.Double();
			try
			{
				transform.inverseTransform(d_Pt, w_Pt);
			}
			catch (NoninvertibleTransformException ex) {
				return;
			}

			if(doHighlight){
				for(Rectangle2D rect : rectangles){
					if(rect.contains(w_Pt)){
						highlight = new DrawingRect(
								rect, new Color(
								178, 208, 254, 64));
						repaint();
					}
				}
			}

		}
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			setScale(scale + (e.getPreciseWheelRotation() * 0.05));
		}	
	};

	public void setScale(double newScale) {
		scale = newScale;
		if(scale < 0){
			scale = -scale;
		}
		il.zoomChanged(scale);
		this.repaint();
	}

	public void zoomIn() {
		setScale(scale + 0.1);
	}
	
	public void zoomOut() {
		setScale(scale - 0.1);
	}

	public void inversion(){
		// iterate thru each pixel and simple invert
		for(int x = 0; x<image.getWidth(); x++){
			for(int y = 0; y<image.getHeight(); y++){
		//	System.out.println("Coords: ");
		//	System.out.println("x	: " + x);
		//	System.out.println("y	: " + y);
				int original = image.getRGB(x, y);
				Color res = new Color(original, true);
				res = new Color(255 - res.getRed(),
						255 - res.getGreen(),
						255 - res.getBlue());
				image.setRGB(x, y, res.getRGB());
			}
		}
		this.repaint();
	}


	
	/////////////////
	// Drawing Shape
	/////////////////
	
	
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
			g2.setColor(color);
			g2.fill(rect);
		}
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
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
			g2.drawImage(image, (int)rect.getMinX(), (int)rect.getMinY(), (int)rect.getMaxX(), (int)rect.getMaxY(),
							0, 0, image.getWidth(null), image.getHeight(null), null);
		}	
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
		}
	}


	
}




