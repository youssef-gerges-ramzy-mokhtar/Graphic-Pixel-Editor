import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

class OurCanvas extends JPanel implements CanvasObservable, Observable {
	private BufferedImage image;
	private Color col;
	private boolean canDrag = false;
	private int newWidth, newHeight;
	private ArrayList<CanvasObserver> canvasObservers;
	private ArrayList<Observer> observers;

	public OurCanvas() {
		image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D imgGraphics = image.createGraphics();
		imgGraphics.setBackground(col);
		imgGraphics.clearRect(0, 0, 800, 600);


		canvasObservers = new ArrayList<CanvasObserver>();
		observers = new ArrayList<Observer>();

		col = Color.white;
		setBackground(new Color(202, 211, 227));
		repaint();

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (Math.abs(e.getX() - image.getWidth()) <= 5 && Math.abs(e.getY() - image.getHeight()) <= 5) {
					canDrag = true; 
					notifyObservers();
					return;
				}

				canDrag = false;
			}
		});

		addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (!canDrag) return;

				newWidth = e.getX();
				newHeight = e.getY();
				Cursor cursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR); 
 				setCursor(cursor);
 				incCanvasSz();
			}

			public void mouseMoved(MouseEvent e) {
				if (image == null) return;

				if (Math.abs(e.getX() - image.getWidth()) <= 5 && Math.abs(e.getY() - image.getHeight()) <= 5) {
					Cursor cursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR); 
     				setCursor(cursor);
					return;
				}

				setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));				
			}
		});

	}

	private void incCanvasSz() {
		if (newWidth == image.getWidth() && newHeight == image.getHeight()) return;

		// Still Under Development
		if (newWidth >= getWidth() || newHeight >= getHeight()) {
			setPreferredSize(new Dimension(newWidth + 100, newHeight + 100));
			revalidate();
		}

		BufferedImage tempImg = (BufferedImage) createImage(newWidth, newHeight);
		Graphics2D imgGraphics = tempImg.createGraphics();
		imgGraphics.setBackground(col);
		imgGraphics.clearRect(0, 0, newWidth, newHeight);
    	
    	image = tempImg;
		repaint();

		newWidth = image.getWidth();
		newHeight = image.getHeight();		
		notifyCanvasObservers();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	public Color getCanvasColor() {
		return col;
	}

	public Color getCanvasColor(int x, int y) {
		return new Color(image.getRGB(x, y));
	}

	public void setPixel(int x, int y, int rgb) {
		if (!inRange(x, y)) return;

		image.setRGB(x, y, rgb);
		revalidate();
		repaint();
	}

	public Integer getPixel(int x, int y) {
		if (!inRange(x, y)) return null;
		
		return image.getRGB(x, y);
	}

	private boolean inRange(int x, int y) {
		if (x < 0) return false;
		if (y < 0) return false;
		if (x >= image.getWidth()) return false;
		if (y >= image.getHeight()) return false;

		return true;
	}

	public void drawLayer(LayerData img) {
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

		g2d.drawImage(img.getImage(), img.getX(), img.getY(), null);
		repaint();
	}

	public void clearCanvas() {
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		g2d.setBackground(col);
		g2d.clearRect(0, 0, getCanvasWidth(), getCanvasHeight());
		repaint();
	}

	public int getCanvasWidth() {
		return image.getWidth();
	}

	public int getCanvasHeight() {
		return image.getHeight();
	}

	public BufferedImage getCanvasLayer() {
		return image;
	}

	// Observer Pattern //
	public void notifyCanvasObservers() {
		for (CanvasObserver observer: canvasObservers)
			observer.update();
	}

	public void addCanvasObserver(CanvasObserver observer) {
		canvasObservers.add(observer);
	}

	public void removeCanvasObserver(CanvasObserver observer) {
		canvasObservers.remove(observer);
	}

	public void notifyObservers() {
		for (Observer observer: observers)
			observer.update3();
	}

	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}
}