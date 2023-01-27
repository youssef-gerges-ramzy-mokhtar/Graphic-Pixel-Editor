import java.awt.*;

// TriangleGraphics is used to store properties of a Triangle and used to Draw a Triangle using a Layer's Graphics2D Object
public class TriangleGraphics implements SpecificGraphic {
	Point position;
	float stroke_sz;
	int len;
	Color stroke_col;

	public TriangleGraphics(Point position) {
		this.position = position;
		stroke_sz = 2;
		len = 3;
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

	public void setLen(int len) {
		this.len = len;
	}

	public void draw(Graphics2D g) {
		g.setStroke(new BasicStroke(stroke_sz));
		g.setColor(stroke_col);

		// In the future will try to Make the Triangle appear in the center of the cursor
        g.drawPolygon(
			new int[] {position.x + (len/2), position.x + len, position.x},
			new int[] {position.y, position.y + len, position.y + len},
			3
		);

		g.dispose();
	}
}