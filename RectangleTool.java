import java.awt.*;

// RectangleTool is a ShapeTool and is mainly used to set the shortcut specific to the Rectangle Tool and define the Graphical Properties of a Rectangle
class RectangleTool extends ShapeTool {
	public RectangleTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, canvas, undo);
		shapeBtn.setText("Rectangle");
		shapeBtn.addKeyBinding('r');
	}

	protected SpecificGraphic getSpecificGrahic(ShapeLayer shapeLayer, Point coords) {
		RectangleGraphics rectangleGraphics = new RectangleGraphics(shapeLayer.getCoords(coords));
		rectangleGraphics.setStrokeColor(strokeCol);
		rectangleGraphics.setDimension(layerWidth, layerHeight);

		return rectangleGraphics;
	}

	protected ShapeLayer createShapeLayer(Point layerPos) {
		RectangleLayer rectangleLayer = new RectangleLayer(layerWidth, layerHeight, Color.white, layerPos); // Color will change in the future
		rectangleLayer.setStrokeCol(strokeCol);
		return rectangleLayer;
	}
}