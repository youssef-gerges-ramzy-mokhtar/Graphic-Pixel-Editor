import java.awt.image.*;
import java.awt.*;

class RectangleLayer extends LayerData {
	private RectangleGraphics rectangleGraphics;
	private Color strokeCol;

	public RectangleLayer(BufferedImage layer) {super(layer);}
	public RectangleLayer(BufferedImage layer, Point layerPos) {super(layer, layerPos);}
	public RectangleLayer(int width, int height, Color col) {super(width, height, col);}
	public RectangleLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	public void resize(int width, int height) {
		rectangleGraphics = new RectangleGraphics(new Point(0, 0));
		if (width == 0 || height == 0) return;

		setImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
		Graphics2D g2d = getLayerGraphics();

		rectangleGraphics.setDimensions(width, height);
		rectangleGraphics.setColor(strokeCol);
	
		updateGraphics(rectangleGraphics);
		updateSelectionLayer();
	}

	public void resize(Point newLayerEndPos) {
		int layerWidth = Math.abs(newLayerEndPos.x - getX());
		int layerHeight = Math.abs(newLayerEndPos.y - getY());
		resize(layerWidth, layerHeight);
		setLocation(validPoint(getCoords(), newLayerEndPos));
	}

	public void setStrokeCol(Color col) {
		this.strokeCol = col;
	}
}