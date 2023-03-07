import java.awt.*;

// PenTool is used to represent the Pen Tool
class PenTool extends DrawingTool {
	/**
	 * Pen Tool is used to represent the Pen
	 * @param layerObserver a layerObserver is an object that observers changes that happens to the layers structure
	 * @param canvas is the current canvas that holds all the layers
	 * @param undo is the tool that manages how the undo and redo works
	 */ 
	public PenTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, canvas, undo);
		drawingBtn.setText("Pen");
		drawingBtn.addKeyBinding('p');

		brush = new Pen(1, Color.black);
		lineGraphic = new LineGraphics(brush.getThickness(), brush.getCol());
	}
	
	protected void setBrushProperties() {}

	// Observer Pattern	

	/**
	 * used to observer changs in the color choose to change update the pen color
	 * @param col choosen color
	 */
	// update2() is used to observer changes in the color chooser
	public void update2(Color col) {
		brush.setColor(col);
	}
}