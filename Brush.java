import java.awt.*;

// Brush is an abstract class used to represent common properties that is shared between all brushes
abstract class Brush {
	private int thickness;
	private Color col;
	private Point pos;

	public Brush(int thickness, Color col) {
		this.thickness = thickness;
		this.col = col;
		this.pos = new Point(0, 0);
	}

	public int getThickness() {
		return thickness;
	}

	public Color getCol() {
		return col;
	}

	public Point getPos() {
		return pos;
	}

	public void setThickness(int thickness) {
		System.out.println("this is working");
		this.thickness = thickness;
	}

	public void setColor(Color col) {
		this.col = col;
	}

	public void setPos(Point newPenPos) {
		pos = newPenPos;
	}

	public void setPos(int x, int y) {
		pos.setLocation(x, y);
	}
}