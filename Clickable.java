import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.*;

// Clickable is used to represent a Button associated with a Tool and all the behaviour associated with this button
class Clickable implements Observable {
	private boolean btnActive;
	private JButton btn;
	private ArrayList<Observer> clickObservers;
	private Color selectorCol;
	private ArrayList<Character> keyBindings;

	public Clickable(String title) {
		this.clickObservers = new ArrayList<Observer>();
		this.btnActive = false;
		this.selectorCol = new Color(255, 242, 0);
		this.btn = new JButton(title);
		this.keyBindings = new ArrayList<Character>();

		addBtnListener();
	}

	// isActive checks if a button is currently selected or not
	public boolean isActive() {
		return btnActive;
	}

	// deSelect deSelects the button
	public void deSelect() {
		btnActive = false;
		deSelectBtn();
	}

	public JButton getBtn() {
		return btn;
	}

	// addKeyBinding() sets the shortcut of the button
	public void addKeyBinding(Character key) {
		keyBindings.add(key);
	}

	// addBtnListener() is used to attach an Event Listener to the Button representing the Tool
	private void addBtnListener() {
		// So this is used to handle selection of the button using clicking
		btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent action) {
                selectBtn();
            }
        });

		// So this is used to handle selection of the button using its associated shortcut
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
	  		public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getID() != KeyEvent.KEY_PRESSED) return false;
				
				selectBtn(e.getKeyCode());
	        	return false;
	      	}
		});
	}

	public void setText(String txt) {
		btn.setText(txt);
	}

	// selectBtn() is used to select the Button and notifyObservers() and the observers will deSelect the button that was previously selected
	private void selectBtn() {
		notifyObservers();
        btnActive = true;
		btn.setBackground(selectorCol);
	}

	private void selectBtn(int key) {
		if (keyBindings.size() == 0) return;
		if (key == KeyEvent.getExtendedKeyCodeForChar(keyBindings.get(0))) selectBtn();
	}

	private void deSelectBtn() {
		btn.setBackground(new JButton().getBackground());
	}

	public void select()
	{
		selectBtn();
	}


	/* Observer Pattern: Used to notify the observers whenver a button is clicked //
	notifyObservers() is used to notify the Tools Panel that this button is selected to deselect the other buttons */
	public void notifyObservers() {
		for (Observer observer: clickObservers)
			observer.update3();
	}

	public void addObserver(Observer observer) {
		clickObservers.add(observer);
	}

	public void removeObserver(Observer observer) {
		clickObservers.remove(observer);
	}
}