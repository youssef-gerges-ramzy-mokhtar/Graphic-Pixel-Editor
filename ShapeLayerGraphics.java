import java.awt.*;

// ShapeLayerGraphics is a general class used to represent all common properties between all Shapes Graphics
abstract class ShapeLayerGraphics extends LayerGraphics {
	protected float stroke_sz;
	protected Color stroke_col;
	protected Color fillCol = Color.BLACK;

	public ShapeLayerGraphics(Point position) {
		super(position);
		this.fillCol = fillCol; // this is temporary until we create a shape Control Graphical User Interface for the use to set the fill color
		this.stroke_sz = 2;
	}

	public void setStrokeSize(float sz) {
		this.stroke_sz = sz;
	}

	public void setStrokeColor(Color col) {
		
		this.stroke_col = col;
		this.fillCol = col;
	}

	public void setFillColor(Color col) {
		this.fillCol = col;
	}

	public abstract void draw(Graphics2D g);
}