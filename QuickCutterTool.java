import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;


class QuickCutterTool extends ClickableTool {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private Clickable quickLassoBtn;
	private boolean canDrag;

	private Point startPoint;
	private Point endPoint;

	private ImageLayer selectionOutline;
	private ImageLayer prevSelectionOutline;

	public QuickCutterTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, undo);

		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		
		this.prevSelectionOutline = null;
		this.selectionOutline = new ImageLayer(1, 1, new Color(0,0,0,0), new Point(0, 0));

		addCanvasListener();
	}

	protected void initTool(UndoTool undo) {
		this.quickLassoBtn = new Clickable("Quick Cutter Tool");
		quickLassoBtn.addKeyBinding('q');
		
		addToolBtn(quickLassoBtn);
		setAsChangeMaker(undo);
		setAsLayerChanger();
	}

	private void addCanvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!quickLassoBtn.isActive()) return;
				startPoint = new Point(e.getX(), e.getY());
			}

			public void mouseReleased(MouseEvent e) {
				if (!quickLassoBtn.isActive()) return;
				if (startPoint == null || endPoint == null) return;

				LayerData selectedLayer = quickLassoSelect(layersHandler.mergeAll());
				layersHandler.updateCanvasSelected(selectedLayer);

				recordChange();
				updateLayerObserver();
				
				startPoint = endPoint = null;
			}
		});

		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (!quickLassoBtn.isActive()) return;
				endPoint = new Point(e.getX(), e.getY());

				layersHandler.removeLayer(prevSelectionOutline);
				
				int selectionWidth = Math.abs(startPoint.x - endPoint.x);
				int selectionHeight = Math.abs(startPoint.y - endPoint.y);
				if (selectionWidth == 0 || selectionHeight == 0) return;

				selectionOutline = new ImageLayer(selectionWidth, selectionHeight, new Color(0,0,0,0), LayerData.validTopLeftPoint(startPoint, endPoint));
				prevSelectionOutline = selectionOutline;
				layersHandler.addLayer(selectionOutline);

				layersHandler.updateCanvasSelected(selectionOutline);
			}
		});
	}

	public LayerData quickLassoSelect(LayerData layerChoice) {
		LayerData layerChoiceCopy = layerChoice.getCopy(); // I don't know why I am doing but when clearing a Sub Area of the mergedLayer it has an affect on the selectedArea and I wasn't able to know why

		BufferedImage selectedArea = layerChoiceCopy.getSubImage(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
		ImageLayer selectedAreaLayer = new ImageLayer(selectedArea, LayerData.validTopLeftPoint(startPoint, endPoint));
		layersHandler.addLayer(selectedAreaLayer);

		layerChoice.clearSubArea(startPoint.x, startPoint.y, endPoint.x, endPoint.y, new Color(0,0,0,0));
		return selectedAreaLayer;
	}
}