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
		circleGraphics.setLen(layerWidth);

		return circleGraphics;
	}
}