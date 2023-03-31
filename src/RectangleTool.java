import java.awt.*;

// RectangleTool is a ShapeTool and is mainly used to set the shortcut specific to the Rectangle Tool and define the Graphical Properties of a Rectangle
class RectangleTool extends ShapeTool {
	/**
	 * RectangleTool is used to represent a user-interface to make the user able to add rectangle Layers to the Screen/Canvas
	 * @param layerObserver a layerObserver is an object that observers changes that happens to the layers structure
	 * @param canvas is the current canvas that holds all the layers
	 * @param undo is the tool that manages how the undo and redo works
	 */
	public RectangleTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, canvas, undo);
		shapeBtn.setText("Rectangle");
		shapeBtn.addKeyBinding('r');
	}

	/**
	 * used to return a RectangleGraphics Object to represent the drawing behavior of a rectangle
	 * @param shapeLayer is the rectangle layer that is currently being added by the rectangle tool
	 * @param coords is the position of the rectangle layer on the screen/canvas
	 * @return a RectangleGraphics Object
	 */
	protected SpecificGraphic getSpecificGrahic(ShapeLayer shapeLayer, Point coords) {
		RectangleGraphics rectangleGraphics = new RectangleGraphics(shapeLayer.getCoords(coords));
		rectangleGraphics.setStrokeColor(strokeCol);
		rectangleGraphics.setDimension(layerWidth, layerHeight);

		return rectangleGraphics;
	}

	/**
	 * used to create a new rectangle Layer at the specified position, and set the stroke and fill color of the created rectangle layer
	 * @param layerPos the position in which the rectangle layer will be located in
	 * @return a new Rectangle Layer
	 */
	protected ShapeLayer createShapeLayer(Point layerPos) {
		RectangleLayer rectangleLayer = new RectangleLayer(layerWidth, layerHeight, Color.white, layerPos); // Color will change in the future
		rectangleLayer.setStrokeCol(strokeCol);
	    rectangleLayer.setFillCol(strokeCol);

		return rectangleLayer;
	}
}