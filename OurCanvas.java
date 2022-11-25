import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;

class OurCanvas extends JPanel {
	private BufferedImage image;

	public OurCanvas() {
		repaint();
		setBackground(Color.white);
	}

	public void paintComponent(Graphics g) {
		if (image == null)
			image = (BufferedImage) createImage(getSize().width, getSize().height);

		g.drawImage(image, 0, 0, null);
	}

	void updateCanvas(SpecificGraphic g) {
		g.draw();
		repaint();
	}

	Graphics getCanvasGraphics() {
		return image.getGraphics();
	}
}