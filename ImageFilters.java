import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;

class ImageFilters extends JFrame implements ActionListener {
    protected OurCanvas canvas;
	private LayersHandler layersHandler;
    private MyFilter blurFilter = new BlurFilter();
    private MyFilter greyscaleFilter = new GreyscaleFilter();
    private MyFilter invertFilter = new InvertFilter();
    private MyFilter sepiaFilter = new SepiaFilter();
    private MyFilter sharpenFilter = new SharpenFilter();
    private MyFilter redFilter = new RedFilter();
    private MyFilter greenFilter = new GreenFilter();
    private MyFilter blueFilter = new BlueFilter();

    private LayerData selectedLayer = null;

    /**
     * Constructor used to add an image filter menu.
     * @param layerObserver
     * @param canvas
     * @param undo
     */
    public ImageFilters(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo) {
        this.canvas = canvas;
        this.layersHandler = layersHandler.getLayersHandler(canvas);

        // Creating the drop down menu for the filters 
        JPopupMenu popMenu = new JPopupMenu();
        JMenuItem filter1 = new JMenuItem("Blur Filter");
        filter1.addActionListener(this);
        filter1.setActionCommand("blur");
        popMenu.add(filter1);
        popMenu.addSeparator();

        JMenuItem filter2 = new JMenuItem("Greyscale Filter");
        filter2.addActionListener(this);
        filter2.setActionCommand("greyscale");
        popMenu.add(filter2);
        popMenu.addSeparator();

        JMenuItem filter3 = new JMenuItem("Invert Filter");
        filter3.addActionListener(this);
        filter3.setActionCommand("invert");
        popMenu.add(filter3);
        popMenu.addSeparator();
        
        JMenuItem filter4 = new JMenuItem("Sepia Filter");
        filter4.addActionListener(this);
        filter4.setActionCommand("sepia");
        popMenu.add(filter4);
        popMenu.addSeparator();

        JMenuItem filter5 = new JMenuItem("Sharpen Filter");
        filter5.addActionListener(this);
        filter5.setActionCommand("sharpen");
        popMenu.add(filter5);
        popMenu.addSeparator();

        JMenuItem filter6 = new JMenuItem("Red Filter");
        filter6.addActionListener(this);
        filter6.setActionCommand("red");
        popMenu.add(filter6);
        popMenu.addSeparator();

        JMenuItem filter7 = new JMenuItem("Green Filter");
        filter7.addActionListener(this);
        filter7.setActionCommand("green");
        popMenu.add(filter7);
        popMenu.addSeparator();

        JMenuItem filter8 = new JMenuItem("Blue Filter");
        filter8.addActionListener(this);
        filter8.setActionCommand("blue");
        popMenu.add(filter8);

        // Drop down menu only appears when there's a right click on an image
        canvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    selectedLayer = layersHandler.selectLayer(new Point(e.getX(), e.getY()));

                    if (selectedLayer == null || !(selectedLayer instanceof ImageLayer)) {
                        return; // this means that there are no layers at this coordinate
                    }
                    popMenu.show(canvas, e.getX(), e.getY());
                }
            }
        });
    }
    /**
     * Filters an imported image based on selection
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        String filterChosen = e.getActionCommand();
        ImageLayer img = (ImageLayer) selectedLayer; 
        switch (filterChosen) {
        case "blur":
            if (img == null) return;
            img.setImage(blurFilter.processImage(img.getImage())); // blurs the image
            layersHandler.updateCanvas();
            break;
        case "greyscale":
            if (img == null) return;
            img.setImage(greyscaleFilter.processImage(img.getImage())); // makes the image greyscale
            layersHandler.updateCanvas();
            break;
        case "invert":
            if (img == null) return;
            img.setImage(invertFilter.processImage(img.getImage()));  // inverts the image
            layersHandler.updateCanvas();
            break;
        case "sepia":
            if (img == null) return;
            img.setImage(sepiaFilter.processImage(img.getImage()));  // inverts the image
            layersHandler.updateCanvas();
            break;
        case "sharpen":
            if (img == null) return;
            img.setImage(sharpenFilter.processImage(img.getImage())); // sharpens the image
            layersHandler.updateCanvas();
            break;
        case "red":
            if (img == null) return;
            img.setImage(redFilter.processImage(img.getImage())); // sharpens the image
            layersHandler.updateCanvas();
            break;
        case "green":
            if (img == null) return;
            img.setImage(greenFilter.processImage(img.getImage())); // sharpens the image
            layersHandler.updateCanvas();
            break;
        case "blue":
            if (img == null) return;
            img.setImage(blueFilter.processImage(img.getImage())); // sharpens the image
            layersHandler.updateCanvas();
            break;
        }

        img.updateSelectionLayer();
    }

    interface MyFilter {
        public abstract BufferedImage processImage(BufferedImage image);
    }

    class BlurFilter implements MyFilter {
        public BufferedImage processImage(BufferedImage image) {
            Kernel kernel = new Kernel(3, 3, new float[] { 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f });
            BufferedImageOp blurFilter = new ConvolveOp(kernel);
            return blurFilter.filter(image, null);
        }
    }

    class GreyscaleFilter implements MyFilter {
        public BufferedImage processImage(BufferedImage image) {
            int width = image.getWidth(); // get image width
            int height = image.getHeight(); // get image height

            // loops through each pixel in an image
            for(int y = 0; y < height; y++){ 
                for(int x = 0; x < width; x++){
                    int pixel = image.getRGB(x,y); // getting the value of the pixel

                    // extracting the ARBG values of each pixel
                    int alpha = (pixel >> 24) &0xff;
                    int red = (pixel >> 16) &0xff;
                    int green = (pixel >> 8) &0xff;
                    int blue = (pixel & 0xff);
            
                    int average = ((red + green + blue) / 3); //calculating the average
                    pixel = ((alpha << 24) | (average << 16) | (average << 8) | average); //replace RGB value with the average
            
                    image.setRGB(x, y, pixel); // set new pixel value
                }
            }
            return image;
        }
    }

    class InvertFilter implements MyFilter { // convert colour pixel into negative subtract RGB value from 255 
        public BufferedImage processImage(BufferedImage image) {
            int width = image.getWidth(); // get image width
            int height = image.getHeight(); // get image height

            // loops through each pixel in an image
            for(int y = 0; y < height; y++){ 
                for(int x = 0; x < width; x++){
                    int pixel = image.getRGB(x,y); // getting the value of the pixel

                    // extracts the ARBG values of each pixel
                    int alpha = (pixel >> 24) & 0xff;
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = (pixel & 0xff);

                    // subtract RGB value from 255
                    red = 255 - red;
                    green = 255 - green;
                    blue = 255 - blue;

                    pixel = (alpha << 24) | (red << 16) | (green << 8) | blue; 
                    image.setRGB(x, y, pixel); // set new pixel value
                }
            }
            return image;
        }
    }

    class SepiaFilter implements MyFilter {
        public BufferedImage processImage(BufferedImage image) {
            int width = image.getWidth(); // get image width
            int height = image.getHeight(); // get image height

            // loops through each pixel in an image
            for(int y = 0; y < height; y++){ 
                for(int x = 0; x < width; x++){
                    int pixel = image.getRGB(x,y); // getting the value of the pixel

                    // extracting the ARBG values of each pixel
                    int alpha = (pixel >> 24) & 0xff;
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = (pixel & 0xff);
                    
                    // calculating the RGB values 
                    int tRed = (int) ((0.393 * red) + (0.769 * green) + (0.189 * blue));
                    int tGreen = (int) ((0.349 * red) + (0.686 * green) + (0.168 * blue));
                    int tBlue = (int) ((0.272 * red) + (0.534 * green) + (0.131 * blue));

                    // comparing the values to the condition for sepia 
                    if (tRed > 255) {
                        red = 255;
                    } else {
                        red = tRed;
                    }
                    if (tGreen > 255) {
                        green = 255;
                    } else {
                        green = tGreen;
                    }
                    if (tBlue > 255) {
                        blue = 255;
                    } else {
                        blue = tBlue;
                    }

                    pixel = ((alpha << 24) | (red << 16) | (green << 8) | blue); 
                    image.setRGB(x, y, pixel); // set new pixel value
                }
            }
            return image;
        }
    }

    class SharpenFilter implements MyFilter {
        public BufferedImage processImage(BufferedImage image) {
            Kernel kernel = new Kernel (3,3, new float[]{ // 3 by 3 kernel that sharpens the selected image
                -1, -1, -1,
                -1, 9, -1,
                -1, -1, -1}); 
            BufferedImageOp sharpenFilter = new ConvolveOp(kernel);
            return sharpenFilter.filter(image, null);
        }
    }

    class RedFilter implements MyFilter { // For a red filter you need to set the green and blue values to 0
        public BufferedImage processImage(BufferedImage image) {
            int width = image.getWidth(); // get image width
            int height = image.getHeight(); // get image height

            // loops through each pixel in an image
            for(int y = 0; y < height; y++){ 
                for(int x = 0; x < width; x++){
                    int pixel = image.getRGB(x,y); // getting the value of the pixel

                    // extracting the AR values of each pixel
                    int alpha = (pixel >> 24) & 0xff;
                    int red = (pixel >> 16) & 0xff;
            
                    pixel = ((alpha << 24) | (red << 16) | (0 << 8) | 0); //set GB values to 0
                    image.setRGB(x, y, pixel); // set new pixel value
                }
            }
            return image;
        }
    }

    class GreenFilter implements MyFilter { // For a green filter you need to set the red and blue values to 0
        public BufferedImage processImage(BufferedImage image) {
            int width = image.getWidth(); // get image width
            int height = image.getHeight(); // get image height

            // loops through each pixel in an image
            for(int y = 0; y < height; y++){ 
                for(int x = 0; x < width; x++){
                    int pixel = image.getRGB(x,y); // getting the value of the pixel

                    // extracting the AG values of each pixel
                    int alpha = (pixel >> 24) & 0xff;
                    int green = (pixel >> 8) & 0xff;
            
                    pixel = ((alpha << 24) | (0 << 16) | (green << 8) | 0); //set RB values to 0
                    image.setRGB(x, y, pixel); // set new pixel value
                }
            }
            return image;
        }
    }

    class BlueFilter implements MyFilter { // For a blue filter you need to set the red and green values to 0
        public BufferedImage processImage(BufferedImage image) {
            int width = image.getWidth(); // get image width
            int height = image.getHeight(); // get image height

            // loops through each pixel in an image
            for(int y = 0; y < height; y++){ 
                for(int x = 0; x < width; x++){
                    int pixel = image.getRGB(x,y); // getting the value of the pixel

                    // extracting the AB values of each pixel
                    int alpha = (pixel >> 24) & 0xff;
                    int blue = (pixel & 0xff);
            
                    pixel = ((alpha << 24) | (0 << 16) | (0 << 8) | blue); //set RG values to 0
                    image.setRGB(x, y, pixel); // set new pixel value
                }
            }
            return image;
        }
    }
}