import javax.swing.*;
import java.awt.*;
import java.util.*;

class LayersOptions extends JPanel implements LayerObserver {
	private LayersHandler layersHandler;
    private UndoTool undo;

    public LayersOptions(LayersHandler layersHandler) {
        this.layersHandler = layersHandler;

        setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(500, screenSize.height);              //sets x and y dimension
        setBackground(Constants.mainColor);

        update();
    }

    public void update() {
        removeAll();
        revalidate();

        JPanel mainPanel = new JPanel(new GridBagLayout());
        add(mainPanel, BorderLayout.PAGE_START);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        ArrayList<LayerData> layers = layersHandler.getLayers();
        for (int i = layers.size() - 1; i >= 0; i--) {
            LayerOption layerOption = new LayerOption(this, layersHandler, layers.get(i), undo, i);
            if (layers.get(i) == layersHandler.getSelectedLayer()) layerOption.select();

            mainPanel.add(layerOption, gbc);
        }

        revalidate();
        repaint();
    }

    public void setUndo(UndoTool undo) {
        this.undo = undo;
    }
}