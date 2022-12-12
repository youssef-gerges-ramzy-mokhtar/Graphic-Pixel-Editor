import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

class Rectangle extends Clickable implements Observer {
	private int sideLen = 5;
	private Color col;

	public Rectangle(OurCanvas recCanvas) {
		super(recCanvas);
		col = Color.black;
		btn.setText("Rectangle Shape");

		recCanvas.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (!btnActive) return;

				// RectangleGraphics rectangleGraphics = new RectangleGraphics(new Point(e.getX(), e.getY()), recCanvas.getCanvasGraphics());
				// rectangleGraphics.setColor(col);
				// rectangleGraphics.setLen(sideLen);				
				// recCanvas.updateCanvas(rectangleGraphics);
			}
		});
	}

	// Observer Pattern //
	public void update(int val) {
		sideLen = val;
	}
	
	public void update2(Color col) {
		this.col = col;
	}

	public void update3() {}

	/*
		// We should have a Separate Shape Controller GUI to the User
		We need to have controls to let the user control the following:
			- stroke size of the shape
			- stroke color of the shape
			- fill color of the shape
			- shape dimensions
	*/
}