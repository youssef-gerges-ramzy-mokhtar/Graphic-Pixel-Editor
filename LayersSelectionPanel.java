import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

// This class is just a temporary Class to provide the user with an interface to choose between different layers
// In the future the graphical user interface will change to look nicer
class LayersSelectionPanel extends JPanel {
	private LayersHandler layersHandler;
	private OptionsPanel optionsPanel;
	private JComboBox<Integer> layersDropDownMenu;
	private int layersCount;

	// The Constructor Definition will be changed in the future
	public LayersSelectionPanel(OurCanvas canvas, OptionsPanel optionsPanel) {
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.optionsPanel = optionsPanel;
		this.layersDropDownMenu = new JComboBox<Integer>();

		setPreferredSize(new Dimension(100, 200));

		updateDropDown();
		addDropDownListener();
		add(layersDropDownMenu);
		optionsPanel.add(layersDropDownMenu);
	}

	private void updateDropDown() {
		if (layersCount == layersHandler.getLayersCount()) return;
		this.layersCount = layersHandler.getLayersCount();
		layersDropDownMenu.removeAllItems();
		
		for (int i = 0; i < layersCount; i++)
			layersDropDownMenu.addItem(i);
	}

	private void addDropDownListener() {
		getDropDownBtn(layersDropDownMenu).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDropDown();
			}
		});

		layersDropDownMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (layersDropDownMenu.getSelectedIndex() < 0) return;
            	int layerChoice = layersDropDownMenu.getItemAt(layersDropDownMenu.getSelectedIndex());
            	layersHandler.changeSelectedLayer(layerChoice);
            }
        });
	}

	private JButton getDropDownBtn(Container container) {
		if (container instanceof JButton) return (JButton) container;
		else {
			Component[] components = container.getComponents();
			for (Component component : components)
				if (component instanceof Container) return getDropDownBtn((Container) component);
		}

		return null;
	}
}	