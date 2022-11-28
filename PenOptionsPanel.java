import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

class PenOptionsPanel extends JPanel {
	private int maxBrushSz = 100;
	private JLabel title = new JLabel("Thickness");
	private JSlider sizeSlider = new JSlider(1, maxBrushSz);
	private JTextField sizeInput = new JTextField("1 px");

	public PenOptionsPanel() {
		sizeSlider.setValue(1);
		sizeInput.setColumns(5);
		// setBackground(Color.blue);

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
				int pixels = getBrushSize();
				sizeSlider.setValue(pixels);
			}

			public void keyPressed(KeyEvent ke) {};
			public void keyTyped(KeyEvent ke) {};
		});

		sizeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				int val = sizeSlider.getValue();
				sizeInput.setText(val + " px");
			}
		});

		add(title, BorderLayout.NORTH);
		add(sizeInput, BorderLayout.NORTH);
		add(sizeSlider, BorderLayout.SOUTH);
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
}