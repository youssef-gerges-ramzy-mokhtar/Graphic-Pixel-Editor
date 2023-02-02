import java.util.*;
import javax.swing.*;

abstract class ClickableTool {
	private ArrayList<Clickable> toolBtns;
	private Display display;
	private boolean toolMakesChanges;
	private UndoTool undo;
	private boolean rasterizeShapes;

	private boolean affectsLayers;
	private LayerObserver layerObserver;


	public ClickableTool(LayerObserver layerObserver, UndoTool undo) {
		this.toolBtns = new ArrayList<Clickable>();
		this.toolMakesChanges = false;
		this.undo = null;
		this.rasterizeShapes = false;

		this.affectsLayers = false;
		this.layerObserver = layerObserver;

		initTool(undo);
	}

	protected void addToolBtn(Clickable toolBtn) {
		this.toolBtns.add(toolBtn);
	}

	protected void setAsChangeMaker(UndoTool undo) {
		if (undo == null) this.toolMakesChanges = false;
		else {
			this.toolMakesChanges = true;
			this.undo = undo;
		}
	}

	protected void recordChange() {
		if (!toolMakesChanges) return;
		undo.recordHistory();
	}

	public ArrayList<Clickable> getClickables() {
		return toolBtns;
	}

	protected void setAsShapeRasterizer() {
		this.rasterizeShapes = true;
	}

	protected LayerData rasterizeLayer(LayerData layer, LayersHandler layersHandler) {
		if (!rasterizeShapes) return null;
		if (!(layer instanceof ShapeLayer)) return null;

		for (Clickable toolBtn: toolBtns)
			toolBtn.deSelect();

		int result = JOptionPane.showConfirmDialog(
			null, 
			"Shape Layer Will be Rasterized", 
			"Shape Layer",
			JOptionPane.OK_CANCEL_OPTION
		);

		if (result == JOptionPane.CANCEL_OPTION) return null;

		ShapeLayer shapeLayer = (ShapeLayer) layer;
		LayerData rasterizedShapeLayer = shapeLayer.rasterize();
		layersHandler.replaceLayer(shapeLayer, rasterizedShapeLayer);				

		return rasterizedShapeLayer;
	}

	protected void setAsLayerChanger() {
		affectsLayers = true;
	}

	protected void updateLayerObserver() {
		if (!affectsLayers) return;
		layerObserver.update();
	}

	abstract protected void initTool(UndoTool undo);
}