import java.awt.*;

class CircleLayer extends ShapeLayer {
	public CircleLayer(int width, int height, Color col) {super(width, height, col);}
	public CircleLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	public SpecificGraphic getSpecificGraphic(int width, int height) {
		CircleGraphics circleGraphics = new CircleGraphics(new Point(0, 0));
		circleGraphics.setDimension(width, height);
		circleGraphics.setStrokeColor(strokeCol);
		circleGraphics.setFillColor(strokeCol);

		return circleGraphics;
	}

	protected CircleLayer getShapeLayerCopy() {
		CircleLayer copy = new CircleLayer(layerWidth(), layerHeight(), Color.white);
		return copy;
	}
}