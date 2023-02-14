import java.awt.*;

// CircleTool is responsible for adding & handling Circles to the cavnas
class CircleTool extends ShapeTool {
	public CircleTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, canvas, undo);
		shapeBtn.setText("Circle");
		shapeBtn.addKeyBinding('c');
	}

	protected SpecificGraphic getSpecificGrahic(ShapeLayer shapeLayer, Point coords) {
		CircleGraphics circleGraphics = new CircleGraphics(shapeLayer.getCoords(coords));
		circleGraphics.setStrokeColor(strokeCol);
		circleGraphics.setDimension(layerWidth, layerHeight);

		return circleGraphics;
	}

	protected ShapeLayer createShapeLayer(Point layerPos) {
		CircleLayer circleLayer = new CircleLayer(layerWidth, layerHeight, Color.white, layerPos); // Color will change in the future
		circleLayer.setStrokeCol(strokeCol);
		return circleLayer;
	}
}