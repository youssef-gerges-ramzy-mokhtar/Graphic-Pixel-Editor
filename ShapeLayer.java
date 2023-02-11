import java.awt.image.*;
import java.awt.*;

abstract class ShapeLayer extends LayerData {
	private SpecificGraphic specificGraphic;
	protected Color strokeCol;
	protected Color fillCol;

	public ShapeLayer(int width, int height, Color col) {super(width, height, col);}
	public ShapeLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	public void resize(int width, int height) {
		BufferedImage layerImg = getImage();
		this.fillCol = new Color(layerImg.getRGB(layerImg.getWidth() / 2, layerImg.getHeight() / 2)); // That is not accurate because there might be drawings on the Shape Layer

		specificGraphic = getSpecificGraphic(width, height);
		if (width == 0 || height == 0) return;
		
		BufferedImage oldLayer = getImage();

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

	public ShapeLayer getCopy() {
		ShapeLayer copy = getShapeLayerCopy();
		resetLayerProperties(copy);
		return copy;
	}

	public LayerData rasterize() {
		// Code Refactor Here
		ImageLayer rasterizedShape = new ImageLayer(layerWidth(), layerHeight(), Color.white);
		rasterizedShape.clear(new Color(0, 0, 0, 0));

		rasterizedShape.mergeLayer(this.getImage(), 0, 0);
		rasterizedShape.setLocation(new Point(getX(), getY()));
		rasterizedShape.updateSelectionLayer();
		return rasterizedShape;
	}

	protected abstract SpecificGraphic getSpecificGraphic(int width, int heights);
	protected abstract ShapeLayer getShapeLayerCopy();
}