import java.awt.*;

// TriangleGraphics is used to store properties of a Triangle and used to Draw a Triangle using a Layer's Graphics2D Object
public class TriangleGraphics implements SpecificGraphic {
	Point position;
	float stroke_sz;
	int width;
	int height;
	Color stroke_col;

	public TriangleGraphics(Point position) {
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
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR)); // This is used to set the pixels to transparent, will also look into this in the future
		g.fillRect(0, 0, width, height);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		g.setStroke(new BasicStroke(stroke_sz));
		g.setColor(stroke_col);

		int x = position.x;
		int y = position.y;
        g.drawPolygon(
        	new int[] {x + (width/2), x + width, x},
        	new int[] {0, height, height},
			3
		);
		g.dispose();
	}
}