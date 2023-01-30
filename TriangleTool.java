import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

// TriangleTool is responsible for adding & handling Triangles to the cavnas
class TriangleTool extends ShapeTool {
	public TriangleTool(OurCanvas canvas) {
		super(canvas);
		shapeBtn.setText("Triangle");
	}

	protected SpecificGraphic getSpecificGrahic(LayerData shapeLayer, Point coords) {
		TriangleGraphics TriangleGraphics = new TriangleGraphics(shapeLayer.getCoords(coords));
		TriangleGraphics.setColor(strokeCol);
		TriangleGraphics.setDimension(layerWidth, layerHeight);

		return TriangleGraphics;
	}

	protected LayerData createShapeLayer(Point layerPos) {
		TriangleLayer triangleLayer = new TriangleLayer(layerWidth, layerHeight, Color.white, layerPos); // Color will change in the future
		triangleLayer.setStrokeCol(strokeCol);
		return triangleLayer;
	}
}