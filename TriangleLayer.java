import java.awt.*;

// TriangleLayer simply represents any Triangle Layer on the Canvas
class TriangleLayer extends ShapeLayer {
	public TriangleLayer(int width, int height, Color col) {super(width, height, col);}
	public TriangleLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	// getSpecificGraphic() is used to return a TriangleGraphic that represents the Graphical Properties of a Triangle
	public SpecificGraphic getSpecificGraphic(int width, int height) {
		TriangleGraphics triangleGraphics = new TriangleGraphics(new Point(0, 0));
		triangleGraphics.setDimension(width, height);
		triangleGraphics.setStrokeColor(strokeCol);
		triangleGraphics.setFillColor(fillCol);
		
	
		return triangleGraphics;
	}

	protected TriangleLayer getShapeLayerCopy() {
		TriangleLayer copy = new TriangleLayer(layerWidth(), layerHeight(), new Color(0,0,0,0));
		return copy;
	}
}