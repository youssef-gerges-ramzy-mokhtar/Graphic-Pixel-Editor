import java.awt.image.*;
import java.awt.*;

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

}