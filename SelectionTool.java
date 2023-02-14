import java.util.*;
import java.awt.event.*;
import java.awt.*;

// SelectionTool is used to move layers in the canvas
class SelectionTool extends ClickableTool {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private LayerData layerToMove;
	private Clickable selectionBtn;
	private boolean canDrag;
	private boolean changeMade;
	private final int spacingRange;

	public SelectionTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, undo);

		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.changeMade = false;
		this.spacingRange = 15;

		addCanvasListener();
	}

	protected void initTool(UndoTool undo) {
		this.selectionBtn = new Clickable("Selection");
		selectionBtn.addKeyBinding('v');
		
		addToolBtn(selectionBtn);
		setAsChangeMaker(undo);
		setAsLayerChanger();
	}

	// addCanvasListener() attachs an Event Listener to the canvas
	private void addCanvasListener() {
		// when the mouse is pressed a border should appear
		canvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (!selectionBtn.isActive()) return;

                layerToMove = layersHandler.selectLayer(new Point(e.getX(), e.getY()));
                if (layerToMove == null) {layersHandler.updateCanvas(); return;}

                layerToMove.drawBorder();
                refreshCanvasSelection(layerToMove);
                if (layerToMove.canResize(e.getX(), e.getY(), spacingRange) == Resize.BOTTOMRIGHT) canDrag = true;
                else canDrag = false;
            }

            public void mouseReleased(MouseEvent e) {
				try{
            	if (!selectionBtn.isActive()) return;

            	if (changeMade) {recordChange(); updateLayerObserver();}
            	changeMade = false;}catch(Exception exp){}

            }
        });

		// The layer that will be choose/selected will be decided by the layers handler class and the layers handler class will determine the layer to select based on the coordinates of the cursor
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (!selectionBtn.isActive()) return;
                if (layerToMove == null) return;

				try{
                if (canDrag) layerToMove.resize(new Point(e.getX(), e.getY()));
				else layerToMove.setLocation(e.getX() - layersHandler.getHorizontalOffset(), e.getY() - layersHandler.getVerticalOffset());

                refreshCanvasSelection(layerToMove);
                changeMade = true;
				}catch(Exception exc){}
            }

            public void mouseMoved(MouseEvent e) {
				try{
					if (!selectionBtn.isActive()) return;
					if (layerToMove == null) return;

					if (layerToMove.canResize(e.getX(), e.getY(), spacingRange) == Resize.BOTTOMRIGHT) {
						Cursor cursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR); 
						canvas.setCursor(cursor);
						return;
            	}}catch(Exception exc){}

			}
        });
	}

	private void refreshCanvasSelection(LayerData selectedLayer) {
		try{
		layersHandler.updateCanvasSelected(selectedLayer);}catch(Exception e){}
	}


	private boolean atCorner(int x, int y) {
		try{
		int cornerRange = 10;
		System.out.println(x - layerToMove.getX());
		System.out.println(y - layerToMove.getY());
		
		if(x - layerToMove.getX() <0 || y - layerToMove.getY() <0) return false; 
		if (Math.abs(x - layerToMove.getEndX()) <= cornerRange && Math.abs(y - layerToMove.getEndY()) <= cornerRange) return true;
		if (Math.abs(x - layerToMove.getX()) <= cornerRange && Math.abs(y - layerToMove.getY()) <= cornerRange) return true;
		
		return false; }catch(Exception e){return false;}
	}


	public CanvasObserver getCanvasObserver() {
		return layersHandler;
	}

	public LayersHandler getLayerHandler() {
		return layersHandler;
	}	

	public ArrayList<Clickable> getClickable() {
		ArrayList<Clickable> selectionToolBtn = new ArrayList<Clickable>();
		selectionToolBtn.add(selectionBtn);
		return selectionToolBtn;
	}
}