import java.awt.*;

public class DrawLineGraphics implements SpecificGraphic {
	Point firstPoint, secondPoint;
	float stroke_sz;
	Color stroke_col;

	public DrawLineGraphics(Point firstPoint, Point secondPoint, float sz, Color col) {
		this.firstPoint = firstPoint;
		this.secondPoint = secondPoint;
		this.stroke_sz = sz;
		this.stroke_col = col;
	}

	public DrawLineGraphics(float sz, Color col) {
		this(new Point(0, 0), new Point(0, 0), sz, col);
	}

	public void setPoints(Point firstPoint, Point secondPoint) {
		this.firstPoint = firstPoint;
		this.secondPoint = secondPoint;
	}

	public void setStrokeSize(float sz) {
		this.stroke_sz = sz;
	}

	public void setColor(Color col) {
		this.stroke_col = col;
	}

	public void draw(Graphics2D g) {
		if (firstPoint.equals(secondPoint)) return;

		g.setStroke(new BasicStroke(stroke_sz, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g.setColor(stroke_col);

		g.drawLine(
			firstPoint.x,
			firstPoint.y,
			secondPoint.x,
			secondPoint.y
		);

		g.dispose();
	}
}