import java.awt.event.*;
import java.awt.*;
import javax.swing.JOptionPane; 

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
                String text = JOptionPane.showInputDialog(null, "Please Enter Text");  //takes a string for text
                TextLayer txtLayer = new TextLayer(new Point(e.getX(), e.getY()), fontCol, fontSz, text, canvas.getMainLayer().getWidth());

                layersHandler.addLayer(txtLayer);
                layersHandler.updateCanvas();

				recordChange();
				updateLayerObserver();
			}
		});
	}

	// Observer Pattern //
	public void update(int fontSz) {
		this.fontSz = fontSz;
	}
	public void update2(Color col) {
		this.fontCol = col;
	}
	public void update3() {};
}