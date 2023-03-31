import java.awt.*;

// TriangleLayer simply represents any Triangle Layer on the Canvas
class TriangleLayer extends ShapeLayer {
	/**
	 * Creates a new TriangleLayer which is used to represent a triangle on the screen/canvas
	 * @param width defines the width of the triangle layer
	 * @param height defines the height of the triangle layer
	 * @param col defines the fill color of the triangle
	 */
	public TriangleLayer(int width, int height, Color col) {super(width, height, col);}
	
	/**
	 * Creates a new TriangleLayer which is used to represent a triangle on the screen/canvas
	 * @param width defines the width of the triangle layer
	 * @param height defines the height of the triangle layer
	 * @param col defines the fill color of the triangle
	 * @param layerPos defines the position of the triangle layer on the screen/canvas
	 */
	public TriangleLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	/**
	 * used to return a TriangleGraphics Object to represent the drawing behavior of a triangle
	 * @param width the width of the triangle layer
	 * @param height the height of the triangle layer
	 * @return a TriangleGraphics Object
	 */
	// getSpecificGraphic() is used to return a TriangleGraphic that represents the Graphical Properties of a Triangle
	public SpecificGraphic getSpecificGraphic(int width, int height) {
		TriangleGraphics triangleGraphics = new TriangleGraphics(new Point(0, 0));
		triangleGraphics.setDimension(width, height);
		triangleGraphics.setStrokeColor(strokeCol);
		triangleGraphics.setFillColor(fillCol);
		
	
		return triangleGraphics;
	}

	/**
	 * @return returns a new Triangle Layer filled completely with transparent pixels
	 */
	protected TriangleLayer getShapeLayerCopy() {
		TriangleLayer copy = new TriangleLayer(layerWidth(), layerHeight(), Constants.transparentColor);
		return copy;
	}

	/**
	 * the symbol is used by the project loader to identify the type of layer that is being loaded
	 * @return returns a string representing the symbol that is used to define a Triangle Layer
	 */
	protected String getShapeSymbol() {
		return "t";
	}
}