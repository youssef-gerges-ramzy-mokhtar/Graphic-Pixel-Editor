import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// ColorGui is used to represent the Color Wheel
class ColorGui implements Observable, Observer {
	ArrayList<Observer> observers = new ArrayList<>();

	private Color currentColor;
	private JButton colorBtn; // colorBtn is used to show the choosen color from the color wheel and from the Eye Dropper Tool

	public ColorGui() {
		colorBtn = new JButton();
		colorBtn.setPreferredSize(new Dimension(50, 25));
		colorBtn.setBackground(Color.white);
		
		currentColor = Color.black;
		addColorBtnListener();
	}

	// Adds an Event Listener to the colorBtn so that whenever it clicked a Color Chooser/Color Wheel is displayed for the user to choose a color
	private void addColorBtnListener() {
		colorBtn.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				JColorChooser colorChooser = new JColorChooser();
				currentColor = JColorChooser.showDialog(null, "Pick a Color", currentColor);
				colorBtn.setBackground(currentColor);
				notifyObservers();
			}
		});
	}

	//////// Getters /////////
	public Color getColor() {
		return currentColor;
	}

	public JButton getBtn() {
		return colorBtn;
	}

	// Obsever Design Pattern //

	// notifyObservers() is used to notify the Pen, Fill, Rectangle & Circle whenver the Color is changed so their color changes
	public void notifyObservers() {
		for (Observer observer : observers)
			observer.update2(currentColor);
	}

	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	public void update(int val) {}

	// update2() is used to change the button color whenver the Eye Dropper Tool is used
	public void update2(Color col) {
		currentColor = col;
		colorBtn.setBackground(currentColor);
		notifyObservers();
	}
	public void update3() {}
}