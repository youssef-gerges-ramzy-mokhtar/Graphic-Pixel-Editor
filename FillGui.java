import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class FillGui implements Observable, Observer {
	private Color col;
	private Color fillCol = Color.black;
	private JButton fillBtn;
    private OurCanvas canvas;
    private boolean buttonSelected = false;
    private ArrayList<Observer> observers = new ArrayList<Observer>();

	public FillGui(OurCanvas canvas) {
		col = Color.white;
		fillBtn = new JButton("Fill");

		this.canvas = canvas;

		canvas.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent mouse) {
            	if (!buttonSelected) return;
            	if (canvas.getPixel(mouse.getX(), mouse.getY()) == null) return;

            	col = new Color(canvas.getPixel(mouse.getX(), mouse.getY()));
            	fillCanvas2(mouse.getX(), mouse.getY()); // Starting Position of Flood Fill
            }
        });

        fillBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent action) {
                notifyObservers();
                buttonSelected = true;
                SelectButton.selectBtn(fillBtn);
            }
        });
	}

	private void fillCanvas(int x, int y) {
		if (canvas.getPixel(x, y) == null) return;
		if (canvas.getPixel(x, y) == fillCol.getRGB()) return;
		if (canvas.getPixel(x, y) != col.getRGB()) return;

		canvas.setPixel(x, y, fillCol.getRGB());
		fillCanvas(x + 1, y);
		fillCanvas(x - 1, y);
		fillCanvas(x, y + 1);
		fillCanvas(x, y - 1);
	}

	private void fillCanvas2(int x, int y) {
		// Iterative Based Solution
		Stack<Integer> x_coord = new Stack<Integer>(); 
		Stack<Integer> y_coord = new Stack<Integer>(); 
		x_coord.push(x);
		y_coord.push(y);

		while (!x_coord.empty()) {
			int x_pos = x_coord.pop();
			int y_pos = y_coord.pop();

			// Base Cases
			if (canvas.getPixel(x_pos, y_pos) == null) continue; // invalid coordinates
			if (canvas.getPixel(x_pos, y_pos) == fillCol.getRGB()) continue; // already visited
			if (canvas.getPixel(x_pos, y_pos) != col.getRGB()) continue; // reached a block

			canvas.setPixel(x_pos, y_pos, fillCol.getRGB());

			{x_coord.push(x_pos + 1); y_coord.push(y_pos);}
			{x_coord.push(x_pos - 1); y_coord.push(y_pos);}
			{x_coord.push(x_pos); y_coord.push(y_pos + 1);}
			{x_coord.push(x_pos); y_coord.push(y_pos - 1);}
		}
	}

	public JButton getFillBtn() {
		return fillBtn;
	}

	public boolean isActive() {
		return buttonSelected;
	}

	public void deSelect() {
		buttonSelected = false;
        SelectButton.deSelectBtn(fillBtn);
	}

	// Observer Pattern
	public void notifyObservers() {
        for (Observer observer : observers)
            observer.update3();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void update(int val) {}
    public void update2(Color col) {
    	fillCol = col;
    }
    public void update3() {}
}