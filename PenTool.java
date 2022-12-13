import java.awt.*;

class PenTool extends DrawingTool implements Observer {
	public PenTool(OurCanvas canvas) {
		super(canvas);
		drawingBtn = new Clickable("Pen");
		brush = new Pen(1, Color.black);
		lineGraphic = new DrawLineGraphics(brush.getThickness(), brush.getCol());
	}

	protected void setBrushProperties() {}

	// Observer Pattern
	public void update2(Color col) {
		brush.setColor(col);
	}
}