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

	public DrawingTool(OurCanvas canvas, UndoTool undo) {
		super(undo);

		this.canvas = canvas;		
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.dragPoint = new Point(0, 0);
		this.released = true;
		
		canvasListener();
	}

	protected void initTool(UndoTool undo) {
		drawingBtn = new Clickable("Default Drawing Tool");
		addToolBtn(drawingBtn);
		setAsChangeMaker(undo);
	}

	private void canvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				released = true;

				if (!drawingBtn.isActive()) return;
				recordChange();
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
			}
		});
	}

	// drawBrush() used to draw a line between 2 points in the current layer
	private void drawBrush(Point pos) {
		LayerData currentLayer = layersHandler.getSelectedLayer();
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
		brush.setPos(currentLayer.getX(pos.x), currentLayer.getY(pos.y));
		Point clickPoint = new Point(pos.x + 1, pos.y + 1);
		setBrushProperties();

		lineGraphic.setPoints(brush.getPos(), clickPoint);
		lineGraphic.setColor(brush.getCol());
		lineGraphic.setStrokeSize(brush.getThickness());
		currentLayer.updateGraphics(lineGraphic);
		layersHandler.updateCanvas();
	}

	// setBrushProperties() is an abstract method used by the all drawing tools to define the drawing tool own behavior
	protected abstract void setBrushProperties();

	public ArrayList<Clickable> getClickable() {
		ArrayList<Clickable> drawingToolToolBtn = new ArrayList<Clickable>();
		drawingToolToolBtn.add(drawingBtn);
		return drawingToolToolBtn;
	}

	// Observer Pattern 
	// update() is used to update the brush Thickness when the Pen Options Slider is Changed 
	public void update(int thickness) {
		brush.setThickness(thickness);
	}
	public void update2(Color col) {}
	public void update3() {};
}