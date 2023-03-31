import java.awt.event.*;
import java.awt.*;

// Crop Tool is simply used to represent the Crop Tool
class CropTool extends ClickableTool {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private LayerData layerToCrop;
	private Clickable cropBtn;
	private boolean changeMade;
	private Resize cropType;
	private final int spacingRange; // spacingRange is used to specify the amount of space from the cursor to the layer that the user is allowed to move within to crop a layer

	/**
	 * CropTool is simply used to reprsent the Crop Tool
	 * @param layerObserver a layerObserver is an object that observers changes that happens to the layers structure
	 * @param canvas is the current canvas that holds all the layers
	 * @param undo is the tool that manages how the undo and redo works
	 */
	public CropTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, undo);

		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.changeMade = false;
		this.spacingRange = 15;

		addCanvasListener();
	}

	/**
	 * initTool initialize the properties of the Crop Tool
	 * - The Crop Tool Affects the Undo Tool
	 * - The Crop Tool Affects the Layers Panel
	 * - The Crop Tool Rasterizes Shape Layers
	 * @param undo is the tool that manages how the undo and redo works
	 */
	// initTool initialize the properties of the Crop Tool
	/*
		- The Crop Tool Affects the Undo Tool
		- The Crop Tool Affects the Layers Panel
		- The Crop Tool Rasterizes Shape Layers
		- The Crop Tool has shortcut 'w'
	*/
	protected void initTool(UndoTool undo) {
		this.cropBtn = new Clickable("Crop");
		cropBtn.addKeyBinding('w');

		addToolBtn(cropBtn);
		setAsChangeMaker(undo);
		setAsShapeRasterizer();
		setAsLayerChanger();
	}

	private void addCanvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
			// mousePressed is used to select the layer that is going to be cropped
			public void mousePressed(MouseEvent e) {
				if (!cropBtn.isActive()) return;

				layerToCrop = layersHandler.selectLayer(new Point(e.getX(), e.getY()));
				if (layerToCrop == null) {layersHandler.updateCanvas(); return;} // this means that the user clicked at a point where no layers are present

				// if a layer is a shape layer then we rasterize the shape Layer
				if (layerToCrop instanceof ShapeLayer) {
					layerToCrop = rasterizeLayer(layerToCrop, layersHandler);
					layersHandler.updateCanvas();
					return;
				}

				layerToCrop.drawBorder(); // draws a border on the layer to give the feeling that it is selected
				layersHandler.updateCanvasSelected(layerToCrop); // refershes the canvas to show the selected layer

				cropType = layerToCrop.canResize(e.getX(), e.getY(), spacingRange); // cropType stores the position that we will crop from
			}

			// mouseReleased is used to record the change for the undo tool and update the Layers Panel
			public void mouseReleased(MouseEvent e) {
				if (!cropBtn.isActive()) return;
				if (changeMade) {recordChange(); updateLayerObserver();}
				changeMade = false;
			}
		});

		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			// mouseDragged is used to crop the layer and refresh the canvas to display the change sthat has been made
			public void mouseDragged(MouseEvent e) {
                if (!cropBtn.isActive()) return;
                if (layerToCrop == null) return;
                if (cropType == Resize.INVALID) return;

                layerToCrop.crop(new Point(e.getX(), e.getY()), cropType);
				layersHandler.updateCanvasSelected(layerToCrop);
                changeMade = true;
            }
		
			// mouseMoved is mainly used to change the cursor simply to indicate to the user that he can crop the current layer or not
            public void mouseMoved(MouseEvent e) {
	        	if (!cropBtn.isActive()) return;
	        	if (layerToCrop == null) return;

	        	Cursor cursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);

	        	cropType = layerToCrop.canResize(e.getX(), e.getY(), spacingRange);
	        	if (cropType == Resize.BOTTOMRIGHT || cropType == Resize.TOPLEFT) 
	        		cursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR); 
	        	
	        	if (cropType == Resize.BOTTOMLEFT || cropType == Resize.TOPRIGHT) 
	        		cursor = Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
	        	
	        	if (cropType == Resize.TOP || cropType == Resize.BOTTOM) 
	        		cursor = Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
	        	
	        	if (cropType == Resize.RIGHT || cropType == Resize.LEFT) 
	        		cursor = Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);

				canvas.setCursor(cursor);				
			}
		});
	}
}