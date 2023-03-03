import java.io.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

class ProjectSaver {
	private JMenu saveProjectMenu;
	private OurCanvas canvas;
	private LayersHandler layersHandler;

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
            	System.out.println("HI");
                JFileChooser fileChooser = new JFileChooser();
                int fileResponse = fileChooser.showSaveDialog(null);
                if (fileResponse != JFileChooser.APPROVE_OPTION) return;

                saveProject(fileChooser.getSelectedFile().getAbsolutePath());
			}
        });
    }

	private void saveProject(String filePath) {
		File projectFile = new File(filePath + ".scc210");
		File projectFolder = new File(filePath + ".project");

		try {
			projectFile.createNewFile();
			projectFolder.mkdirs();
		} catch (IOException e) {
			System.out.println("An Error Occured");
			projectFile = null;
		}

		if (projectFile == null) {
			// output an error message to the user (error occured while creating project files)
			return;
		}

		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(projectFile);

			String layersInfo = this.getLayersInfo(filePath + ".project/");
			System.out.println(layersInfo);

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

	private void saveImage(LayerData layer, int layerPos, String filePath) {
		SaveAs imageSaver = new SaveAs(canvas);
		imageSaver.saveImageAs(layer.getImage(), filePath + Integer.toString(layerPos));
	}

	public JMenu getSaveProjectMenu() {
		return saveProjectMenu;
	}
}