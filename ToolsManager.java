import java.util.*;
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
	private Delete delete;
    private ColorGui colorGui;
    private OptionsPanel optionsPanel;
    private UndoTool undo;
    private CropTool crop;
    
    private ImageLoader imageLoader;
	private SaveAs imageSaver;
    private LayersHandler layersHandler;
    private LayersSelectionPanel layersSelectionPanel;

    private MenuPanel menuPanel;
    private Display display;

    private ArrayList<ClickableTool> clickableContainers;

	public ToolsManager(Display display) {
		this.display = display;

		this.canvas = new OurCanvas();
		this.toolsPanel = new ToolsPanel();

		this.undo = new UndoTool(canvas);
		this.selectionTool = new SelectionTool(canvas, undo);
		this.penTool = new PenTool(canvas, undo);
		this.eraserTool = new EraserTool(canvas, undo);
		this.fillTool = new FillTool(canvas, undo);
		this.eyeDropperTool = new EyeDropperTool(canvas);
		this.rectangleTool = new RectangleTool(canvas, undo);
		this.circleTool = new CircleTool(canvas, undo);
		this.triangleTool = new TriangleTool(canvas, undo);
		this.airBrush = new Airbrush(canvas, undo);
		this.text = new TextTool(canvas, undo);
		this.delete = new Delete(canvas, undo);
		this.crop = new CropTool(canvas, undo);

		this.colorGui = new ColorGui();
        this.optionsPanel = new OptionsPanel(colorGui);

        this.imageLoader = new ImageLoader(canvas, undo); // For Loading Images from the user computer
        this.layersHandler = LayersHandler.getLayersHandler(canvas); // For Handling Layers
        this.layersSelectionPanel = new LayersSelectionPanel(canvas, optionsPanel); // Update

        this.menuPanel = new MenuPanel(canvas, imageLoader, imageSaver);

        this.clickableContainers = new ArrayList<ClickableTool>();
        initToolPanel();
		initObservers();
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
		colorGui.addObserver(triangleTool);
		colorGui.addObserver(airBrush);
		colorGui.addObserver(text);

        eyeDropperTool.addObserver(colorGui); // so whenver the eye Dropper Tool is used on cnavas it notifies the colorGui to updates the color preview
	
        optionsPanel.getPenOptionsPanel().addObserver(penTool); // Pen Tool observers changes in the brush size
        optionsPanel.getPenOptionsPanel().addObserver(eraserTool); // Eraser Tool observers changes in the brush size
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