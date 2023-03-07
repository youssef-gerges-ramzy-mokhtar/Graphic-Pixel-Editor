import java.awt.*;

// LayerGraphics is a general class used to represent all common properties between all Layer Graphics
abstract class LayerGraphics extends SpecificGraphic {
	protected int width;
	protected int height;
	protected Point position;

	/**
	 * LayerGraphics is a general class used to represent all common properties between all Layer Graphics
	 * @param position the layer position on the Screen/Canvas
	 */
	public LayerGraphics(Point position) {
		this.position = position;
	}

	/**
	 * updates the layer position
	 * @param position the new layer position on the Screen/Canvas
	 */
	public void setPoints(Point position) {
		this.position = position;
	}

	/**
	 * sets the dimensions of the layer
	 * @param width the layer width
	 * @param height the layer height
	 */
	public void setDimension(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * draw is an abstract method that defines how a layer is drawn into the Screen/Canvas
	 * @param Graphics2D is used to render the layer draw behaviour into the Graphics2D object
	 */
	public abstract void draw(Graphics2D g);
}