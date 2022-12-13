import java.awt.*;

public class CircleGraphics implements SpecificGraphic {
	Point position;
	float stroke_sz;
	int len;
	Color stroke_col;

	public CircleGraphics(Point position) {
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
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		g.fillRect(0, 0, len, len);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

		g.setStroke(new BasicStroke(stroke_sz));
		g.setColor(stroke_col);

		// In the future will try to Make the Circle appear in the center of the cursor
		g.drawOval(
			position.x,
			position.y,
			len,
			len
		);

		g.dispose();
	}
}