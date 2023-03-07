import java.awt.*;
import java.util.*;

// TextGraphics is used to store properties of a Text and used to Draw Text using a Layer's Graphics2D Object
public class TextGraphics extends LayerGraphics {
    ArrayList<ArrayList<String>> sentences;
	int fontSz;
	Color fontCol;

	/**
	 * TextGraphics is used to store the properties of a Text and is used to define how is a Text drawn into the Screen/Canvas
	 * @param position represent the text position on the screen/canvas
	 * @param sentences defines the text structure specifically (the words in a sentence & the number of sentences)
	 */
	public TextGraphics(Point position, ArrayList<ArrayList<String>> sentences) {
		this(position, sentences, 50, Color.black);
	}

	/**
	 * TextGraphics is used to store the properties of a Text and is used to define how is a Text drawn into the Screen/Canvas
	 * @param position represent the text position on the screen/canvas
	 * @param sentences defines the text structure specifically (the words in a sentence & the number of sentences)
	 * @param fontSz the font size of the text
	 * @param fontCol the font color of the text
	 */
	public TextGraphics(Point position, ArrayList<ArrayList<String>> sentences, int fontSz, Color fontCol) {
		super(position);
		this.sentences = sentences;
		this.fontSz = fontSz;
		this.fontCol = fontCol;
	}

	/**
	 * sets the font size
	 * @param sz font size
	 */
	public void setFontSize(int sz) {
		this.fontSz = sz;
	}

	/**
	 * sets the font color
	 * @param col font color
	 */
	public void setFontColor(Color col) {
		this.fontCol = col;
	}

	/**
	 * used to draw the text sentences structure into the Graphics2D object
	 * @param Graphics2D used to render the text into the Graphics2D object
	 */
	public void draw(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		g.fillRect(0, 0, width, height);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

		Font font = new Font("Arial", Font.PLAIN, fontSz);
		g.setFont(font);
		g.setColor(fontCol);
		FontMetrics fontInfo = g.getFontMetrics();

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