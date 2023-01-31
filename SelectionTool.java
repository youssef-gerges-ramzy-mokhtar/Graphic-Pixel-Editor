import java.awt.event.*;
import java.awt.*;

// SelectionTool is used to move layers in the canvas
class SelectionTool extends ChangeMaker implements ClickableContainer {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private LayerData imgToMove;
	private Clickable selectionBtn;
	private boolean canDrag;
	private boolean changeMade;

	public SelectionTool(OurCanvas canvas, UndoTool undo) {
		super(undo);
		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.selectionBtn = new Clickable("Selection Tool");
		this.changeMade = false;

		addCanvasListener();
	}

	// addCanvasListener() attachs an Event Listener to the canvas
	private void addCanvasListener() {
		// when the mouse is pressed a border should appear
		canvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (!selectionBtn.isActive()) return;

                imgToMove = layersHandler.selectLayer(new Point(e.getX(), e.getY()));
                if (imgToMove == null) return;

                imgToMove.drawBorder();
                refreshCanvasSelection(imgToMove);
                if (atCorner(e.getX(), e.getY())) canDrag = true;
                else canDrag = false;
            }

            public void mouseReleased(MouseEvent e) {
            	if (!selectionBtn.isActive()) return;
            	if (changeMade) recordChange();
            	changeMade = false;
            }
        });

		// The layer that will be choose/selected will be decided by the layers handler class and the layers handler class will determine the layer to select based on the coordinates of the cursor
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (!selectionBtn.isActive()) return;
                if (imgToMove == null) return;

                if (canDrag) imgToMove.resize(new Point(e.getX(), e.getY()));
				else imgToMove.setLocation(e.getX() - layersHandler.getHorizontalOffset(), e.getY() - layersHandler.getVerticalOffset());

                refreshCanvasSelection(imgToMove);
                changeMade = true;
            }

            public void mouseMoved(MouseEvent e) {
            	if (!selectionBtn.isActive()) return;
            	if (imgToMove == null) return;

            	if (atCorner(e.getX(), e.getY())) {
					Cursor cursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR); 
     				canvas.setCursor(cursor);
					return;
            	}

				canvas.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));				
			}
        });
	}

	private void refreshCanvasSelection(LayerData selectedLayer) {
		layersHandler.updateCanvasSelected(selectedLayer);
	}

	private boolean atCorner(int x, int y) {
		int cornerRange = 10;
		if (Math.abs(x - imgToMove.getEndX()) <= cornerRange && Math.abs(y - imgToMove.getEndY()) <= cornerRange) return true;
		if (Math.abs(x - imgToMove.getX()) <= cornerRange && Math.abs(y - imgToMove.getY()) <= cornerRange) return true;

		return false;
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