import javax.swing.*;

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

    private ColorGui colorGui;
    private OptionsPanel optionsPanel;
    
    private ImageLoader imageLoader;
    private LayersHandler layersHandler;
    private LayersSelectionPanel layersSelectionPanel;

    private MenuPanel menuPanel;

	public ToolsManager() {
		this.canvas = new OurCanvas();
		this.toolsPanel = new ToolsPanel();

		this.selectionTool = new SelectionTool(canvas);
		this.penTool = new PenTool(canvas);
		this.eraserTool = new EraserTool(canvas);
		this.fillTool = new FillTool(canvas);
		this.eyeDropperTool = new EyeDropperTool(canvas);
		this.rectangleTool = new RectangleTool(canvas);
		this.circleTool = new CircleTool(canvas);
		this.triangleTool = new TriangleTool(canvas);
		this.airBrush = new Airbrush(canvas);
		this.text = new TextTool(canvas);

		this.colorGui = new ColorGui();
        this.optionsPanel = new OptionsPanel(colorGui);

        this.imageLoader = new ImageLoader(canvas); // For Loading Images from the user computer
        this.layersHandler = LayersHandler.getLayersHandler(canvas); // For Handling Layers
        this.layersSelectionPanel = new LayersSelectionPanel(canvas, optionsPanel); // Update

        this.menuPanel = new MenuPanel(canvas, imageLoader);

		initToolPanel();
		initObservers();
	}

	// initToolPanel() is used to add every clickable associated with each Tool to the toolsPanel
	private void initToolPanel() {
		toolsPanel.addClickable(selectionTool.getClickable());
		toolsPanel.addClickable(penTool.getClickable());
		toolsPanel.addClickable(eraserTool.getClickable());
		toolsPanel.addClickable(fillTool.getClickable());
		toolsPanel.addClickable(eyeDropperTool.getClickable());
		toolsPanel.addClickable(rectangleTool.getClickable());
		toolsPanel.addClickable(circleTool.getClickable());
		toolsPanel.addClickable(triangleTool.getClickable());
		toolsPanel.addClickable(airBrush.getClickable());
		toolsPanel.addClickable(text.getClickable());

		toolsPanel.addClickable(new Clickable("Blur")); // Temporary Clickable
	}

	// initObservers() is used to add add Observers & attach Observables to tools that are related
	private void initObservers() {
        imageLoader.addObserver(layersHandler); // So whenever a user imports an image from his computer will automatically be added to the layersHandler
        canvas.addCanvasObserver(layersHandler); // For Observing Changes in the Canvas Size

        colorGui.addObserver(penTool); // so whenever the colorGui changes it will notify the Pen Tool to change color
        colorGui.addObserver(fillTool); // so whenever the colorGui changes it will notify the Fill Tool to change Color
        colorGui.addObserver(rectangleTool); // so whenver the colorGui changes it will notify the Rectnalge Tool to change Stroke Color
        colorGui.addObserver(circleTool); // so whenver the colorGui changes it will notify the Circle Tool to change stroke Color
		colorGui.addObserver(airBrush);
		colorGui.addObserver(text);

        eyeDropperTool.addObserver(colorGui); // so whenver the eye Dropper Tool is used on cnavas it notifies the colorGui to updates the color preview
	
        optionsPanel.getPenOptionsPanel().addObserver(penTool); // Pen Tool observers changes in the brush size
        optionsPanel.getPenOptionsPanel().addObserver(eraserTool); // Eraser Tool observers changes in the brush size
        optionsPanel.getPenOptionsPanel().addObserver(rectangleTool); // Rectnalge Tool observers changes in the brush size (That is Temporary)
        optionsPanel.getPenOptionsPanel().addObserver(circleTool); // Circle Tool observers change sin the brush size (That is Temporary)
		optionsPanel.getPenOptionsPanel().addObserver(triangleTool);
		optionsPanel.getPenOptionsPanel().addObserver(airBrush);
		optionsPanel.getPenOptionsPanel().addObserver(text);
        
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
}