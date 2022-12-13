import java.awt.*;

// Pen is used to represent the Properties of a Pen
class Pen extends Brush {
	public Pen(int thickness, Color col) {
		super(thickness, col);
	}

	public Pen() {
		this(1, Color.black);
	}
}