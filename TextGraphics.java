import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;

// TextGraphics is used to store properties of a Rectnalge and used to Draw a Rectnalge using a Layer's Graphics2D Object
public class TextGraphics implements SpecificGraphic {
    ArrayList<ArrayList<String>> sentences;
	Point position;
    int width;
    int height;
	int fontSz;
	Color fontCol;

	public TextGraphics(Point position, ArrayList<ArrayList<String>> sentences) {
		this(position, sentences, 50, Color.black);
	}

	public TextGraphics(Point position, ArrayList<ArrayList<String>> sentences, int fontSz, Color fontCol) {
		this.position = position;
		this.sentences = sentences;
		this.fontSz = fontSz;
		this.fontCol = fontCol;
	}

	public void setPoints(Point position) {
		this.position = position;
	}

	public void setFontSize(int sz) {
		this.fontSz = sz;
	}

	public void setFontColor(Color col) {
		this.fontCol = col;
	}

    public void setDimensions(int width, int height) {
    	this.width = width;
    	this.height = height;
    }

	public void draw(Graphics2D g) {
		// g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		// g.fillRect(0, 0, width, height);
		// g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		g.setColor(Color.green);
		g.fillRect(0, 0, width, height);

		Font font = new Font("Arial", Font.PLAIN, fontSz);
		g.setFont(font);
		g.setColor(Color.black);
		FontMetrics fontInfo = g.getFontMetrics();

		System.out.println(fontCol + " " + fontSz);
		// System.out.println(fm + "\n" + " " + fm.getHeight() + " " + fm.getAscent() + " " + fm.stringWidth("Youssef Gerges"));
		// System.out.println(fm.getStringBounds(text, g));
		// System.out.println(width + " " + height);

		int y = fontInfo.getHeight();
		int spacing = 10;
		for (ArrayList<String> sentence: sentences) {
			String line = "";
			for (String word: sentence)
				line += word + " ";

			g.drawString(line, 5, y);
			y += spacing + (fontInfo.getAscent() - fontInfo.getDescent());
		}

		g.dispose();
	}
}