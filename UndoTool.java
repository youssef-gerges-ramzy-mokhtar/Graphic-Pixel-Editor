import java.util.*;
import java.awt.event.*;
import java.awt.*;
import java.lang.Runtime;

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
		this.historyLimit = 30; // a history limit of 10 means that the user can go back a.k.a. undo in history by a maximum of 10 only

		this.recordHistory();
		addCanvasListener();
	}

	// initTool initialize the properties of the Undo & Redo Tool
	/*
		- The Undo & Redo Tools Affects the Layers Panel
		- The Undo Tool has shortcut 'ctrl + z'
		- The Redo Tool has shortcut 'ctrl + y'
	*/
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
		// when the undo button is clicked we undo
		undoBtn.getBtn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				undo();     
			}
		});

		// when the redo button is clicked we redo
		redoBtn.getBtn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				redo();
			}
		});

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
	  		public boolean dispatchKeyEvent(KeyEvent e) {
	  			if (e.getID() != KeyEvent.KEY_PRESSED) return false;

				if (e.getKeyCode() == KeyEvent.VK_Z) undo(); // when ctrl + z is clicked we undo
				if (e.getKeyCode() == KeyEvent.VK_Y) redo(); // when ctrl + y is clicked we redo

	        	return false;
	      	}
		});
	}

	// recordHistory() is used to save changes from the undo
	public void recordHistory() {
		if (layersUndoHistory.size() == historyLimit) layersUndoHistory.removeFirst();
		layersUndoHistory.add(layersHandler.getLayersCopy());
		this.clearRedo();

		// undo_debug("Recording History", layersUndoHistory); // temporary helper function for debugging
	}

	private void recordRedoHistory() {
		layersRedoHistory.add(layersUndoHistory.peekLast());
	}

	// undo simply returns back 1 step in history and update the canvas to the previous state
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

        // undo_debug("Removing History", layersUndoHistory); // temporary helper function for debugging
        updateLayerObserver();
	}

	// redo simply used to re-do an undo action
	private void redo() {
    	if (layersRedoHistory.size() == 0) return;

    	layersHandler.setLayers(layersRedoHistory.peekLast());
    	layersUndoHistory.add(layersRedoHistory.peekLast());
    	layersHandler.updateCanvas();

    	layersRedoHistory.removeLast();
        updateLayerObserver();
        
        // undo_debug("Redoing", layersRedoHistory); // temporary helper function for debugging 
		// undo_debug("Recording History", layersUndoHistory); // temporary helper function for debugging
	}

	// clear Redo removes anything that was stored for redo
	private void clearRedo() {
		layersRedoHistory.clear();
	}

	public void setRedoUndo(LinkedList<ArrayList<LayerData>> undo, LinkedList<ArrayList<LayerData>> redo) {
		layersUndoHistory = undo;
		layersRedoHistory = redo;
	}

	public LinkedList<ArrayList<LayerData>> getUndo() {
		return layersUndoHistory;
	}

	public LinkedList<ArrayList<LayerData>> getRedo() {
		return layersRedoHistory;
	}


	// Temporary Helper Function for Debugging Purpose
	// Although we don't need this function anymore but it will be useful for any future bugs associated with the Undo Tool
	void undo_debug(String msg, LinkedList<ArrayList<LayerData>> historyToDebug) {
		System.out.println(msg);

		System.out.println("{");
		for (ArrayList<LayerData> layers: historyToDebug)
			System.out.println("	" + layers);
		System.out.println("}");
	}
}