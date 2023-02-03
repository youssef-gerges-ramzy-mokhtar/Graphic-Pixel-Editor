import java.util.*;
import java.awt.*;
import java.awt.event.*;

// ShapeTool is responsible for adding & handling any generic Shape to the cavnas
abstract class ShapeTool extends ClickableTool implements Observer {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private Point pivot;
	private ShapeLayer shapeLayer;
	protected Color strokeCol;
	protected Color fillCol;
	protected Clickable shapeBtn;
	protected int layerWidth;
	protected int layerHeight;

	public ShapeTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, undo);
		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.strokeCol = Color.black;
		this.fillCol = Color.white; // that is temp
		this.shapeLayer = null;

		addCanvasListener();
	}

	protected void initTool(UndoTool undo) {
		this.shapeBtn = new Clickable("Dummy Shape");

		addToolBtn(shapeBtn);
		setAsChangeMaker(undo);
		setAsLayerChanger();
	}

	// addCanvasListener() is used to attach an Event Listner to the canvas, and adds a shape to the canvas based on the user click coordinates
	// Also each layer is created into its own layer and added to the layers handler
	private void addCanvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!shapeBtn.isActive()) return;
				pivot = new Point(e.getX(), e.getY());
			}

			public void mouseReleased(MouseEvent e){
				if (!shapeBtn.isActive()) return;
				shapeLayer = null;
				recordChange();
				updateLayerObserver();
			}
		});

		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (!shapeBtn.isActive()) return;

				Point finalPoint = new Point(e.getX(), e.getY());
				layerWidth = Math.abs(finalPoint.x - pivot.x);
				layerHeight = Math.abs(finalPoint.y - pivot.y);

				if (layerWidth == 0 || layerHeight == 0) return;

				ShapeLayer prevLayer = shapeLayer;
				shapeLayer = createShapeLayer(validPoint(pivot, finalPoint));
				SpecificGraphic shapeGraphics = getSpecificGrahic(shapeLayer, validPoint(pivot, finalPoint));

				shapeLayer.updateGraphics(shapeGraphics);
				layersHandler.addLayer(shapeLayer);
				layersHandler.removeLayer(prevLayer);
				layersHandler.updateCanvas();
			}
		});
	}

	private Point validPoint(Point p1, Point p2) {
		int x1 = p1.x;
		int y1 = p1.y;
		int x2 = p2.x;
		int y2 = p2.y;
		if (y2 > y1 && x2 > x1){
			return p1;
		}
		if (y2 < y1 && x2 > x1){
			return new Point(x1, y2);
		}
		if(x2 < x1 && y2 < y1){
			return p2;
		}
		if(x2 < x1 && y2 > y1){
		return new Point(x2, y1);
		}
		return null;
	}

	// getSpecificGrahic() is used by all a Specific Shape to define its own Graphical Properties
	protected abstract SpecificGraphic getSpecificGrahic(ShapeLayer shapeLayer, Point coords);

	// Creates a Layer to store a Shape
	protected abstract ShapeLayer createShapeLayer(Point layerPos);

	public ArrayList<Clickable> getClickable() {
		ArrayList<Clickable> shapeToolBtn = new ArrayList<Clickable>();
		shapeToolBtn.add(shapeBtn);
		return shapeToolBtn;
	}

	// Observer Pattern //
	// update2(Color col) is used to change the shape stroke color based on the Color Chooser or the Eye Dropper
	public void update2(Color col) {
		this.fillCol = col;
		this.strokeCol = col;
	}

	public void update(int val) {}
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