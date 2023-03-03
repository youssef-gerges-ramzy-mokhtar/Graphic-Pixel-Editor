import java.awt.*;

// CircleTool is a ShapeTool and is mainly used to set the shortcut specific to the Circle Tool and define the Graphical Properties of a Circle
class CircleTool extends ShapeTool {
	public CircleTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, canvas, undo);
		shapeBtn.setText("Circle");
		shapeBtn.addKeyBinding('c'); // sets the shortcut for the circle tool
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
		circleLayer.setFillCol(strokeCol);

		return circleLayer;
	}
}