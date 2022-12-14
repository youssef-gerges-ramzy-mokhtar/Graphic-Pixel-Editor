import java.awt.*;

// PenTool is used to represent the Pen Tool
class PenTool extends DrawingTool implements Observer {
	public PenTool(OurCanvas canvas) {
		super(canvas);
		drawingBtn = new Clickable("Pen");
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