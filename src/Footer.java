import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class Footer extends JMenuBar {
    private OurCanvas canvas;
    private LayersHandler layersHandler;
    private JSlider magnifySlider;
    private double magnifyValue;
    private double prevMagnifyValue;
    private UndoTool undo;
    private LayerObserver layerObserver;

    public Footer(LayerObserver layerObserver, UndoTool undo, OurCanvas canvas) {
        this.canvas = canvas;
        this.layersHandler = LayersHandler.getLayersHandler(canvas);
        this.magnifySlider = new JSlider(1, 800);
        this.magnifyValue = 1.0;
        this.prevMagnifyValue = 1.0;
        this.undo = undo;
        this.layerObserver = layerObserver;

        magnifySlider.setValue(100);
        this.add(magnifySlider);
        addMagnifySliderListener();
    }

    private void addMagnifySliderListener() {
        magnifySlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				magnifyValue = magnifySlider.getValue() / 100.0;
			}
		});
        
        magnifySlider.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (magnifyValue <= 0.00001) return;
                
                double zoomRate = 1 + ((magnifyValue - prevMagnifyValue) / prevMagnifyValue); // 1 signifies the layer dimensions
                layersHandler.zoomAllLayers(zoomRate);
                canvas.zoom(zoomRate);
                
                prevMagnifyValue = magnifyValue;

                undo.recordHistory();
                layerObserver.update();
            }
        });
    }
}