import java.awt.*;

// CircleGraphics is used to store properties of a Circle and used to Draw a Circle using a Layer's Graphics2D Object
public class CircleGraphics implements SpecificGraphic {
	Point position;
	float stroke_sz;
	int width;
	int height;
	Color stroke_col;
	Color fillCol;

	public CircleGraphics(Point position) {
		this.position = position;
		this.fillCol = Color.black;
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

	public void setFillCol(Color col) {
		this.fillCol = col;
	}

	public void setDimension(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void draw(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR)); // This is used to set the pixels to transparent, will also look into this in the future
		g.fillRect(0, 0, width, height);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

		g.setColor(fillCol);
		g.fillOval(
			position.x,
			position.y,
			width,
			height
		);

		g.setStroke(new BasicStroke(stroke_sz));
		g.setColor(stroke_col);
		g.drawOval(
			position.x,
			position.y,
			width,
			height
		);

		g.dispose();
	}
}