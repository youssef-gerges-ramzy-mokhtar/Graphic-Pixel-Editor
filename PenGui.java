// import java.awt.*;
// import javax.swing.*;
// import java.awt.event.*;
// import javax.swing.*;
// import java.util.*;

// class PenGui extends JPanel implements Observer {
// 	//private ArrayList<Observer> observers = new ArrayList<Observer>();
// 	private Brush pen;
// 	private Brush eraser;
	
// 	// private Point dragPoint;
// 	// private OurCanvas canvas;
// 	// private DrawLineGraphics lineGraphic;
// 	// private JButton penBtn;
// 	// private JButton eraserBtn;

// 	// private boolean released;
// 	// private boolean eraserSelected;
// 	// private boolean penSelected;
// 	// private int currentSz = 1;
// 	// private Color currentCol;

// 	// private LayersHandler layerHandler;

// 	public PenGui(OurCanvas canvas) {
// 		// this.canvas = canvas;
// 		// this.layerHandler = layerHandler;
// 		// currentCol = Color.black;

// 		// pen = new Pen();
// 		// eraser = new Pen();

// 		// dragPoint = new Point(0, 0);
// 		// lineGraphic = new DrawLineGraphics(pen.getThickness(), pen.getCol());
// 		// penBtn = new JButton("Pen");
// 		// eraserBtn = new JButton("Eraser");
// 		// released = true;
// 		// eraserSelected = false;
// 		// penSelected = false;

// 		// canvasListener();
// 		// penBtnListener();
// 		// eraserBtnListener();
// 	}

// 	// private void canvasListener() {
// 	// 	canvas.addMouseListener(new MouseAdapter() {
// 	// 		public void mouseReleased(MouseEvent e) {
// 	// 			released = true;
// 	// 		}
// 	// 	});

// 	// 	canvas.addMouseMotionListener(new MouseAdapter() {
// 	// 		public void mouseDragged(MouseEvent e) {
// 	// 			if (!penSelected && !eraserSelected) return;
// 	// 			drawBrush(pen, e);
// 	// 		}
// 	// 	});

// 	// 	canvas.addMouseListener(new MouseAdapter() {
// 	// 		public void mouseClicked(MouseEvent e) {
// 	// 			if (!penSelected && !eraserSelected) return;
// 	// 			drawPointBrush(pen, e);
// 	// 		}
// 	// 	});
// 	// }

// 	// private void drawBrush(Brush brush, MouseEvent e) {
// 	// 	LayerData currentLayer = layerHandler.getSelectedLayer();

// 	// 	if (released) {
// 	// 		brush.setPos(currentLayer.getX(e.getX()), currentLayer.getY(e.getY()));
// 	// 		released = false;
// 	// 	} else
// 	// 		brush.setPos(dragPoint.x, dragPoint.y);

// 	// 	// Understand what happens to the startPos when a layer moves outside the canvas
// 	// 	dragPoint.setLocation(currentLayer.getX(e.getX()), currentLayer.getY(e.getY())); // coordinates might be negative
// 	// 	// brush.setThickness(currentSz);

// 	// 	if (penSelected) brush.setColor(currentCol);
// 	// 	if (eraserSelected) brush.setColor(canvas.getCanvasColor());

// 	// 	lineGraphic.setPoints(brush.getPos(), dragPoint);
// 	// 	lineGraphic.setColor(brush.getCol());
// 	// 	lineGraphic.setStrokeSize(brush.getThickness());
	
// 	// 	currentLayer.updateGraphics(lineGraphic);
// 	// 	layerHandler.updateCanvas();
// 	// }

// 	private void drawPointBrush(Brush brush, MouseEvent e) {
// 		LayerData currentLayer = layerHandler.getSelectedLayer();

// 		brush.setPos(currentLayer.getX(e.getX()), currentLayer.getY(e.getY()));
// 		Point dragPoint = new Point(e.getX() + 1, e.getY() + 1);
		
// 		brush.setThickness(currentSz);
// 		if (penSelected) brush.setColor(currentCol);
// 		if (eraserSelected) brush.setColor(canvas.getCanvasColor());

// 		lineGraphic.setPoints(brush.getPos(), dragPoint);
// 		lineGraphic.setColor(brush.getCol());
// 		lineGraphic.setStrokeSize(brush.getThickness());
	
// 		currentLayer.updateGraphics(lineGraphic);
// 		layerHandler.updateCanvas();
// 	}

// 	// Observer Pattern //
// 	// public void notifyObservers() {
// 	// 	for (Observer observer : observers)
// 	// 		observer.update3();
// 	// }

// 	// public void addObserver(Observer observer) {
// 	// 	observers.add(observer);
// 	// }

// 	// public void removeObserver(Observer observer) {
// 	// 	observers.remove(observer);
// 	// }
// }