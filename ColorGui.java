import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class ColorGui implements Observable, Observer {
	ArrayList<Observer> observers = new ArrayList<>();

	private Color currentColor;
	private JButton colorBtn;

	public ColorGui() {
		colorBtn = new JButton();
		colorBtn.setPreferredSize(new Dimension(50, 25));
		colorBtn.setBackground(Color.white);
		
		currentColor = Color.black;
		addColorBtnListener();
	}

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
	public void update2(Color col) {
		currentColor = col;
		colorBtn.setBackground(currentColor);
		notifyObservers();
	}
	public void update3() {}
}