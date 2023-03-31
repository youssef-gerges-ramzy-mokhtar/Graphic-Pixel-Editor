import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// LayersOptions is the Layer Panel on the Right Displaying all the Layers that currently exist on the Canvas
public class LayersOptions extends JPanel implements LayerObserver {
	private LayersHandler layersHandler;
    private UndoTool undo;
    private ArrayList<LayerOption> layersOptions;

    private JButton moveUp;
    private JButton moveDown;
    private JButton merge;

    /**
     * LayersOptions is the Layers Panel Displaying all the Layers that currently exist on the Canvas
     * @param layersHandler the layers handler that handles all layers on the canvas
     */
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

    /**
     * used to update the layer panel whenever the layers in the layers handler is changed
     * to make the layer panel always in sync with the current state of the layers
     */
    // update() is used to clear the layer panel and re-create the layers again whenever the layers in the layers handler is changed
    // to make the right panel always in sync with the current state of the layers
    public void update() {
        // This is to prevent memory leaks
        for (LayerOption layerOption: layersOptions)
            layerOption.deleteObject();
        Runtime.getRuntime().gc(); // running the garbage collector

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

    // addBtnListeners() is used to attach event handlers on the moveUp, moveDown & merge Buttons
    private void addBtnListeners() {
        // When moveUp is cliked we move up the layer option that is selected
        moveUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                layersHandler.moveLayerUp(layersHandler.getSelectedLayer());
                updateState();
            }
        });

        // When moveDown is clicked we move down the layer option that is selected 
        moveDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                layersHandler.moveLayerDown(layersHandler.getSelectedLayer());
                updateState();
            }
        });

        // When mereg is clicked we call mergeLayers method to merge all the layers that are marked ready for merge
        merge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mergeLayers();
            }
        });
    }

    /**
     * used to copy all the layers that are selected
     */
    // copyLayerOptions() is used to copy all the layers that are selected
    public void copyLayerOptoins() {
        for (LayerOption layerOption: layersOptions) {
            if (!layerOption.isSelected()) continue;
            layerOption.copyOperation();
        }

        updateState();
    }

    /**
     * used to delete all the layers that are selected
     */
    // deleteLayerOptions() is used to delete all the layers that are selected
    public void deleteLayerOptions() {
        for (LayerOption layerOption: layersOptions) {
            if (!layerOption.isSelected()) continue;
            layerOption.deleteOperation();
        }

        updateState();
    }

    /**
     * used to merge all layers that are selected for merge into one layer and adds the merged layer to the canvas and removes the old layers that were merged from the canvas
     */
    // mergeLayers() is used to merge all layers that are selected for merge into one layer and adds the merged layer to the canvas
    // and removes the old layers that were merged from the canvas
    public void mergeLayers() {
        ArrayList<LayerData> layers = layersHandler.getLayers();
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        int layersMergeCount = 0;

        for (LayerData layer: layers) {
            if (!layer.isSelectedForMerge()) continue;

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

    /**
     * used when the user decides to merge the layers thorugh the right-click pop-up menu
     */
    // mergeLayerOptions() is used when the user decides to merge the layers thorugh the right-click pop-up menu
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

    // updateState() is used to refresh the canvas and update the right panel to be in sync with the current state of the layers
    // and record the history for the undo tool
    private void updateState() {
        layersHandler.updateCanvas();
        update();
        
        if (undo != null) undo.recordHistory();
    }

    /**
     * sets the undo
     * @param undo the tool that manages how the undo and redo works
     */
    public void setUndo(UndoTool undo) {
        this.undo = undo;
    }
}