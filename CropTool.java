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

	public CropTool(OurCanvas canvas, UndoTool undo) {
		super(undo);

		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.changeMade = false;

		addCanvasListener();
	}

	protected void initTool(UndoTool undo) {
		this.cropBtn = new Clickable("Crop");
		cropBtn.addKeyBinding('w');

		addToolBtn(cropBtn);
		setAsChangeMaker(undo);
	}

	private void addCanvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!cropBtn.isActive()) return;

				layerToCrop = layersHandler.selectLayer(new Point(e.getX(), e.getY()));
				if (layerToCrop == null) return;

				layerToCrop.drawBorder();
				layersHandler.updateCanvasSelected(layerToCrop);

				cropType = atCorner(e.getX(), e.getY());
			}

			public void mouseReleased(MouseEvent e) {
				if (!cropBtn.isActive()) return;
				if (changeMade) recordChange();
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

	        	cropType = atCorner(e.getX(), e.getY());
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

	private Resize atCorner(int x, int y) {
		int x1 = layerToCrop.getX();
		int y1 = layerToCrop.getY();
		int x2 = layerToCrop.getEndX();
		int y2 = layerToCrop.getEndY();
	
		if (0 < x2-x && x2-x <= 5 && 0 < y2-y && y2-y <= 5) return Resize.BOTTOMRIGHT;
		else if (0 < x-x1 && x-x1 <= 5 && 0 < y2-y && y2-y <= 5) return Resize.BOTTOMLEFT;
		else if (0 < x-x1 && x2-x <= 5 && 0 < y-y1 && y-y1 <= 5) return Resize.TOPRIGHT;
		else if (0 < x-x1 && x-x1 <= 5 && 0 < y-y1 && y-y1 <= 5) return Resize.TOPLEFT;
		else if (x1+5 < x && x < x2-5) {
			if (y1 < y && y < y1+5) return Resize.TOP;
			if (y2-5 < y && y < y2) return Resize.BOTTOM;
		} else if (y1+5 < y && y < y2-5) {
			if (x2-5 < x && x < x2) return Resize.RIGHT;
			if (x1 < x && x < x1+5) return Resize.LEFT;
		} 
	
		return Resize.INVALID;
	}
}