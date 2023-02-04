import java.awt.image.*;
import java.awt.*;

class TextLayer extends ShapeLayer {
	private String text;

	public TextLayer(int width, int height, Color col, String text) {super(width, height, col); this.text = text;}
	public TextLayer(int width, int height, Color col, Point layerPos, String text) {super(width, height, col, layerPos); this.text = text;}

	public SpecificGraphic getSpecificGraphic(int width, int height) {
		TextGraphics textGraphics = new TextGraphics(new Point(0, 0), text, 700);
		textGraphics.setFontColor(Color.black);
		textGraphics.setDimensions(layerWidth(), layerHeight());

		return textGraphics;
	}

	public TextLayer getShapeLayerCopy() {
		TextLayer copy = new TextLayer(layerWidth(), layerHeight(), Color.white, text);
		return copy;
	}

	public void resize(Point newLayerEndPos) {

	}

	// public LayerData getCopy() {
	// 	TextLayer copy = new TextLayer(width, height, Color.black, text);
	// 	copy.clear(new Color(0, 0, 0, 0));

	// 	copy.mergeLayer(this.getImage(), 0, 0);
	// 	copy.setLocation(new Point(getX(), egetY()));
	// 	copy.updateSelectionLayer();
	// 	return copy;
	// }
}