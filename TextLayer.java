import java.awt.image.*;
import java.awt.*;

class TextLayer extends LayerData {
	public TextLayer(BufferedImage layer) {
		super(layer);
	}

	public TextLayer(BufferedImage layer, Point layerPos) {
		super(layer, layerPos);
	}

	public TextLayer(int width, int height, Color col) {
		super(width, height, col);
	}

	public TextLayer(int width, int height, Color col, Point layerPos) {
		super(width, height, col, layerPos);
	}
}