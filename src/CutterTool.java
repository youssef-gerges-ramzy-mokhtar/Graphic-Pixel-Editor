import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;

/*
	CutterTool represents the following 2 Tools:
		1. Quick Cutter Tool: Merges all layers and cuts part of the merged layer based on the user choice and puts the selected part into its own layer
		2. Cutter Tool: Same idea as Quick Cutter Tool but acts on each layer independtly. Simply used to cut part of a layer and add the cutted part into its own layer 
*/
public class CutterTool extends ClickableTool {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private Clickable cutterBtn;
	private Clickable quickCutterBtn;

	private Point startPoint;
	private Point endPoint;

	private ImageLayer selectionOutline;
	private ImageLayer prevSelectionOutline;

	/**
	 * CutterTool represents the following 2 Tools:
	 * 1. Quick Cutter Tool: Merges all layers and cuts part of the merged layer based on the user choice and puts the selected part into its own layer
	 * 2. Cutter Tool: Same idea as Quick Cutter Tool but acts on each layer independtly. Simply used to cut part of a layer and add the cutted part into its own layer 
	 * @param layerObserver a layerObserver is an object that observers changes that happens to the layers structure
	 * @param canvas is the current canvas that holds all the layers
	 * @param undo is the tool that manages how the undo and redo works
	 */
	public CutterTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, undo);

		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		addCanvasListener();
	}

	/**
	 * initTool initialize the properties of the Cutter Tools
	 * - Both Tools Affects the Undo Tool
	 * - Both Tools Affects the Layers Panel
	 * - Both Tools Rasterizes Shape Layers
	 * - The Quick Cutter Tool has shortcut 'q'
	 * @param undo is the tool that manages how the undo and redo works
	 */
	// initTool initialize the properties of the Cutter Tool & the Quick Cutter Tool
 	/*
		- Both Tools Affects the Undo Tool
		- Both Tools Affects the Layers Panel
		- Both Tools Rasterizes Shape Layers
		- The Cutter Tool has shortcut 'l'
		- The Quick Cutter Tool has shortcut 'q'
	*/
	protected void initTool(UndoTool undo) {
		this.quickCutterBtn = new Clickable("Quick Cutter Tool");
		this.cutterBtn = new Clickable("Cutter Tool");
		quickCutterBtn.addKeyBinding('q');
		cutterBtn.addKeyBinding('l');
		
		addToolBtn(quickCutterBtn);
		addToolBtn(cutterBtn);

		setAsChangeMaker(undo);
		setAsShapeRasterizer();
		setAsLayerChanger();
	}

	private void addCanvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!cutterBtn.isActive() && !quickCutterBtn.isActive()) return;
				startPoint = new Point(e.getX(), e.getY());
			}

			public void mouseReleased(MouseEvent e) {
				if (!cutterBtn.isActive() && !quickCutterBtn.isActive()) return;
				if (startPoint == null || endPoint == null) return;
				layersHandler.removeLayer(prevSelectionOutline); // remove the outline that was displayed to the user

				LayerData selectedLayer = null;
				if (cutterBtn.isActive()) selectedLayer = layersHandler.getSelectedLayer(); // if Cutter Tool is active we get the Current Selected Layer
				else if (quickCutterBtn.isActive()) selectedLayer = layersHandler.mergeAll(); // if Quick Cutter Tool is active we merge all layers

				// if a layer is a shape layer then we rasterize the shape Layer
				if (selectedLayer instanceof ShapeLayer) {
					selectedLayer = rasterizeLayer(selectedLayer, layersHandler);
					if (selectedLayer == null) {layersHandler.updateCanvas(); return;} // this means the user didn't want to rasterize the shape layer
				}

				LayerData updatedSelectedLayer = selectedCut(selectedLayer);
				layersHandler.updateCanvasSelected(updatedSelectedLayer); // refershes the canvas to show the newly created cutted layer

				recordChange();
				updateLayerObserver();
				startPoint = endPoint = null;
			}
		});

		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (!cutterBtn.isActive() && !quickCutterBtn.isActive()) return;
				endPoint = new Point(e.getX(), e.getY());

				displayOutline();		
			}
		});
	}

	// displayOutline() is used to show the selected areay the user has choosen while clicking and dragging
	private void displayOutline() {
		layersHandler.removeLayer(prevSelectionOutline);
		
		int selectionWidth = Math.abs(startPoint.x - endPoint.x);
		int selectionHeight = Math.abs(startPoint.y - endPoint.y);
		if (selectionWidth == 0 || selectionHeight == 0) return;

		selectionOutline = new ImageLayer(selectionWidth, selectionHeight, Constants.transparentColor, LayerData.validTopLeftPoint(startPoint, endPoint));
		prevSelectionOutline = selectionOutline;
		layersHandler.addLayer(selectionOutline);

		layersHandler.updateCanvasSelected(selectionOutline);
	}

	// selectedCut() cuts the specified area from the layerChoice and adds the cutted part into its own layer
	private LayerData selectedCut(LayerData layerChoice) {
		LayerData layerChoiceCopy = layerChoice.getCopy(); // I don't know why I am doing but when clearing a Sub Area of the layerChoice it has an affect on the selectedArea and I wasn't able to know why

		BufferedImage selectedArea = layerChoiceCopy.getSubImage(startPoint.x, startPoint.y, endPoint.x, endPoint.y); // Cutting part of the layerChoice
		if (selectedArea == null) return null; // coordinates are invalid

		// Here we are determining the layer start position
		Point cuttedLayerPos = null;
		if (!layerChoice.pointInBounds(startPoint)) cuttedLayerPos = layerChoice.getCoords(); // if the startPoint specified by the user is not inside the layerChoice then the position of the cutted layer will be the position of the layer choice
		else cuttedLayerPos = LayerData.validTopLeftPoint(startPoint, endPoint); // else the cuttedLayerPos will wil equal to the Top Left Point based on the start point and the end point selected by the user 

		// Creating a new Layer located at ... for the cutted porition and adding to the layersHandler
		ImageLayer selectedAreaLayer = new ImageLayer(selectedArea, cuttedLayerPos);
		layersHandler.addLayer(selectedAreaLayer);

		layerChoice.clearSubArea(startPoint.x, startPoint.y, endPoint.x, endPoint.y, Constants.transparentColor); // clearing the cutted area from the layerChoice and filling the cleared area with transparentColor
		return selectedAreaLayer;
	}
}