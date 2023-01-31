import java.awt.*;

// RectangleTool is responsible for adding & handling Rectangles to the cavnas
class RectangleTool extends ShapeTool {
	public RectangleTool(OurCanvas canvas, UndoTool undo) {
		super(canvas, undo);
		shapeBtn.setText("Rectangle");
	}

	protected SpecificGraphic getSpecificGrahic(ShapeLayer shapeLayer, Point coords) {
		RectangleGraphics rectangleGraphics = new RectangleGraphics(shapeLayer.getCoords(coords));
		rectangleGraphics.setColor(strokeCol);
		rectangleGraphics.setDimensions(layerWidth, layerHeight);

		return rectangleGraphics;
	}

	protected ShapeLayer createShapeLayer(Point layerPos) {
		RectangleLayer rectangleLayer = new RectangleLayer(layerWidth, layerHeight, Color.white, layerPos); // Color will change in the future
		rectangleLayer.setStrokeCol(strokeCol);
		return rectangleLayer;
	}
}