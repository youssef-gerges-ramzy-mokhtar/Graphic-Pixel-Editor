abstract class ChangeMaker {
	private UndoTool undo;

	public ChangeMaker(UndoTool undo) {
		this.undo = undo;
	}

	protected void recordChange() {
		undo.recordHistory();
	}
}