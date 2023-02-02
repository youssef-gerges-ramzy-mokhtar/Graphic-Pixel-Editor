import java.awt.*;

// Eraser is used to represent the Eraser Tool
class EraserTool extends DrawingTool {
	public EraserTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, canvas, undo);
		drawingBtn.setText("Eraser");
		drawingBtn.addKeyBinding('e');

		brush = new Pen(1, Color.black);
		lineGraphic = new LineGraphics(brush.getThickness(), brush.getCol());
	}

	// setBrushProperties() is used to set the color of the Eraser to the Canvas Color
	protected void setBrushProperties() {
		brush.setColor(canvas.getCanvasColor());
	}
}