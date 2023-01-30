import java.awt.image.*;
import java.awt.*;

class TriangleLayer extends LayerData {
	private TriangleGraphics triangleGraphics;
	private Color strokeCol;

	public TriangleLayer(BufferedImage layer) {super(layer);}
	public TriangleLayer(BufferedImage layer, Point layerPos) {super(layer, layerPos);}
	public TriangleLayer(int width, int height, Color col) {super(width, height, col);}
	public TriangleLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	public void resize(int width, int height) {
		triangleGraphics = new TriangleGraphics(new Point(0, 0));
		if (width == 0 || height == 0) return;

		setImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
		Graphics2D g2d = getLayerGraphics();

		triangleGraphics.setDimension(width, height);
		triangleGraphics.setColor(strokeCol);
	
		updateGraphics(triangleGraphics);
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