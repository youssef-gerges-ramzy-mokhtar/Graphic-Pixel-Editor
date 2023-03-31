import java.awt.*;

// RectangleLayer simply represents any Rectangle Layer on the Canvas
class RectangleLayer extends ShapeLayer {
	/**
	 * Creates a new RectangleLayer which is used to represent a rectangle on the screen/canvas
	 * @param width defines the width of the rectangle layer
	 * @param height defines the height of the rectangle layer
	 * @param col defines the fill color of the rectangle
	 */
	public RectangleLayer(int width, int height, Color col) {super(width, height, col);}

	/**
	 * Creates a new RectangleLayer which is used to represent a rectangle on the screen/canvas
	 * @param width defines the width of the rectangle layer
	 * @param height defines the height of the rectangle layer
	 * @param col defines the fill color of the rectangle
	 * @param layerPos defines the position of the rectangle layer on the screen/canvas
	 */
	public RectangleLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	/**
	 * used to return a RectangleGraphics Object to represent the drawing behavior of a rectangle
	 * @param width the width of the rectangle layer
	 * @param height the height of the rectangle layer
	 * @return a RectangleGraphics Object
	 */
	// getSpecificGraphic() is used to return a RectangleGraphic that represents the Graphical Properties of a Rectangle
	public SpecificGraphic getSpecificGraphic(int width, int height) {
		RectangleGraphics rectangleGraphics = new RectangleGraphics(new Point(0, 0));
		rectangleGraphics.setDimension(width, height);
		rectangleGraphics.setStrokeColor(strokeCol);
		rectangleGraphics.setFillColor(fillCol);
	
		return rectangleGraphics;
	}

	/**
	 * @return returns a new Rectangle Layer filled completely with transparent pixels
	 */
	protected RectangleLayer getShapeLayerCopy() {
		RectangleLayer copy = new RectangleLayer(layerWidth(), layerHeight(), Constants.transparentColor);
		return copy;
	}

	/**
	 * the symbol is used by the project loader to identify the type of layer that is being loaded
	 * @return returns a string representing the symbol that is used to define a Rectangle Layer
	 */
	protected String getShapeSymbol() {
		return "r";
	}
}