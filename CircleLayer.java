import java.awt.image.*;
import java.awt.*;

class CircleLayer extends LayerData {
	private CircleGraphics circleGraphics;
	private Color strokeCol;

	public CircleLayer(BufferedImage layer) {super(layer);}
	public CircleLayer(BufferedImage layer, Point layerPos) {super(layer, layerPos);}
	public CircleLayer(int width, int height, Color col) {super(width, height, col);}
	public CircleLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	public void resize(int width, int height) {
		circleGraphics = new CircleGraphics(new Point(0, 0));
		if (width == 0 || height == 0) return;

		setImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
		Graphics2D g2d = getLayerGraphics();

		circleGraphics.setDimension(width, height);
		circleGraphics.setColor(strokeCol);
	
		updateGraphics(circleGraphics);
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