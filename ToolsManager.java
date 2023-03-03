import java.util.*;
import javax.swing.*;
import java.awt.event.*;  

// ToolsManager is like a Facade that is responsible for creating the Tools and linking the different tools to each other
class ToolsManager {
	private OurCanvas canvas;
	private ToolsPanel toolsPanel;
    private ColorGui colorGui;
    private OptionsPanel optionsPanel;
	private ImageFilters imageFilters;
	
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
	private BlurTool blur;
    private UndoTool undo;
    private CropTool crop;
	private Clear clear;
    private CutterTool cutter;
    private ImageLoader imageLoader;
    private SaveAs imageSaver;
	private Help helpMenu;
    private LayersHandler layersHandler;

    private MenuPanel menuPanel;
    private LayersOptions layersOptions;

    private ArrayList<ClickableTool> clickableContainers;
	private JComboBox canvasDropList;
	
	private JButton[] canvasButtons = new JButton[3];
	private CanvasHandler canvasHandler;

	private Footer hamzaFooter;

	public ToolsManager() {
		initState();
        initToolPanel();
		initObservers();
	}

	public void changeCanvas(int canvasNum) {
		canvasHandler.updateCanvas(canvasNum);
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
		clickableContainers.add(blur);
		clickableContainers.add(cutter);
		clickableContainers.add(clear);

		for (ClickableTool clickableContainer: clickableContainers)
			for (Clickable clickable: clickableContainer.getClickables())
				toolsPanel.addClickable(clickable);
	}

	// initState() is used to create new instances of all the Class Data Members
	private void initState() {
		this.canvas = new OurCanvas(); // For representing the Canvas

		ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
                for(int i=0; i<3; i++){if(source == canvasButtons[i]) changeCanvas(i);}
            }
        };

		for (int i = 0; i < 3; i++) {
			canvasButtons[i] = new JButton();
			canvasButtons[i].addActionListener(listener);
		}

        this.layersHandler = LayersHandler.getLayersHandler(canvas); // For Handling Layers
        this.layersOptions = new LayersOptions(layersHandler); // For Having the Layers Panel

		this.toolsPanel = new ToolsPanel(); // For Displaying the Buttons of each tool on the left

		// All the following represents the tools in the program
		this.undo = new UndoTool(layersOptions, canvas);
		this.selectionTool = new SelectionTool(layersOptions, canvas, undo);
		this.penTool = new PenTool(layersOptions, canvas, undo);
		this.eraserTool = new EraserTool(layersOptions, canvas, undo);
		this.fillTool = new FillTool(layersOptions, canvas, undo);
		this.eyeDropperTool = new EyeDropperTool(canvas);
		this.blur = new BlurTool(layersOptions, canvas, undo);
		this.rectangleTool = new RectangleTool(layersOptions, canvas, undo);
		this.circleTool = new CircleTool(layersOptions, canvas, undo);
		this.triangleTool = new TriangleTool(layersOptions, canvas, undo);
		this.airBrush = new Airbrush(layersOptions, canvas, undo);
		this.text = new TextTool(layersOptions, canvas, undo);
		this.delete = new Delete(layersOptions, canvas, undo);
		this.crop = new CropTool(layersOptions, canvas, undo);
		this.clear = new Clear(layersOptions, canvas, undo);
		this.imageFilters = new ImageFilters(layersOptions, canvas, undo);
		this.cutter = new CutterTool(layersOptions, canvas, undo);
		
		this.colorGui = new ColorGui(); // colorGui Color Picker
        this.optionsPanel = new OptionsPanel(colorGui); // optionsPanel holds all the options available for the user on the top

		this.helpMenu = new Help(canvas);
        this.imageLoader = new ImageLoader(layersOptions, canvas, undo); // For Loading Images from the user computer
        this.imageSaver = new SaveAs(canvas); // For Saving Images to the User computer
        this.menuPanel = new MenuPanel(canvas, imageLoader, imageSaver, helpMenu); // MenuPanel for Dispalying the Buttons associated with the loading and saving classes
		this.hamzaFooter = new Footer(layersOptions, undo, canvas);

        this.clickableContainers = new ArrayList<ClickableTool>(); // contains all the tools that are accessed through a clickable component
        layersOptions.setUndo(undo);

		canvasHandler = new CanvasHandler(canvas, canvasButtons, layersOptions);
		
		canvasHandler.setUndo(undo);
		changeCanvas(2);
		changeCanvas(1);
		changeCanvas(0);

	}

	// initObservers() is used to add add Observers & attach Observables to tools that are related
	private void initObservers() {
        canvas.addCanvasObserver(layersHandler); // For Observing Changes in the Canvas Size

        colorGui.addObserver(penTool); // so whenever the colorGui changes it will notify the Pen Tool to change color
        colorGui.addObserver(fillTool); // so whenever the colorGui changes it will notify the Fill Tool to change Color
        colorGui.addObserver(rectangleTool); // so whenver the colorGui changes it will notify the Rectnalge Tool to change Stroke Color
        colorGui.addObserver(circleTool); // so whenver the colorGui changes it will notify the Circle Tool to change stroke Color
		colorGui.addObserver(triangleTool); // so whenever the colorGui changes it will notify the triangle tool to change its color
		colorGui.addObserver(airBrush); // so whenever the colorGui changes it will notify the air brush tool to change its color
		colorGui.addObserver(text); // so whenever the colorGui changes it will notify the text tool to change its color

        eyeDropperTool.addObserver(colorGui); // so whenver the eye Dropper Tool is used on cnavas it notifies the colorGui to updates the color preview
	
        optionsPanel.getPenOptionsPanel().addObserver(penTool); // Pen Tool Observes changes in the brush size
        optionsPanel.getPenOptionsPanel().addObserver(eraserTool); // Eraser Tool Observes changes in the brush size
		optionsPanel.getPenOptionsPanel().addObserver(airBrush); /// Air Brush Tool Observes changes in the brush size
		optionsPanel.getPenOptionsPanel().addObserver(text); // Text Tool Observes changes in the brush size 
		optionsPanel.getPenOptionsPanel().addObserver(blur); // Blur Tool Observes changes in the brush size
        
        canvas.addObserver(toolsPanel); // So whenver the canvas is being resized it will notify the toolsPanel to deselect any button that is being active
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

	public JPanel getLayerOptionsPanel() {
		return layersOptions;
	}

	public JButton[] getCanvasButtons() {
		return canvasButtons;
	}

	public Footer getFooter() {
		return hamzaFooter;
	}
}