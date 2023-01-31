import java.util.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.*;

class UndoTool implements ClickableContainer {
	private OurCanvas canvas;
	private Clickable undoBtn;
	private Clickable redoBtn;
	private LayersHandler layersHandler;
	private LinkedList<ArrayList<LayerData>> layersUndoHistory;
	private LinkedList<ArrayList<LayerData>> layersRedoHistory;
	private int historyLimit;
	private Shortcut shortcut;

	public UndoTool(OurCanvas canvas) {
		this.canvas = canvas;
		this.undoBtn = new Clickable("Undo");
		this.redoBtn = new Clickable("Redo");
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.layersUndoHistory = new LinkedList<ArrayList<LayerData>>();
		this.layersRedoHistory = new LinkedList<ArrayList<LayerData>>();
		this.historyLimit = 100;
		this.shortcut = null;

		this.recordHistory();
		addCanvasListener();	
	}

	private void addCanvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent mouse) {
                if (!undoBtn.isActive()) return;
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
        });

        canvas.addMouseListener(new MouseAdapter() {
        	public void mouseReleased(MouseEvent mouse) {
	        	if (!redoBtn.isActive()) return;
	        	if (layersRedoHistory.size() == 0) return;

	        	layersHandler.setLayers(layersRedoHistory.peekLast());
	        	layersUndoHistory.add(layersRedoHistory.peekLast());
	        	layersHandler.updateCanvas();

	        	layersRedoHistory.removeLast();
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

	public void setShortcut(Shortcut shortcut) {
		this.shortcut = shortcut;
		shortcut.setKeyBinding('r');
	}

	public ArrayList<Clickable> getClickable() {
		ArrayList<Clickable> historyBtns = new ArrayList<Clickable>();
		historyBtns.add(undoBtn);
		historyBtns.add(redoBtn);
		return historyBtns;
	}
}

class Shortcut {
	char keyBinding;
	Display display;

	public Shortcut(Display display) {
		this.display = display;
		addShortcutListener();
	}

	public Shortcut(char keyBinding, Display display) {
		this.keyBinding = keyBinding;
		this.display = display;
		addShortcutListener();
	}

	public void setKeyBinding(char keyBinding) {
		this.keyBinding = keyBinding;
	}

	private void addShortcutListener() {
		display.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				System.out.println(e.getKeyChar());
			}

			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});
	}
}