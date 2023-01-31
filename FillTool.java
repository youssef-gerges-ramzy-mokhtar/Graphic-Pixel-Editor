import java.awt.*;
import java.awt.event.*;
import java.util.*;

// FillTool is used to pour large areas of paint onto the Canvas that expand until they find a border they cannot flow over.
class FillTool extends ChangeMaker implements Observer, ClickableContainer {
    private OurCanvas canvas;
    private Clickable fillBtn;
	private Color barrierCol;
	private Color fillCol;
    private LayersHandler layersHandler;

	public FillTool(OurCanvas canvas, UndoTool undo) {
		super(undo);
		this.canvas = canvas;
		this.fillBtn = new Clickable("Fill");
		this.barrierCol = Color.white;
		this.fillCol = Color.black;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		addCanvasListener();
	}

	// addCanvasListener() is used to attach an Event Listener to the canvas
	// Fill Tool is used on each separate layer, and the barrier color will be the color at the cursor coordinates
	// And when the Fill Process is finished we use update the canvas using the layersHandler
	private void addCanvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent mouse) {
            	if (!fillBtn.isActive()) return;

            	LayerData selectedLayer = layersHandler.getSelectedLayer();
            	Point selectedLayerCoords = new Point(selectedLayer.getX(mouse.getX()), selectedLayer.getY(mouse.getY()));
            	if (selectedLayer.getPixel((int) selectedLayerCoords.getX(), (int) selectedLayerCoords.getY()) == null) return;

            	barrierCol = new Color(selectedLayer.getPixel((int) selectedLayerCoords.getX(), (int) selectedLayerCoords.getY())); // barrierCol represents the color that should be changed to the fill color and any othe color on the canvas that is not equal to the barrierCol then it is a block and we can't fill it
            	fillCanvas((int) selectedLayerCoords.getX(), (int) selectedLayerCoords.getY()); // Starting Position of Flood Fill
				selectedLayer.updateSelectionLayer();
				layersHandler.updateCanvas();

				recordChange();
            }
        });
	}

	// fillCanvas() takes in the x,y coordinates and keeps filling pixels in an enclosed bounded area
	private void fillCanvas(int x, int y) {
		LayerData selectedLayer = layersHandler.getSelectedLayer();

		// Iterative Based Solution (Because Recursion overflowed the stack)
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
			if (selectedLayer.getPixel(x_pos, y_pos) != barrierCol.getRGB()) continue; // reached a block

			selectedLayer.setPixel(x_pos, y_pos, fillCol.getRGB());

			{x_coord.push(x_pos + 1); y_coord.push(y_pos);}
			{x_coord.push(x_pos - 1); y_coord.push(y_pos);}
			{x_coord.push(x_pos); y_coord.push(y_pos + 1);}
			{x_coord.push(x_pos); y_coord.push(y_pos - 1);}
		}
	}

	public ArrayList<Clickable> getClickable() {
		ArrayList<Clickable> fillToolBtn = new ArrayList<Clickable>();
		fillToolBtn.add(fillBtn);
		return fillToolBtn;
	}

	// Observer Pattern
    public void update(int val) {}
    
    // update2() is used to change the fill color whenver the Eye Dropper Tool or the Color Picker are used
    public void update2(Color col) {
    	fillCol = col;
    }
    public void update3() {}
}