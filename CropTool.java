import java.util.*;
import java.awt.event.*;
import java.awt.*;

class CropTool extends ClickableTool {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private LayerData layerToCrop;
	private Clickable cropBtn;
	private boolean changeMade;
	private boolean canDrag;

	public CropTool(OurCanvas canvas, UndoTool undo) {
		super(undo);

		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.changeMade = false;
		this.canDrag = false;

		addCanvasListener();
	}

	protected void initTool(UndoTool undo) {
		this.cropBtn = new Clickable("Crop");
		cropBtn.addKeyBinding('w');

		addToolBtn(cropBtn);
		setAsChangeMaker(undo);
	}

	private void addCanvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!cropBtn.isActive()) return;

				layerToCrop = layersHandler.selectLayer(new Point(e.getX(), e.getY()));
				if (layerToCrop == null) return;

				// layerToCrop.drawBorder();
				// refreshCanvasSelection(layerToMove);
				layerToCrop.crop(new Point(e.getX(), e.getY()), 4);
				layersHandler.updateCanvas();
			}
		});
	}
}