import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

// OurCanvas represents the Main area in the program that the user uses to draw, add shapes, add images and so on...
class OurCanvas extends JPanel implements CanvasObservable, Observable {
	// In the future mainLayer might be part of the LayersHandler Class
	private int width;
	private int height;
	private Color col;
	private LayerData mainLayer;

	private boolean canDrag = false;
	private ArrayList<CanvasObserver> canvasObservers;
	private ArrayList<Observer> observers;

	public OurCanvas() {
		this.width = 800;
		this.height = 600;
		this.col = Color.white;
		this.mainLayer = new LayerData(width, height, col);

		this.canvasObservers = new ArrayList<CanvasObserver>();
		this.observers = new ArrayList<Observer>();

		setBackground(new Color(202, 211, 227));
		addCanvasListener();
		repaint();
	}

	// addCanvasListener() is used to support the resizing of the canvas
	private void addCanvasListener() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (Math.abs(e.getX() - mainLayer.getWidth()) <= 5 && Math.abs(e.getY() - mainLayer.getHeight()) <= 5) {
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

				width = e.getX();
				height = e.getY();
				Cursor cursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR); 
 				setCursor(cursor);
 				updateCanvasSz();
			}

			public void mouseMoved(MouseEvent e) {
				if (Math.abs(e.getX() - mainLayer.getWidth()) <= 5 && Math.abs(e.getY() - mainLayer.getHeight()) <= 5) {
					Cursor cursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR); 
     				setCursor(cursor);
					return;
				}

				setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));				
			}
		});
	}

	// updateCanvasSz() is used to change the canvas size when the user resizes the canvas
	private void updateCanvasSz() {
		// Still Under Development
		if (width >= getWidth() || height >= getHeight()) {
			setPreferredSize(new Dimension(width + 100, height + 100));
			revalidate();
		}

		mainLayer.clear(width, height, col);
		repaint();
		notifyCanvasObservers();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(mainLayer.getImage(), 0, 0, null);
	}

	public Color getCanvasColor() {
		return col;
	}

	// drawLayer() merges/draws a layer into the canvas
	public void drawLayer(LayerData img) {
		mainLayer.mergeLayer(img);
		repaint();
	}

	// clearCanvas() changes all the pixels in the canvas to col
	public void clearCanvas() {
		mainLayer.clear(col);
		repaint();
	}

	public LayerData getMainLayer() {
		return mainLayer;
	}

	// Observer Pattern: Might change this part in the future //
	
	// notifyCanvasObservers() is used to notify the observers when the canvas is resized to increase the Drawing Area Size and refresh the canvas
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

	// notifyObservers() is used to notify the Tools Panel that the canvas is being resized to deselct all Tools
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