import java.awt.*;

public class DrawLineGraphics implements SpecificGraphic {
	Point firstPoint, secondPoint;
	Graphics g;
	float stroke_sz = (float) 2;

	public DrawLineGraphics(Point firstPoint, Point secondPoint, Graphics g) {
		this.firstPoint = firstPoint;
		this.secondPoint = secondPoint;
		this.g = g;
	}

	public DrawLineGraphics() {
	}

	public void setPoints(Point firstPoint, Point secondPoint) {
		this.firstPoint = firstPoint;
		this.secondPoint = secondPoint;
	}

	public void setGraphics(Graphics g) {
		this.g = g;
	}

	public void draw() {
		if (firstPoint.equals(secondPoint)) return;

		// Graphics2D g2 = (Graphics2D) g;
		// stroke_sz += 0.1;
		// g2.setStroke(new BasicStroke(stroke_sz));

		g.drawLine(
			firstPoint.x,
			firstPoint.y,
			secondPoint.x,
			secondPoint.y
		);
	}

	public Graphics getGraphics() {
		return g;
	}
}