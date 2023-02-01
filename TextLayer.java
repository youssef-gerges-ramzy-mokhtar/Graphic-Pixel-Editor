import java.awt.image.*;
import java.awt.*;

class TextLayer extends ShapeLayer {
	public TextLayer(int width, int height, Color col) {super(width, height, col);}
	public TextLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	public SpecificGraphic getSpecificGraphic(int width, int height) {
		TextGraphics textGraphics = new TextGraphics(new Point(0, 0));
		textGraphics.setColor(strokeCol);
	
		return textGraphics;
	}

	public TextLayer getShapeLayerCopy() {
		TextLayer copy = new TextLayer(layerWidth(), layerHeight(), Color.white);
		return copy;
	}
}