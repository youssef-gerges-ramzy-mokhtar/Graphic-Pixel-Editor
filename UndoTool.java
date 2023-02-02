import java.util.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

class UndoTool extends ClickableTool {
	private OurCanvas canvas;
	private Clickable undoBtn;
	private Clickable redoBtn;
	
	private LayersHandler layersHandler;
	private LinkedList<ArrayList<LayerData>> layersUndoHistory;
	private LinkedList<ArrayList<LayerData>> layersRedoHistory;
	private int historyLimit;
	
	public UndoTool(OurCanvas canvas) {
		super(null);
		this.canvas = canvas;
		
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.layersUndoHistory = new LinkedList<ArrayList<LayerData>>();
		this.layersRedoHistory = new LinkedList<ArrayList<LayerData>>();
		this.historyLimit = 100;

		this.recordHistory();
		addCanvasListener();
	}

	protected void initTool(UndoTool undo) {
		this.undoBtn = new Clickable("Undo");
		this.redoBtn = new Clickable("Redo");
		undoBtn.addKeyBinding((char) KeyEvent.VK_Z);
		redoBtn.addKeyBinding((char) KeyEvent.VK_Y);
		
		addToolBtn(redoBtn);
		addToolBtn(undoBtn);
		setAsChangeMaker(null);
	}

	private void addCanvasListener() {
		undoBtn.getBtn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				undo();                
			}
		});

		redoBtn.getBtn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				redo();
			}
		});

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
	  		public boolean dispatchKeyEvent(KeyEvent e) {
	  			if (e.getID() != KeyEvent.KEY_PRESSED) return false;

				if (e.getKeyCode() == KeyEvent.VK_Z) undo();
				if (e.getKeyCode() == KeyEvent.VK_Y) redo();

	        	return false;
	      	}
		});
	}

	public void recordHistory() {
		if (layersUndoHistory.size() == historyLimit) layersUndoHistory.removeFirst();
		layersUndoHistory.add(layersHandler.getLayersCopy());
	}

	private void recordRedoHistory() {
		layersRedoHistory.add(layersUndoHistory.peekLast());
	}

	private void undo() {
		if (layersUndoHistory.size() == 1) {
        	layersHandler.setLayers(layersHandler.getLayersCopy());
        	layersHandler.updateCanvas();
        	return;
        }

        recordRedoHistory();
        layersUndoHistory.removeLast();
        layersHandler.setLayers(layersUndoHistory.peekLast());
        layersHandler.updateCanvas();
	}

	private void redo() {
    	if (layersRedoHistory.size() == 0) return;

    	layersHandler.setLayers(layersRedoHistory.peekLast());
    	layersUndoHistory.add(layersRedoHistory.peekLast());
    	layersHandler.updateCanvas();

    	layersRedoHistory.removeLast();				
	}
}