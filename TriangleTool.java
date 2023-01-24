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
		TriangleGraphics.setLen(layerWidth);

		return TriangleGraphics;
	}
}