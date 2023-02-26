import java.awt.*;
import java.util.*;

// TextGraphics is used to store properties of a Text and used to Draw Text using a Layer's Graphics2D Object
public class TextGraphics extends LayerGraphics {
    ArrayList<ArrayList<String>> sentences;
	int fontSz;
	Color fontCol;

	public TextGraphics(Point position, ArrayList<ArrayList<String>> sentences) {
		this(position, sentences, 50, Color.black);
	}

	public TextGraphics(Point position, ArrayList<ArrayList<String>> sentences, int fontSz, Color fontCol) {
		super(position);
		this.sentences = sentences;
		this.fontSz = fontSz;
		this.fontCol = fontCol;
	}

	public void setFontSize(int sz) {
		this.fontSz = sz;
	}

	public void setFontColor(Color col) {
		this.fontCol = col;
	}

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