import java.awt.image.*;
import java.awt.*;

abstract class ShapeLayer extends LayerData {
	private SpecificGraphic specificGraphic;
	protected Color strokeCol;
	protected Color fillCol;

	public ShapeLayer(int width, int height, Color col) {super(width, height, col);}
	public ShapeLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	public void resize(int width, int height) {
		specificGraphic = getSpecificGraphic(width, height);
		if (width == 0 || height == 0) return;

		setImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
		Graphics2D g2d = getLayerGraphics();
		updateGraphics(specificGraphic);
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

	protected abstract SpecificGraphic getSpecificGraphic(int width, int heights);
}