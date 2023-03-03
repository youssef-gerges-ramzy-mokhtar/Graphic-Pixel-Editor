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
		System.out.println("HI");
		File projectFile;
		projectFile = new File(filePath + ".scc210");

		try {
			projectFile.createNewFile();
		} catch (IOException e) {
			System.out.println("An Error Occured");
			projectFile = null;
		}

		if (projectFile == null) {
			// output an error message to the user
			return;
		}

		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(projectFile);

			String layersInfo = getLayersInfo();
			System.out.println(layersInfo);

			fileWriter.write(layersInfo);
			fileWriter.close();
		} catch (Exception e) {
			fileWriter = null;
		}

		if (fileWriter == null) {
			// output an error message to the user
			return;
		}
	}

	public String getLayersInfo() {
		AbstractList<LayerData> layers = layersHandler.getLayers();

		String layersInfo = "";
		for (int layerPos = 0; layerPos < layers.size(); layerPos++)
			layersInfo += layers.get(layerPos).getLayerInfo(layerPos);

		return layersInfo;
	}

	public JMenu getSaveProjectMenu() {
		return saveProjectMenu;
	}
}