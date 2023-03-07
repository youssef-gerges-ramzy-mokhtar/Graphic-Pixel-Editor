import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
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
	private final int spacingRange;

	/**
	 * OurCanvas represents the Main area in the program that the user uses to draw, add shapes, add images and so on...
	 */
	public OurCanvas() {
		this.width = 800;
		this.height = 600;
		this.col = Color.white;
		this.mainLayer = new ImageLayer(width, height, col);
		this.spacingRange = 10;

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
				if (mainLayer.canResize(e.getX(), e.getY(), spacingRange) == Resize.BOTTOMRIGHT) {
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
				if (mainLayer.canResize(e.getX(), e.getY(), spacingRange) == Resize.BOTTOMRIGHT) {
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

	/**
	 * changes the canvas dimensions
	 * @param newWidth the new width of the cavnas
	 * @param newHeight the new height of the canvas
	 */
	public void updateCanvasSize(int newWidth, int newHeight) {
		this.width = newHeight;
		this.height = newHeight;
		updateCanvasSz();
	}

	/**
	 * used to zoom the canvas based on a factor
	 * @param factor the zoom factor
	 */
	public void zoom(double factor) {
		width = (int) Math.floor(factor * width);
		height = (int) Math.floor(factor * height);
		updateCanvasSz();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(mainLayer.getImage(), 0, 0, null);
	}

	/**
	 * return the color of the canvas
	 * @return canvas color
	 */
	public Color getCanvasColor() {
		return col;
	}

	/**
	 * merges/draws a layer into the canvas
	 * @param img the layer that will be drawn into the canvas
	 */
	// drawLayer() merges/draws a layer into the canvas
	public void drawLayer(LayerData img) {
		mainLayer.mergeLayer(img);
		repaint();
	}

	/**
	 * merges/draws a selected layer into the canvas
	 * @param img the layer that will be drawn as a selection into the canvas
	 */
	public void drawSelectedLayer(LayerData img) {
		mainLayer.mergeLayerSelection(img);
		repaint();
	}

	/**
	 * changes all the pixels in the canvas to the initial canvas color
	 */
	// clearCanvas() changes all the pixels in the canvas to col
	public void clearCanvas() {
		mainLayer.clear(col);
		repaint();
	}

	/**
	 * returns the mainLayer that is used by the canvas to display all other layers
	 * @return returns the mainer layer
	 */
	public LayerData getMainLayer() {
		return mainLayer;
	}

	/**
	 * return the canvas dimensions
	 * @param return the canvas dimensions
	 */
	public Dimension getDimensions() {
		return new Dimension(mainLayer.getWidth(), mainLayer.getHeight());
	}

	// Observer Pattern: Might change this part in the future //

	/**
	 * used to notify the observers when the canvas is resized to increase the Drawing Area Size and refresh the canvas
	 */	
	// notifyCanvasObservers() is used to notify the observers when the canvas is resized to increase the Drawing Area Size and refresh the canvas
	public void notifyCanvasObservers() {
		for (CanvasObserver observer: canvasObservers)
			observer.update();
	}

	/**
	 * adds a new Canvas Observer
	 * @param observer a canvas observer that needs to be added
	 */
	public void addCanvasObserver(CanvasObserver observer) {
		canvasObservers.add(observer);
	}

	/**
	 * removes an existing Canvas Observer
	 * @param observer a canvas observer that needs to be removed
	 */
	public void removeCanvasObserver(CanvasObserver observer) {
		canvasObservers.remove(observer);
	}

	/**
	 * used to notify the Tools Panel that the canvas is being resized to deselct all Tools
	 */
	// notifyObservers() is used to notify the Tools Panel that the canvas is being resized to deselct all Tools
	public void notifyObservers() {
		for (Observer observer: observers)
			observer.update3();
	}

	/**
	 * adds a new Observer observing the canvas when being resized
	 * @param observer an observer that needs to be added
	 */
	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	/**
	 * removes an existing Observer observing the canvas when being resized
	 * @param observer an observer that needs to be removed
	 */
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}
}