import java.util.*;
import javax.swing.*;
import java.awt.event.*;  

// ToolsManager is like a Facade that is responsible for creating the Tools and linking the different tools to each other
class ToolsManager {
	private OurCanvas canvas;
	private ToolsPanel toolsPanel;
	private SelectionTool selectionTool;
    private PenTool penTool;
    private EraserTool eraserTool;
    private EyeDropperTool eyeDropperTool;
    private FillTool fillTool;
    private RectangleTool rectangleTool;
    private CircleTool circleTool;
	private TriangleTool triangleTool;
	private Airbrush airBrush;
	private TextTool text;
	private Delete delete;
    private ColorGui colorGui;
    private OptionsPanel optionsPanel;

	private BlurTool blur;

    private UndoTool undo;
    private CropTool crop;

    
    private ImageLoader imageLoader;
    private LayersHandler layersHandler;
    private LayersSelectionPanel layersSelectionPanel;

    private MenuPanel menuPanel;
    private Display display;
    private LayersOptions layersOptions;

    private ArrayList<ClickableTool> clickableContainers;
	private JComboBox canvasDropList;

	

	private JButton[] canvasButtons = new JButton[3];
	
	private CanvasHandler canvasHandler;


	public ToolsManager(Display display) {

		this.canvas = new OurCanvas();
		ActionListener listener = new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
                for(int i=0; i<3; i++){if(source == canvasButtons[i]) changeCanvas(i); }
                
				
            }
        };

		for (int i = 0; i < 3; i++) {
			canvasButtons[i] = new JButton();
			canvasButtons[i].addActionListener(listener);
		 }

		this.display = display;
		
		
		canvasHandler = new CanvasHandler(canvas, canvasButtons);
		changeCanvas(2);
		changeCanvas(1);
		changeCanvas(0);
		
		


		
		
        this.layersHandler = LayersHandler.getLayersHandler(canvas); // For Handling Layers
        this.layersOptions = new LayersOptions(layersHandler);

		this.toolsPanel = new ToolsPanel();

		this.undo = new UndoTool(layersOptions, canvas);
		this.selectionTool = new SelectionTool(canvas, undo);
		this.penTool = new PenTool(layersOptions, canvas, undo);
		this.eraserTool = new EraserTool(layersOptions, canvas, undo);
		this.fillTool = new FillTool(layersOptions, canvas, undo);
		this.eyeDropperTool = new EyeDropperTool(canvas);
		this.blur = new BlurTool(canvas);
		this.rectangleTool = new RectangleTool(layersOptions, canvas, undo);
		this.circleTool = new CircleTool(layersOptions, canvas, undo);
		this.triangleTool = new TriangleTool(layersOptions, canvas, undo);
		this.airBrush = new Airbrush(layersOptions, canvas, undo);
		this.text = new TextTool(layersOptions, canvas, undo);
		this.delete = new Delete(layersOptions, canvas, undo);
		this.crop = new CropTool(layersOptions, canvas, undo);

		this.colorGui = new ColorGui();
        this.optionsPanel = new OptionsPanel(colorGui);

        this.layersSelectionPanel = new LayersSelectionPanel(canvas, optionsPanel); // Update

        this.imageLoader = new ImageLoader(layersOptions, canvas, undo); // For Loading Images from the user computer
        this.imageSaver = new SaveAs(canvas);
        this.menuPanel = new MenuPanel(canvas, imageLoader, imageSaver);

        this.clickableContainers = new ArrayList<ClickableTool>();
        layersOptions.setUndo(undo);

        initToolPanel();
		initObservers();
	}

	public void changeCanvas(int canvasNum)
	{
		
		
		canvasHandler.updateCanvas(canvasNum);
		//display.changeCanvas(canvas);
	}

	// initToolPanel() is used to add every clickable associated with each Tool to the toolsPanel
	private void initToolPanel() {
		clickableContainers.add(selectionTool);
		clickableContainers.add(delete);
		clickableContainers.add(penTool);
		clickableContainers.add(eraserTool);
		clickableContainers.add(fillTool);
		clickableContainers.add(eyeDropperTool);
		clickableContainers.add(rectangleTool);
		clickableContainers.add(circleTool);
		clickableContainers.add(triangleTool);
		clickableContainers.add(airBrush);
		clickableContainers.add(text);
		clickableContainers.add(delete);
		clickableContainers.add(undo);
		clickableContainers.add(crop);

		for (ClickableTool clickableContainer: clickableContainers)
			for (Clickable clickable: clickableContainer.getClickables())
				toolsPanel.addClickable(clickable);
	}

	// initObservers() is used to add add Observers & attach Observables to tools that are related
	private void initObservers() {
        canvas.addCanvasObserver(layersHandler); // For Observing Changes in the Canvas Size

        colorGui.addObserver(penTool); // so whenever the colorGui changes it will notify the Pen Tool to change color
        colorGui.addObserver(fillTool); // so whenever the colorGui changes it will notify the Fill Tool to change Color
        colorGui.addObserver(rectangleTool); // so whenver the colorGui changes it will notify the Rectnalge Tool to change Stroke Color
        colorGui.addObserver(circleTool); // so whenver the colorGui changes it will notify the Circle Tool to change stroke Color
		colorGui.addObserver(triangleTool);
		colorGui.addObserver(airBrush);
		colorGui.addObserver(text);

        eyeDropperTool.addObserver(colorGui); // so whenver the eye Dropper Tool is used on cnavas it notifies the colorGui to updates the color preview
	
        optionsPanel.getPenOptionsPanel().addObserver(penTool); // Pen Tool observers changes in the brush size
        optionsPanel.getPenOptionsPanel().addObserver(eraserTool); // Eraser Tool observers changes in the brush size
		optionsPanel.getPenOptionsPanel().addObserver(airBrush);
		optionsPanel.getPenOptionsPanel().addObserver(text);
		optionsPanel.getPenOptionsPanel().addObserver(blur);
        
        canvas.addObserver(toolsPanel);
	} 

	// Getters //
	public JPanel getToolsPanel() {
		return toolsPanel;
	}

	public JPanel getOptionsPanel() {
		return optionsPanel;
	}

	public JMenuBar getMenuPanel() {
		return menuPanel;
	}

	public JPanel getCanvas() {
		return canvas;
	}

	public JButton[] getCanvasButtons() {
		return canvasButtons;
	}
}