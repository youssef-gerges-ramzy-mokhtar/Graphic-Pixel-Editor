import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.*;

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
                JFileChooser fileChooser = new JFileChooser();
                int fileResponse = fileChooser.showSaveDialog(null);
                if (fileResponse != JFileChooser.APPROVE_OPTION) return;

                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                saveImageAs(canvas.getMainLayer().getImage(), filePath);
                
                // ImageIO.write(canvas.getMainLayer().getImage(), "png", new File(filePath + ".png"));       //creates an image using the layer data
                System.out.println("image created");
			}
        });
    }

    public void saveImageAs(BufferedImage img, String filePath) {
        try {
            ImageIO.write(img, "png", new File(filePath + ".png")); //creates an image using the layer data
        } catch (IOException e1) {
            e1.printStackTrace();        
            System.out.println("ERROR... Image Not Created");
        }
    }
}