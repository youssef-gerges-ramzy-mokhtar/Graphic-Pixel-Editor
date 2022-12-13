import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

// Should that be Singelton
class LayersHandler implements ImageObserver, CanvasObserver {
	// Used for Image Selection
	private ArrayList<LayerData> layers; // Here the Top Layer Image is the Image at the end of the Array List
	private OurCanvas canvas;
	private int verticalOffset;
	private int horizontalOffset;
	private LayerData drawingLayer;
	private LayerData selectedLayer;
	private static LayersHandler layersHandler; // Singelton Design Pattern

	private LayersHandler(OurCanvas canvas) {
		this.layers = new ArrayList<LayerData>();
		this.canvas = canvas;

		initDrawingLayer();
		changeSelectedLayer(0);
	}

	private void initDrawingLayer() {
		BufferedImage drawingImg = new BufferedImage(canvas.getMainLayer().getWidth(), canvas.getMainLayer().getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D imgGraphics = drawingImg.createGraphics();
		imgGraphics.setBackground(Color.white);
		imgGraphics.clearRect(0, 0, canvas.getMainLayer().getWidth(), canvas.getMainLayer().getHeight());
		
		this.drawingLayer = new LayerData(drawingImg);
		layers.add(drawingLayer);	
	}

	public void addLayer(LayerData layer) {
		layers.add(layer);
	}

	public void removeImage(LayerData layer) {
		layers.remove(layer);
	}

	public void moveToTopLayer(int idx) {
		LayerData layerToBeMoved = layers.get(idx);
		for (int i = idx; i < layers.size() - 1; i++)
			layers.set(i, layers.get(i + 1));
	
		layers.set(layers.size() - 1, layerToBeMoved);
	}

	public void moveToTopLayer(LayerData currentLayer) {
		for (int i = 0; i < layers.size(); i++)
			if (layers.get(i) == currentLayer) {moveToTopLayer(i); return;}
	}

	public LayerData selectLayer(Point pos) {
		for (int i = layers.size() - 1; i >= 0; i--) {
			LayerData layerData = layers.get(i);
			if (layerData == drawingLayer) continue;
            
            if (pos.getX() >= layerData.getX() && pos.getX() <= layerData.getEndX())
                if (pos.getY() >= layerData.getY() && pos.getY() <= layerData.getEndY()) {
                    horizontalOffset = (int) pos.getX() - (int) layerData.getX();
                    verticalOffset = (int) pos.getY() - (int) layerData.getY();
                    return layers.get(i);
                }
        }

        return null;
	}

	public void updateCanvas() {
		canvas.clearCanvas();
		for (LayerData layerData: layers)
			canvas.drawLayer(layerData);
	}

	public int getHorizontalOffset() {
		return horizontalOffset;
	}

	public int getVerticalOffset() {
		return verticalOffset;
	}

	public LayerData getDrawingLayer() {
		return drawingLayer;
	}

	private void resizeDrawingArea(int newWidth, int newHeight) {
		if (newWidth < drawingLayer.getImage().getWidth() && newHeight < drawingLayer.getImage().getHeight()) return;

		try {
			BufferedImage tempImg = (BufferedImage) new BufferedImage
			(
				Math.max(newWidth, drawingLayer.getImage().getWidth()),
				Math.max(newHeight, drawingLayer.getImage().getHeight()),
				BufferedImage.TYPE_INT_ARGB
			);
			Graphics2D imgGraphics = tempImg.createGraphics();
			imgGraphics.setBackground(Color.white);
			imgGraphics.clearRect(0, 0, newWidth, newHeight);

			for (int i = 0; i < Math.max(drawingLayer.getImage().getWidth(), newWidth); i++)
	    		for (int j = 0; j < Math.max(drawingLayer.getImage().getHeight(), newHeight); j++) {
	    			if (i < drawingLayer.getImage().getWidth() && j < drawingLayer.getImage().getHeight())
	    				tempImg.setRGB(i, j, drawingLayer.getImage().getRGB(i, j));
	    			else 
	    				tempImg.setRGB(i, j, Color.white.getRGB());
	    		}

	    	drawingLayer.setImage(tempImg);
		} catch (Exception e) {
			System.out.println("Problem Here");
		}
	}

	// Observer Pattern //
	public void update(LayerData layerData) {
		addLayer(layerData);
		updateCanvas();
	}

	public void update() {
		resizeDrawingArea(canvas.getMainLayer().getWidth(), canvas.getMainLayer().getHeight());
		updateCanvas();
	}

	public LayerData getSelectedLayer() {
		return layers.get(0);
		// return selectedLayer;
	}

	public void changeSelectedLayer(int layerPos) {
		if (layerPos < 0) return;
		if (layerPos >= layers.size()) return;

		selectedLayer = layers.get(layerPos);
	}

	// Singelton Pattern //
	public static LayersHandler getLayersHandler(OurCanvas canvas) {
		if (layersHandler == null) layersHandler = new LayersHandler(canvas);

		return layersHandler;
	}
}