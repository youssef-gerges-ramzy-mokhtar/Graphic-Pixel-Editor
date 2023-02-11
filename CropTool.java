import java.util.*;
import java.awt.event.*;
import java.awt.*;

class CropTool extends ClickableTool {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private LayerData layerToCrop;
	private Clickable cropBtn;
	private boolean changeMade;
	private Resize cropType;
	private final int spacingRange;

	public CropTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, undo);

		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.changeMade = false;
		this.spacingRange = 15;

		addCanvasListener();
	}

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
			public void mousePressed(MouseEvent e) {
				if (!cropBtn.isActive()) return;

				layerToCrop = layersHandler.selectLayer(new Point(e.getX(), e.getY()));
				if (layerToCrop == null) {layersHandler.updateCanvas(); return;}

				layerToCrop.drawBorder();
				layersHandler.updateCanvasSelected(layerToCrop);

				cropType = layerToCrop.canResize(e.getX(), e.getY(), spacingRange);
			}

			public void mouseReleased(MouseEvent e) {
				if (!cropBtn.isActive()) return;
				if (changeMade) {recordChange(); updateLayerObserver();}
				changeMade = false;
			}
		});

		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
                if (!cropBtn.isActive()) return;
                if (layerToCrop == null) return;
                if (cropType == Resize.INVALID) return;

                layerToCrop.crop(new Point(e.getX(), e.getY()), cropType);
				layersHandler.updateCanvasSelected(layerToCrop);
                changeMade = true;
            }
		
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