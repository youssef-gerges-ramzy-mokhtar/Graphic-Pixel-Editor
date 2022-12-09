import java.awt.*;

public class CircleGraphics implements SpecificGraphic {
	Point position;
	Graphics2D g;
	float stroke_sz;
	int len;
	Color stroke_col;

	public CircleGraphics(Point position, Graphics2D g) {
		this.position = position;
		this.g = g;
		stroke_sz = 2;
		len = 3;
	}

	public void setPoints(Point position) {
		this.position = position;
	}

	public void setGraphics(Graphics2D g) {
		this.g = g;
	}

	public void setStrokeSize(float sz) {
		this.stroke_sz = sz;
	}

	public void setColor(Color col) {
		this.stroke_col = col;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public void draw() {
		g.setStroke(new BasicStroke(stroke_sz));
		g.setColor(stroke_col);

		// Make the Circle appear in the center of the cursor

		g.drawOval(
			position.x - (len / 2),
			position.y - (len / 2),
			len,
			len
		);

		g.dispose();
	}

	public Graphics getGraphics() {
		return g;
	}
}