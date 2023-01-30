import javax.swing.*;
import java.awt.event.*;
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

	protected SpecificGraphic getSpecificGrahic(LayerData shapeLayer, Point coords) {
        String dropText = JOptionPane.showInputDialog(null, "Please enter the text");

		TextGraphics textGraphics = new TextGraphics(shapeLayer.getCoords(coords));
        textGraphics.setText(dropText);
		textGraphics.setColor(strokeCol);
		textGraphics.setLen(layerWidth);

		return textGraphics;
	}

	protected LayerData createShapeLayer(Point layerPos) {
		TextLayer textLayer = new TextLayer(layerWidth, layerHeight, Color.white, layerPos); // Color will change in the future
		return textLayer;
	}
}