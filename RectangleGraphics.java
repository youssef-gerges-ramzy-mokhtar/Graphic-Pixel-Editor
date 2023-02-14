import java.awt.*;

// RectangleGraphics is used to store properties of a Rectnalge and used to Draw a Rectnalge using a Layer's Graphics2D Object
public class RectangleGraphics extends ShapeLayerGraphics {
	public RectangleGraphics(Point position) {
		super(position);
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