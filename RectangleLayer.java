import java.awt.*;

class RectangleLayer extends ShapeLayer {
	public RectangleLayer(int width, int height, Color col) {super(width, height, col);}
	public RectangleLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	public SpecificGraphic getSpecificGraphic(int width, int height) {
		RectangleGraphics rectangleGraphics = new RectangleGraphics(new Point(0, 0));
		rectangleGraphics.setDimension(width, height);
		rectangleGraphics.setStrokeColor(strokeCol);
		rectangleGraphics.setFillColor(fillCol);
	
		return rectangleGraphics;
	}

	protected RectangleLayer getShapeLayerCopy() {
		RectangleLayer copy = new RectangleLayer(layerWidth(), layerHeight(), Color.white);
		return copy;
	}
}