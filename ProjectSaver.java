import java.io.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

// ProjectSaver is used to Save the Layers State for later user re-access 
class ProjectSaver {
	private JMenu saveProjectMenu;
	private OurCanvas canvas;
	private LayersHandler layersHandler;

	/**
	 * ProjectSaver is used to Save the Layers State for later user re-access 
	 * @param canvas is the current canvas that holds all the layers
	 */
	public ProjectSaver(OurCanvas canvas) {
		this.canvas = canvas;
		this.layersHandler = LayersHandler.getLayersHandler(canvas);
		this.saveProjectMenu = new JMenu("Save Project");

		addMenuListener();
	}

	private void addMenuListener() {
    	saveProjectMenu.addMenuListener(new MenuListener() {
            public void menuCanceled(MenuEvent e) {}
            public void menuDeselected(MenuEvent e) {}

            public void menuSelected(MenuEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int fileResponse = fileChooser.showSaveDialog(null);
                if (fileResponse != JFileChooser.APPROVE_OPTION) return;

                saveProject(fileChooser.getSelectedFile().getAbsolutePath());
			}
        });
    }

    /*
		saveProject() takes the filePath where the user wants to save his project
		and creates a text file with the 'scc210' extension to sae the layers information
		and creates a .project folder that contains all images used in the project
    */
	private void saveProject(String filePath) {
		File projectFile = new File(filePath + ".scc210");
		File projectFolder = new File(filePath + ".project");

		try {
			projectFile.createNewFile(); // create the project file
			projectFolder.mkdirs(); // create the project folder
		} catch (IOException e) {
			System.out.println("An Error Occured");
			projectFile = null;
		}

		if (projectFile == null) {
			// output an error message to the user (error occured while creating project files)
			return;
		}

		// Writing the all the layers information to the projectFile		
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(projectFile);
			String layersInfo = this.getLayersInfo(filePath + ".project/");

			fileWriter.write(layersInfo);
			fileWriter.close();
		} catch (Exception e) {
			fileWriter = null;
		}

		if (fileWriter == null) {
			// output an error message to the user (error occured while saving project information)
			return;
		}
	}

	// getLayersInfo() is used to return a String containg all information for all layers
	private String getLayersInfo(String filePath) {
		AbstractList<LayerData> layers = layersHandler.getLayers();

		String layersInfo = "";
		for (int layerPos = 0; layerPos < layers.size(); layerPos++) {
			LayerData currentLayer = layers.get(layerPos);
			layersInfo += currentLayer.getLayerInfo(layerPos);

			if (currentLayer instanceof DrawingLayer || currentLayer instanceof ImageLayer)
				this.saveImage(currentLayer, layerPos, filePath);
		}

		return layersInfo;
	}

	// saveImage() is used to save an Image into the .project folder
	private void saveImage(LayerData layer, int layerPos, String filePath) {
		SaveAs imageSaver = new SaveAs(canvas);
		imageSaver.saveImageAs(layer.getImage(), filePath + Integer.toString(layerPos));
	}

	/**
     * return a menu button to allow the user to save projects
     * @return a menu button to access the Project Saver functionality
     */
	public JMenu getMenu() {
		return saveProjectMenu;
	}
}