import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

abstract class DrawingTool implements ClickableContainer, Observer {
	protected Clickable drawingBtn;
	protected Brush brush;
	protected DrawLineGraphics lineGraphic;

	protected OurCanvas canvas;
	private LayersHandler layersHandler;
	private Point dragPoint;
	private boolean released;

	public DrawingTool(OurCanvas canvas) {
		this.canvas = canvas;		
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.dragPoint = new Point(0, 0);
		this.released = true;

		canvasListener();
	}

	private void canvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				released = true;
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
				// drawPointBrush(new Point(e.getX(), e.getY()));
			}
		});
	}

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

	protected abstract void setBrushProperties();

	public Clickable getClickable() {
		return drawingBtn;
	}

	// Observer Pattern 
	public void update(int thickness) {
		brush.setThickness(thickness);
	}
	public void update2(Color col) {}
	public void update3() {};
}