import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class ColorGui {
	private Color currentColor;
	private JButton colorBtn;

	public ColorGui() {
		colorBtn = new JButton("Color");
		currentColor = Color.black;
		addColorBtnListener();
	}

	private void addColorBtnListener() {
		colorBtn.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				JColorChooser colorChooser = new JColorChooser();
				currentColor = JColorChooser.showDialog(null, "Pick a Color", currentColor);
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
}