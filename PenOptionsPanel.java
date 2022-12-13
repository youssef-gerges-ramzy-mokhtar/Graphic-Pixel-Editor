import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;

// Pen Options Panel is the Panel to display brush properties, for now that is the Brush Size
class PenOptionsPanel extends JPanel implements Observable {
	// The code in this class will be changed in the future to be more DRY, and to look more better
	private final int maxBrushSz = 50;
	private JLabel title = new JLabel("Thickness");
	private JLabel title2 = new JLabel("Brushes", JLabel.CENTER);
	private JSlider sizeSlider = new JSlider(1, maxBrushSz);
	private JTextField sizeInput = new JTextField("1 px");
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private int currentBrushSz = 1;


	public PenOptionsPanel() {
		setLayout(new BorderLayout());
		sizeSlider.setValue(1);
		sizeInput.setColumns(5);
		setBackground(Constants.mainColor);

		sizeInput.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
	            sizeInput.setForeground(Color.BLACK);
		    }

		    public void focusLost(FocusEvent e) {
	            sizeInput.setForeground(Color.GRAY);
		    }
		});

		sizeInput.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent ke) {
				currentBrushSz = getBrushSize();
				sizeSlider.setValue(currentBrushSz);
				notifyObservers();
			}

			public void keyPressed(KeyEvent ke) {};
			public void keyTyped(KeyEvent ke) {};
		});

		sizeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				currentBrushSz = sizeSlider.getValue();
				sizeInput.setText(currentBrushSz + " px");
				notifyObservers();
			}
		});

		// This will be chaned //
		JPanel main = new JPanel(new BorderLayout());
		JPanel main2 = new JPanel(new FlowLayout());
		main2.add(title);
		main2.add(sizeInput);
		main.add(main2, BorderLayout.NORTH);
		main.add(sizeSlider, BorderLayout.CENTER);
		main.setBackground(Constants.mainColor);
		main2.setBackground(Constants.mainColor);

		add(title2, BorderLayout.NORTH);
		add(main, BorderLayout.CENTER);
		// This will be chaned //
		setBounds(100, 100, 100, 100);
	}

	public int getBrushSize() {
		String inputTxt = sizeInput.getText();
		int pixels = 0;
		boolean num_found = false;

		for (int i = 0; i < inputTxt.length(); i++) {
			char currentChar = inputTxt.charAt(i);
			if (currentChar >= '0' && currentChar <= '9') {
				num_found = true;
				pixels = (pixels * 10) + (currentChar - '0'); 
			}
			else break;
		}

		if (!num_found) return 1;
		if (pixels > maxBrushSz) return maxBrushSz;
		return pixels;
	}

	// Observer Design Pattern //
	public void notifyObservers() {
		for (Observer observer : observers)
			observer.update(currentBrushSz);
	}

	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}
}