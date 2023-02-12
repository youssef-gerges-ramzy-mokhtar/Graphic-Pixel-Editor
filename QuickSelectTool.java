import java.awt.event.*;
import java.awt.*;

class QuickSelectTool extends ClickableTool {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private Clickable quickSelectionBtn;
	private boolean canDrag;

	private Point startPoint;
	private Point endPoint;

	public QuickSelectTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, undo);

		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		addCanvasListener();
	}

	protected void initTool(UndoTool undo) {
		this.quickSelectionBtn = new Clickable("Quick Selection");
		quickSelectionBtn.addKeyBinding('q');
		
		addToolBtn(quickSelectionBtn);
		setAsChangeMaker(undo);
		setAsLayerChanger();
	}

	private void addCanvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!quickSelectionBtn.isActive()) return;
				startPoint = new Point(e.getX(), e.getY());
			}

			public void mouseReleased(MouseEvent e) {
				if (!quickSelectionBtn.isActive()) return;
				if (startPoint == null || endPoint == null) return;

				// layersHandler.selectPart(validPoint(startPoint, endPoint));
				LayerData selectedLayer = layersHandler.selectPart(startPoint, endPoint);
				layersHandler.updateCanvasSelected(selectedLayer);

				recordChange();
				updateLayerObserver();
				
				startPoint = endPoint = null;
			}
		});

		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (!quickSelectionBtn.isActive()) return;
				endPoint = new Point(e.getX(), e.getY());
			}
		});
	}

	// validPoint should be in the LayersHandler Class
	private Point validPoint(Point p1, Point p2) {
		int x1 = p1.x;
		int y1 = p1.y;
		int x2 = p2.x;
		int y2 = p2.y;
		if (y2 > y1 && x2 > x1){
			return p1;
		}
		if (y2 < y1 && x2 > x1){
			return new Point(x1, y2);
		}
		if(x2 < x1 && y2 < y1){
			return p2;
		}
		if(x2 < x1 && y2 > y1){
		return new Point(x2, y1);
		}
		return null;
	}
}