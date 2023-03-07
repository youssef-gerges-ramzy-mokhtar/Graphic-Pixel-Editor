import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Airbrush extends ClickableTool implements Observable, Observer
{
    private OurCanvas canvas;
    private Random rand;
    private Clickable airBrushBtn;
    private Brush pen;
    private int penSize=20;
    private Color currentCol;
    private ArrayList<Observer> observers = new ArrayList<Observer>();
    private ArrayList<Observer> clickObservers = new ArrayList<Observer>();
    private Boolean buttonSelected = false;
    private LineGraphics lineGraphic;
    private LayersHandler layersHandler;
    
    /**
     * Constructor for airbrush.
     * Allows airbrush to be used.
     * @param layerObserver
     *  layerObserver is an object that observers changes that happens to the layers structure
     * @param canvas
     * Current canvas which holds all layers
     * @param undo
     * the tool which manages the undo and redo of the application
     */
    public Airbrush(LayerObserver layerObserver, OurCanvas canvas, UndoTool undo)
    {
        super(layerObserver, undo);
        
        rand = new Random();
        this.canvas = canvas;
        pen = new Pen();
        this.layersHandler = LayersHandler.getLayersHandler(canvas);
        lineGraphic = new LineGraphics(pen.getThickness(), pen.getCol());
        canvasListener();
    }
    /**
     * Adds key binding
     * Adds undo tool.
     * @param undo
     * The tool which manages the undo and redo of the application
     */
    protected void initTool(UndoTool undo) {
        this.airBrushBtn = new Clickable("Air Brush");
        airBrushBtn.addKeyBinding('a');

        addToolBtn(airBrushBtn);
        setAsChangeMaker(undo);
        setAsShapeRasterizer();
        setAsLayerChanger();
    }

    private void canvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
                if (airBrushBtn.isActive())
                    AddPoints(e.getX(),e.getY());
			}

            public void mouseReleased(MouseEvent e) {
                if (!airBrushBtn.isActive()) return;
                recordChange();
                updateLayerObserver();
            }
		});

		canvas.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (!airBrushBtn.isActive()) return;
				AddPoints(e.getX(), e.getY());
			}
		});
    }

    /**
     * @return 
     * button active
     */
    public boolean isActive() {
		return buttonSelected;
	}

    //Used to calculate a list of points which will be painted on
    /**
     * Randomly selects points around x,y within pensize range
     * @param x
     * X coordinate of where to generate points
     * @param y
     * Y coordinate of where to generate points
     */
    public void AddPoints(double x,double y)
    {
        Point[] points = new Point[31];
        int xPoint;
        int yPoint;
        int xMax = (int)Math.floor(x + penSize/2);
        int xMin = (int)Math.ceil(x - penSize/2);
        double yAbsolute = Math.floor(penSize/2);
        int yMin = (int)(y - yAbsolute);
        int yMax = (int)(y + yAbsolute);

        for(int i=0;i<20;i++)
        {
            xPoint = (rand.nextInt((xMax - xMin) + 1) + xMin);
            yPoint =  (rand.nextInt((int)(yMax - yMin) + 1) + yMin);
            points[i] = new Point(xPoint,yPoint);
        }
         drawPointBrush(pen,points);
    }

    /**
     *  Physically points the random points generated from {@link #AddPoints() addPoints} into the screen.
     * @param brush
     * @param points
     */
    //Physically paint selected points to screen
    private void drawPointBrush(Brush brush, Point[] points) {
        LayerData currentLayer = layersHandler.getSelectedLayer();
        if (currentLayer instanceof ShapeLayer) currentLayer = rasterizeLayer(currentLayer, layersHandler);
        if (currentLayer == null) return;

        for(Point point: points)
        {
            try{
                brush.setPos(point);
                int innerPenSize=penSize/10;
                Point dragPoint = new Point(1,1);
                int x = (int) currentLayer.getX((int) point.getX());
                int y = (int) currentLayer.getY((int) point.getY());
                brush.setPos(x,y);
                dragPoint.setLocation(x+1,y+1);
                
                brush.setThickness(innerPenSize);
                brush.setColor(currentCol);
                brush.setColor(canvas.getCanvasColor());

                lineGraphic.setPoints(point, dragPoint);
		        lineGraphic.setColor(currentCol);
		        lineGraphic.setStrokeSize(brush.getThickness());
            
                currentLayer.updateGraphics(lineGraphic);
                layersHandler.updateCanvas();
            }
            catch(Exception e){}
        }
	}

    public void addObserver(Observer observer) {
		observers.add(observer);
	}

	public void removeObserver(Observer observer) {
		clickObservers.remove(observer);
	}

    public void update(int thickness) {    
		penSize = thickness +20;
    }

	public void update2(Color col) {
	    currentCol = col;
	}

    public void update3() {};

	public void notifyObservers() {  
        for (Observer observer : observers)
            observer.update3();
    }

    public ArrayList<Clickable> getClickable() {
        ArrayList<Clickable> airBrushToolBtn = new ArrayList<Clickable>();
        airBrushToolBtn.add(airBrushBtn);
        return airBrushToolBtn;
    }
}