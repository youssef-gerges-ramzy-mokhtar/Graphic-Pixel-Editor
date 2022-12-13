import javax.swing.*;

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

		this.colorGui = new ColorGui();
        this.optionsPanel = new OptionsPanel(colorGui);

        this.imageLoader = new ImageLoader(canvas); // For Loading Images from the user computer
        this.layersHandler = LayersHandler.getLayersHandler(canvas); // For Handling Layers
        this.layersSelectionPanel = new LayersSelectionPanel(canvas, optionsPanel); // Update

        this.menuPanel = new MenuPanel(canvas, imageLoader);

		initToolPanel();
		initObservers();
	}

	private void initToolPanel() {
		toolsPanel.addClickable(selectionTool.getClickable());
		toolsPanel.addClickable(penTool.getClickable());
		toolsPanel.addClickable(eraserTool.getClickable());
		toolsPanel.addClickable(fillTool.getClickable());
		toolsPanel.addClickable(eyeDropperTool.getClickable());
		toolsPanel.addClickable(rectangleTool.getClickable());
		toolsPanel.addClickable(circleTool.getClickable());

		toolsPanel.addClickable(new Clickable("Air Brush")); // Temporary Clickable
		toolsPanel.addClickable(new Clickable("Blur")); // Temporary Clickable
	}

	private void initObservers() {
        imageLoader.addObserver(layersHandler); // So whenever a user imports an image from his computer will automatically be added to the layersHandler
        canvas.addCanvasObserver(layersHandler); // For Observing Changes in the Canvas Size

        colorGui.addObserver(penTool);
        colorGui.addObserver(fillTool);
        colorGui.addObserver(rectangleTool);
        colorGui.addObserver(circleTool);

        eyeDropperTool.addObserver(colorGui);
	
        optionsPanel.getPenOptionsPanel().addObserver(penTool);
        optionsPanel.getPenOptionsPanel().addObserver(eraserTool);
        optionsPanel.getPenOptionsPanel().addObserver(rectangleTool);
        optionsPanel.getPenOptionsPanel().addObserver(circleTool);
        canvas.addObserver(toolsPanel);
	} 

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