import java.awt.image.*;
import java.awt.*;

class LayerData {
	private BufferedImage layer; // layer holds all the pixels that represent any layer
	private Point layerPos; // layerPos represent the coordinates of the top left corner of the image
	private Point layerEndPos; // layerEndPos represent the coordinates of the bottom right corner of the image

	public LayerData(BufferedImage layer) {
		this(layer, new Point(0, 0));
	}

	public LayerData(BufferedImage layer, Point layerPos) {
		this.layer = layer;
		this.layerPos = layerPos;
		layerEndPos = new Point((int) layerPos.getX() + layer.getWidth(), (int) layerPos.getY() + layer.getHeight());
	}

	public LayerData(int width, int height, Color col) {
		this(width, height, col, new Point(0, 0));
	}

	public LayerData(int width, int height, Color col, Point layerPos) {
		this(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB), layerPos);

		Graphics2D layerGraphics = layer.createGraphics();
		layerGraphics.setBackground(col);
		layerGraphics.clearRect(0, 0, width, height);
	}

	public int getX() {
		return (int) layerPos.getX();
	}

	public int getY() {
		return (int) layerPos.getY();
	}

	public Point getCoords() {
		return layerPos;
	}

	// Might Rename to Absolute
	public int getX(int canvasXPos) {
		return canvasXPos - (int) layerPos.getX();	// Used to get the Absolute X Position of a Layer no matter its position on the Canvas
	}

	public int getY(int canvasYPos) {
		return canvasYPos - (int) layerPos.getY();	// Used to get the Absolute Y Position of a Layer no matter its position on the Canvas
	}

	public Point getCoords(Point canvasPos) {
		return new Point(canvasPos.x - layerPos.x, canvasPos.y - layerPos.y);	// Used to get the Absolute Position of a Layer no matter its position on the Canvas
	}

	public int getEndX() {
		return (int) layerEndPos.getX();
	}

	public int getEndY() {
		return (int) layerEndPos.getY();
	}

	public BufferedImage getImage() {
		return layer;
	}

	public int getWidth() {
		return layer.getWidth();
	}

	public int getHeight() {
		return layer.getHeight();
	}

	public void setLocation(int x, int y) {
		layerPos.setLocation(x, y);
		layerEndPos.setLocation(x + layer.getWidth(), y + layer.getHeight());
	}

	public void setLocation(Point layerPos) {
		this.layerPos = layerPos;
		layerEndPos.setLocation((int) layerPos.getX() + layer.getWidth(), (int) layerPos.getY() + layer.getHeight());
	}

	public void setImage(BufferedImage newImg) {
		layer = newImg;
	}

	public Graphics2D getLayerGraphics() {
		Graphics2D layerGrahics = layer.createGraphics();
		return layerGrahics;
	}

	// update Graphics Takes a SpecificGraphic Object and updates the layer Drawing Based on the Draw Method
	public void updateGraphics(SpecificGraphic g) {
		g.draw(layer.createGraphics());
	}

	public void setPixel(int x, int y, int rgb) {
		if (!inRange(x, y)) return;
		layer.setRGB(x, y, rgb);
	}

	public Integer getPixel(int x, int y) {
		if (!inRange(x, y)) return null;
		return layer.getRGB(x, y);
	}

	private boolean inRange(int x, int y) {
		if (x < 0) return false;
		if (y < 0) return false;
		if (x >= layer.getWidth()) return false;
		if (y >= layer.getHeight()) return false;

		return true;
	}

	// updateLayerSz() used to change the size of the layer based on the width & height and will move all previous pixels into the updated layer
	public void updateLayerSz(int width, int height) {
		BufferedImage tempLayer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D imgGraphics = tempLayer.createGraphics();
		imgGraphics.setBackground(Color.white);
		imgGraphics.clearRect(0, 0, width, height);

		for (int i = 0; i < Math.min(layer.getWidth(), width); i++)
    		for (int j = 0; j < Math.min(layer.getHeight(), height); j++)
				tempLayer.setRGB(i, j, layer.getRGB(i, j));

		layer = tempLayer;
	}

	// mergeLayer merges the newLayer with this layer
	public void mergeLayer(LayerData newLayer) {
		Graphics2D g2d = (Graphics2D) layer.getGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
		g2d.drawImage(newLayer.getImage(), newLayer.getX(), newLayer.getY(), null);
	}

	// Updates this layer size based on width and height and sets each pixel of this updated layer to the specified color
	public void clear(int width, int height, Color col) {
		layer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		clear(col);
	}

	// Sets each pixel of this layer to the specified color
	public void clear(Color col) {
		Graphics2D g2d = (Graphics2D) layer.getGraphics();
		g2d.setBackground(col);
		g2d.clearRect(0, 0, layer.getWidth(), layer.getHeight());
	}
}