import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;

class OurCanvas extends JPanel {
	private BufferedImage image;
	private Color col;

	public OurCanvas() {
		col = Color.white;
		setBackground(col);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image == null) {
			image = (BufferedImage) createImage(getSize().width, getSize().height);
			
			Graphics2D imgGraphics = image.createGraphics();
			imgGraphics.setBackground(getBackground());
			imgGraphics.clearRect(0, 0, getSize().width, getSize().height);
		}

		g.drawImage(image, 0, 0, null);
	}

	public void updateCanvas(SpecificGraphic g) {
		g.draw();
		repaint();
	}

	public Graphics2D getCanvasGraphics() {
		return (Graphics2D) image.getGraphics();
	}

	public Color getCanvasColor() {
		return col;
	}

	public Color getCanvasColor(int x, int y) {
		return new Color(image.getRGB(x, y));
	}
}