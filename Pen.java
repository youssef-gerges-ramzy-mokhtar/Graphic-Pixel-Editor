import java.awt.*;

// Pen is used to represent the Properties of a Pen
class Pen extends Brush {
	/**
	 * used to represent the Properties of a Pen
	 * @param thickness pen thicknes
	 * @param col pen color
	 */
	public Pen(int thickness, Color col) {
		super(thickness, col);
	}

	/**
	 * used to represent the Properties of a Pen
	 */
	public Pen() {
		this(1, Color.black);
	}
}