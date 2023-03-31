import java.util.*;
import java.awt.event.*;
import java.awt.*;

// SelectionTool is used to move & resize layers in the canvas
class SelectionTool extends ClickableTool {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private LayerData layerToMove;
	private Clickable selectionBtn;
	private boolean canDrag;
	private boolean changeMade;
	private final int spacingRange;

	/**
	 * SelectionTool is used to move & resize layers in the canvas
	 * @param layerObserver a layerObserver is an object that observers changes that happens to the layers structure
	 * @param canvas is the current canvas that holds all the layers
	 * @param undo is the tool that manages how the undo and redo works
	 */
	public SelectionTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, undo);

		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.changeMade = false;
		this.spacingRange = 15;

		addCanvasListener();
	}

	/**
	 * initTool initialize the properties of the Selection Tool
	 * - The Selection Tool Affects the Undo Tool
	 * - The Selection Tool Affects the Layers Panel
	 * @param undo is the tool that manages how the undo and redo works
	 */
	// initTool initialize the properties of the Selection Tool
	/*
		- The Selection Tool Affects the Undo Tool
		- The Selection Tool Affects the Layers Panel
		- The Selection Tool has shortcut 'v'
	*/
	protected void initTool(UndoTool undo) {
		this.selectionBtn = new Clickable("Selection");
		selectionBtn.addKeyBinding('v');
		
		addToolBtn(selectionBtn);
		setAsChangeMaker(undo);
		setAsLayerChanger();
	}

	// addCanvasListener() attachs an Event Listener to the canvas
	private void addCanvasListener() {
		// mousePressed is used to select the layer
		canvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (!selectionBtn.isActive()) return;

                layerToMove = layersHandler.selectLayer(new Point(e.getX(), e.getY()));
                if (layerToMove == null) {layersHandler.updateCanvas(); return;} // this means that the user clicked at a point where no layers are present

                layerToMove.drawBorder(); // draws a border on the layer to give the feeling that it is selected
                refreshCanvasSelection(layerToMove); // refershes the canvas to show the selected layer

                // Here we are checking if the point where the user have clicked it the Bottom Right Corner if that is the case then the user can drag and resize the layer else the user only wants to move the layer around
                if (layerToMove.canResize(e.getX(), e.getY(), spacingRange) == Resize.BOTTOMRIGHT) canDrag = true;
                else canDrag = false;
            }

			// mouseReleased is used to record the change for the undo tool and update the Layers Panel
            public void mouseReleased(MouseEvent e) {
				try {
	            	if (!selectionBtn.isActive()) return;
	            	if (changeMade) {recordChange(); updateLayerObserver();}
            		changeMade = false;
            	} catch(Exception exp) {}
            }
        });

		// The layer that will be choose/selected will be decided by the layers handler class and the layers handler class will determine the layer to select based on the coordinates of the cursor
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (!selectionBtn.isActive()) return;
                if (layerToMove == null) return;

				try {
	                if (canDrag) layerToMove.resize(new Point(e.getX(), e.getY())); // if we can drag then we resize the layer
					else layerToMove.setLocation(e.getX() - layersHandler.getHorizontalOffset(), e.getY() - layersHandler.getVerticalOffset()); // here we are just moving the layer

	                refreshCanvasSelection(layerToMove); // and after changing the layer we are refreshing the canvas to display the change that have taken place
	                changeMade = true;
				} catch(Exception exc) {}
            }

			// mouseMoved is mainly used to change the cursor simply to indicate to the user that he can resize the current layer or not
            public void mouseMoved(MouseEvent e) {
				try {
					if (!selectionBtn.isActive()) return;
					if (layerToMove == null) return;

					if (layerToMove.canResize(e.getX(), e.getY(), spacingRange) == Resize.BOTTOMRIGHT) {
						Cursor cursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR); 
						canvas.setCursor(cursor);
						return;
            		}
            	} catch(Exception exc) {}

			}
        });
	}

	private void refreshCanvasSelection(LayerData selectedLayer) {
		try {
			layersHandler.updateCanvasSelected(selectedLayer); // refresh the canvas to the display any changes that have been made
		} catch(Exception e) {}
	}

	/**
	 * gets the Layers Handler
	 * @return layers handler
	 */
	public LayersHandler getLayerHandler() {
		return layersHandler;
	}
}