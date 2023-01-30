import java.awt.*;

// CircleGraphics is used to store properties of a Circle and used to Draw a Circle using a Layer's Graphics2D Object
public class CircleGraphics implements SpecificGraphic {
	Point position;
	float stroke_sz;
	int width;
	int height;
	Color stroke_col;

	public CircleGraphics(Point position) {
		this.position = position;
		stroke_sz = 2;
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

	public void setDimension(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void draw(Graphics2D g) {
		g.fillRect(0, 0, width, height);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		g.setStroke(new BasicStroke(stroke_sz));
		g.setColor(stroke_col);

		// In the future will try to Make the Circle appear in the center of the cursor
		g.drawOval(
			position.x,
			position.y,
			width,
			height
		);
		g.dispose();
	}
}