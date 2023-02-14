import java.awt.image.*;
import java.awt.*;

class ImageLayer extends LayerData {
	public ImageLayer(BufferedImage layer) {super(layer);}
	public ImageLayer(BufferedImage layer, Point layerPos) {super(layer, layerPos);}
	public ImageLayer(int width, int height, Color col) {super(width, height, col);}
	public ImageLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	public void resize(int width, int height) {
		if (width < 15 || height < 15) return;

		Image scaledImg = originalLayer.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		layer = new BufferedImage(width, height, layer.getType());
		Graphics2D g2d = getLayerGraphics();
		g2d.drawImage(scaledImg, 0, 0, null);

		updateSelectionLayer();
	}

	public void resize(Point newLayerEndPos) {
		int layerWidth = Math.abs(newLayerEndPos.x - getX());
		int layerHeight = Math.abs(newLayerEndPos.y - getY());
		if(layerWidth < 15 || newLayerEndPos.x - getX() < 0) layerWidth = 15;
		if(layerHeight < 15 || newLayerEndPos.y - getY() < 0) layerHeight = 15;
		
		resize(layerWidth, layerHeight);
		
		if(newLayerEndPos.x - getX() > 15 && newLayerEndPos.y -getY() > 15)
		setLocation(validPoint(getCoords(), newLayerEndPos));
	}

	public ImageLayer getCopy() {
		ImageLayer copy = new ImageLayer(layerWidth(), layerHeight(), Color.white);
		resetLayerProperties(copy);
		return copy;
	}
}