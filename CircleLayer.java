import java.awt.*;

// CircleLayer simply represents any Circle Layer on the Canvas
class CircleLayer extends ShapeLayer {
	public CircleLayer(int width, int height, Color col) {super(width, height, col);}
	public CircleLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	// getSpecificGraphic() is used to return a CircleGraphic that represents the Graphical Properties of a Circle
	public SpecificGraphic getSpecificGraphic(int width, int height) {
		CircleGraphics circleGraphics = new CircleGraphics(new Point(0, 0));
		circleGraphics.setDimension(width, height);
		circleGraphics.setStrokeColor(strokeCol);
		circleGraphics.setFillColor(strokeCol);

		return circleGraphics;
	}

	protected CircleLayer getShapeLayerCopy() {
		CircleLayer copy = new CircleLayer(layerWidth(), layerHeight(), Constants.transparentColor);
		return copy;
	}
}