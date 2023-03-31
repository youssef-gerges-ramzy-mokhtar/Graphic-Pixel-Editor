import java.awt.image.*;
import java.awt.*;

// ShapeLayer is used to represent the common propertiese of all shape layers
public abstract class ShapeLayer extends LayerData {
	private SpecificGraphic specificGraphic;
	protected Color strokeCol;
	protected Color fillCol;

	/**
	 * ShapeLayer is used to represent the common propertiese of all shape layers
	 * @param width defines the width of the shape layer
	 * @param height defines the height of the shape layer
	 * @param col defines the fill color of the shape
	 */
	public ShapeLayer(int width, int height, Color col) {super(width, height, col);}
	
	/**
	 * ShapeLayer is used to represent the common propertiese of all shape layers
	 * @param width defines the width of the shape layer
	 * @param height defines the height of the shape layer
	 * @param col defines the fill color of the shape
	 * @param layerPos defines the position of the shape layer on the screen/canvas
	 */
	public ShapeLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	/**
	 * used to resize the shape layer to the desired width and height
	 * @param width the new width you want to resize the shape layer to
	 * @param height the new height you want to resize the shape layer to	
	 */
	/*
		Also note that when we resize a Shape Layer it doesn't get pixaleted because each time we resize a shape we redraw the shape again
		based on the new width and height
	*/
	// resize() is used to represent the layer to the specified width and height
	public void resize(int width, int height) {
		if (width <= 5 || height <= 5) return;

		BufferedImage layerImg = getImage();
		this.fillCol = new Color(layerImg.getRGB(layerImg.getWidth() / 2, layerImg.getHeight() / 2)); // That is not accurate because there might be drawings on the Shape Layer

		specificGraphic = getSpecificGraphic(width, height);
		
		BufferedImage oldLayer = getImage();

		setImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
		Graphics2D g2d = getLayerGraphics();
		updateGraphics(specificGraphic);
		updateSelectionLayer();
	}	

	/**
	 * used to resize the shape layer to reach the desired coordinate on the screen/canvas
	 * @param newLayerEndPos the new coordinate in which you want the shape layer to scale to
	 */
	// resize() is used to get a coordinate on the canvas and resize the layer to reach this coordinate
	public void resize(Point newLayerEndPos) {
		int layerWidth = Math.abs(newLayerEndPos.x - getX());
		int layerHeight = Math.abs(newLayerEndPos.y - getY());
		if(layerWidth < 15 || newLayerEndPos.x - getX() < 0) layerWidth = 15;
		if(layerHeight < 15 || newLayerEndPos.y - getY() < 0) layerHeight = 15;
		
		resize(layerWidth, layerHeight);
		
		if(newLayerEndPos.x - getX() > 15 && newLayerEndPos.y -getY() > 15)
		setLocation(validTopLeftPoint(getCoords(), newLayerEndPos));
		

	}

	/**
	 * sets the shape stroke color
	 * @param col stroke color
	 */
	public void setStrokeCol(Color col) {
		this.strokeCol = col;
	}
	
	/**
	 * sets the shape fill color
	 * @param col fill color
	 */
	public void setFillCol(Color col) {
		this.fillCol = col;
	}

	/**
	 * returns a Deep Copy of the shape Layer
	 * @return returns a Deep Copy of the shape Layer
	 */
	// getCopy return a Deep Copy of the Shape Layer
	public ShapeLayer getCopy() {
		ShapeLayer copy = getShapeLayerCopy();
		resetLayerProperties(copy);
		return copy;
	}

	/**
	 * rasterize converts the shape layer to an Image Layer
	 * @return ImageLayer representing the rasterized shape layer
	 */
	// rasterize() is used to convert a Shape Layer to an Image Layer
	public LayerData rasterize() {
		// Code Refactor Here
		ImageLayer rasterizedShape = new ImageLayer(layerWidth(), layerHeight(), Color.white);
		rasterizedShape.clear(Constants.transparentColor);

		rasterizedShape.mergeLayer(this.getImage(), 0, 0);
		rasterizedShape.setLocation(new Point(getX(), getY()));
		rasterizedShape.updateSelectionLayer();
		return rasterizedShape;
	}

	/**
	 * returns a String containg all the information of this shape layer
	 * @param seperator each piece of information is seperated by the seperator character
	 * @param layerPos the position of the shape layer on the Screen/Canvas
	 * @return a String containg the shape layer information
	 */
	// getLayerInfo() returns a String containg all the information of this shape layer
	public String getLayerInfo(char seperator, int layerPos) {
		String shapeSymbol = getShapeSymbol();
		String width = Integer.toString(this.getWidth());
		String height = Integer.toString(this.getHeight());
		String xCoord = Integer.toString(this.getX());
		String yCoord = Integer.toString(this.getY());
		String rgbFillCol = Integer.toString(getImage().getRGB(getImage().getWidth() / 2, getImage().getHeight() / 2));

		String endl = "\n";
		return
			shapeSymbol + seperator + 
			width + seperator + 
			height + seperator + 
			xCoord + seperator + 
			yCoord + seperator + 
			rgbFillCol + endl;
	}

	/**
	 * an abstract method that is defined by each concrete shape to represent the drawing behavior of a shape
	 * @return a SpecificGraphic Object
	 */
	protected abstract SpecificGraphic getSpecificGraphic(int width, int heights);

	/**
	 * @return returns a concrete shape Layer filled completely with transparent pixels
	 */
	protected abstract ShapeLayer getShapeLayerCopy();

	/**
	 * the symbol is used by the project loader to identify the type of layer that is being loaded
	 * @return returns a string representing the symbol that is used to define a Concrete Shape Layer
	 */
	protected abstract String getShapeSymbol();
}