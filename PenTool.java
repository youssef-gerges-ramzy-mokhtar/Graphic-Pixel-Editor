import java.awt.*;

// PenTool is used to represent the Pen Tool
class PenTool extends DrawingTool {
	public PenTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, canvas, undo);
		drawingBtn.setText("Pen");
		drawingBtn.addKeyBinding('p');

		brush = new Pen(1, Color.black);
		lineGraphic = new LineGraphics(brush.getThickness(), brush.getCol());
	}
	
	protected void setBrushProperties() {}

	// Observer Pattern	
	// update2() is used to observer changes in the color chooser
	public void update2(Color col) {
		brush.setColor(col);
	}
}