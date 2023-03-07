import java.util.*;
import java.awt.*;
import java.awt.event.*;

// Drawing Tool is an abstract class used to represent any Tool that draws into the Canvas
abstract class DrawingTool extends ClickableTool implements Observer {
	protected Clickable drawingBtn;
	protected Brush brush;
	protected LineGraphics lineGraphic;
	protected OurCanvas canvas;
	private LayersHandler layersHandler;
	private Point dragPoint;
	private boolean released;

	/**
	 * Drawing Tool is an abstract class used to represent any Tool that draws into the Canvas
	 * @param layerObserver a layerObserver is an object that observers changes that happens to the layers structure
	 * @param canvas is the current canvas that holds all the layers
	 * @param undo is the tool that manages how the undo and redo works
	 */
	public DrawingTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, undo);

		this.canvas = canvas;		
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.dragPoint = new Point(0, 0);
		this.released = true;
		
		canvasListener();
	}

	/**
	 * initTool initialize the properties of the Drawing Tool
	 * - The Drawing Tool Affects the Undo Tool
	 * - The Drawing Tool Affects the Layers Panel
	 * - The Drawing Tool Rasterizes Shape Layers
	 * @param undo is the tool that manages how the undo and redo works
	 */
	protected void initTool(UndoTool undo) {
		drawingBtn = new Clickable("Default Drawing Tool");
		
		addToolBtn(drawingBtn);
		setAsChangeMaker(undo);
		setAsShapeRasterizer();
		setAsLayerChanger();

		
	}

	private void canvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				released = true;

				if (!drawingBtn.isActive()) return;
				recordChange();
				updateLayerObserver();
			}
		});

		canvas.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (!drawingBtn.isActive()) return;
				drawBrush(new Point(e.getX(), e.getY()));
			}
		});

		canvas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!drawingBtn.isActive()) return;
				drawPointBrush(new Point(e.getX(), e.getY()));
				updateLayerObserver();
			}
		});
	}

	// drawBrush() used to draw a line between 2 points in the current layer
	private void drawBrush(Point pos) {
		LayerData currentLayer = layersHandler.getSelectedLayer();
		if (currentLayer instanceof ShapeLayer) currentLayer = rasterizeLayer(currentLayer, layersHandler);
		if (currentLayer == null) return;

		if (released) {
			brush.setPos(currentLayer.getX(pos.x), currentLayer.getY(pos.y));
			released = false;
		} else
			brush.setPos(dragPoint.x, dragPoint.y);

		// Understand what happens to the startPos when a layer moves outside the canvas
		dragPoint.setLocation(currentLayer.getX(pos.x), currentLayer.getY(pos.y)); // coordinates might be negative
		setBrushProperties();

		lineGraphic.setPoints(brush.getPos(), dragPoint);
		lineGraphic.setColor(brush.getCol());
		lineGraphic.setStrokeSize(brush.getThickness());
		currentLayer.updateGraphics(lineGraphic);
		layersHandler.updateCanvas();
	}

	// drawPointBrush() used to draw a circle when a user clicks on the canvas
	private void drawPointBrush(Point pos) {
		LayerData currentLayer = layersHandler.getSelectedLayer();
		if (currentLayer instanceof ShapeLayer) currentLayer = rasterizeLayer(currentLayer, layersHandler);
		if (currentLayer == null) return;

		brush.setPos(currentLayer.getX(pos.x), currentLayer.getY(pos.y));
		Point clickPoint = new Point(pos.x + 1, pos.y + 1);
		setBrushProperties();

		lineGraphic.setPoints(brush.getPos(), clickPoint);
		lineGraphic.setColor(brush.getCol());
		lineGraphic.setStrokeSize(brush.getThickness());
		currentLayer.updateGraphics(lineGraphic);
		layersHandler.updateCanvas();
	}

	/**
	 * an abstract method used by the all drawing tools to define the drawing tool own behavior
	 */ 
	// setBrushProperties() is an abstract method used by the all drawing tools to define the drawing tool own behavior
	protected abstract void setBrushProperties();

	// Observer Pattern 
	// update() is used to update the brush Thickness when the Pen Options Slider is Changed 
	/**
	 * used to update the brush Thickness when the Pen Options Slider is Changed
	 * @param thickness the new brush thickness 
	 */ 
	public void update(int thickness) {
		brush.setThickness(thickness);
	}
	public void update2(Color col) {}
	public void update3() {};
}