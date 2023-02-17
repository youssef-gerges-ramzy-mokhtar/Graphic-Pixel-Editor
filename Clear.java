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
            public void mousePressed(MouseEvent e) {
				if (!clearBtn.isActive()) return;
				if(layersHandler.getLayersCount() <= 1) return;

				layers = layersHandler.getLayers();
				ArrayList<LayerData> tempList = new ArrayList<>(); 
					for(LayerData i : layers){
						if (i != layersHandler.getDrawingLayer()){
							layersHandler.getDrawingLayer().mergeLayer(i);
							tempList.add(i);
							updateLayerObserver();
						}
					}

				for(LayerData i: tempList){
					layersHandler.removeLayer(i);
				}
				layersHandler.getDrawingLayer().clear(Color.white);

                recordChange();
				layersHandler.updateCanvas();
                updateLayerObserver();
            }
        });
	}

	public void update() {
		
	}	

    
}