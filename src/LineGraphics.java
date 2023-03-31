import java.awt.*;

// LineGraphics is used to store properties of a Line and is used to Draw a Line using a Layer's Graphics2D Object
public class LineGraphics extends SpecificGraphic {
	Point firstPoint, secondPoint;
	float stroke_sz;
	Color stroke_col;

	/**
	 * LineGraphics is used to store the properties of a line and is used to define how is a line drawn between a start point and an end point
	 * @param firstPoint the start position of the line
	 * @param secondPoint the end position of the line
	 * @param sz line thickness
	 * @param col line color
	 */
	public LineGraphics(Point firstPoint, Point secondPoint, float sz, Color col) {
		this.firstPoint = firstPoint;
		this.secondPoint = secondPoint;
		this.stroke_sz = sz;
		this.stroke_col = col;
	}

	/**
	 * LineGraphics is used to store the properties of a line and is used to define how is a line drawn between a start point and an end point
	 * @param sz line thickness
	 * @param col line color
	 */
	public LineGraphics(float sz, Color col) {
		this(new Point(0, 0), new Point(0, 0), sz, col);
	}

	/**
	 * sets the start and end points for the line to be drawn through the 2 points
	 * @param firstPoint the start position of the line
	 * @param secondPoint the end position of the line
	 */
	public void setPoints(Point firstPoint, Point secondPoint) {
		this.firstPoint = firstPoint;
		this.secondPoint = secondPoint;
	}

	/**
	 * sets the line thickness/stroke size
	 * @param sz line stroke size
	 */
	public void setStrokeSize(float sz) {
		this.stroke_sz = sz;
	}

	/**
	 * sets the line color
	 * @param col line color
	 */
	public void setColor(Color col) {
		this.stroke_col = col;
	}

	/**
	 * used to draw a line into the Graphics2D object
	 * @param g used to render a line into the Graphics2D object
	 */
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