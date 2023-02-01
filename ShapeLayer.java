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

		// mergeLayer(oldLayer, 0, 0); // that is the best what I could do to resize a Shape Layer with drawings on it and make the image pixelated
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
		copy.clear(new Color(0, 0, 0, 0));

		copy.mergeLayer(this.getImage(), 0, 0);
		copy.setLocation(new Point(getX(), getY()));
		copy.updateSelectionLayer();
		return copy;
	}

	// Under Development for Cropping Shapes & for Using drawing toll with shape
	// public ImageLayer rasterize() {

	// }

	protected abstract SpecificGraphic getSpecificGraphic(int width, int heights);
	protected abstract ShapeLayer getShapeLayerCopy();
}