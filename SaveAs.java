import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

import javax.imageio.ImageIO;

// SaveAs is used Loads Images from the User's Computer into the Program
class SaveAs{
    private JMenu saveMenu = new JMenu("Save As");
    private OurCanvas canvas;

    /* creates a button on the menu to save the canvas as a file */
    public SaveAs(OurCanvas canvas) {
    	this.canvas = canvas;
    	addSaveMenuListener();
    }

    /* returns the JMenu for this button */
    public JMenu getMenu() {
    	return saveMenu;
    }

     // Used to attach an event handler to the Menu Button
     private void addSaveMenuListener() {
    	saveMenu.addMenuListener(new MenuListener() {
            public void menuCanceled(MenuEvent e) {}

            public void menuDeselected(MenuEvent e) {}

            public void menuSelected(MenuEvent e) {
                    try {
                        String fileName = JOptionPane.showInputDialog(null, "Please enter the file name");  //takes a string for the file name

                        ImageIO.write(canvas.getMainLayer().getImage(), "jpg", new File(fileName + ".jpg") );       //creates an image using the layer data
                        System.out.println("image created");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        System.out.println("ERROR... Image Not Created");
                    }
			}
        });
    }
}