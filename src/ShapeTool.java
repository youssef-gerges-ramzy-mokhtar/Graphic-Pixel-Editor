import java.util.*;
import java.awt.*;
import java.awt.event.*;

// ShapeTool is responsible for adding & handling any generic Shape to the cavnas
public abstract class ShapeTool extends ClickableTool implements Observer {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private Point pivot;
	private ShapeLayer shapeLayer;
	protected Color strokeCol;
	protected Color fillCol;
	protected Clickable shapeBtn;
	protected int layerWidth;
	protected int layerHeight;

	/**
	 * ShapeTool is responsible for adding & handling any generic Shape to the cavnas
	 * @param layerObserver a layerObserver is an object that observers changes that happens to the layers structure
	 * @param canvas is the current canvas that holds all the layers
	 * @param undo is the tool that manages how the undo and redo works
	 */
	public ShapeTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, undo);
		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.strokeCol = Color.black;
		this.fillCol = Color.white; // that is temp
		this.shapeLayer = null;

		addCanvasListener();
	}

	/**
	 * initTool initialize the properties of the Shape Tool
	 * - The Shape Tool Affects the Undo Tool
	 * - The Shape Tool Affects the Layers Panel
	 * @param undo is the tool that manages how the undo and redo works
	 */
	protected void initTool(UndoTool undo) {
		this.shapeBtn = new Clickable("Dummy Shape");

		addToolBtn(shapeBtn);
		setAsChangeMaker(undo); // this function call states that this tool will effect the undo tool
		setAsLayerChanger(); // this function call states that this tool will have an effect on the layers panel
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
				shapeLayer = createShapeLayer(LayerData.validTopLeftPoint(pivot, finalPoint));
				SpecificGraphic shapeGraphics = getSpecificGrahic(shapeLayer, LayerData.validTopLeftPoint(pivot, finalPoint));

				shapeLayer.updateGraphics(shapeGraphics);
				layersHandler.addLayer(shapeLayer);
				layersHandler.removeLayer(prevLayer);
				layersHandler.updateCanvas();
			}
		});
	}

	/**
	 * used by all a Specific Shapes to define their own Graphical Properties
	 */
	// getSpecificGrahic() is used by all a Specific Shape to define its own Graphical Properties
	protected abstract SpecificGraphic getSpecificGrahic(ShapeLayer shapeLayer, Point coords);

	/**
	 * creates a specific shape layer to represent a specific shape
	 */
	// Creates a Layer to store a Shape
	protected abstract ShapeLayer createShapeLayer(Point layerPos);

	// Observer Pattern //

	/**
	 * used to change the shape fill and stroke color based on the Color Chooser or the Eye Dropper
	 * @param col new shape fill & stroke color
	 */
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