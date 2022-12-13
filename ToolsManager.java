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

	public ToolsManager(OurCanvas canvas, ToolsPanel toolsPanel) {
		this.selectionTool = new SelectionTool(canvas);
		this.penTool = new PenTool(canvas);
		this.eraserTool = new EraserTool(canvas);
		this.fillTool = new FillTool(canvas);
		this.eyeDropperTool = new EyeDropperTool(canvas);
		this.rectangleTool = new RectangleTool(canvas);
		this.circleTool = new CircleTool(canvas);

		initToolPanel(toolsPanel);
	}

	private void initToolPanel(ToolsPanel toolsPanel) {
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



	// See in the Future //
	public PenTool getPenGui() {
		return penTool;
	}

	public EyeDropperTool getEyeDropper() {
		return eyeDropperTool;
	}

	public RectangleTool getRectangle() {
		return rectangleTool;
	}

	public FillTool getFill() {
		return fillTool;
	}

	public CircleTool getCircle() {
		return circleTool;
	}

	public EraserTool getEraserTool() {
		return eraserTool;
	}
}