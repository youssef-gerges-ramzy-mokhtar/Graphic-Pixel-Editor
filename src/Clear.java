import java.awt.event.*;

/** Clear Tool is used to clear everything in the canvas **/
public class Clear extends ClickableTool  implements CanvasObserver {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private Clickable clearBtn;

	/**
	 * Clear Tool is used to clear everything in the canvas
	 * @param layerObserver a layerObserver is an object that observers changes that happens to the layers structure
	 * @param canvas is the current canvas that holds all the layers
	 * @param undo is the tool that manages how the undo and redo works
	 */
	public Clear(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, undo);

		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.canvas = canvas;
		addCanvasListener();	
	}

	/**
	 * initTool initialize the properties of the Clear Tool
	 * - The Clear Tool Affects the Undo Tool
	 * - The Clear Tool Affects the Layers Panel
	 * @param undo is the tool that manages how the undo and redo works
	 */
	/*
		- The Clear Tool Affects the Undo Tool
		- The Clear Tool Affects the Layers Panel
	*/
	protected void initTool(UndoTool undo) {
		this.clearBtn = new Clickable("Clear Canvas");
		
		addToolBtn(clearBtn);
		setAsChangeMaker(undo);
		setAsLayerChanger();
	}

	/** addCanvasListener() attachs an Event Listener to the canvas **/
	private void addCanvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent mouse) {
				if (!clearBtn.isActive()) return;
				layersHandler.clear();

                recordChange();
				layersHandler.updateCanvas();
                updateLayerObserver();
            }
        });
	}

	public void update() {
		
	}	

    
}