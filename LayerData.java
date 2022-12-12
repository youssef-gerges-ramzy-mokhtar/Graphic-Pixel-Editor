import java.awt.image.*;
import java.awt.*;

class LayerData {
	private BufferedImage layer;
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
		this(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
		
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

	public int getX(int canvasXPos) {
		return canvasXPos - (int) layerPos.getX();
	}

	public int getY(int canvasYPos) {
		return canvasYPos - (int) layerPos.getY();
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
}