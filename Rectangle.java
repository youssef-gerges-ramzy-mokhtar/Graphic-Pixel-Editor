import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

class Rectangle implements Observer, Observable {
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private JButton rectangleBtn = new JButton("Rectangle Shape");
	private int sideLen = 5;
	private Color col;
	private OurCanvas recCanvas;
	private boolean selected = false;

	public Rectangle(OurCanvas recCanvas) {
		this.recCanvas = recCanvas;
		col = Color.black;

		rectangleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				notifyObservers();
				selected = true;
				SelectButton.selectBtn(rectangleBtn);
			}
		});

		recCanvas.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (!selected) return;

				RectangleGraphics rectangleGraphics = new RectangleGraphics(new Point(e.getX(), e.getY()), recCanvas.getCanvasGraphics());
				rectangleGraphics.setColor(col);
				rectangleGraphics.setLen(sideLen);				
				recCanvas.updateCanvas(rectangleGraphics);
			}
		});
	}

	public JButton getButton() {
		return rectangleBtn;
	}

	public boolean isActive() {
		return selected;
	}

	public void deSelect() {
		selected = false;
        SelectButton.deSelectBtn(rectangleBtn);
	}

	// Observer Pattern //
	public void update(int val) {
		sideLen = val;
	}
	
	public void update2(Color col) {
		this.col = col;
	}

	public void update3() {}

	public void notifyObservers() {
		for (Observer observer: observers) {
			observer.update3();
		}
	}

	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	/*
		// We should have a Separate Shape Controller GUI to the User
		We need to have controls to let the user control the following:
			- stroke size of the shape
			- stroke color of the shape
			- fill color of the shape
			- shape dimensions
	*/
}