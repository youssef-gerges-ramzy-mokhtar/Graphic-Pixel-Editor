import java.awt.image.*;
import java.awt.*;
import java.util.*;
import java.awt.geom.*;

class TextLayer extends LayerData {
	private Color fontCol;
	private int fontSz;
	private String text;
	private int canvasWidth;

	public TextLayer(Point layerLocation, Color fontCol, int fontSz, String text, int canvasWidth) {
		super(1, 1, Color.white, layerLocation);
		this.fontCol = fontCol;
		this.fontSz = fontSz;
		this.text = text;
		this.canvasWidth = canvasWidth;
	
		initLayer();
	}

	private void initLayer() {
		ArrayList<ArrayList<String>> sentences = wordsDivider(canvasWidth - getX());

		int width = canvasWidth - getX();
		int height = (getTextHeight() * sentences.size());
		width = Math.min(width, getTextWidth()+5);
		clear(width, height, fontCol);
		
		TextGraphics textGraphics = new TextGraphics(new Point(0, 0), sentences, fontSz, fontCol);
		textGraphics.setDimensions(width, height);
		updateGraphics(textGraphics);
	}

	public int getTextHeight() {
    	BufferedImage dummyImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g2d = dummyImg.createGraphics();

    	Font font = new Font("Arail", Font.PLAIN, fontSz);
    	g2d.setFont(font);

    	FontMetrics fontMetrics = g2d.getFontMetrics();
    	Rectangle2D r2d = fontMetrics.getStringBounds(text, g2d);

    	return (int) Math.ceil(r2d.getHeight());
    } 

    public int getTextWidth() {
    	BufferedImage dummyImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g2d = dummyImg.createGraphics();

    	Font font = new Font("Arail", Font.PLAIN, fontSz);
    	g2d.setFont(font);

    	FontMetrics fontInfo = g2d.getFontMetrics();
    	return fontInfo.stringWidth(text);
    }

	private ArrayList<ArrayList<String>> wordsDivider(int maxWidth) {
		Font font = new Font(text, Font.PLAIN, fontSz);
		Graphics2D g2d = getLayerGraphics();
		g2d.setFont(font);
		FontMetrics fontInfo = g2d.getFontMetrics();

		String[] words = text.split(" ");
		ArrayList<ArrayList<String>> word_divisoins = new ArrayList<ArrayList<String>>();

		int idx = 0;
		int remaining_width = maxWidth;
		ArrayList<String> sequence = new ArrayList<String>();
		while (idx < words.length) {
			String current_word = words[idx];
			Rectangle2D r2d = fontInfo.getStringBounds(current_word + " ", g2d);
			int word_width = (int) Math.ceil(r2d.getWidth());

			if (word_width < remaining_width) {
				sequence.add(current_word);
				remaining_width -= word_width;
			} else {
				word_divisoins.add(sequence);
				sequence = new ArrayList<String>();
				sequence.add(current_word);
				remaining_width = maxWidth;
				remaining_width -= word_width;
			}

			idx++;
		}

		word_divisoins.add(sequence);
		return word_divisoins;
	}

	public void resize(Point newLayerEndPos) {
		int layerWidth = Math.abs(newLayerEndPos.x - getX());
		int layerHeight = Math.abs(newLayerEndPos.y - getY());
		resize(layerWidth, layerHeight);
		setLocation(validPoint(getCoords(), newLayerEndPos));
	}
	public void resize(int width, int height) {
		if (width <= 0 || height <= 0) return;

		ArrayList<ArrayList<String>> sentences = wordsDivider(width);
		clear(width, height, fontCol);
		
		TextGraphics textGraphics = new TextGraphics(new Point(0, 0), sentences, fontSz, fontCol);
		textGraphics.setDimensions(width, height);
		updateGraphics(textGraphics);
	}

	public LayerData getCopy() {
		TextLayer copy = new TextLayer(new Point(getX(), getY()), fontCol, fontSz, text, canvasWidth);
		resetLayerProperties(copy);
		return copy;
	}
}