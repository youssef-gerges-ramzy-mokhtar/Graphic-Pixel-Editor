import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.swing.event.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;

// ImageLoader is used Loads Images from the User's Computer into the Program
class ImageLoader extends ClickableTool {
    private JMenu openImageMenu = new JMenu("Open Image");
    private OurCanvas canvas;
    private ImageLayer lastLoadedImg;
    private ArrayList<ImageObserver> observers;

    public ImageLoader(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
        super(layerObserver, undo);
    	this.canvas = canvas;
        this.observers = new ArrayList<ImageObserver>();
        addOpenImageMenuListener();
    }

    protected void initTool(UndoTool undo) {
        setAsChangeMaker(undo);
        setAsLayerChanger();
    }

    // Used to attach an event handler to the Menu Button
    private void addOpenImageMenuListener() {
    	openImageMenu.addMenuListener(new MenuListener() {
            public void menuCanceled(MenuEvent e) {}
            public void menuDeselected(MenuEvent e) {}

            public void menuSelected(MenuEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int fileResponse = fileChooser.showOpenDialog(null);
                if (fileResponse != JFileChooser.APPROVE_OPTION) return;

				lastLoadedImg = new ImageLayer(loadImage(fileChooser.getSelectedFile().getAbsolutePath(), true), new Point(0, 0));                
                LayersHandler.getLayersHandler(canvas).addLayer(lastLoadedImg);
                LayersHandler.getLayersHandler(canvas).updateCanvas();
    
                recordChange();
                updateLayerObserver();
			}
        });
    }

    // loadImage takes the filePath and loads the image from this filePath and returns the loaded image
    public BufferedImage loadImage(String filePath, boolean scaleImg) {
    	try {
    		File imgFile = new File(filePath);
            BufferedImage img = ImageIO.read(imgFile);
            if (scaleImg) img = scaleImage(img);
            
            return img;
        } catch (Exception e) {
            return null; // if image couldn't be loaded from the user computer we return null
        }
    }

    // scaleImage() is used to change the width & height of the loaded image if the loaded image size is larger than the canvas size
    private BufferedImage scaleImage(BufferedImage img) {
    	double factor1 = 1;
        if (img.getWidth() > canvas.getMainLayer().getWidth())
            factor1 = (double) img.getWidth() / (double) canvas.getMainLayer().getWidth();

        double factor2 = 1;
        if (img.getHeight() > canvas.getMainLayer().getHeight())
            factor2 = (double) img.getHeight() / (double) canvas.getMainLayer().getHeight();

        double aspectRatio = Math.max(factor1, factor2);
        int newWidth = (int) Math.floor((double) img.getWidth() / aspectRatio);
        int newHeight = (int) Math.floor((double) img.getHeight() / aspectRatio);
        Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        img = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(scaledImg, 0, 0, null);
    
        return img;
    }

    public JMenu getMenu() {
    	return openImageMenu;
    }
}