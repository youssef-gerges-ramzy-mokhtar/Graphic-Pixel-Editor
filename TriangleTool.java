import java.awt.*;

// TriangleTool is a ShapeTool and is mainly used to set the shortcut specific to the Triangle Tool and define the Graphical Properties of a Triangle
class TriangleTool extends ShapeTool {
	public TriangleTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, canvas, undo);
		shapeBtn.setText("Triangle");
		shapeBtn.addKeyBinding('t');
	}

	protected SpecificGraphic getSpecificGrahic(ShapeLayer shapeLayer, Point coords) {
		TriangleGraphics TriangleGraphics = new TriangleGraphics(shapeLayer.getCoords(coords));
		TriangleGraphics.setStrokeColor(strokeCol);
		TriangleGraphics.setDimension(layerWidth, layerHeight);

		return TriangleGraphics;
	}

	protected ShapeLayer createShapeLayer(Point layerPos) {
		TriangleLayer triangleLayer = new TriangleLayer(layerWidth, layerHeight, Color.white, layerPos); // Color will change in the future
		triangleLayer.setStrokeCol(strokeCol);
	    triangleLayer.setFillCol(strokeCol);
		

		return triangleLayer;
	}
}