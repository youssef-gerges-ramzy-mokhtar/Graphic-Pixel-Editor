import java.awt.*;

class Brush {
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