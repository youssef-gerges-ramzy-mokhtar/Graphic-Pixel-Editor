import javax.swing.*;
import java.awt.*;

class ToolsPanel extends JPanel implements Observer {
	private PenGui penGui;
    private EyeDropper eyeDropper;
    private OurCanvas currentCanvas;
    private Rectangle rectangle;
    private FillGui fill;
	private Circle circle;

	public ToolsPanel(OurCanvas currentCanvas) {
        penGui = new PenGui(currentCanvas);
        eyeDropper = new EyeDropper(currentCanvas);
       	rectangle = new Rectangle(currentCanvas);
       	fill = new FillGui(currentCanvas);
		circle = new Circle(currentCanvas);

        penGui.addObserver(this);
        eyeDropper.addObserver(this);
        rectangle.addObserver(this);
        fill.addObserver(this);
		circle.addObserver(this);

        setBackground(new Color(63, 72, 204));      //set panel colour, store this color in the constants class
        setBounds(0, 100, 200, getHeight()-100);      //set panel area
        setLayout(new GridLayout(8,1)); //applies panel layout to panel
        
        JButton airbrush = new JButton("Airbrush");
        JButton blur = new JButton("Blur");
        add(penGui.getPenBtn());
        add(penGui.getEraserBtn());
        add(fill.getFillBtn());
        add(eyeDropper.getEyeDropperBtn());
        add(airbrush);
        add(blur);
        add(rectangle.getButton());
		add(circle.getButton());

	}

	public PenGui getPenGui() {
		return penGui;
	}

	public EyeDropper getEyeDropper() {
		return eyeDropper;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public FillGui getFill() {
		return fill;
	}

	public Circle getCircle() {
		return circle;
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
	}
}