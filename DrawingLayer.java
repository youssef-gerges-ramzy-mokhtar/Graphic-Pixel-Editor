import java.awt.image.*;
import java.awt.*;

// The DrawingLayer is used to represent the Base/Background Layer 
class DrawingLayer extends LayerData {
	public DrawingLayer(BufferedImage layer) {
		super(layer);
	}
	public DrawingLayer(BufferedImage layer, Point layerPos) {
		super(layer, layerPos);
	}
	public DrawingLayer(int width, int height, Color col) {
		super(width, height, col);
	}
	public DrawingLayer(int width, int height, Color col, Point layerPos) {
		super(width, height, col, layerPos);
	}

	public void resize(int width, int height) {}
	public void resize(Point newLayerEndPos) {}

	public DrawingLayer getCopy() {
		DrawingLayer copy = new DrawingLayer(layerWidth(), layerHeight(), Color.white);
		resetLayerProperties(copy);
		return copy;
	}

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
}

/* 
	My Notes
		- Also it is a bad design decision to create a complete seperate class that doesn't offer any new functionality a Draw Layer is technically just a Special Type of an ImageLayer
		- The main reason I have created this class because there wer parts of the code logic that needed to identify the DrawingLayer that exists 
		  inside the layers Collection and the quickest was to achieve that was just to create a seperate class for the Drawing Layer
*/