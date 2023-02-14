
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.geom.Ellipse2D;

public class BlurTool extends ClickableTool implements Observer{
    private LayersHandler layerHandler;
    private Clickable blurBtn;
    private OurCanvas canvas;
    private int pensize=1;
    
    public BlurTool(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo)
    {
    	super(layerObserver, undo);

        this.layerHandler = LayersHandler.getLayersHandler(canvas);
        this.canvas = canvas;

        canvasListener();
    }

    protected void initTool(UndoTool undo) {
		blurBtn = new Clickable("Blur");
		blurBtn.addKeyBinding('b');
		addToolBtn(blurBtn);

		setAsChangeMaker(undo);
		setAsShapeRasterizer();
		setAsLayerChanger();
	}

    private void canvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
                if (blurBtn.isActive())
                System.out.println("this is currently working");
			}

			public void mouseReleased(MouseEvent e) {
				if (!blurBtn.isActive()) return;
				recordChange();
			}
		});

		canvas.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (!blurBtn.isActive()) return;
                try {
                    blur(e.getX(),e.getY());
                    updateLayerObserver();
                  }
                  catch(IOException | InterruptedException error) {
                    System.out.println("Exception caught");
                  }
			}
		});
    }

   





	// Blurs the small segment of the screen and applies it to the layer
	private void blur(double xCoor, double yCoor) 
        throws IOException, InterruptedException
	{
       
       
      
		

		// converts the current layer into a buffered image
        LayerData currentLayer = layerHandler.getSelectedLayer();
		BufferedImage bigImage = currentLayer.getImage();
    
		//Takes a smaller part of the layer around the pen point.
        BufferedImage input = bigImage.getSubimage(currentLayer.getX((int) xCoor), currentLayer.getY((int) yCoor), pensize, pensize);
        

		
		
		//Block blurs the BufferedImage
   		Kernel kernel = new Kernel(3, 3, new float[] { 1f / 9f, 1f / 9f, 1f / 9f,
        1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f });
   		BufferedImageOp op = new ConvolveOp(kernel);
		input = op.filter(input, null);

		//Block makes the bufferedImage a circle
		int width = input.getWidth();
		BufferedImage circleBuffer = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = circleBuffer.createGraphics();
		g2.setClip(new Ellipse2D.Float(0, 0, width, width));
		g2.drawImage(input, 0, 0, width, width, null);
		

		//Use this in future to save to files.____________________________________________________________________________________________________________
		//ImageIO.write(circleBuffer, "JPEG", new File("/home/silsby/h-drive/scc210/coursework/scc210-2223-grp-67/scc210-2223-grp-67/BlurredImage.jpeg"));
		
		
      
        
        //Stamps the smaller blurred image onto the big image
        bigImage.getGraphics().drawImage(circleBuffer, currentLayer.getX((int) xCoor), currentLayer.getY((int) yCoor), null);
		

		currentLayer.setImage(bigImage);
        layerHandler.updateCanvas();
        currentLayer.updateSelectionLayer();

        
		
		

       
	}

    

    public Clickable getClickable() {
        return blurBtn;
    }


    public void update(int thickness) {    
       
		pensize = thickness;
    }

	public void update2(Color col) {
	    
	}

    public void update3() {
       
       
    };
}


