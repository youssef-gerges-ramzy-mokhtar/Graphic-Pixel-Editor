import javax.swing.*;
import java.awt.*;

class ToolsPanel extends JPanel implements Observer {
	private PenGui penGui;
    private EyeDropper eyeDropper;
    private OurCanvas currentCanvas;

	public ToolsPanel(OurCanvas currentCanvas) {
        penGui = new PenGui(currentCanvas);
        eyeDropper = new EyeDropper(currentCanvas);
       
        penGui.addObserver(this);
        eyeDropper.addObserver(this);

        setBackground(new Color(63, 72, 204));      //set panel colour, store this color in the constants class
        setBounds(0, 100, 200, getHeight()-100);      //set panel area
        setLayout(new GridLayout(8,1)); //applies panel layout to panel
        
        JButton fill = new JButton("Fill");
        JButton airbrush = new JButton("Airbrush");
        JButton blur = new JButton("Blue");
        add(penGui.getPenBtn());
        add(penGui.getEraserBtn());
        add(fill);
        add(eyeDropper.getEyeDropperBtn());
        add(airbrush);
        add(blur);

	}

	public PenGui getPenGui() {
		return penGui;
	}

	public EyeDropper getEyeDropper() {
		return eyeDropper;
	}

	// Observer Pattern //
	public void update(int val) {}
	public void update2(Color col) {}
	public void update3() {
		if (penGui.isActive()) penGui.deSelect();
		if (eyeDropper.isActive()) eyeDropper.deSelect();
	}
}