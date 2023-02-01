import java.awt.*;

// TriangleGraphics is used to store properties of a Triangle and used to Draw a Triangle using a Layer's Graphics2D Object
public class TriangleGraphics implements SpecificGraphic {
	Point position;
	float stroke_sz;
	int width;
	int height;
	Color stroke_col;
	Color fillCol;

	public TriangleGraphics(Point position) {
		this.position = position;
		this.fillCol = Color.black; // this is temporary until we create a shape Control Graphical User Interface for the use to set the fill color
		this.stroke_sz = 2;
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

	public void setDimension(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void draw(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		g.fillRect(0, 0, width, height);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		
		int x = position.x;
		int y = position.y;

		g.setColor(fillCol);
		g.fillPolygon(
			new int[] {x + (width/2), x + width, x},
        	new int[] {0, height, height},
			3
		);

		g.setStroke(new BasicStroke(stroke_sz));
		g.setColor(stroke_col);

        g.drawPolygon(
        	new int[] {x + (width/2), x + width, x},
        	new int[] {0, height, height},
			3
		);
		g.dispose();
	}
}