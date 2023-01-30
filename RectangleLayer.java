import java.awt.image.*;
import java.awt.*;

class RectangleLayer extends ShapeLayer {
	public RectangleLayer(int width, int height, Color col) {super(width, height, col);}
	public RectangleLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	public SpecificGraphic getSpecificGraphic(int width, int height) {
		RectangleGraphics rectangleGraphics = new RectangleGraphics(new Point(0, 0));
		rectangleGraphics.setDimensions(width, height);
		rectangleGraphics.setColor(strokeCol);
	
		return rectangleGraphics;
	}
}