import java.awt.image.*;
import java.awt.*;

// ShapeLayer is used to represent the commont propertiese of all shape layers
abstract class ShapeLayer extends LayerData {
	private SpecificGraphic specificGraphic;
	protected Color strokeCol;
	protected Color fillCol;

	public ShapeLayer(int width, int height, Color col) {super(width, height, col);}
	public ShapeLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

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

	public void setStrokeCol(Color col) {
		this.strokeCol = col;
	}
	public void setFillCol(Color col) {
		this.fillCol = col;
	}

	// getCopy return a Deep Copy of the Shape Layer
	public ShapeLayer getCopy() {
		ShapeLayer copy = getShapeLayerCopy();
		resetLayerProperties(copy);
		return copy;
	}

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

	protected abstract SpecificGraphic getSpecificGraphic(int width, int heights);
	protected abstract ShapeLayer getShapeLayerCopy();
}