import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import javax.imageio.ImageIO;

// Help is used Loads Images from the User's Computer into the Program
class Help{
    private JMenu helpMenu = new JMenu("Help");
    private OurCanvas canvas;

    /** 
     * creates a button on the menu to save the canvas as a file
     * @param canvas
    */
    public Help(OurCanvas canvas) {
    	this.canvas = canvas;
    	addhelpMenuListener();
    }

    /** 
     * returns the JMenu for this button *
     * @return
    */
    public JMenu getMenu() {
    	return helpMenu;
    }

    /** 
      * Used to attach an event handler to the Menu Button 
    */ 
     private void addhelpMenuListener() {
    	helpMenu.addMenuListener(new MenuListener() {
            public void menuCanceled(MenuEvent e) {}

            public void menuDeselected(MenuEvent e) {}

            public void menuSelected(MenuEvent e) {
                                
			}
        });
    }
}