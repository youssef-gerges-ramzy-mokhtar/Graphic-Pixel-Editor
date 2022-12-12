import javax.swing.*;
import java.awt.*;

class ToolsPanel extends JPanel implements Observer {
    private OurCanvas currentCanvas;
	
	private PenGui penGui;
    private Clickable eyeDropper;
    private Clickable fill;
    private Clickable rectangle;
	private Clickable circle;
    private Clickable selectionTool;

	public ToolsPanel(OurCanvas currentCanvas, LayersHandler layerHandler) {
        penGui = new PenGui(currentCanvas, layerHandler);
        eyeDropper = new EyeDropper(currentCanvas);
       	rectangle = new Rectangle(currentCanvas);
       	fill = new FillGui(currentCanvas, layerHandler);
		circle = new Circle(currentCanvas);
		selectionTool = new SelectionTool(currentCanvas, layerHandler);

        penGui.addObserver(this);
        eyeDropper.addObserver(this);
        rectangle.addObserver(this);
        fill.addObserver(this);
		circle.addObserver(this);
		selectionTool.addObserver(this);
		currentCanvas.addObserver(this);

        setBackground(new Color(63, 72, 204));      //set panel colour, store this color in the constants class
        setBounds(0, 100, 200, getHeight()-100);      //set panel area
        setLayout(new GridLayout(8,1)); //applies panel layout to panel
        
        JButton airbrush = new JButton("Airbrush");
        JButton blur = new JButton("Blur");

        add(selectionTool.getBtn());
        add(penGui.getPenBtn());
        add(penGui.getEraserBtn());
        add(fill.getBtn());
        add(eyeDropper.getBtn());
        add(airbrush);
        add(blur);
        add(rectangle.getBtn());
		add(circle.getBtn());

	}

	public PenGui getPenGui() {
		return penGui;
	}

	public EyeDropper getEyeDropper() {
		return (EyeDropper) eyeDropper;
	}

	public Rectangle getRectangle() {
		return (Rectangle) rectangle;
	}

	public FillGui getFill() {
		return (FillGui) fill;
	}

	public Circle getCircle() {
		return (Circle) circle;
	}

	// Observer Pattern //
	public void update(int val) {}
	public void update2(Color col) {}
	public void update3() {
		if (penGui.isActive()) penGui.deSelect();
		if (eyeDropper.isActive()) eyeDropper.deSelect();
		if (rectangle.isActive()) rectangle.deSelect();
		if (fill.isActive()) fill.deSelect();
		if (circle.isActive()) circle.deSelect();
		if (selectionTool.isActive()) selectionTool.deSelect();
	}
}