import java.util.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

class UndoTool implements ClickableContainer {
	private OurCanvas canvas;
	private Clickable undoBtn;
	private LayersHandler layersHandler;
	private Stack<ArrayList<LayerData>> layersHistory;
	private int historyLimit;

	public UndoTool(OurCanvas canvas) {
		this.canvas = canvas;
		this.undoBtn = new Clickable("Undo");
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.layersHistory = new Stack<ArrayList<LayerData>>();
		this.historyLimit = 50;

		this.recordHistory();
		addCanvasListener();
	}

	private void addCanvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent mouse) {
                if (!undoBtn.isActive()) return;
                System.out.println("Stack Size = " + layersHistory.size());
                if (layersHistory.size() == 1) {
                	layersHandler.setLayers(layersHandler.getLayersCopy());
                	layersHandler.updateCanvas();
                	return;
                }

                layersHistory.pop();
                layersHandler.setLayers(layersHistory.peek());
                layersHandler.updateCanvas();
            }
        });
	}

	public void recordHistory() {
		layersHistory.push(layersHandler.getLayersCopy());
		System.out.println("New Stack Size = " + layersHistory.size());
	}

	// public void addObservable()

	public Clickable getClickable() {
		return undoBtn;
	}
}
