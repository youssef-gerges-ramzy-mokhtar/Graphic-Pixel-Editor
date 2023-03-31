import java.awt.*;

// CircleTool is a ShapeTool and is mainly used to set the shortcut specific to the Circle Tool and define the Graphical Properties of a Circle
public class CircleTool extends ShapeTool {
	/**
	 * CircleTool is used to represent a user-interface to make the user able to add Circle Layers to the Screen/Canvas
	 * @param layerObserver a layerObserver is an object that observers changes that happens to the layers structure
	 * @param canvas is the current canvas that holds all the layers
	 * @param undo is the tool that manages how the undo and redo works
	 */
	public CircleTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, canvas, undo);
		shapeBtn.setText("Circle");
		shapeBtn.addKeyBinding('c'); // sets the shortcut for the circle tool
	}

	/**
	 * used to return a CircleGraphics Object to represent the drawing behavior of a circle
	 * @param shapeLayer is the circle layer that is currently being added by the circle tool
	 * @param coords is the position of the circle layer on the screen/canvas
	 * @return a CircleGraphic Object which is a child of SpecificGraphic
	 */
	protected SpecificGraphic getSpecificGrahic(ShapeLayer shapeLayer, Point coords) {
		CircleGraphics circleGraphics = new CircleGraphics(shapeLayer.getCoords(coords));
		circleGraphics.setStrokeColor(strokeCol);
		circleGraphics.setDimension(layerWidth, layerHeight);

		return circleGraphics;
	}

	/**
	 * used to create a new Circle Layer at the specified position, and set the stroke and fill color of the created circle layer
	 * @param layerPos the position in which the circle layer will be located in
	 * @return a CircleLayer which is a Child of ShapeLayer
	 */
	protected ShapeLayer createShapeLayer(Point layerPos) {
		CircleLayer circleLayer = new CircleLayer(layerWidth, layerHeight, Color.white, layerPos); // Color will change in the future
		circleLayer.setStrokeCol(strokeCol);
		circleLayer.setFillCol(strokeCol);

		return circleLayer;
	}
}