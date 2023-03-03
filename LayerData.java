import java.awt.image.*;
import java.awt.*;

// LayerData is a generic class used to represent the common properties and behaviour that is shared among all existing layers
abstract class LayerData {
	protected BufferedImage layer; // layer holds all the pixels that represent any layer
	protected BufferedImage originalLayer; // originalLayer is used mainly for avoiding image pixilation during resizing
	private BufferedImage layerSelection; // layer holds all the pixels that represent any layer plus a selectin border
	
	private Point layerPos; // layerPos represent the coordinates of the top left corner of the image
	private Point layerEndPos; // layerEndPos represent the coordinates of the bottom right corner of the image
	private boolean hidden; // a boolean stating if a layer is visible or not
	private boolean selectedForMerge; // a boolean stating if a layer will be merged with other layers or not

	public LayerData(BufferedImage layer) {
		this(layer, new Point(0, 0));
	}

	public LayerData(BufferedImage layer, Point layerPos) {
		this.layer = layer;
		this.layerPos = layerPos;
		this.layerEndPos = new Point((int) layerPos.getX() + layer.getWidth(), (int) layerPos.getY() + layer.getHeight());
		this.originalLayer = layer;

		this.hidden = false;
		this.selectedForMerge = false;

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

	// updateSelectionLayer is used to change the selection layer to always be up-to date with the layer
	protected void updateSelectionLayer() {
		layerSelection = new BufferedImage(layer.getWidth(), layer.getHeight(), layer.getType());
		Graphics2D g2d = layerSelection.createGraphics();
		g2d.drawImage(layer, 0, 0, null);
		g2d.dispose();

		drawBorder();
	}

	public int layerWidth() {
		return layer.getWidth();
	}

	public int layerHeight() {
		return layer.getHeight();
	}

	// getX() returns the x-coordinate of the layer on the canvas
	public int getX() {
		if (layerPos != null) return layerPos.x;
		else return 0;
	}

	// getY() returns the y-coordinate of the layer on the canvas
	public int getY() {
		if (layerPos != null) return layerPos.y;
		else return 0;
	}

	public Point getCoords() {
		return new Point(getX(), getY());
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
		this.layer = newImg;
		this.originalLayer = layer;
		updateSelectionLayer();
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
		originalLayer = layer;
		updateSelectionLayer();
	}

	public void setPixel(int x, int y, int rgb) {
		if (!inRange(x, y)) return;
		layer.setRGB(x, y, rgb);
		originalLayer = layer;
	}

	public Integer getPixel(int x, int y) {
		if (!inRange(x, y)) return null;
		return layer.getRGB(x, y);
	}

	// inRange takes a x-coordinate and a y-coordinate and checks if this coordinates are within the layer bounds
	private boolean inRange(int x, int y) {
		if (x < 0) return false;
		if (y < 0) return false;
		if (x >= layer.getWidth()) return false;
		if (y >= layer.getHeight()) return false;

		return true;
	}

	// updateLayerSz() used to change the size of the layer based on the width & height and will move all previous pixels into the updated layer
	public void updateLayerSz(int width, int height) {
		BufferedImage tempLayer = createBufferedImage(width, height);

		for (int i = 0; i < Math.min(layer.getWidth(), width); i++)
    		for (int j = 0; j < Math.min(layer.getHeight(), height); j++)
				tempLayer.setRGB(i, j, layer.getRGB(i, j));

		layer = tempLayer;
		originalLayer = layer;
	}

	// decreaseLayerSz() is used to crop the layer to a new width and height and adjust the layer position based on the Resize Type
	public void decreaseLayerSz(int width, int height, Resize corner) {
		if (width < 0 || height < 0) return;
		if (width > layer.getWidth()) width = layer.getWidth();
		if (height > layer.getHeight()) height = layer.getHeight();

		BufferedImage tempLayer = createBufferedImage(width, height);
		int x_offset = 0, y_offset = 0;
		int layerW = layer.getWidth(), layerH = layer.getHeight();

		if (corner == Resize.BOTTOMRIGHT) {x_offset = 0; y_offset = 0;}
		if (corner == Resize.BOTTOMLEFT) {x_offset = layerW-width; y_offset = 0;}
		if (corner == Resize.TOPRIGHT) {x_offset = 0; y_offset = layerH-height;}
		if (corner == Resize.TOPLEFT) {x_offset = layerW-width; y_offset = layerH-height;}

		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				tempLayer.setRGB(i, j, layer.getRGB(i + x_offset, j + y_offset));

		layer = tempLayer;
		setLocation(getX() + x_offset, getY() + y_offset);

		originalLayer = layer;
	}

	private BufferedImage createBufferedImage(int width, int height) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D imgGraphics = img.createGraphics();
		imgGraphics.setBackground(Color.white);
		imgGraphics.clearRect(0, 0, width, height);

		return img;
	}

	// mergeLayer merges the newLayer with this layer
	public void mergeLayer(LayerData newLayer) {
		mergeLayer(newLayer.getImage(), newLayer.getX() - getX(), newLayer.getY() - getY()); // This might cause problems
	}

	public void mergeLayerSelection(LayerData newLayer) {
		mergeLayer(newLayer.getSelectionImage(), newLayer.getX(), newLayer.getY());
	}

	public void mergeLayer(BufferedImage newLayer, int x, int y) {
		Graphics2D g2d = (Graphics2D) layer.getGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

		g2d.drawImage(newLayer, x, y, null);
		originalLayer = layer;
	}

	// Updates this layer size based on width and height and sets each pixel of this updated layer to the specified color
	public void clear(int width, int height, Color col) {
		layer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		setLocation(getX(), getY());
		clear(col);
	}

	// Sets each pixel of this layer to the specified color
	public void clear(Color col) {
		this.clearSubArea(0, 0, layer.getWidth(), layer.getHeight(), col);
	}

	// clears part of the layer based on 2 (x,y) coordinates a color
	public void clearSubArea(int x1, int y1, int x2, int y2, Color col) {
		Graphics2D g2d = (Graphics2D) this.layer.getGraphics();
		g2d.setBackground(col);
		// g2d.clearRect(getX(Math.min(x1, x2)), getY(Math.min(y1, y2)), Math.abs(x1-x2), Math.abs(y1-y2));
		
		g2d.clearRect(
			Math.max(getX(Math.min(x1, x2)), 0), 
			Math.max(getY(Math.min(y1, y2)), 0), 
			Math.min(Math.abs(x1-x2), layer.getWidth() - Math.max(getX(Math.min(x1, x2)), 0)),
			Math.min(Math.abs(y1-y2), layer.getHeight() - Math.max(getY(Math.min(y1, y2)), 0))
		);

		originalLayer = layer;
		updateSelectionLayer();
		g2d.dispose();
	}

	// is used to take in 2 points and return a Point representing the Top Left Corner Point based on the 2 given points
	public static Point validTopLeftPoint(Point p1, Point p2) {
		int x1 = p1.x, y1 = p1.y;
		int x2 = p2.x, y2 = p2.y;
		
		if (y2 > y1 && x2 > x1) return p1; // imagine as moving from top left corner to bottom right corner
		if (y2 < y1 && x2 > x1) return new Point(x1, y2); // imagine as moving from bottom left corner to top right corner
		if (x2 < x1 && y2 < y1) return p2; // imagine as moving from bottom right corner to top left corner
		if (x2 < x1 && y2 > y1) return new Point(x2, y1); // imagine as moving from top right corner to the bottom left corner
		
		return null;
	}

	// is used to take in 2 points and return a Point representing the Bottom Right Corner Point based on the 2 given points
	public static Point validBottomRightPoint(Point p1, Point p2) {
		int x1 = p1.x, y1 = p1.y;
		int x2 = p2.x, y2 = p2.y;

		if (y2 > y1 && x2 > x1) return p2; // imagine as moving from top left corner to bottom right corner
		if (y2 < y1 && x2 > x1) return new Point(x2, y1); // imagine as moving from bottom left corner to top right corner
		if (x2 < x1 && y2 < y1) return p1; // imagine as moving from bottom right corner to top left corner
		if (x2 < x1 && y2 > y1) return new Point(x1, y2); // imagine as moving from top right corner to the bottom left corner

		return null;
	}

	// drawBorder() is used to draw a border on the selection layer to indicate selection
	public void drawBorder() {
		Graphics2D g2d = getLayerSelectionGraphics();
		
		g2d.setColor(Color.white);
		g2d.setStroke(new BasicStroke(2f));
		drawBorder(g2d);

		float[] dash1 = { 2f, 0f, 2f };
		BasicStroke bs1 = new BasicStroke(
			4f, 
	        BasicStroke.CAP_BUTT, 
	        BasicStroke.JOIN_ROUND, 
	        1.0f,
	        dash1,
	        20f
	    );

		g2d.setStroke(bs1);
		g2d.setColor(Color.black);
		drawBorder(g2d);

		g2d.dispose();
	}
	
	private void drawBorder(Graphics2D g2d) {
		int spacing = 0;
		
		g2d.drawLine(spacing, spacing, layer.getWidth() + spacing, spacing);
		g2d.drawLine(spacing, spacing, spacing, layer.getHeight() + spacing);
		g2d.drawLine(layer.getWidth() + spacing, spacing, layer.getWidth() + spacing, layer.getHeight() + spacing);
		g2d.drawLine(spacing, layer.getHeight() + spacing, layer.getWidth() + spacing, layer.getHeight() + spacing);	
	}

	public BufferedImage getSelectionImage() {
		return layerSelection;
	}

	// crop() is used to crop a layer to reach the newLayerEndPos based on the type of Resizing
	public void crop(Point newLayerEndPos, Resize cropType) {
		if (!pointInBounds(newLayerEndPos)) return;
		if (cropType == Resize.INVALID) return;

		int layerW = layer.getWidth();
		int layerH = layer.getHeight();
		int newWidth = 0, newHeight = 0;
		Resize cornerType;
	
		int x_new = newLayerEndPos.x;
		int y_new = newLayerEndPos.y;
		int x1 = layerPos.x;
		int y1 = layerPos.y;
		int x2 = layerEndPos.x;
		int y2 = layerEndPos.y;

		if (cropType == Resize.TOP || cropType == Resize.LEFT) cornerType = Resize.TOPLEFT;
		else if (cropType == Resize.BOTTOM || cropType == Resize.RIGHT) cornerType = Resize.BOTTOMRIGHT;
		else cornerType = cropType;

		// Code Refactor if you can
		if (cropType == Resize.TOP) {
			newWidth = layerW;
			newHeight = y2 - y_new;
		}

		if (cropType == Resize.BOTTOM) {
			newWidth = layerW;
			newHeight = y_new - y1;
		}

		if (cropType == Resize.RIGHT) {
			newWidth = x_new - x1;
			newHeight = layerH;
		}
		
		if (cropType == Resize.LEFT) {
			newWidth = x2 - x_new;
			newHeight = layerH;
		}

		if (cropType == Resize.BOTTOMRIGHT) {
			newWidth = x_new - x1;
			newHeight = y_new - y1;
		}

		if (cropType == Resize.BOTTOMLEFT) {
			newWidth = x2 - x_new;
			newHeight = y_new - y1;
		}

		if (cropType == Resize.TOPRIGHT) {
			newWidth = x_new - x1;
			newHeight = y2 - y_new;
		}

		if (cropType == Resize.TOPLEFT) {
			newWidth = x2 - x_new;
			newHeight = y2 - y_new;
		}

		decreaseLayerSz(newWidth, newHeight, cornerType);
		updateSelectionLayer();
	}

	// pointInBounds checks if a Point is contained inside the bounds of a layer on the canvas
	// It checks if at this point on the canvas exists a layer or not
	public boolean pointInBounds(Point p) {
		if (layerPos.x < p.x && p.x < layerEndPos.x)
			if (layerPos.y < p.y && p.y < layerEndPos.y)
				return true;

		return false;
	}

	// hide() is used to mark a layer as hidden
	public void hide() {
		this.hidden = true;
	}

	// show() is used to mark a layer as visible
	public void show() {
		this.hidden = false;
	}

	public boolean isHidden() {
		return hidden;
	}

	// canResize() takes the x-coordiante and the y-coordinate and a spacingRange and returns if at this point we can resize and the type of resize that can be applied
	public Resize canResize(int x, int y, int spacingRange) {
		int x1 = getX();
		int y1 = getY();
		int x2 = getEndX();
		int y2 = getEndY();
		
		if (0 < x2-x && x2-x <= spacingRange && 0 < y2-y && y2-y <= spacingRange) return Resize.BOTTOMRIGHT;
		else if (0 < x-x1 && x-x1 <= spacingRange && 0 < y2-y && y2-y <= spacingRange) return Resize.BOTTOMLEFT;
		else if (0 < x-x1 && x2-x <= spacingRange && 0 < y-y1 && y-y1 <= spacingRange) return Resize.TOPRIGHT;
		else if (0 < x-x1 && x-x1 <= spacingRange && 0 < y-y1 && y-y1 <= spacingRange) return Resize.TOPLEFT;
		else if (x1+spacingRange < x && x < x2-spacingRange) {
			if (y1 < y && y < y1+spacingRange) return Resize.TOP;
			if (y2-spacingRange < y && y < y2) return Resize.BOTTOM;
		} else if (y1+spacingRange < y && y < y2-spacingRange) {
			if (x2-spacingRange < x && x < x2) return Resize.RIGHT;
			if (x1 < x && x < x1+spacingRange) return Resize.LEFT;
		} 
	
		return Resize.INVALID;
	}

	public boolean isSelectedForMerge() {
		return selectedForMerge;
	}

	// setSelectedForMerge() sets the layer to be ready for merge with other layers
	public void setSelectedForMerge(boolean selectedForMerge) {
		this.selectedForMerge = selectedForMerge;
	}

	// getSubImage is used to return a subImage from a layer based on 2 (x,y) coordinates
	public BufferedImage getSubImage(int x1, int y1, int x2, int y2) {
		int minX = Math.min(x1, x2);
		int minY = Math.min(y1, y2);

		try {
			return layer.getSubimage(
				Math.max(getX(minX), 0), // specifying the x-coordinate inside the layer or zero if the x-cordinate is negative
				Math.max(getY(minY), 0),  // specifying the y-coordinate inside the layer or zero if the y-coordinate is negative
				Math.min(Math.abs(x1-x2), layer.getWidth() - Math.max(getX(minX), 0)), // specifying the width. Width will be from the start point to the end of the layer in case the width is outisde the layer
				Math.min(Math.abs(y1-y2), layer.getHeight() - Math.max(getY(minY), 0)) // specfiying the height. Height will be from the start point to the end of the layer in case the height is outside the layer
			);
		} catch (Exception e) {
			return null; // In case where all coordinates are outisde the layer
		}
	}

	// zoom() is used to resize the layer based on a factor
	public void zoom(double factor) {
		int zoomedWidth = (int) Math.floor(factor * layer.getWidth());
		int zoomedHeight = (int) Math.floor(factor * layer.getHeight()); 
		System.out.println(zoomedWidth + " " + zoomedHeight);
		resize(zoomedWidth, zoomedHeight);
	}

	// resetLayerProperties() is used to copy all the properties of this layer to the layerCopy that is given as an argument 
	protected void resetLayerProperties(LayerData layerCopy) {
		if (hidden) layerCopy.hide();
		else layerCopy.show();

		layerCopy.setSelectedForMerge(selectedForMerge);
		layerCopy.setLocation(new Point(getX(), getY()));
		layerCopy.clear(Constants.transparentColor);
		layerCopy.mergeLayer(this.layer, 0, 0);
		layerCopy.updateSelectionLayer();
	}

	public String getLayerInfo(int layerPos) {
		return this.getLayerInfo(',', layerPos); // getLayerInfo with a default seperator of ','
	}

	// resize() & getCopy() are abastract and should be defined by every concrete Layer
	abstract void resize(int width, int height);
	abstract void resize(Point newLayerEndPos);
	abstract public LayerData getCopy();

	// getLayerInfo() is used to return a String containing all the information of a specific Layer and each piece of information if seperated by the seperator character
	abstract public String getLayerInfo(char seperator, int layerPos); 
}