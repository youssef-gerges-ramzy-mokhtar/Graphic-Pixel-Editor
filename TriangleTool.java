import java.awt.*;

// TriangleTool is responsible for adding & handling Triangles to the cavnas
class TriangleTool extends ShapeTool {
	public TriangleTool(OurCanvas canvas, UndoTool undo) {
		super(canvas, undo);
		shapeBtn.setText("Triangle");
		shapeBtn.addKeyBinding('t');
	}

	protected SpecificGraphic getSpecificGrahic(ShapeLayer shapeLayer, Point coords) {
		TriangleGraphics TriangleGraphics = new TriangleGraphics(shapeLayer.getCoords(coords));
		TriangleGraphics.setColor(strokeCol);
		TriangleGraphics.setDimension(layerWidth, layerHeight);

		return TriangleGraphics;
	}

	protected ShapeLayer createShapeLayer(Point layerPos) {
		TriangleLayer triangleLayer = new TriangleLayer(layerWidth, layerHeight, Color.white, layerPos); // Color will change in the future
		triangleLayer.setStrokeCol(strokeCol);
		return triangleLayer;
	}
}