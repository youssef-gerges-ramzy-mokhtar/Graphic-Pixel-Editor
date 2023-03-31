import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.*;

// ProjectLoader is used to load a saved project into the canvas
public class ProjectLoader extends ClickableTool {
	private JMenu openProjectMenu;
	private OurCanvas canvas;
	private LayersHandler layersHandler;
	private ImageLoader imgLoader;

	/**
	 * ProjectLoader is used to load a saved project into the canvas
	 * @param layerObserver a layerObserver is an object that observers changes that happens to the layers structure
	 * @param canvas is the current canvas that holds all the layers
	 * @param undo is the tool that manages how the undo and redo works
	 */
	public ProjectLoader(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, undo);
		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.openProjectMenu = new JMenu("Open Project");
		this.imgLoader = new ImageLoader(layerObserver, canvas, undo);

		addMenuListener();
	}

	/**
	 * initTool initialize the properties of the Project Loader
	 * - The Project Loader Affects the Undo Tool
	 * - The Project Loader Affects the Layers Panel
	 * @param undo is the tool that manages how the undo and redo works
	 */
	protected void initTool(UndoTool undo) {
		setAsChangeMaker(undo);
        setAsLayerChanger();
    }

    private void addMenuListener() {
    	openProjectMenu.addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e) {}
			public void menuDeselected(MenuEvent e) {}

			public void menuSelected(MenuEvent e) {
				JFileChooser fileChooser = new JFileChooser();
                int fileResponse = fileChooser.showOpenDialog(null);
                if (fileResponse != JFileChooser.APPROVE_OPTION) return;

            
				loadProject(fileChooser.getSelectedFile().getAbsolutePath());                
                recordChange();
                updateLayerObserver();
			}
		});
    }

    private void loadProject(String filePath) {
		// Here we are checking if the project text file has the extension 'scc210' if the file doesn't have the 'scc210' extension we don't load the project
		try {
			String fileExtension = filePath.substring(filePath.length()-6, filePath.length());
			if (!fileExtension.equals("scc210")) return;
		} catch (Exception e) {
			// Output an error message to the user
			return;
		}

		this.layersHandler.clear();
		File projectFile = new File(filePath); // the projectFile contains all the information for all layers of the project
		String projectFolderPath = filePath.replace(".scc210", ".project");
	
		try {
			BufferedReader projectReader = new BufferedReader(new FileReader(projectFile));
			String line;

			// We read line by line from the projectFile to get the information for each layer seperately
			while ((line = projectReader.readLine()) != null) {
				String[] info = line.split(","); // info is an array containg all the information of the current layer

				/*
					info[0] is used to hold a character representing the type of layer that this info represent
					r -- indicates that the current info is representing Rectangle Layer
					c -- indicates that the current info is representing Circle Layer
					t -- indicates that the current info is representing Triangle Layer
					d -- indicates that the current info is representing Drawing Layer
					i -- indicates that the current info is representing Image Layer
					s -- indicates that the current info is representing Text Layer
				*/

				// Loading a certaing Layer based on the type of layer that this info represent
				if (info[0].equals("r")) loadRectangle(info); 
				if (info[0].equals("c")) loadCircle(info);
				if (info[0].equals("t")) loadTriangle(info);
				if (info[0].equals("d")) loadDrawing(info, projectFolderPath);
				if (info[0].equals("i")) loadImage(info, projectFolderPath);
				if (info[0].equals("s")) loadText(info);
			}
			projectReader.close();
		} catch (Exception e) {
			System.out.println("Error Occurred");
			e.printStackTrace();
		}	

		// Finally we refresh the canvas to display all the added layers
		layersHandler.updateCanvas();
	}

	// loadRectangle() is used to create a new Rectangle Layer based on the rectangleInfo and adds it to the layersHandler
	private void loadRectangle(String[] rectangleInfo) {
		int width = Integer.parseInt(rectangleInfo[1]);
		int height = Integer.parseInt(rectangleInfo[2]);
		int x = Integer.parseInt(rectangleInfo[3]);
		int y = Integer.parseInt(rectangleInfo[4]);
		int rgb = Integer.parseInt(rectangleInfo[5]);

		RectangleLayer rectangleLayer = new RectangleLayer(width, height, new Color(rgb), new Point(x, y));
		RectangleGraphics rectangleGraphics = new RectangleGraphics(new Point(0, 0));
		rectangleGraphics.setDimension(width, height);
		rectangleGraphics.setFillColor(new Color(rgb));
		rectangleLayer.updateGraphics(rectangleGraphics);
		
		layersHandler.addLayer(rectangleLayer);
	}

	// loadTriangle() is used to create a new Triangle Layer based on the triangleInfo and adds it to the layersHandler
	private void loadTriangle(String[] triangleInfo) {
		int width = Integer.parseInt(triangleInfo[1]);
		int height = Integer.parseInt(triangleInfo[2]);
		int x = Integer.parseInt(triangleInfo[3]);
		int y = Integer.parseInt(triangleInfo[4]);
		int rgb = Integer.parseInt(triangleInfo[5]);

		TriangleLayer triangleLayer = new TriangleLayer(width, height, new Color(rgb), new Point(x, y));
		TriangleGraphics triangleGraphics = new TriangleGraphics(new Point(0, 0));
		triangleGraphics.setDimension(width, height);
		triangleGraphics.setFillColor(new Color(rgb));
		triangleLayer.updateGraphics(triangleGraphics);
		
		layersHandler.addLayer(triangleLayer);
	}

	// loadCircle() is used to create a new Circle Layer based on the circleInfo and adds it to the layersHandler
	private void loadCircle(String[] circleInfo) {
		int width = Integer.parseInt(circleInfo[1]);
		int height = Integer.parseInt(circleInfo[2]);
		int x = Integer.parseInt(circleInfo[3]);
		int y = Integer.parseInt(circleInfo[4]);
		int rgb = Integer.parseInt(circleInfo[5]);
	
		CircleLayer circleLayer = new CircleLayer(width, height, new Color(rgb), new Point(x, y));
		CircleGraphics circleGraphics = new CircleGraphics(new Point(0, 0));
		circleGraphics.setDimension(width, height);
		circleGraphics.setFillColor(new Color(rgb));
		circleLayer.updateGraphics(circleGraphics);
		
		layersHandler.addLayer(circleLayer);
	}

	// loadImage() is used to create a new Image Layer based on the imgInfo and importing the image based on the projectFolderPath. And we add the image layer to the layersHandler
	private void loadImage(String[] imgInfo, String projectFolderPath) {
		String imgPath = projectFolderPath + "/" + imgInfo[imgInfo.length - 1];
		int xCoord = Integer.parseInt(imgInfo[1]);
		int yCoord = Integer.parseInt(imgInfo[2]);
		BufferedImage img = imgLoader.loadImage(imgPath, false);
		
		layersHandler.addLayer(new ImageLayer(img, new Point(xCoord, yCoord)));
	}

	// loadDrawing() is used to create a new Drawing Layer based on the drawingInfo and importing the drawingImage based on the projectFolderPath
	// finally we set the drawing layer in the layersHandler to the newly created Draing Layer
	private void loadDrawing(String[] drawingInfo, String projectFolderPath) {
		String drawingPath = projectFolderPath + "/" + drawingInfo[drawingInfo.length - 1];
		int xCoord = Integer.parseInt(drawingInfo[1]);
		int yCoord = Integer.parseInt(drawingInfo[2]);
		BufferedImage img = imgLoader.loadImage(drawingPath, false);

		layersHandler.setDrawingLayer(new DrawingLayer(img, new Point(xCoord, yCoord)));
		layersHandler.changeSelectedLayer(0);
	}

	// loadText() is used to create a new Text Layer based on the textInfo and add it to the layersHandler
	private void loadText(String[] textInfo) {
		int width = Integer.parseInt(textInfo[1]);
		int height = Integer.parseInt(textInfo[2]);
		int x = Integer.parseInt(textInfo[3]);
		int y = Integer.parseInt(textInfo[4]);
		int fontCol = Integer.parseInt(textInfo[5]);
		int fontSz = Integer.parseInt(textInfo[6]);
		String text = textInfo[7];

		TextLayer textLayer = new TextLayer(new Point(x, y), new Color(fontCol), fontSz, text, canvas.getWidth());
		textLayer.resize(width, height);

		layersHandler.addLayer(textLayer);
	}

	/**
     * return a menu button to allow the user to load projects
     * @return a menu button to access the Project Loader functionality
     */
	public JMenu getMenu() {
		return openProjectMenu;
	}
}

/*
	Some Notes:
		- This code is not DRY
		- The code structure is not the best

	Reason:
		Due to Time Constraints I wasn't able to improve the code structure

	Future Improvements:
		- Having a method in the LayerData Class that contains all the common Information between all layers
		- Having a Class for each type of Layer Load (for example ImageLayerLoader, RectangleLayerLoader, etc...)
		- Having a Class called ShapeLayerLoader that contains a common method for loading any shape to make the code more DRY
		- Displaying Error messages to the user when something goes wrong
		- Having a RasterizedLayer Class to represent both the DrawingLayer and the ImageLayer

	==> Note, some of the proposed improvements might turn out to be no good during development but because I didn't try them myself I don't actually know how good and efficient is this improvements
*/