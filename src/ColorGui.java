import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// ColorGui is used to represent the Color Wheel
public class ColorGui implements Observable, Observer {
	ArrayList<Observer> observers = new ArrayList<>();
	private Color currentColor;
	private JButton colorBtn; // colorBtn is used to show the choosen color from the color wheel and from the Eye Dropper Tool

	/**
	 * ColorGui is used to represent the Color Wheel
	 * used to display the currently selected color
	 */
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
				if (currentColor == null) return;

				colorBtn.setBackground(currentColor);
				notifyObservers();
			}
		});
	}

	//////// Getters /////////
	/**
	 * used to return the current choosen color from the color chooser
	 * @return choosen color from the color chooser
	 */
	public Color getColor() {
		return currentColor;
	}

	/**
	 * used to return the color preview button
	 * @return color preview button
	 */
	public JButton getBtn() {
		return colorBtn;
	}
	// Obsever Design Pattern //

	/**
	 * used to notify all observers that is observing color changes
	 */
	// notifyObservers() is used to notify the Pen, Fill, Rectangle & Circle whenver the Color is changed so their color changes
	public void notifyObservers() {
		for (Observer observer : observers)
			observer.update2(currentColor);
	}

	/**
	 * used to add an observer observing color changes
	 * @param observer an observer observing color changes
	 */
	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	/**
	 * used to remove an observer observing color changes
	 * @param observer an observer observing color changes
	 */
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}


	public void update(int val) {}

	/**
	 * used to change the button color whenever a color is choosen not through the color chooser
	 * @param col is the color to set for the color preview button
	 */
	// update2() is used to change the button color whenver the Eye Dropper Tool is used
	public void update2(Color col) {
		currentColor = col;
		colorBtn.setBackground(currentColor);
		notifyObservers();
	}
	public void update3() {}
}