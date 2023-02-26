import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;

class CutterTool extends ClickableTool {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private Clickable lassoBtn;
	private boolean canDrag;

	private Point startPoint;
	private Point endPoint;

	public CutterTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, undo);

		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		addCanvasListener();
	}

	protected void initTool(UndoTool undo) {
		this.lassoBtn = new Clickable("Cutter Tool");
		lassoBtn.addKeyBinding('l');
		
		addToolBtn(lassoBtn);
		setAsChangeMaker(undo);
		setAsLayerChanger();
	}

	private void addCanvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!lassoBtn.isActive()) return;
				startPoint = new Point(e.getX(), e.getY());
			}

			public void mouseReleased(MouseEvent e) {
				if (!lassoBtn.isActive()) return;
				if (startPoint == null || endPoint == null) return;

				LayerData selectedLayer = quickLassoSelect(layersHandler.getSelectedLayer());
				layersHandler.updateCanvasSelected(selectedLayer);

				recordChange();
				updateLayerObserver();
				
				startPoint = endPoint = null;
			}
		});

		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (!lassoBtn.isActive()) return;
				endPoint = new Point(e.getX(), e.getY());
			}
		});
	}

	public LayerData quickLassoSelect(LayerData layerChoice) {
		System.out.println(layerChoice);
		LayerData layerChoiceCopy = layerChoice.getCopy(); // I don't know why I am doing but when clearing a Sub Area of the mergedLayer it has an affect on the selectedArea and I wasn't able to know why

		BufferedImage selectedArea = layerChoiceCopy.getSubImage(
			startPoint.x, 
			startPoint.y, 
			endPoint.x, 
			endPoint.y
		);

		// debugging
		if (selectedArea == null) {
			System.out.println("Fuck");
			return null;
		} 

		ImageLayer selectedAreaLayer = new ImageLayer(selectedArea, LayerData.validTopLeftPoint(startPoint, endPoint));
		layersHandler.addLayer(selectedAreaLayer);

		layerChoice.clearSubArea(startPoint.x, startPoint.y, endPoint.x, endPoint.y, new Color(0,0,0,0));
		return selectedAreaLayer;
	}
}