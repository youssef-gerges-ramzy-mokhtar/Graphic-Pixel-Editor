import java.util.*;
import javax.swing.*;

// ClickableTool is mainly used to represent the properties of any tool that is used through a button
// Mainly this class specifies if a tool has an affect on the following:
/*
	- Does the Tool needs to update the Undo History when used
	- Does the Tool Rasterize Shape Layers by modifying them (for example drawing on a shape layer)
	- Does the Tool needs to update the Layers Panel when used
*/
abstract class ClickableTool {
	private ArrayList<Clickable> toolBtns;
	private Display display;
	private boolean toolMakesChanges;
	private UndoTool undo;
	private boolean rasterizeShapes;

	private boolean affectsLayers;
	private LayerObserver layerObserver;


	// The layerObserver represent the Layers Panel (In other words the layerObserver represent any class that needs to have an up to date record of the layers)
	public ClickableTool(LayerObserver layerObserver, UndoTool undo) {
		this.toolBtns = new ArrayList<Clickable>();
		this.toolMakesChanges = false; // a boolean value determining if a tool update the undo history when used
		this.undo = null;
		this.rasterizeShapes = false; // a boolean value determining if a tool rasterize a shape layer

		this.affectsLayers = false; // a boolean value determining if a tool has an affect on the layers
		this.layerObserver = layerObserver;

		initTool(undo);
	}

	// addToolBtn is used to add a Clickable that is associated with the Clickable Tool
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

	// Records the Undo History
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

	// rasterizeLayer prompts the user to accept if he really wants to rasterize the shape layer and the replaces the shape layers with the rasterized version
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

	// Updates the LayersObserver (For now that is the Layers Panel)
	protected void updateLayerObserver() {
		if (!affectsLayers) return;
		layerObserver.update();
	}

	// initTool is an abstract method used by each tool to specify its specific properties
	abstract protected void initTool(UndoTool undo);
}


/*
	My Notes:
		- In general I don't like the structure of this class it is used to represent a lot of differents tools that has different properties
		- Although I wanted to break this class into other seperate classes but the problem is that Java doesn't support Mult-Inhertiance
		- And proberly there was a better way to structure this class and its relation with the other tools, but I didn't want to but a lot of effort to search on how to achieve that
*/