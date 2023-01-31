import java.util.*;
import java.awt.event.*;
import java.awt.*;

/** DeleteTool is used to delete shapes and layers in the canvas **/
class Delete implements ClickableContainer {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private Clickable deleteBtn;

	public Delete(OurCanvas canvas) {
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.canvas = canvas;
		this.deleteBtn = new Clickable("Delete Shape");
		addCanvasListener();	
	}
	/** addCanvasListener() attachs an Event Listener to the canvas **/
	private void addCanvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
				if (!deleteBtn.isActive()) return;
                canvas.clearCanvas();
                layersHandler.removeLayer(layersHandler.selectLayer(new Point(e.getX(), e.getY())));
                layersHandler.updateCanvas();
            }
        });
	}	

	/** return clickable for this feature **/
	public ArrayList<Clickable> getClickable() {
		ArrayList<Clickable> deleteToolBtn = new ArrayList<Clickable>();
		deleteToolBtn.add(deleteBtn);
		return deleteToolBtn;
	}
}