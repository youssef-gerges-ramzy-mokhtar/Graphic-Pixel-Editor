import java.awt.*;

// TriangleGraphics is used to store properties of a Triangle and used to Draw a Triangle using a Layer's Graphics2D Object
public class TriangleGraphics extends ShapeLayerGraphics {
	public TriangleGraphics(Point position) {
		super(position);
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