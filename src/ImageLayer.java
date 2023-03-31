import java.awt.image.*;
import java.awt.*;

// ImageLayer simply represents any Image Layer on the Canvas
public class ImageLayer extends LayerData {
	/**
	 * ImageLayer is used to reprsent any Image on the Screen/Canvas
	 * @param layer is the image representing the image layer
	 */
	public ImageLayer(BufferedImage layer) {super(layer);}

	/**
	 * ImageLayer is used to reprsent any Image on the Screen/Canvas
	 * @param layer is the image representing the image layer
	 * @param layerPos the layer position on the screen/canvas
	 */
	public ImageLayer(BufferedImage layer, Point layerPos) {super(layer, layerPos);}
	
	/**
	 * ImageLayer is used to reprsent any Image on the Screen/Canvas
	 * @param width the width of the Image Layer
	 * @param height the height of the Image Layer
	 * @param col the fill color of the Image Layer
	 */
	public ImageLayer(int width, int height, Color col) {super(width, height, col);}
	
	/**
	 * ImageLayer is used to reprsent any Image on the Screen/Canvas
	 * @param width the width of the Image Layer
	 * @param height the height of the Image Layer
	 * @param col the fill color of the Image Layer
	 * @param layerPos the layer position on the screen/canvas
	 */
	public ImageLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	/**
	 * resizes the image layer to the desired width and height
	 * @param width the new width you want to resize the image layer to
	 * @param height the new height you want to resize the image layer to
	 */ 
	// resize() is used to represent the layer to the specified width and height
	public void resize(int width, int height) {
		if (width < 15 || height < 15) return;

		Image scaledImg = originalLayer.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		layer = new BufferedImage(width, height, layer.getType());
		Graphics2D g2d = getLayerGraphics();
		g2d.drawImage(scaledImg, 0, 0, null);

		updateSelectionLayer();
	}

	/**
	 * used to resize the image layer to reach the desired coordinate on the screen/canvas
	 * @param newLayerEndPos the new coordinate in which you want the image layer to scale to
	 */
	// resize() is used to get a coordinate on the canvas and resize the layer to reach this coordinate
	public void resize(Point newLayerEndPos) {
		int layerWidth = Math.abs(newLayerEndPos.x - getX());
		int layerHeight = Math.abs(newLayerEndPos.y - getY());
		if(layerWidth < 15 || newLayerEndPos.x - getX() < 0) layerWidth = 15;
		if(layerHeight < 15 || newLayerEndPos.y - getY() < 0) layerHeight = 15;
		
		resize(layerWidth, layerHeight);
		
		if(newLayerEndPos.x - getX() > 15 && newLayerEndPos.y -getY() > 15)
		setLocation(validTopLeftPoint(getCoords(), newLayerEndPos));
	}

	/**
	 * returns a Deep Copy of the Image Layer
	 * @return returns a Deep Copy of the Image Layer
	 */
	// getCopy return a Deep Copy of the Image Layer
	public ImageLayer getCopy() {
		ImageLayer copy = new ImageLayer(layerWidth(), layerHeight(), Constants.transparentColor);
		resetLayerProperties(copy);
		return copy;
	}

	/**
	 * returns a String containg all the information of this image layer
	 * @param seperator each piece of information is seperated by the seperator character
	 * @param layerPos the position of the image layer on the Screen/Canvas
	 * @return a String containg the image layer information
	 */
	// getLayerInfo() returns a String containg all the information of this image layer
	public String getLayerInfo(char seperator, int layerPos) {
		String imgSymbol = "i";
		String xCoord = Integer.toString(this.getX());
		String yCoord = Integer.toString(this.getY());
		String imgFilePath = Integer.toString(layerPos) + ".png";

		return 
			imgSymbol + seperator + 
			xCoord + seperator + 
			yCoord + seperator +
			imgFilePath + "\n";
	}
}