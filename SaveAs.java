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
    	
    }

    public JMenu getMenu() {
    	return saveMenu;
    }
}