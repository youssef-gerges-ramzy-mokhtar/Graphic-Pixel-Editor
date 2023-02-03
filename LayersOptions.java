import javax.swing.*;
import java.awt.*;
import java.util.*;

class LayersOptions extends JFrame implements LayerObserver {
	private LayersHandler layersHandler;
    private UndoTool undo;

    public LayersOptions(LayersHandler layersHandler) {
        this.layersHandler = layersHandler;

        // setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        setLayout(new FlowLayout());
        setTitle("Pixel Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);          //resizeable frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(700, screenSize.height);              //sets x and y dimension
        setVisible(true);
        getContentPane().setBackground(Constants.mainColor);  //change background colour
        
        update();
    }

    public void update() {
        getContentPane().removeAll();
        revalidate();

        ArrayList<LayerData> layers = layersHandler.getLayers();
        for (int i = layers.size() - 1; i >= 0; i--) {
            LayerOption layerOption = new LayerOption(this, layersHandler, layers.get(i), undo, i);
            if (layers.get(i) == layersHandler.getSelectedLayer()) layerOption.select();

            getContentPane().add(layerOption);
        }

        revalidate();
        repaint();
    }

    public void setUndo(UndoTool undo) {
        this.undo = undo;
    }
}