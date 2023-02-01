import java.util.*;

abstract class ClickableTool {
	ArrayList<Clickable> toolBtns;
	Display display;
	boolean toolMakesChanges;
	UndoTool undo;

	public ClickableTool(UndoTool undo) {
		this.toolBtns = new ArrayList<Clickable>();
		this.toolMakesChanges = false;
		this.undo = null;

		initTool(undo);
	}

	protected void addToolBtn(Clickable toolBtn) {
		this.toolBtns.add(toolBtn);
	}

	protected void setAsChangeMaker(UndoTool undo) {
		if (undo == null) this.toolMakesChanges = false;
		else {
			this.toolMakesChanges = true;
			this.undo = undo;
		}
	}

	protected void recordChange() {
		if (!toolMakesChanges) return;
		undo.recordHistory();
	}

	public ArrayList<Clickable> getClickables() {
		return toolBtns;
	}

	abstract protected void initTool(UndoTool undo);
}