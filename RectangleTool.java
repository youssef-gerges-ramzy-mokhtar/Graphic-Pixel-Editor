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
		rectangleGraphics.setLen(layerWidth);

		return rectangleGraphics;
	}
}