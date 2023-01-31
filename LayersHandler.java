import java.util.*;
import java.awt.*;
import java.awt.image.*;

// LayersHandler is used to handle all the layers on the Canvas
class LayersHandler implements ImageObserver, CanvasObserver {
	private ArrayList<LayerData> layers; // Here the Top Layer is the Layer at the end of the Array List
	private OurCanvas canvas;
	private int verticalOffset;
	private int horizontalOffset;
	private LayerData drawingLayer; // is the Main Layer on Canvas, same idea as the Background Layer in Photoshop
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
		
		this.drawingLayer = new ImageLayer(drawingImg);
		layers.add(drawingLayer);	
	}

	public void addLayer(LayerData layer) {
		layers.add(layer);
	}

	public void removeLayer(LayerData layer) {
		if (layer == null) return;
		layers.remove(layer);
	}

	// move the Layer at idx to the Top Layer
	public void moveToTopLayer(int idx) {
		LayerData layerToBeMoved = layers.get(idx);
		for (int i = idx; i < layers.size() - 1; i++)
			layers.set(i, layers.get(i + 1));
	
		layers.set(layers.size() - 1, layerToBeMoved);
	}

	// moves the given layer to the Top Layer
	public void moveToTopLayer(LayerData currentLayer) {
		for (int i = 0; i < layers.size(); i++)
			if (layers.get(i) == currentLayer) {moveToTopLayer(i); return;}
	}

	// Select Layer takes in a position and based on this position will return the Top Most Layer at this gievn position
	// and there is no layers at this position null will be returned
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

	// updateCanvas() is used to refresh the canvas by redrawing all the layers into the canvas
	public void updateCanvas() {
		canvas.clearCanvas();
		for (LayerData layerData: layers)
			canvas.drawLayer(layerData);
	}

	public void updateCanvasSelected(LayerData selectedLayer) {
		canvas.clearCanvas();
		for (LayerData layerData: layers) {
			canvas.drawLayer(layerData);
			if (layerData == selectedLayer) canvas.drawSelectedLayer(layerData);
		}
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

	// Returnes the Number of layers
	public int getLayersCount() {
		return layers.size();
	}

	// Retunrs the Current Selected Layer
	public LayerData getSelectedLayer() {
		return selectedLayer;
	}

	// Selects a Different layer based on layerPosition
	public void changeSelectedLayer(int layerPos) {
		if (layerPos < 0) return;
		if (layerPos >= layers.size()) return;

		selectedLayer = layers.get(layerPos);
	}

	public ArrayList<LayerData> getLayersCopy() {
		ArrayList<LayerData> layersCopy = new ArrayList<LayerData>();
		for (LayerData layer: layers)
			layersCopy.add(layer.getCopy());
	
		return layersCopy;
	}

	public void setLayers(ArrayList<LayerData> layers) {
		this.layers = layers;
		this.drawingLayer = layers.get(0);
		changeSelectedLayer(0);
	}

	// Observer Pattern //
	// update() is called whenever a new Image is added to the Canvas, to add this image as a seperate layer to the layers
	public void update(LayerData layerData) {
		addLayer(layerData);
		updateCanvas();
	}

	// update() is called whenver the canvas is resized, and is used to resize the drawing area
	public void update() {
		resizeDrawingArea(canvas.getMainLayer().getWidth(), canvas.getMainLayer().getHeight());
		updateCanvas();
	}

	// Singelton Pattern //
	public static LayersHandler getLayersHandler(OurCanvas canvas) {
		if (layersHandler == null) layersHandler = new LayersHandler(canvas);

		return layersHandler;
	}
}