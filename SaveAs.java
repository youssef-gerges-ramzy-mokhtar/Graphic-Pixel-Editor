import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.swing.event.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;

// SaveAs is used Loads Images from the User's Computer into the Program
class SaveAs{
    private JMenu saveMenu = new JMenu("Save As");
    private OurCanvas canvas;
    private LayerData layer;

    public SaveAs(OurCanvas canvas) {
    	this.canvas = canvas;
    	addSaveMenuListener();
    }

    public JMenu getMenu() {
    	return saveMenu;
    }

     // Used to attach an event handler to the Menu Button
     private void addSaveMenuListener() {
    	saveMenu.addMenuListener(new MenuListener() {
            public void menuCanceled(MenuEvent e) {}
            public void menuDeselected(MenuEvent e) {}

            public void menuSelected(MenuEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int fileResponse = fileChooser.showOpenDialog(null);
                if (fileResponse != JFileChooser.APPROVE_OPTION) return;
			}
        });
    }
}