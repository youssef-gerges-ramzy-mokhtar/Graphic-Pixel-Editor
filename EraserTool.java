import java.awt.*;

class EraserTool extends DrawingTool {
	public EraserTool(OurCanvas canvas) {
		super(canvas);
		drawingBtn = new Clickable("Eraser");
		brush = new Pen(1, Color.black);
		lineGraphic = new DrawLineGraphics(brush.getThickness(), brush.getCol());
	}

	protected void setBrushProperties() {
		brush.setColor(canvas.getCanvasColor());
	}
}