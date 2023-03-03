import java.io.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

class ProjectLoader extends ClickableTool {
	private JMenu openProjectMenu;
	private OurCanvas canvas;
	private LayersHandler layersHandler;

	public ProjectLoader(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
		super(layerObserver, undo);
		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.openProjectMenu = new JMenu("Open Project");

		addMenuListener();
	}

	protected void initTool(UndoTool undo) {
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
		try {
			String fileExtension = filePath.substring(filePath.length()-6, filePath.length());
			if (!fileExtension.equals("scc210")) return;
		} catch (Exception e) {
			// Output an error message to the user
			return;
		}

		this.layersHandler.clear();

		File projectFile;
		projectFile = new File(filePath);
	
		try {
			BufferedReader projectReader = new BufferedReader(new FileReader(projectFile));
			String line;
			while ((line = projectReader.readLine()) != null) {
				// System.out.println(line);

				String[] info = line.split(",");
				System.out.println("Printing Info: " +  info[0]);
				if (info[0].equals("r")) {
					System.out.println("Loading Rectangle");
					loadRectangle(info);
				} 

				if (info[0].equals("c")) {
					System.out.println("Loading Circle");
					loadCircle(info);
				} 

				if (info[0].equals("t")) {
					System.out.println("Loading Triangle");
					loadTriangle(info);
				}

				if (info[0].equals("d")) {
					System.out.println("Loading Drawing Layer");
					loadDrawing(info);
				}

				if (info[0].equals("i")) {
					System.out.println("Loading Image");
					loadImage(info);
				}
			}
		} catch (Exception e) {
			System.out.println("Error Occurred");
			e.printStackTrace();
		}

		layersHandler.updateCanvas();
	}

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
		
		System.out.println(rectangleLayer);
		layersHandler.addLayer(rectangleLayer);
	}

	private void loadTriangle(String[] rectangleInfo) {
		int width = Integer.parseInt(rectangleInfo[1]);
		int height = Integer.parseInt(rectangleInfo[2]);
		int x = Integer.parseInt(rectangleInfo[3]);
		int y = Integer.parseInt(rectangleInfo[4]);
		int rgb = Integer.parseInt(rectangleInfo[5]);

		TriangleLayer triangleLayer = new TriangleLayer(width, height, new Color(rgb), new Point(x, y));
		TriangleGraphics triangleGraphics = new TriangleGraphics(new Point(0, 0));
		triangleGraphics.setDimension(width, height);
		triangleGraphics.setFillColor(new Color(rgb));
		
		triangleLayer.updateGraphics(triangleGraphics);
		layersHandler.addLayer(triangleLayer);
	}

	private void loadCircle(String[] rectangleInfo) {
		int width = Integer.parseInt(rectangleInfo[1]);
		int height = Integer.parseInt(rectangleInfo[2]);
		int x = Integer.parseInt(rectangleInfo[3]);
		int y = Integer.parseInt(rectangleInfo[4]);
		int rgb = Integer.parseInt(rectangleInfo[5]);
	
		CircleLayer circleLayer = new CircleLayer(width, height, new Color(rgb), new Point(x, y));
		CircleGraphics circleGraphics = new CircleGraphics(new Point(0, 0));
		circleGraphics.setDimension(width, height);
		circleGraphics.setFillColor(new Color(rgb));
		
		circleLayer.updateGraphics(circleGraphics);
		layersHandler.addLayer(circleLayer);
	}

	private void loadImage(String[] imgInfo) {

	}

	private void loadDrawing(String[] drawingInfo) {
		
	}

	public JMenu getOpenProjectMenu() {
		return openProjectMenu;
	}
}