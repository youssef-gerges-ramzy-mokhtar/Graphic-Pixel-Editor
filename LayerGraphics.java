import java.awt.*;

// LayerGraphics is a general class used to represent all common properties between all Layer Graphics
abstract class LayerGraphics extends SpecificGraphic {
	protected int width;
	protected int height;
	protected Point position;

	public LayerGraphics(Point position) {
		this.position = position;
	}

	public void setPoints(Point position) {
		this.position = position;
	}

	public void setDimension(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public abstract void draw(Graphics2D g);
}