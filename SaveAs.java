import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.swing.event.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

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
                    try {
                        String fileName = JOptionPane.showInputDialog(null, "Please enter the text");

                        ImageIO.write(canvas.getMainLayer().getImage(), "jpg", new File(fileName + ".jpg") );
                        System.out.println("image created");
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                        System.out.println("ERROR... Image Not Created");
                    }
                    
                

                /*JFileChooser fileChooser = new JFileChooser();
                int fileResponse = fileChooser.showOpenDialog(null);
                if (fileResponse != JFileChooser.APPROVE_OPTION) return;*/
			}
        });
    }
}