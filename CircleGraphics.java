import java.awt.*;

// CircleGraphics is used to store properties of a Circle and used to Draw a Circle using a Layer's Graphics2D Object
public class CircleGraphics extends ShapeLayerGraphics {
	/**
	 * CircleGraphics is used to store the properties of a Circle and is used to define how is a circle drawn
	 * @param Point represent the circle position on the screen/canvas
	 */
	public CircleGraphics(Point position) {
		super(position);
	}

	/**
	 * used to draw a circle into the Graphics2D object
	 * @param Graphics2D is used to render a circle into the Graphics2D object
	 */
	public void draw(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
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