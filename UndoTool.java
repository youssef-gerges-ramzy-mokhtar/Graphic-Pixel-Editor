import java.util.*;
import java.awt.event.*;
import java.awt.*;

class UndoTool extends ClickableTool {
	private OurCanvas canvas;
	private Clickable undoBtn;
	private Clickable redoBtn;
	
	private LayersHandler layersHandler;
	private LinkedList<ArrayList<LayerData>> layersUndoHistory;
	private LinkedList<ArrayList<LayerData>> layersRedoHistory;
	private int historyLimit;
	
	public UndoTool(LayerObserver layerObserver, OurCanvas canvas) {
		super(layerObserver, null);
		this.canvas = canvas;
		
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.layersUndoHistory = new LinkedList<ArrayList<LayerData>>();
		this.layersRedoHistory = new LinkedList<ArrayList<LayerData>>();
		this.historyLimit = 10;

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
		setAsLayerChanger();
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
		this.clearRedo();

		// undo_debug("Recording History", layersUndoHistory); // temporary helper function
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

        // undo_debug("Removing History", layersUndoHistory); // temporary helper function
        updateLayerObserver();
	}

	private void redo() {
    	if (layersRedoHistory.size() == 0) return;

    	layersHandler.setLayers(layersRedoHistory.peekLast());
    	layersUndoHistory.add(layersRedoHistory.peekLast());
    	layersHandler.updateCanvas();

    	layersRedoHistory.removeLast();
        updateLayerObserver();

        // undo_debug("Redoing", layersRedoHistory);
		// undo_debug("Recording History", layersUndoHistory);
	}

	private void clearRedo() {
		layersRedoHistory.clear();
	}

	public void setRedoUndo(LinkedList<ArrayList<LayerData>> undo, LinkedList<ArrayList<LayerData>> redo)
	{
		layersUndoHistory = undo;
		layersRedoHistory = redo;
	}

	public LinkedList<ArrayList<LayerData>> getUndo()
	{
		return(layersUndoHistory);
	}

	public LinkedList<ArrayList<LayerData>> getRedo()
	{
		return(layersRedoHistory);
	}


	// Temporary Helper Function for Debugging Purpose
	void undo_debug(String msg, LinkedList<ArrayList<LayerData>> historyToDebug) {
		System.out.println(msg);

		System.out.println("{");
		for (ArrayList<LayerData> layers: historyToDebug)
			System.out.println("	" + layers);
		System.out.println("}");
	}
}