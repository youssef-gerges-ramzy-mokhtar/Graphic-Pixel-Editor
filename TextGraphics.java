import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;

// TextGraphics is used to store properties of a Rectnalge and used to Draw a Rectnalge using a Layer's Graphics2D Object
public class TextGraphics implements SpecificGraphic {
	Point position;
    int width;
    int height;
    int canvasWidth;

    String text;
	int font_sz;
	Color font_col;

	public TextGraphics(Point position, String text, int canvasWidth) {
		this.position = position;
		this.text = text;
		this.canvasWidth = canvasWidth;
		this.font_sz = 50;
		this.font_col = Color.black;
	}

	public void setPoints(Point position) {
		this.position = position;
	}

	public void setFontSize(int sz) {
		this.font_sz = sz;
	}

	public void setFontColor(Color col) {
		this.font_col = col;
	}

    public void setDimensions(int width, int height) {
    	this.width = width;
    	this.height = height;
    }

    public Dimension getDimensions() {
    	BufferedImage dummyImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g2d = dummyImg.createGraphics();

    	Font font = new Font(text, Font.PLAIN, font_sz);
    	g2d.setFont(font);

    	FontMetrics fontMetrics = g2d.getFontMetrics();
    	Rectangle2D r2d = fontMetrics.getStringBounds(text, g2d);

    	return new Dimension((int) Math.ceil(r2d.getWidth()), (int) Math.ceil(r2d.getHeight()));
    } 

	public void draw(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		g.fillRect(0, 0, width, height);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

		Font f = new Font(text, Font.PLAIN, font_sz);
		g.setFont(f);

		FontMetrics fm = g.getFontMetrics();
		// System.out.println(fm + "\n" + " " + fm.getHeight() + " " + fm.getAscent() + " " + fm.stringWidth("Youssef Gerges"));
		// System.out.println(fm.getStringBounds(text, g));
		// System.out.println(width + " " + height);

		g.setColor(font_col);
		text = text + " That is a Split another split another split";
		ArrayList<ArrayList<String>> word_divisoins = algorithm(text, g, fm);
		int y = fm.getHeight();
		for (ArrayList<String> sequence: word_divisoins) {
			System.out.println(sequence);
			String line = "";
			for (String word: sequence)
				line += word + " ";

			g.drawString(line, 5, y);

			y += fm.getAscent() - fm.getDescent();
		}

		g.dispose();
	}

	private ArrayList<ArrayList<String>> algorithm(String str, Graphics2D g2d, FontMetrics fontMetrics) {
		String[] words = str.split(" ");

		ArrayList<ArrayList<String>> word_divisoins = new ArrayList<ArrayList<String>>();

		int idx = 0;
		int remaining_width = canvasWidth;
		ArrayList<String> sequence = new ArrayList<String>();
		while (idx < words.length) {
			String current_word = words[idx];
			Rectangle2D r2d = fontMetrics.getStringBounds(current_word + " ", g2d);
			int word_width = (int) Math.ceil(r2d.getWidth());

			System.out.println(word_width);
			if (word_width < remaining_width) {
				sequence.add(current_word);
				remaining_width -= word_width;
			} else {
				word_divisoins.add(sequence);
				sequence = new ArrayList<String>();
				sequence.add(current_word);
				remaining_width = canvasWidth;
				remaining_width -= word_width;
			}

			idx++;
		}

		word_divisoins.add(sequence);
		return word_divisoins;
	}
}