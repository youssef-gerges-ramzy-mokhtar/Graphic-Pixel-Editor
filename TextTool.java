import java.awt.event.*;
import java.awt.*;
import javax.swing.JOptionPane; 

// Text Tool is used to add text into the canvas
class TextTool extends ClickableTool implements Observer {
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private Clickable textBtn;
	private int fontSz;
	private Color fontCol;

	public TextTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, undo);
		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.fontSz = 20;
		this.fontCol = Color.black;

		addCanvasListener();
	}

	// initTool initialize the properties of the Text Tool
	/*
		- The Text Tool Affects the Undo Tool
		- The Text Tool Affects the Layers Panel
		- The Text Tool has shortcut 's'
	*/
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
                String text = JOptionPane.showInputDialog(null, "Please Enter Text");  // prompting the user to input a string
                TextLayer txtLayer = new TextLayer(new Point(e.getX(), e.getY()), fontCol, fontSz, text, canvas.getMainLayer().getWidth());

                layersHandler.addLayer(txtLayer);
                layersHandler.updateCanvas();

				recordChange();
				updateLayerObserver();
			}
		});
	}

	// Observer Pattern //

	// update(fontSz) is called whenver the user updates the size slider
	public void update(int fontSz) {
		this.fontSz = fontSz;
	}

	// update(col) is used whenver the user picks a color from the color picker
	public void update2(Color col) {
		this.fontCol = col;
	}
	public void update3() {};
}