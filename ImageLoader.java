import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.swing.event.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;

// ImageLoader is used Loads Images from the User's Computer into the Program
class ImageLoader extends ChangeMaker implements ImageObservable {
    private JMenu openImageMenu = new JMenu("Open Image");
    private OurCanvas canvas;
    private ImageLayer lastLoadedImg;
    private ArrayList<ImageObserver> observers;

    public ImageLoader(OurCanvas canvas, UndoTool undo) {
        super(undo);
    	this.canvas = canvas;
        this.observers = new ArrayList<ImageObserver>();
    	
        addOpenImageMenuListener();
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

				loadImage(fileChooser.getSelectedFile().getAbsolutePath());                
                recordChange();
			}
        });
    }

    // loadImage takes the filePath and loads the image and notify the LayersHandler to add the loaded image to a separate layer
    private void loadImage(String filePath) {
    	try {
    		File imgFile = new File(filePath);
            BufferedImage img = ImageIO.read(imgFile);
            img = scaleImage(img);

            lastLoadedImg = new ImageLayer(img);
            notifyObservers();
        } catch (Exception e) {}
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

    // Observer Pattern 
    public void addObserver(ImageObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(ImageObserver observer) {
        observers.remove(observer);
    }

    // notifyObserver() notifies the LayersHandler to add the last loaded image to a separate layer
    public void notifyObservers() {
        for (ImageObserver observer: observers)
            observer.update(lastLoadedImg);
    }
}