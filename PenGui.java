import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class PenGui extends JPanel {
	private Point firstPoint = new Point(0, 0);
	private Point secondPoint = new Point(0, 0);

	private OurCanvas currentCanvas;
	private boolean released = true;
	private DrawLineGraphics lineGraphic;

	// int count = 1;

	public PenGui(OurCanvas currentCanvas) {
		this.currentCanvas = currentCanvas;

		lineGraphic = new DrawLineGraphics();
		currentCanvas.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				released = true;
				// count = 1;
			}
		});

		currentCanvas.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				// if (count % 3 != 0) {
					// count++;
					// return;
				// }

				if (released) {
					firstPoint.setLocation(e.getX(), e.getY());
					released = false;
				} else {
					firstPoint.x = secondPoint.x;
					firstPoint.y = secondPoint.y;
				}

				System.out.println("HI");
				secondPoint.setLocation(e.getX(), e.getY());
				lineGraphic.setGraphics(currentCanvas.getCanvasGraphics());
				lineGraphic.setPoints(firstPoint, secondPoint);
				currentCanvas.updateCanvas(lineGraphic);
			}
		});
	}
}