import java.awt.*;

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