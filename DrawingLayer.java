import java.awt.image.*;
import java.awt.*;

// The DrawingLayer is used to represent the Base/Background Layer 
class DrawingLayer extends LayerData {
	/**
	 * DrawingLayer is used to reprsent the Base/Background Layer on the Screen/Canvas
	 * @param layer is the image representing the drawing layer
	 */
	public DrawingLayer(BufferedImage layer) {super(layer);}
	
	/**
	 * DrawingLayer is used to reprsent the Base/Background Layer on the Screen/Canvas
	 * @param layer the image representing the drawing layer
	 * @param layerPos the layer position on the screen/canvas
	 */
	public DrawingLayer(BufferedImage layer, Point layerPos) {super(layer, layerPos);}

	/**
	 * DrawingLayer is used to reprsent the Base/Background Layer on the Screen/Canvas
	 * @param width the width of the Drawing Layer
	 * @param height the height of the Drawing Layer
	 * @param col the fill color of the Drawing Layer
	 */
	public DrawingLayer(int width, int height, Color col) {super(width, height, col);}
	
	/**
	 * DrawingLayer is used to reprsent the Base/Background Layer on the Screen/Canvas
	 * @param width the width of the Drawing Layer
	 * @param height the height of the Drawing Layer
	 * @param col the fill color of the Drawing Layer
	 * @param layerPos the layer position on the screen/canvas
	 */
	public DrawingLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	/
	public DrawingLayer getCopy() {
		DrawingLayer copy = new DrawingLayer(layerWidth(), layerHeight(), Color.white);
		resetLayerProperties(copy);
		return copy;
	}

	/**
	 * returns a String containg all the information of this drawing layer
	 * @param seperator each piece of information is seperated by the seperator character
	 * @param layerPos the position of the drawing layer on the Screen/Canvas
	 * @return a String containg the drawing layer information
	 */
	// getLayerInfo() returns a String containg all the information of this drawing layer
	public String getLayerInfo(char seperator, int layerPos) {
		String drawingSymbol = "d";
		String xCoord = Integer.toString(this.getX());
		String yCoord = Integer.toString(this.getY());
		String imgFilePath = Integer.toString(layerPos) + ".png";

		return 
			drawingSymbol + seperator + 
			xCoord + seperator + 
			yCoord + seperator + 
			imgFilePath + "\n";
	}

	public void resize(int width, int height) {}
	public void resize(Point newLayerEndPos) {}
}

/* 
	My Notes
		- Also it is a bad design decision to create a complete seperate class that doesn't offer any new functionality a Draw Layer is technically just a Special Type of an ImageLayer
		- The main reason I have created this class because there wer parts of the code logic that needed to identify the DrawingLayer that exists 
		  inside the layers Collection and the quickest was to achieve that was just to create a seperate class for the Drawing Layer
*/