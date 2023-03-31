import java.awt.*;

// CircleLayer simply represents any Circle Layer on the Canvas
public class CircleLayer extends ShapeLayer {
	/**
	 * Creates a new CircleLayer which is used to represent a circle on the screen/canvas
	 * @param width defines the width of the circle layer
	 * @param height defines the height of the circle layer
	 * @param col defines the fill color of the circle
	 */
	public CircleLayer(int width, int height, Color col) {super(width, height, col);}
	
	/**
	 * Creates a new CircleLayer which is used to represent a circle on the screen/canvas at the specified position
	 * @param width defines the width of the circle layer
	 * @param height defines the height of the circle layer
	 * @param col defines the fill color of the circle
	 * @param layerPos defines the position of the circle layer on the screen/canvas
	 */
	public CircleLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	/**
	 * used to return a CircleGraphics Object to represent the drawing behavior of a circle
	 * @param width the width of the circle layer
	 * @param height the height of the circle layer
	 * @return a CircleGraphic Object which is a child of SpecificGraphic
	 */
	// getSpecificGraphic() is used to return a CircleGraphic that represents the Graphical Properties of a Circle
	public SpecificGraphic getSpecificGraphic(int width, int height) {
		CircleGraphics circleGraphics = new CircleGraphics(new Point(0, 0));
		circleGraphics.setDimension(width, height);
		circleGraphics.setStrokeColor(strokeCol);
		circleGraphics.setFillColor(strokeCol);

		return circleGraphics;
	}

	/**
	 * @return returns a new Circle Layer filled completely with transparent pixels
	 */
	protected CircleLayer getShapeLayerCopy() {
		CircleLayer copy = new CircleLayer(layerWidth(), layerHeight(), Constants.transparentColor);
		return copy;
	}

	/**
	 * the symbol is used by the project loader to identify the type of layer that is being loaded
	 * @return returns a string representing the symbol that is used to define a Circle Layer
	 */
	protected String getShapeSymbol() {
		return "c";
	}
}