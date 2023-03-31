import java.awt.*;

// RectangleGraphics is used to store properties of a Rectnalge and used to Draw a Rectnalge using a Layer's Graphics2D Object
public class RectangleGraphics extends ShapeLayerGraphics {
	/**
	 * RectangleGraphics is used to store the properties of a Rectangle and is used to define how is a Rectangle drawn
	 * @param position represent the rectangle position on the screen/canvas
	 */
	public RectangleGraphics(Point position) {
		super(position);
	}

	/**
	 * used to draw a rectangle into the Graphics2D object
	 * @param g used to render a rectangle into the Graphics2D object
	 */
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