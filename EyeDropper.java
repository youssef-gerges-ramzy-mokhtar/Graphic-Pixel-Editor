import java.awt.Color;
import java.awt.AWTException;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;  
import java.awt.event.MouseListener;
import java.util.*;

public class EyeDropper extends Clickable {
    private ArrayList<Observer> colorObservers = new ArrayList<Observer>();
    private boolean buttonSelected = false;
    private Color col;
    
    public EyeDropper(OurCanvas canvas) {
        super(canvas);
        btn.setText("EyeDropper");

        getColour();
    }

    public void getColour() {
        canvas.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent mouse) {
                if (!btnActive) return;

                col = canvas.getCanvasColor(mouse.getX(), mouse.getY());
                notifyColorObservers();
            }
        });
    }

    // Observer Pattern //
    public void notifyColorObservers() {
        for (Observer observer : colorObservers)
            observer.update2(col);
    }

    public void addColorObserver(Observer observer) {
        colorObservers.add(observer);
    }
}