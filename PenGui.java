import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class PenGui extends JPanel implements Observer, Observable {
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private Brush pen;
	private Brush eraser;
	
	private Point dragPoint;
	private OurCanvas canvas;
	private DrawLineGraphics lineGraphic;
	private JButton penBtn;
	private JButton eraserBtn;
	private EyeDropper eyeDropper;

	private boolean released;
	private boolean eraserSelected;
	private boolean penSelected;
	private int currentSz = 1;
	private Color currentCol;

	public PenGui(OurCanvas canvas) {
		this.canvas = canvas;
		pen = new Pen();
		eraser = new Pen();

		dragPoint = new Point(0, 0);
		lineGraphic = new DrawLineGraphics(pen.getThickness(), pen.getCol());
		penBtn = new JButton("Pen");
		eraserBtn = new JButton("Eraser");
		eyeDropper = new EyeDropper(canvas);
		released = true;
		eraserSelected = false;
		penSelected = false;

		canvasListener();
		penBtnListener();
		eraserBtnListener();
	}

	private void canvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				released = true;
			}
		});

		canvas.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (!penSelected && !eraserSelected) return;
				drawBrush(pen, e);
			}
		});

		canvas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!penSelected && !eraserSelected) return;
				drawPointBrush(pen, e);
			}
		});
	}

	private void drawBrush(Brush brush, MouseEvent e) {
		if (released) {
			brush.setPos(e.getX(), e.getY());
			released = false;
		} else
			brush.setPos(dragPoint.x, dragPoint.y);

		dragPoint.setLocation(e.getX(), e.getY());
		brush.setThickness(currentSz);

		if (penSelected) brush.setColor(currentCol);
		if (eraserSelected) brush.setColor(canvas.getCanvasColor());

		lineGraphic.setPoints(brush.getPos(), dragPoint);
		lineGraphic.setGraphics(canvas.getCanvasGraphics());
		lineGraphic.setColor(brush.getCol());
		lineGraphic.setStrokeSize(brush.getThickness());
	
		canvas.updateCanvas(lineGraphic);
	}

	private void drawPointBrush(Brush brush, MouseEvent e) {
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
	}

	private void penBtnListener() {
		penBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
         		notifyObservers();
         		penSelected = true;
                eraserSelected = false;
				SelectButton.selectBtn((JButton) e.getSource());
            }
        });
	}

	private void eraserBtnListener() {
		eraserBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
         		notifyObservers();
				eraserSelected = true;
				penSelected = false;
				SelectButton.selectBtn((JButton) e.getSource());
			}
		});
	}

	public JButton getPenBtn() {
		return penBtn;
	}

	public JButton getEraserBtn() {
		return eraserBtn;
	}

	public boolean isActive() {
		if (penSelected || eraserSelected) return true;
		return false;
	}

	public void deSelect() {
		penSelected = false;
		eraserSelected = false;
		SelectButton.deSelectBtn(penBtn);
		SelectButton.deSelectBtn(eraserBtn);
	}

	// Observer Pattern //
	public void update(int thickness) {
		currentSz = thickness;
	}

	public void update2(Color col) {
		currentCol = col;
	}

	public void update3() {};

	public void notifyObservers() {
		for (Observer observer : observers)
			observer.update3();
	}

	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}
}