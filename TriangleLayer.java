import java.awt.image.*;
import java.awt.*;

class TriangleLayer extends ShapeLayer {
	public TriangleLayer(int width, int height, Color col) {super(width, height, col);}
	public TriangleLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	public SpecificGraphic getSpecificGraphic(int width, int height) {
		TriangleGraphics triangleGraphics = new TriangleGraphics(new Point(0, 0));
		triangleGraphics.setDimension(width, height);
		triangleGraphics.setColor(strokeCol);
	
		return triangleGraphics;
	}
}