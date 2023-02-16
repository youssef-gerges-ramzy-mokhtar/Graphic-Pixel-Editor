import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class LayersOptions extends JPanel implements LayerObserver {
	private LayersHandler layersHandler;
    private UndoTool undo;
    private ArrayList<LayerOption> layersOptions;

    private JButton moveUp;
    private JButton moveDown;
    private JButton merge;

    public LayersOptions(LayersHandler layersHandler) {
        this.layersHandler = layersHandler;
        this.layersOptions = new ArrayList<LayerOption>();

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
        layersOptions.clear();
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
            layersOptions.add(layerOption);
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

        merge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mergeLayers();
            }
        });
    }

    public void copyLayerOptoins() {
        for (LayerOption layerOption: layersOptions) {
            if (!layerOption.isSelected()) continue;
            layerOption.copyOperation();
        }

        updateState();
    }
    public void deleteLayerOptions() {
        for (LayerOption layerOption: layersOptions) {
            if (!layerOption.isSelected()) continue;
            layerOption.deleteOperation();
        }

        updateState();
    }
    public void mergeLayers() {
        ArrayList<LayerData> layers = layersHandler.getLayers();
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        int layersMergeCount = 0;

        for (LayerData layer: layers) {
            if (!layer.isSelectedForMerge()) continue;

            System.out.println(layer + " " + layer.getX() + " " + layer.getY() + " " + layer.getEndX() + " " + layer.getEndY());
            minX = Math.min(minX, layer.getX());
            minY = Math.min(minY, layer.getY());
            maxX = Math.max(maxX, layer.getEndX());
            maxY = Math.max(maxY, layer.getEndY());
            ++layersMergeCount;
        }

        if (layersMergeCount <= 1) return;

        int width = maxX - minX;
        int height = maxY - minY;
        ImageLayer mergedLayer = new ImageLayer(width, height, Constants.transparentColor, new Point(minX, minY));

        ArrayList<LayerData> unMergedLayers = new ArrayList<LayerData>();
        for (LayerData layer: layers) {
            if (!layer.isSelectedForMerge()) {unMergedLayers.add(layer); continue;}
            mergedLayer.mergeLayer(layer);
        }

        unMergedLayers.add(mergedLayer);
        layersHandler.setLayers(unMergedLayers);
        updateState();
    }

    public void mergeLayerOptions() {
        int layersMergeCount = 0;
        for (LayerOption layerOption: layersOptions) {
            if (!layerOption.isSelected()) continue;
            ++layersMergeCount;
        }
        if (layersMergeCount <= 1) return;

        for (LayerOption layerOption: layersOptions) {
            if (!layerOption.isSelected()) continue;
            layerOption.getLayer().setSelectedForMerge(true);
        }

        mergeLayers();
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