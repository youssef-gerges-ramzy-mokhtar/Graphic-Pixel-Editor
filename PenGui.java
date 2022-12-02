import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.*;

class PenGui extends JPanel {
	private Brush pen;
	private Brush eraser;
	

	private Point dragPoint;
	private Display frame;
	private DrawLineGraphics lineGraphic;
	private PenOptionsPanel penOptionsPanel;
	private JButton penBtn;
	private JButton eraserBtn;
	private EyeDropper eyeDropper;

	private boolean released;
	private boolean opened;
	private boolean eraserSelected;
	private boolean penSelected;

	public PenGui(Display frame) {
		this.frame = frame;
		pen = new Pen();
		eraser = new Pen();
		
		

		dragPoint = new Point(0, 0);
		lineGraphic = new DrawLineGraphics(pen.getThickness(), pen.getCol());
		penOptionsPanel = new PenOptionsPanel();
		penBtn = new JButton("Pen");
		eraserBtn = new JButton("Eraser");
		eyeDropper = new EyeDropper(frame.getCanvas());
		released = true;
		opened = false;
		eraserSelected = false;
		penSelected = false;

		canvasListener();
		penBtnListener();
		eraserBtnListener();
	}

	private void canvasListener() {
		OurCanvas currentCanvas = frame.getCanvas();

		currentCanvas.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				// pen.setPos(dragPoint);
				released = true;
			}
		});

		currentCanvas.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (!penSelected && !eraserSelected) return;

				drawBrush(pen, e);
			}
		});
	}

	private void drawBrush(Brush brush, MouseEvent e) {
		OurCanvas currentCanvas = frame.getCanvas();
		
		if (released) {
			brush.setPos(e.getX(), e.getY());
			released = false;
		} else
			brush.setPos(dragPoint.x, dragPoint.y);

		dragPoint.setLocation(e.getX(), e.getY());
		brush.setThickness(penOptionsPanel.getBrushSize());

		if (penSelected) brush.setColor(frame.getColor());
		if (eraserSelected) brush.setColor(currentCanvas.getCanvasColor());

		lineGraphic.setPoints(brush.getPos(), dragPoint);
		lineGraphic.setGraphics(currentCanvas.getCanvasGraphics());
		lineGraphic.setColor(brush.getCol());
		lineGraphic.setStrokeSize(brush.getThickness());
	
		currentCanvas.updateCanvas(lineGraphic);
	}

	private void penBtnListener() {
		penBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
         		penSelected = true;
                eraserSelected = false;
                if (!opened) {System.out.println("Button Clicked"); penOptionsPanel.setVisible(true); opened = true; return;}

                penOptionsPanel.setVisible(false);
                opened = false;
            }
        });
	}

	private void eraserBtnListener() {
		eraserBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eraserSelected = true;
				penSelected = false;
				if (!opened) {System.out.println("Eraser clicked"); penOptionsPanel.setVisible(true); opened = true; return;}

				penOptionsPanel.setVisible(false);
				opened = false;
			}
		});
	}

	public void addComponentsToFrame() {
		Container contentPane = frame.getContentPane();
		contentPane.add(penOptionsPanel, BorderLayout.NORTH);
		penOptionsPanel.setVisible(false);
	}

	public JButton getPenBtn() {
		return penBtn;
	}

	public JButton getEraserBtn() {
		return eraserBtn;
	}
}