import javax.swing.*;
import java.awt.*;

class OptionsPanel extends JPanel {
	private PenOptionsPanel penOptionsPanel;

	public OptionsPanel(ColorGui colorGui) {
		penOptionsPanel = new PenOptionsPanel();

		setLayout(new FlowLayout());
		setBackground(Constants.mainColor);
        add(penOptionsPanel);
        add(tempToUpdate(colorGui));
	}

	private JPanel tempToUpdate(ColorGui colorGui) {
		// This function will be removed and a Better Code structure will be used in the near future
		JLabel label = new JLabel("Color", JLabel.CENTER);

		JPanel main = new JPanel(new BorderLayout(5, 5));
		main.add(colorGui.getBtn(), BorderLayout.SOUTH);
		main.add(new ColorsPanel(colorGui), BorderLayout.CENTER);
		main.add(label, BorderLayout.NORTH);
		main.setBackground(Constants.mainColor);

		return main;
	}

	public PenOptionsPanel getPenOptionsPanel() {
		return penOptionsPanel;
	}
}