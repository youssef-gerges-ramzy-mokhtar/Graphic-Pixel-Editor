import java.awt.image.*;
import java.awt.*;

class ImageLayer extends LayerData {
	public ImageLayer(BufferedImage layer) {super(layer);}
	public ImageLayer(BufferedImage layer, Point layerPos) {super(layer, layerPos);}
	public ImageLayer(int width, int height, Color col) {super(width, height, col);}
	public ImageLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	public void resize(int width, int height) {
		if (width == 0 || height == 0) return;

		Image scaledImg = getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		setImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
		Graphics2D g2d = getLayerGraphics();
		g2d.drawImage(scaledImg, 0, 0, null);

		updateSelectionLayer();
	}

	public void resize(Point newLayerEndPos) {
		int layerWidth = Math.abs(newLayerEndPos.x - getX());
		int layerHeight = Math.abs(newLayerEndPos.y - getY());
		resize(layerWidth, layerHeight);
		setLocation(validPoint(getCoords(), newLayerEndPos));
	}

	public ImageLayer getCopy() {
		ImageLayer copy = new ImageLayer(layerWidth(), layerHeight(), Color.white);
		copy.clear(new Color(0, 0, 0, 0));

		copy.mergeLayer(this.getImage(), 0, 0);
		copy.setLocation(new Point(getX(), getY()));
		copy.updateSelectionLayer();
		return copy;
	}
}