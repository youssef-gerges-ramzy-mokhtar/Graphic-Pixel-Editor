import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Airbrush implements Observable, Observer
{
    private OurCanvas canvas;
    private Random rand;
    private Clickable airBrushBtn;
    private Brush pen;
    private int penSize=1;
    private Color currentCol;
    private boolean mouseDown = false;
    private ArrayList<Observer> observers = new ArrayList<Observer>();
    private ArrayList<Observer> clickObservers = new ArrayList<Observer>();
    private Boolean buttonSelected = false;
    private LineGraphics lineGraphic;
    private LayersHandler layersHandler;
    
    public Airbrush(OurCanvas canvas)
    {
        rand = new Random();
        this.canvas = canvas;
        this.airBrushBtn = new Clickable("Air Brush");
        pen = new Pen();
        this.layersHandler = LayersHandler.getLayersHandler(canvas);
        lineGraphic = new LineGraphics(pen.getThickness(), pen.getCol());
        canvasListener();
    }

    private void canvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
                if (airBrushBtn.isActive())
                    AddPoints(e.getX(),e.getY());
			}
		});

		canvas.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (!airBrushBtn.isActive()) return;
				AddPoints(e.getX(), e.getY());
			}
		});
    }

    public boolean isActive() {
		return buttonSelected;
	}

    //Used to calculate a list of points which will be painted on
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

    //Physically paint selected points to screen
    private void drawPointBrush(Brush brush, Point[] points) {
        LayerData currentLayer = layersHandler.getSelectedLayer();
        for(Point point: points)
        {
            try{
                brush.setPos(point);
                int innerPenSize=penSize/10;
                Point dragPoint = new Point(1,1);
                int x = (int)point.getX();
                int y = (int)point.getY();
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
		penSize = thickness;
    }

	public void update2(Color col) {
	    currentCol = col;
	}

    public void update3() {};

	public void notifyObservers() {  
        for (Observer observer : observers)
            observer.update3();
    }

    public Clickable getClickable() {
        return airBrushBtn;
    }
}