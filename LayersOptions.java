import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class LayersOptions extends JPanel implements LayerObserver {
	private LayersHandler layersHandler;
    private UndoTool undo;

    private JButton moveUp;
    private JButton moveDown;
    private JButton merge;

    private LayerData selectedLayer;

    public LayersOptions(LayersHandler layersHandler) {
        this.layersHandler = layersHandler;

        setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(500, screenSize.height);              //sets x and y dimension
        setBackground(Constants.mainColor);

        this.moveUp = new JButton("Move Up");
        this.moveDown = new JButton("Move Down");
        this.merge = new JButton("Merge");

        update();
        addBtnListeners();
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

        mainPanel.add(getLayerOptions(), gbc);
        
        ArrayList<LayerData> layers = layersHandler.getLayers();
        for (int i = layers.size() - 1; i >= 0; i--) {
            LayerOption layerOption = new LayerOption(this, layersHandler, layers.get(i), undo);
            if (layers.get(i) == layersHandler.getSelectedLayer()) layerOption.select();

            mainPanel.add(layerOption, gbc);
        }

        revalidate();
        repaint();
    }

    private JPanel getLayerOptions() {
        JPanel options = new JPanel();
        options.add(moveUp);
        options.add(moveDown);
        options.add(merge);
        options.setBackground(Color.black);

        return options;
    }

    private void addBtnListeners() {
        moveUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                layersHandler.moveLayerUp(layersHandler.getSelectedLayer());
                updateState();
            }
        });

        moveDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                layersHandler.moveLayerDown(layersHandler.getSelectedLayer());
                updateState();
            }
        });
    }

    private void updateState() {
        layersHandler.updateCanvas();
        update();
        
        if (undo != null) undo.recordHistory();
    }

    public void setUndo(UndoTool undo) {
        this.undo = undo;
    }
}