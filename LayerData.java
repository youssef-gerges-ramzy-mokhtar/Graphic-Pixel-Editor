import java.awt.image.*;
import java.awt.*;

class LayerData {
	private BufferedImage layer; // layer holds all the pixels that represent any layer
	private BufferedImage layerSelection; // layer holds all the pixels that represent any layer plus a selectin border
	private Point layerPos; // layerPos represent the coordinates of the top left corner of the image
	private Point layerEndPos; // layerEndPos represent the coordinates of the bottom right corner of the image

	public LayerData(BufferedImage layer) {
		this(layer, new Point(0, 0));
	}

	public LayerData(BufferedImage layer, Point layerPos) {
		this.layer = layer;
		this.layerPos = layerPos;
		layerEndPos = new Point((int) layerPos.getX() + layer.getWidth(), (int) layerPos.getY() + layer.getHeight());

		updateSelectionLayer();
	}

	public LayerData(int width, int height, Color col) {
		this(width, height, col, new Point(0, 0));
	}

	public LayerData(int width, int height, Color col, Point layerPos) {
		this(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB), layerPos);

		Graphics2D layerGraphics = layer.createGraphics();
		layerGraphics.setBackground(col);
		layerGraphics.clearRect(0, 0, width, height);

		updateSelectionLayer();
	}

	protected void updateSelectionLayer() {
		layerSelection = new BufferedImage(layer.getWidth(), layer.getHeight(), layer.getType());
		Graphics2D g2d = layerSelection.createGraphics();
		g2d.drawImage(layer, 0, 0, null);

		float[] dash1 = { 2f, 0f, 2f };
		BasicStroke bs1 = new BasicStroke(
			5, 
	        BasicStroke.CAP_BUTT, 
	        BasicStroke.JOIN_ROUND, 
	        1.0f,
	        dash1,
	        20f
	    );

		g2d.setStroke(bs1);
		g2d.setColor(Color.black);
		g2d.drawLine(0, 0, layer.getWidth(), 0);
		g2d.drawLine(0, 0, 0, layer.getHeight());
		g2d.drawLine(layer.getWidth(), 0, layer.getWidth(), layer.getHeight());
		g2d.drawLine(0, layer.getHeight(), layer.getWidth(), layer.getHeight());

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
		// Used to get the Absolute X Position of a Layer no matter its position on the Canvas
		return canvasXPos - (int) layerPos.getX();
	}

	public int getY(int canvasYPos) {
		// Used to get the Absolute Y Position of a Layer no matter its position on the Canvas
		return canvasYPos - (int) layerPos.getY();
	}

	public Point getCoords(Point canvasPos) {
		// Used to get the Absolute Position of a Layer no matter its position on the Canvas
		return new Point(canvasPos.x - layerPos.x, canvasPos.y - layerPos.y);
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

	public Graphics2D getLayerSelectionGraphics() {
		Graphics2D layerSelectionGraphics = layerSelection.createGraphics();
		return layerSelectionGraphics;
	}

	// update Graphics Takes a SpecificGraphic Object and updates the layer Drawing Based on the Draw Method
	public void updateGraphics(SpecificGraphic g) {
		g.draw(layer.createGraphics());
		updateSelectionLayer();
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

	// UsupdateLayerSz() used to change the size of the layer based on the width & height and will move all previous pixels into the updated layer
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
		mergeLayer(newLayer.getImage(), newLayer.getX(), newLayer.getY());
	}

	public void mergeLayerSelection(LayerData newLayer) {
		mergeLayer(newLayer.getSelectionImage(), newLayer.getX(), newLayer.getY());
	}

	private void mergeLayer(BufferedImage newLayer, int x, int y) {
		Graphics2D g2d = (Graphics2D) layer.getGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

		g2d.drawImage(newLayer, x, y, null);
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

	public void resize(int width, int height) {
		if (width == 0 || height == 0) return;

		Image scaledImg = layer.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		layer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = getLayerGraphics();
		g2d.drawImage(scaledImg, 0, 0, null);

		updateSelectionLayer();
	}

	public void resize(Point newLayerEndPos) {
		int layerWidth = Math.abs(newLayerEndPos.x - layerPos.x);
		int layerHeight = Math.abs(newLayerEndPos.y - layerPos.y);
		resize(layerWidth, layerHeight);
		layerPos = validPoint(layerPos, newLayerEndPos);
	}

	protected Point validPoint(Point p1, Point p2) {
		int x1 = p1.x, y1 = p1.y;
		int x2 = p2.x, y2 = p2.y;
		
		if (y2 > y1 && x2 > x1) return p1;
		if (y2 < y1 && x2 > x1) return new Point(x1, y2);
		if(x2 < x1 && y2 < y1) return p2;
		if(x2 < x1 && y2 > y1) return new Point(x2, y1);
		
		return null;
	}

	public void drawBorder() {
		Graphics2D g2d = getLayerSelectionGraphics();
		float[] dash1 = { 2f, 0f, 2f };
		BasicStroke bs1 = new BasicStroke(
			0.5f, 
	        BasicStroke.CAP_BUTT, 
	        BasicStroke.JOIN_ROUND, 
	        1.0f,
	        dash1,
	        20f
	    );

		g2d.setStroke(bs1);
		g2d.setColor(Color.black);
		
		int spacing = 0;
		g2d.drawLine(spacing, spacing, layer.getWidth() + spacing, spacing);
		g2d.drawLine(spacing, spacing, spacing, layer.getHeight() + spacing);
		g2d.drawLine(layer.getWidth() + spacing, spacing, layer.getWidth() + spacing, layer.getHeight() + spacing);
		g2d.drawLine(spacing, layer.getHeight() + spacing, layer.getWidth() + spacing, layer.getHeight() + spacing);
	}

	public BufferedImage getSelectionImage() {
		return layerSelection;
	}
}