
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.ObjectStreamConstants;

import javax.swing.*;
import java.util.*;

public class Airbrush implements Observable, Observer
{
    //TODO put random pen Points within the givin radius of the pen size. 
    //TODO set random pen Points to e.g. size/3

    private OurCanvas canvas;
    Random rand;
    JButton airbrushbtn;
    Brush pen;
    int penSize=1;
    Color currentCol;
    private ArrayList<Observer> observers = new ArrayList<Observer>();
    private ArrayList<Observer> clickObservers = new ArrayList<Observer>();
    private Boolean buttonSelected = false;
    private DrawLineGraphics lineGraphic;
    
   
    public Airbrush(OurCanvas canvas)
    {
        rand = new Random();
        this.canvas = canvas;
        airbrushbtn = new JButton("Air brush");
        pen = new Pen();
        lineGraphic = new DrawLineGraphics(pen.getThickness(), pen.getCol());
        canvasListener();
        airbrushBtnListener();
    }

    private void canvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			
                if (buttonSelected)
                    AddPoints(e.getX(),e.getY());
                
			}
		});
       

    }

    public JButton getairbrushbtn()
    {
        return airbrushbtn;
    }

    public boolean isActive() {
		
		return buttonSelected;
	}

    public void AddPoints(double pX,double pY)
    {
       
        double x = pX;
        double y = pY;
        int xMax = (int)Math.floor(x + penSize/2);
        int xMin = (int)Math.ceil(x - penSize/2);

    
        
        int xPoint = (rand.nextInt((xMax - xMin) + 1) + xMin);
        
        double yAbsolute = Math.floor(penSize/2);
        int yMin = (int)(y - yAbsolute);
        
        int yMax = (int)(y + yAbsolute);
      
        int yPoint =  (rand.nextInt((int)(yMax - yMin) + 1) + yMin);
         
        Point point = new Point(xPoint,yPoint);
        
        drawPointBrush(pen,point);
        
        
    }

    private void drawPointBrush(Brush brush, Point e) {
         
		brush.setPos(e);
        int innerPenSize=penSize/5;
		Point dragPoint = new Point(1,1);
        dragPoint.setLocation(e.getX()+1,e.getY()+1);
        
		
		brush.setThickness(penSize);
		brush.setColor(currentCol);
		brush.setColor(canvas.getCanvasColor());

		lineGraphic.setPoints(brush.getPos(), dragPoint);
        //System.out.println(penSize); 
		lineGraphic.setGraphics(canvas.getCanvasGraphics());
		lineGraphic.setColor(currentCol);
		lineGraphic.setStrokeSize(innerPenSize);
	
		canvas.updateCanvas(lineGraphic);
        
	}

    private void airbrushBtnListener() {
		airbrushbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
         		notifyObservers();
                buttonSelected = true;
                SelectButton.selectBtn(airbrushbtn);
            }
        });
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

    public void deSelect() {
       
        buttonSelected = false;
        SelectButton.deSelectBtn(airbrushbtn);
    }

    
}