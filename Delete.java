import java.awt.event.*;
import java.awt.*;

/** DeleteTool is used to delete shapes and layers in the canvas **/
class Delete extends ClickableTool {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private Clickable deleteBtn;

	/**
	 * Delete Tool is used to delete shapes and layers in the canvas 
	 * @param layerObserver a layerObserver is an object that observers changes that happens to the layers structure
	 * @param canvas is the current canvas that holds all the layers
	 * @param undo is the tool that manages how the undo and redo works
	 */
	public Delete(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, undo);

		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.canvas = canvas;
		addCanvasListener();	
	}

	/**
	 * initTool initialize the properties of the Delete Tool
	 * - The Delete Tool Affects the Undo Tool
	 * - The TDelete Tool Affects the Layers Panel
	 * @param undo is the tool that manages how the undo and redo works
	 */
	protected void initTool(UndoTool undo) {
		this.deleteBtn = new Clickable("Delete Shape");
		deleteBtn.addKeyBinding('d');
		
		addToolBtn(deleteBtn);
		setAsChangeMaker(undo);
		setAsLayerChanger();
	}

	/** 
	 * addCanvasListener() attachs an Event Listener to the canvas
	 */
	private void addCanvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
				if (!deleteBtn.isActive()) return;
				if (layersHandler.selectLayer(new Point(e.getX(), e.getY())) == null) return;
                layersHandler.removeLayer(layersHandler.selectLayer(new Point(e.getX(), e.getY())));
                layersHandler.updateCanvas();

                recordChange();
                updateLayerObserver();
            }
        });
	}	
}