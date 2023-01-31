import java.awt.*;

// RectangleGraphics is used to store properties of a Rectnalge and used to Draw a Rectnalge using a Layer's Graphics2D Object
public class RectangleGraphics implements SpecificGraphic {
	Point position;
	float stroke_sz;
	int width;
	int height;
	Color stroke_col;
	Color fillCol;

	public RectangleGraphics(Point position) {
		this.position = position;
		this.fillCol = Color.black; // this is temporary until we create a shape Control Graphical User Interface for the use to set the fill color
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

	public void setFillColor(Color col) {
		this.fillCol = col;
	}

	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void draw(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		g.fillRect(0, 0, width, height);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		
		g.setColor(fillCol);
		g.fillRect(
			position.x,
			position.y,
			width,
			height
		);

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