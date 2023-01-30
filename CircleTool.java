import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

// CircleTool is responsible for adding & handling Circles to the cavnas
class CircleTool extends ShapeTool {
	public CircleTool(OurCanvas canvas) {
		super(canvas);
		shapeBtn.setText("Circle");
	}

	protected SpecificGraphic getSpecificGrahic(LayerData shapeLayer, Point coords) {
		CircleGraphics circleGraphics = new CircleGraphics(shapeLayer.getCoords(coords));
		circleGraphics.setColor(strokeCol);
		circleGraphics.setDimension(layerWidth, layerHeight);

		return circleGraphics;
	}

	protected LayerData createShapeLayer(Point layerPos) {
		CircleLayer circleLayer = new CircleLayer(layerWidth, layerHeight, Color.white, layerPos); // Color will change in the future
		circleLayer.setStrokeCol(strokeCol);
		return circleLayer;
	}
}