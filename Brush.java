import java.awt.*;

// Brush is an abstract class used to represent common properties that is shared between all brushes
abstract class Brush {
	private int thickness;
	private Color col;
	private Point pos;

	/**
	 * Brush is an abstract class representing properties any type of brush should have.
	 *
	 * @param thickness represent the brush thickness.
	 * @param col represent the brush color.
	 */
	public Brush(int thickness, Color col) {
		this.thickness = thickness;
		this.col = col;
		this.pos = new Point(0, 0);
	}

	/**
	 * @return the brush thickness
	 */
	public int getThickness() {
		return thickness;
	}

	/**
	 * @return the brush color
	 */
	public Color getCol() {
		return col;
	}

	/**
	 * @return the brush position on the screen/canvas
	 */
	public Point getPos() {
		return pos;
	}

	/**
	 * sets the brush thickness
	 */
	public void setThickness(int thickness) {
		this.thickness = thickness;
	}

	/**
	 * sets the brush color
	 */
	public void setColor(Color col) {
		this.col = col;
	}

	/**
	 * @param newPenPos represent the new brush position
	 * sets the brush position on the screen/canvas
	 */
	public void setPos(Point newPenPos) {
		pos = newPenPos;
	}

	/**
	 * @param x represent the new brush x-coord position
	 * @param y represent the new brush y-coord position
	 * sets the brush position on the screen/canvas
	 */
	public void setPos(int x, int y) {
		pos.setLocation(x, y);
	}
}