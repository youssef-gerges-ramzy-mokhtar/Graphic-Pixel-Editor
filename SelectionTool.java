import java.awt.event.*;
import java.awt.*;

// SelectionTool is used to move layers in the canvas
class SelectionTool implements ClickableContainer {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private LayerData imgToMove;
	private Clickable selectionBtn;

	public SelectionTool(OurCanvas canvas) {
		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.selectionBtn = new Clickable("Selection Tool");

		addCanvasListener();
	}

	// addCanvasListener() attachs an Event Listener to the canvas
	private void addCanvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                imgToMove = layersHandler.selectLayer(new Point(e.getX(), e.getY()));
            }
        });

		// The layer that will be choose/selected will be decided by the layers handler class and the layers handler class will determine the layer to select based on the coordinates of the cursor
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (!selectionBtn.isActive()) return;
                if (imgToMove == null) return;

	            canvas.clearCanvas();
                imgToMove.setLocation(e.getX() - layersHandler.getHorizontalOffset(), e.getY() - layersHandler.getVerticalOffset());
                layersHandler.updateCanvas();
            }
        });
	}

	public CanvasObserver getCanvasObserver() {
		return layersHandler;
	}

	public LayersHandler getLayerHandler() {
		return layersHandler;
	}	

	public Clickable getClickable() {
		return selectionBtn;
	}
}