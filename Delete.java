import java.util.*;
import java.awt.event.*;
import java.awt.*;

/** DeleteTool is used to delete shapes and layers in the canvas **/
class Delete extends ClickableTool {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private Clickable deleteBtn;

	public Delete(OurCanvas canvas, UndoTool undo) {
		super(undo);

		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.canvas = canvas;
		addCanvasListener();	
	}

	protected void initTool(UndoTool undo) {
		this.deleteBtn = new Clickable("Delete Shape");
		deleteBtn.addKeyBinding('d');
		
		addToolBtn(deleteBtn);
		setAsChangeMaker(undo);
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
}