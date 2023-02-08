import java.util.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class CanvasHandler{
    private ArrayList<LayerData> canvasLayers1 = new ArrayList<LayerData>();
    private ArrayList<LayerData> canvasLayers2 = new ArrayList<LayerData>();
    private ArrayList<LayerData> canvasLayers3 = new ArrayList<LayerData>();
    private LayersHandler layersHandler;
    private OurCanvas canvas;
    private JButton[] canvasButtons;
    private int currentCanvasNum;

    public CanvasHandler(OurCanvas canvas, JButton[] canvasButtons)
    {
      
        this.canvasButtons = canvasButtons;
        this.canvas = canvas;
        layersHandler = LayersHandler.getLayersHandler(canvas);

        //Puts a blank image into canvasLayers 2 and 3 so that they are not empty
        BufferedImage drawingImg = new BufferedImage(canvas.getMainLayer().getWidth(), canvas.getMainLayer().getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D imgGraphics = drawingImg.createGraphics();
		imgGraphics.setBackground(Color.white);
		imgGraphics.clearRect(0, 0, canvas.getMainLayer().getWidth(), canvas.getMainLayer().getHeight());
        canvasLayers2.add(new ImageLayer(drawingImg));
        canvasLayers3.add(new ImageLayer(drawingImg));


        //Resize the image for the button

        ImageIcon img = changeIconSize(drawingImg);

        

        for(JButton button : canvasButtons)
        {
            button.setIcon(img);
        }

    }

    private ImageIcon changeIconSize(BufferedImage inputImage)
    {
        Image tmp = inputImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
    
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();


        return(new ImageIcon(dimg));
    }

    public void updateCanvas(int canvasNum)
    {
        ImageIcon icon = changeIconSize(layersHandler.getSelectedLayer().getImage());
       

        //Updates the current canvas with the new data
        if(currentCanvasNum == 0) { canvasLayers1 = layersHandler.getLayersCopy();   canvasButtons[0].setIcon(icon);}
        else if (currentCanvasNum == 1) { canvasLayers2 = layersHandler.getLayersCopy();  canvasButtons[1].setIcon(icon);}
        else {canvasLayers3 = layersHandler.getLayersCopy();  canvasButtons[2].setIcon(icon);}

        currentCanvasNum = canvasNum;


        //Updates the layers in the layerHandler of the canvas to be displayed
        if(canvasNum == 0)  layersHandler.setLayers(canvasLayers1);
        else if (canvasNum == 1)  layersHandler.setLayers(canvasLayers2);
        else  layersHandler.setLayers(canvasLayers3);

        
		
		
			


      
        layersHandler.updateCanvas();
       
    }

    //returns the canvas number between 0 and 2.
    public int getCurrentCanvasNumber()
    {
        return(currentCanvasNum);
    }


}