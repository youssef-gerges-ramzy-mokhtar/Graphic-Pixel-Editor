import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// Will EyeDropper Hadle Individual Layers or Will be used for the main Canvas Layer
class FillGui extends Clickable implements Observable, Observer {
	private Color col;
	private Color fillCol = Color.black;
    private LayersHandler layersHandler;

	public FillGui(OurCanvas canvas, LayersHandler layersHandler) {
		super(canvas);
		
		col = Color.white;
		btn.setText("Fill");
		
		this.layersHandler = layersHandler;
		addCanvasListener();
	}

	private void addCanvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent mouse) {
            	LayerData selectedLayer = layersHandler.getSelectedLayer();
            	Point selectedLayerCoords = new Point(selectedLayer.getX(mouse.getX()), selectedLayer.getY(mouse.getY()));
            
            	if (!btnActive) return;
            	if (selectedLayer.getPixel((int) selectedLayerCoords.getX(), (int) selectedLayerCoords.getY()) == null) return;

            	col = new Color(selectedLayer.getPixel((int) selectedLayerCoords.getX(), (int) selectedLayerCoords.getY()));
            	fillCanvas((int) selectedLayerCoords.getX(), (int) selectedLayerCoords.getY()); // Starting Position of Flood Fill
				layersHandler.updateCanvas();
            }
        });
	}

	private void fillCanvas(int x, int y) {
		LayerData selectedLayer = layersHandler.getSelectedLayer();

		// Iterative Based Solution
		Stack<Integer> x_coord = new Stack<Integer>(); 
		Stack<Integer> y_coord = new Stack<Integer>(); 
		x_coord.push(x);
		y_coord.push(y);

		while (!x_coord.empty()) {
			int x_pos = x_coord.pop();
			int y_pos = y_coord.pop();

			// Base Cases
			if (selectedLayer.getPixel(x_pos, y_pos) == null) continue; // invalid coordinates
			if (selectedLayer.getPixel(x_pos, y_pos) == fillCol.getRGB()) continue; // already visited
			if (selectedLayer.getPixel(x_pos, y_pos) != col.getRGB()) continue; // reached a block

			selectedLayer.setPixel(x_pos, y_pos, fillCol.getRGB());

			{x_coord.push(x_pos + 1); y_coord.push(y_pos);}
			{x_coord.push(x_pos - 1); y_coord.push(y_pos);}
			{x_coord.push(x_pos); y_coord.push(y_pos + 1);}
			{x_coord.push(x_pos); y_coord.push(y_pos - 1);}
		}
	}

	// Observer Pattern
    public void update(int val) {}
    public void update2(Color col) {
    	fillCol = col;
    }
    public void update3() {}
}