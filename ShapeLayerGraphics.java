import java.awt.*;

// ShapeLayerGraphics is a general class used to represent all common properties between all Shapes Graphics
abstract class ShapeLayerGraphics extends LayerGraphics {
	protected float stroke_sz;
	protected Color stroke_col;
	protected Color fillCol = Color.BLACK;

	/**
	 * ShapeLayerGraphics is a general class used to store the graphics properties of any shape
	 * @param position represent the shape position on the screen/canvas 
	 */
	public ShapeLayerGraphics(Point position) {
		super(position);
		this.fillCol = fillCol; // this is temporary until we create a shape Control Graphical User Interface for the use to set the fill color
		this.stroke_sz = 2;
	}

	/**
	 * sets the outer stroke size of the shape
	 * @param sz stroke size
	 */
	public void setStrokeSize(float sz) {
		this.stroke_sz = sz;
	}

	/**
	 * sets the stroke color of the shape
	 * @param col stroke color
	 */
	public void setStrokeColor(Color col) {
		
		this.stroke_col = col;
		this.fillCol = col;
	}

	/**
	 * sets the fill color of the shape
	 * @param col fill color
	 */
	public void setFillColor(Color col) {
		this.fillCol = col;
	}

	/**
	 * draw is an abstract method that defines how a shape is drawn into the Screen/Canvas
	 * @param Graphics2D is used to render the shape draw behaviour into the Graphics2D object
	 */
	public abstract void draw(Graphics2D g);
}