import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import javax.swing.JOptionPane; 

class TextTool extends ClickableTool {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private Clickable textBtn;

	public TextTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, undo);
		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		addCanvasListener();
	}

	protected void initTool(UndoTool undo) {
		this.textBtn = new Clickable("Text");
		textBtn.addKeyBinding('s');

		addToolBtn(textBtn);
		setAsChangeMaker(undo);
		setAsLayerChanger();
	}

	private void addCanvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!textBtn.isActive()) return;
                String text = JOptionPane.showInputDialog(null, "Please Enter Text");  //takes a string for the file name
             
                // int w = canvas.getMainLayer().getWidth() - e.getX();
                // int h = canvas.getMainLayer().getHeight() - e.getY();

                // TextGraphics txtGraphics = new TextGraphics(new Point(0, 0), text, w);
                // Dimension layerDim = txtGraphics.getDimensions();
                TextLayer txtLayer = new TextLayer(new Point(e.getX(), e.getY()), Color.red, 20, text, canvas.getMainLayer().getWidth());

                // int midLayerWidth = (int) layerDim.getWidth() / 2;
                // int midLayerHeight = (int) layerDim.getHeight() / 2;
                // txtLayer.setLocation(e.getX(), e.getY());

                // txtGraphics.setDimensions((int) layerDim.getWidth(), (int) layerDim.getHeight());
                // txtGraphics.setDimensions(w, h);
                // txtLayer.updateGraphics(txtGraphics);
                
                layersHandler.addLayer(txtLayer);
                layersHandler.updateCanvas();

				recordChange(); 
				updateLayerObserver();
			}
		});
	}
}