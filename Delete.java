import java.awt.event.*;
import java.awt.*;

// SelectionTool is used to move layers in the canvas
class Delete implements ClickableContainer {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private Clickable selectionBtn;

	public Delete(OurCanvas canvas) {
		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.selectionBtn = new Clickable("Delete Shape");

		addCanvasListener();
	}

	// addCanvasListener() attachs an Event Listener to the canvas
	private void addCanvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                canvas.clearCanvas();
                layersHandler.removeLayer(layersHandler.selectLayer(new Point(e.getX(), e.getY())));
                layersHandler.updateCanvas();
            }
        });
	}	

	public Clickable getClickable() {
		return selectionBtn;
	}
}