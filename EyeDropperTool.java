import java.awt.Color;
import java.awt.event.*;
import java.util.*;

// EyeDropperTool is responsible for detecing colors on the canvas
public class EyeDropperTool extends ClickableTool implements Observable {
    private OurCanvas canvas;
    private Clickable eyeDropperBtn;
    private ArrayList<Observer> colorObservers;
    private Color col;

    public EyeDropperTool(OurCanvas canvas) {
        super(null, null);

        this.canvas = canvas;
        this.colorObservers = new ArrayList<Observer>();
        getColour();
    }

    // initTool initialize the properties of the Eye Dropper Tool
    /*
        - A Eye Dropper Tool has shortcut 'i'
    */
    protected void initTool(UndoTool undo) {
        this.eyeDropperBtn = new Clickable("Eye Dropper");
        eyeDropperBtn.addKeyBinding('i');

        addToolBtn(eyeDropperBtn);
        setAsChangeMaker(null);
    }

    // getColour() is used to attach an Event Listener to the cavnas to detect colors at the cursor coordinates
    public void getColour() {
        canvas.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent mouse) {
                if (!eyeDropperBtn.isActive()) return;
                
                col = new Color(canvas.getMainLayer().getPixel(mouse.getX(), mouse.getY()));
                notifyObservers();
            }
        });
    }

    // Observer Pattern //
    // notifyObservers() is used to notify the Color Picker preview button whenver the eye dropper detects a color on the canvas
    public void notifyObservers() {
        for (Observer observer : colorObservers)
            observer.update2(col);
    }

    public void addObserver(Observer observer) {
        colorObservers.add(observer);
    }

    public void removeObserver(Observer observer) {
        colorObservers.remove(observer);
    }
}