import java.awt.image.*;
import java.awt.*;

class ImageLayer extends LayerData {
	public ImageLayer(BufferedImage layer) {
		super(layer);
	}

	public ImageLayer(BufferedImage layer, Point layerPos) {
		super(layer, layerPos);
	}

	public ImageLayer(int width, int height, Color col) {
		super(width, height, col);
	}

	public ImageLayer(int width, int height, Color col, Point layerPos) {
		super(width, height, col, layerPos);
	}
}