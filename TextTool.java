import java.awt.*;
import java.util.*;
import java.io.*;
import javax.swing.JOptionPane; 



// RectangleTool is responsible for adding & handling Rectangles to the cavnas
class TextTool extends ShapeTool {
	public TextTool(OurCanvas canvas) {
		super(canvas);
		shapeBtn.setText("Text");
	}

	protected SpecificGraphic getSpecificGrahic(ShapeLayer shapeLayer, Point coords) {
		System.out.println("HI");
        String dropText = JOptionPane.showInputDialog(null, "Please enter the text");

		TextGraphics textGraphics = new TextGraphics(shapeLayer.getCoords(coords));
		textGraphics.setColor(strokeCol);
        textGraphics.setText(dropText);
		textGraphics.setLen(layerWidth);

		return textGraphics;
	}

	protected ShapeLayer createShapeLayer(Point layerPos) {
		TextLayer textLayer = new TextLayer(layerWidth, layerHeight, Color.white, layerPos); // Color will change in the future
		return textLayer;
	}
}