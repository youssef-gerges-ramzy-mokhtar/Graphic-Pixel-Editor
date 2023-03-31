import java.awt.*;

// Eraser is used to represent the Eraser Tool
public class EraserTool extends DrawingTool {
	/**
	 * Eraser Tool is used to represent the Eraser
	 * @param layerObserver a layerObserver is an object that observers changes that happens to the layers structure
	 * @param canvas is the current canvas that holds all the layers
	 * @param undo is the tool that manages how the undo and redo works
	 */ 
	public EraserTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, canvas, undo);
		drawingBtn.setText("Eraser");
		drawingBtn.addKeyBinding('e');

		brush = new Pen(1, Color.black);
		lineGraphic = new LineGraphics(brush.getThickness(), brush.getCol());
	}

	/**
	 * used to set the color of the Eraser to the Canvas Color
	 */
	// setBrushProperties() is used to set the color of the Eraser to the Canvas Color
	protected void setBrushProperties() {
		brush.setColor(canvas.getCanvasColor());
	}
}