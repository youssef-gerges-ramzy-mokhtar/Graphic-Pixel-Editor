import java.awt.image.*;
import java.awt.*;

// ImageLayer simply represents any Image Layer on the Canvas
class ImageLayer extends LayerData {
	public ImageLayer(BufferedImage layer) {super(layer);}
	public ImageLayer(BufferedImage layer, Point layerPos) {super(layer, layerPos);}
	public ImageLayer(int width, int height, Color col) {super(width, height, col);}
	public ImageLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	// resize() is used to represent the layer to the specified width and height
	public void resize(int width, int height) {
		if (width < 15 || height < 15) return;

		Image scaledImg = originalLayer.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		layer = new BufferedImage(width, height, layer.getType());
		Graphics2D g2d = getLayerGraphics();
		g2d.drawImage(scaledImg, 0, 0, null);

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

	// getCopy return a Deep Copy of the Image Layer
	public ImageLayer getCopy() {
		ImageLayer copy = new ImageLayer(layerWidth(), layerHeight(), new Color(0,0,0,0));
		resetLayerProperties(copy);
		return copy;
	}
}