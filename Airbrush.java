
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.ObjectStreamConstants;

import javax.swing.*;
import java.util.*;

public class Airbrush implements Observable
{
    //TODO put random pen Points within the givin radius of the pen size. 
    //TODO set random pen Points to e.g. size/3

    private OurCanvas canvas;
    Random rand;
    JButton airbrushbtn;
    Brush pen;
    private ArrayList<Observer> observers = new ArrayList<Observer>();
    private ArrayList<Observer> clickObservers = new ArrayList<Observer>();
    private Boolean buttonSelected = false;

    public Airbrush(OurCanvas canvas)
    {
        rand = new Random();
        this.canvas = canvas;
        airbrushbtn = new JButton("Air brush");
        pen = new Pen();
        
        canvasListener();
        airbrushBtnListener();
    }

    private void canvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("click identified");
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

    public void AddPoints(int x,int y)
    {
       //Problem get pen size
       /* 
        int xMax = math.floor(x + pensize/2);
        int xMin = math.ceil(x - pensize/2);

    
    
        int xPoint = rand.nextInt((xMax - xMin) + 1) + xMin;
        int yAbsolute = math.floor((pensize^2-xPoint^2)^(1/2));
        int yMin = y - yAbsolute;
        int yMax = y = yAbsolute;
        int yPoint = rand.nextInt((yMax - yMin) + 1) + yMin;

        */
    }

    private void drawPointBrush(Brush brush, MouseEvent e) {
        /* 
		brush.setPos(e.getX(), e.getY());
		Point dragPoint = new Point(e.getX() + 1, e.getY() + 1);
		
		brush.setThickness(currentSz);
		if (penSelected) brush.setColor(currentCol);
		if (eraserSelected) brush.setColor(canvas.getCanvasColor());

		lineGraphic.setPoints(brush.getPos(), dragPoint);
		lineGraphic.setGraphics(canvas.getCanvasGraphics());
		lineGraphic.setColor(brush.getCol());
		lineGraphic.setStrokeSize(brush.getThickness());
	
		canvas.updateCanvas(lineGraphic);
        */
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
		int currentSz = thickness;
	}

	public void update2(Color col) {
		Color currentCol = col;
	}


	public void notifyObservers() {
      
        for (Observer observer : observers)
             observer.update3();
    }

    public void deSelect() {
       
        buttonSelected = false;
        SelectButton.deSelectBtn(airbrushbtn);
    }

    
}