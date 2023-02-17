import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*;

/** DeleteTool is used to delete shapes and layers in the canvas **/
class Clear extends ClickableTool  implements CanvasObserver {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private Clickable clearBtn;
	private ArrayList<LayerData> layers;

	public Clear(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, undo);

		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.canvas = canvas;
		addCanvasListener();	
	}

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