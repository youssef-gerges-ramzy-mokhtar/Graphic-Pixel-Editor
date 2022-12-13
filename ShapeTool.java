import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

abstract class ShapeTool implements Observer, ClickableContainer {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	protected Color strokeCol;
	protected Color fillCol;
	protected Clickable shapeBtn;

	protected int layerWidth;
	protected int layerHeight;

	public ShapeTool(OurCanvas canvas) {
		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.strokeCol = Color.black;
		this.fillCol = Color.white; // that is temp
		this.shapeBtn = new Clickable("Dummy Shape");

		addCanvasListener();
	}

	private void addCanvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (!shapeBtn.isActive()) return;

				Point pos = new Point(e.getX(), e.getY());
				LayerData shapeLayer = createShapeLayer(pos);
				SpecificGraphic shapeGraphics = getSpecificGrahic(shapeLayer, pos);
			
				shapeLayer.updateGraphics(shapeGraphics);
				layersHandler.addLayer(shapeLayer);
				layersHandler.updateCanvas();
			}
		});
	}

	protected abstract SpecificGraphic getSpecificGrahic(LayerData shapeLayer, Point coords);

	private LayerData createShapeLayer(Point layerPos) {
		LayerData shapeLayer = new LayerData(layerWidth, layerHeight, Color.white, layerPos); // Color will change in the future
		return shapeLayer;
	}

	public Clickable getClickable() {
		return shapeBtn;
	}

	// Observer Pattern //
	public void update(int val) {
		layerWidth = val;
		layerHeight = val;
	}
	
	public void update2(Color col) {
		this.fillCol = col;
	}

	public void update3() {}

	/*
		// We should have a Separate Shape Controller GUI to the User
		We need to have controls to let the user control the following:
			- stroke size of the shape
			- stroke color of the shape
			- fill color of the shape
			- shape dimensions
	*/
}