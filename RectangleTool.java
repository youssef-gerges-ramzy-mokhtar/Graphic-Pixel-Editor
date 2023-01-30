import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

// RectangleTool is responsible for adding & handling Rectangles to the cavnas
class RectangleTool extends ShapeTool {
	public RectangleTool(OurCanvas canvas) {
		super(canvas);
		shapeBtn.setText("Rectangle");
	}

	protected SpecificGraphic getSpecificGrahic(LayerData shapeLayer, Point coords) {
		RectangleGraphics rectangleGraphics = new RectangleGraphics(shapeLayer.getCoords(coords));
		rectangleGraphics.setColor(strokeCol);
		rectangleGraphics.setDimensions(layerWidth, layerHeight);

		return rectangleGraphics;
	}

	protected LayerData createShapeLayer(Point layerPos) {
		RectangleLayer rectangleLayer = new RectangleLayer(layerWidth, layerHeight, Color.white, layerPos); // Color will change in the future
		rectangleLayer.setStrokeCol(strokeCol);
		return rectangleLayer;
	}
}