import java.awt.Color;
import java.awt.event.*;
import java.util.*;

// EyeDropperTool is responsible for detecing colors on the canvas
public class EyeDropperTool extends ClickableTool implements Observable {
    private OurCanvas canvas;
    private Clickable eyeDropperBtn;
    private ArrayList<Observer> colorObservers;
    private Color col;

    /**
     * EyeDropperTool is responsible for detecing and picking colors on the Screen/Canvas
     * @param canvas is the current canvas that holds and displays all the layers
     */
    public EyeDropperTool(OurCanvas canvas) {
        super(null, null);

        this.canvas = canvas;
        this.colorObservers = new ArrayList<Observer>();
        
        getColour();
    }

    /**
     * initTool initialize the properties of the Eye Dropper Tool
     * @param undo is the tool that manages how the undo and redo works
     */
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
    private void getColour() {
        canvas.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent mouse) {
                if (!eyeDropperBtn.isActive()) return;
                
                col = new Color(canvas.getMainLayer().getPixel(mouse.getX(), mouse.getY()));
                notifyObservers();
            }
        });
    }

    // Observer Pattern //

    /**
     * used to notify the Color Picker preview button whenver the eye dropper detects a color on the canvas
     */
    // notifyObservers() is used to notify the Color Picker preview button whenver the eye dropper detects a color on the canvas
    public void notifyObservers() {
        for (Observer observer : colorObservers)
            observer.update2(col);
    }

    /**
     * used to add an observer observing the colors picked by the EyeDropperTool
     * @param observer an observer observing the color picked
     */
    public void addObserver(Observer observer) {
        colorObservers.add(observer);
    }

    /**
     * used to remove an observer that was observing the colors picked by the EyeDropperTool
     * @param observer an observer observing the color picked
     */
    public void removeObserver(Observer observer) {
        colorObservers.remove(observer);
    }
}