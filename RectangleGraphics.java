import java.awt.*;

// RectangleGraphics is used to store properties of a Rectnalge and used to Draw a Rectnalge using a Layer's Graphics2D Object
public class RectangleGraphics implements SpecificGraphic {
	Point position;
	float stroke_sz;
	int width;
	int height;
	Color stroke_col;

	public RectangleGraphics(Point position) {
		this.position = position;
		stroke_sz = 2;
		// len = 3;
	}

	public void setPoints(Point position) {
		this.position = position;
	}

	public void setStrokeSize(float sz) {
		this.stroke_sz = sz;
	}

	public void setColor(Color col) {
		this.stroke_col = col;
	}

	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void draw(Graphics2D g) {
		g.setStroke(new BasicStroke(stroke_sz));
		g.setColor(stroke_col);

		// In the future will try to Make the Rectangle appear in the center of the cursor
		g.drawRect(
			position.x,
			position.y,
			width,
			height
		);
		g.dispose();
	}
}