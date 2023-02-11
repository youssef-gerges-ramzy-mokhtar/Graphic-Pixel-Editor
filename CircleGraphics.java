import java.awt.*;

// CircleGraphics is used to store properties of a Circle and used to Draw a Circle using a Layer's Graphics2D Object
public class CircleGraphics extends ShapeLayerGraphics {
	public CircleGraphics(Point position) {
		super(position);
	}

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