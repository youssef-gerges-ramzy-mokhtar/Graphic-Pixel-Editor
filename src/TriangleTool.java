import java.awt.*;

// TriangleTool is a ShapeTool and is mainly used to set the shortcut specific to the Triangle Tool and define the Graphical Properties of a Triangle
public class TriangleTool extends ShapeTool {
	/**
	 * TriangleTool is used to represent a user-interface to make the user able to add Triangle Layers to the Screen/Canvas
	 * @param layerObserver a layerObserver is an object that observers changes that happens to the layers structure
	 * @param canvas is the current canvas that holds all the layers
	 * @param undo is the tool that manages how the undo and redo works
	 */
	public TriangleTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, canvas, undo);
		shapeBtn.setText("Triangle");
		shapeBtn.addKeyBinding('t');
	}

	/**
	 * used to return a TriangleGraphics Object to represent the drawing behavior of a triangle
	 * @param shapeLayer is the triangle layer that is currently being added by the triangle tool
	 * @param coords is the position of the triangle layer on the screen/canvas
	 * @return a TriangleGraphics Object
	 */
	protected SpecificGraphic getSpecificGrahic(ShapeLayer shapeLayer, Point coords) {
		TriangleGraphics TriangleGraphics = new TriangleGraphics(shapeLayer.getCoords(coords));
		TriangleGraphics.setStrokeColor(strokeCol);
		TriangleGraphics.setDimension(layerWidth, layerHeight);

		return TriangleGraphics;
	}

	/**
	 * used to create a new Triangle Layer at the specified position, and set the stroke and fill color of the created triangle layer
	 * @param layerPos the position in which the triangle layer will be located in
	 * @return a new Triangle Layer
	 */
	protected ShapeLayer createShapeLayer(Point layerPos) {
		TriangleLayer triangleLayer = new TriangleLayer(layerWidth, layerHeight, Color.white, layerPos); // Color will change in the future
		triangleLayer.setStrokeCol(strokeCol);
	    triangleLayer.setFillCol(strokeCol);
		
		return triangleLayer;
	}
}