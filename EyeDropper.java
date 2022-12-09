import java.awt.Color;
import java.awt.AWTException;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;  
import java.awt.event.MouseListener;
import java.util.*;

public class EyeDropper implements Observable {
    private ArrayList<Observer> clickObservers = new ArrayList<Observer>();
    private ArrayList<Observer> colorObservers = new ArrayList<Observer>();

    private OurCanvas canvas;
    private JButton eyeDropperbtn;
    private boolean buttonSelected = false;
    private Color col;
    
    public EyeDropper(OurCanvas pCanvas) {
        canvas = pCanvas;
        eyeDropperbtn = new JButton("EyeDropper");

        eyeDropperbtnListener();
        getColour();
    }

    public JButton getEyeDropperBtn() {
        return eyeDropperbtn;
    }

    public void getColour() {
        canvas.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent mouse) {
                if (!buttonSelected) return;

                col = canvas.getCanvasColor(mouse.getX(), mouse.getY());
                System.out.println(col);
                System.out.println(mouse.getX() + " " + mouse.getY());
                notifyColorObservers();
            }
        });
    }

    private void eyeDropperbtnListener() {
        eyeDropperbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent action) {
                notifyObservers();
                buttonSelected = true;
                SelectButton.selectBtn(eyeDropperbtn);
            }
        });
    }

    public boolean isActive() {
        return buttonSelected;
    }

    public void deSelect() {
        buttonSelected = false;
        SelectButton.deSelectBtn(eyeDropperbtn);
    }

    // Observer Pattern //
    public void notifyObservers() {
        for (Observer observer : clickObservers)
            observer.update3();
    }

    public void notifyColorObservers() {
        for (Observer observer : colorObservers)
            observer.update2(col);
    }

    public void addObserver(Observer observer) {
        clickObservers.add(observer);
    }

    public void addColorObserver(Observer observer) {
        colorObservers.add(observer);
    }

    public void removeObserver(Observer observer) {
        clickObservers.remove(observer);
    }
}