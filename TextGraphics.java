import java.awt.*;

// TextGraphics is used to store properties of a Rectnalge and used to Draw a Rectnalge using a Layer's Graphics2D Object
public class TextGraphics implements SpecificGraphic {
	Point position;
	float stroke_sz;
	int len;
	Color stroke_col;
    String text;

	public TextGraphics(Point position) {
		this.position = position;
		stroke_sz = 2;
		len = 3;
	}

	public void setPoints(Point position) {
		this.position = position;
	}

	public void setStrokeSize(float sz) {
		this.stroke_sz = sz;
	}

	public void setColor(Color col) {
		this.stroke_col = col;
	}

	public void setLen(int len) {
		this.len = len;
	}

    public void setText(String t){
        this.text = t;
    }

	public void draw(Graphics2D g) {
		g.setStroke(new BasicStroke(stroke_sz));
		g.setColor(stroke_col);

		g.drawString(text, 0, 0);

		g.dispose();
	}
}