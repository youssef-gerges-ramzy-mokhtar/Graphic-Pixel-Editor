import java.util.*;
import java.awt.event.*;

class Shortcut {
	private ArrayList<Character> keyBindings;
	private Display display;
	private ClickableTool toolToSelect;

	public Shortcut(Display display, ClickableTool toolToSelect) {
		this.display = display;
		this.toolToSelect = toolToSelect;
		this.keyBindings = new ArrayList<Character>();
		addShortcutListener();
	}

	public void addKeyBinding(char keyBinding) {
		keyBindings.add(keyBinding);
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