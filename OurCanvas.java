import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;

class OurCanvas extends JPanel {
	private BufferedImage image;
	private Color col;
	private boolean canDrag = false;
	private int newWidth, newHeight;

	public OurCanvas() {
		col = Color.white;
		setBackground(new Color(202, 211, 227));
		// setPreferredSize(new Dimension(2000, 2000));
		repaint();

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (Math.abs(e.getX() - image.getWidth()) <= 5 && Math.abs(e.getY() - image.getHeight()) <= 5) {canDrag = true; return;}

				canDrag = false;
			}

			public void mouseReleased(MouseEvent e) {
				if (canDrag) incCanvasSz();
				canDrag = false;
			}
		});

		addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (canDrag) {
					newWidth = e.getX();
					newHeight = e.getY();
					Cursor cursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR); 
     				setCursor(cursor);
				}
			}

			public void mouseMoved(MouseEvent e) {
				if (image == null) return;

				if (Math.abs(e.getX() - image.getWidth()) <= 5 && Math.abs(e.getY() - image.getHeight()) <= 5) {
					Cursor cursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR); 
     				setCursor(cursor);
					return;
				}

				/*
				// Under Development
				if (Math.abs(e.getX() - image.getWidth()) <= 5) {
					Cursor cursor = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
					setCursor(cursor);
					return;
				}

				if (Math.abs(e.getY() - image.getHeight()) <= 5) {
					Cursor cursor = Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
					setCursor(cursor);
					return;
				}
				*/

				setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));				
			}
		});

	}

	private void incCanvasSz() {
		if (newWidth == image.getWidth() && newHeight == image.getHeight()) return;

		// Still Under Development
		if (newWidth >= getWidth() || newHeight >= getHeight()) {
			setPreferredSize(new Dimension(newWidth + 20, newHeight + 20));
			revalidate();
		}

		// if (newWidth >= getWidth())
		// 	setPreferredSize(new Dimension(newWidth + 20, getHeight()));

		// if (newHeight >= getHeight())
		// 	setPreferredSize(new Dimension())


		BufferedImage tempImg = (BufferedImage) createImage(newWidth, newHeight);
		Graphics2D imgGraphics = tempImg.createGraphics();
		imgGraphics.setBackground(col);
		imgGraphics.clearRect(0, 0, newWidth, newHeight);
    	
    	for (int i = 0; i < Math.min(image.getWidth(), newWidth); i++)
    		for (int j = 0; j < Math.min(image.getHeight(), newHeight); j++)
    			tempImg.setRGB(i, j, image.getRGB(i, j));

    	image = tempImg;
		repaint();

		newWidth = image.getWidth();
		newHeight = image.getHeight();		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image == null) {
			image = (BufferedImage) createImage(800, 600);
			
			Graphics2D imgGraphics = image.createGraphics();
			imgGraphics.setBackground(col);
			// imgGraphics.clearRect(0, 0, getSize().width, getSize().height);
			imgGraphics.clearRect(0, 0, 800, 600);
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

	public void setPixel(int x, int y, int rgb) {
		if (x >= image.getWidth() || y >= image.getHeight()) return;
		// if (image.getRGB(x, y) == rgb) return;

		image.setRGB(x, y, rgb);
		revalidate();
		repaint();
	}

	public Integer getPixel(int x, int y) {
		if (!inRange(x, y)) return null;
		
		return image.getRGB(x, y);
	}

	private boolean inRange(int x, int y) {
		if (x < 0) return false;
		if (y < 0) return false;
		if (x >= image.getWidth()) return false;
		if (y >= image.getHeight()) return false;

		return true;
	}
}