import java.util.*;
import java.awt.*;
import java.awt.image.*;

// LayersHandler is used to handle all the layers on the Canvas
public class LayersHandler implements CanvasObserver {
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
		
		this.drawingLayer = new DrawingLayer(drawingImg);
		layers.add(drawingLayer);
	}

	/**
	 * adds a layer to be handled by the layers handler
	 * @param layer the layer that will be added
	 */
	public void addLayer(LayerData layer) {
		layers.add(layer);
	}

	/**
	 * removes a layer from the layers handler
	 * @param layer the layer that will be removed
	 */
	public void removeLayer(LayerData layer) {
		if (layer instanceof DrawingLayer) return;
		if (layer == null) return;
		if (layer == drawingLayer) return;
		layers.remove(layer);
	}

	/**
	 * moves the Layer at the specified index to the Top
	 * @param idx the layer index that needs to be moved to the top
	 */
	// move the Layer at idx to the Top Layer
	public void moveToTopLayer(int idx) {
		LayerData layerToBeMoved = layers.get(idx);
		for (int i = idx; i < layers.size() - 1; i++)
			layers.set(i, layers.get(i + 1));
	
		layers.set(layers.size() - 1, layerToBeMoved);
	}

	/**
	 * moves the layer to the Top
	 * @param currentLayer the layer that needs to be move to the top
	 */
	// moves the given layer to the Top Layer
	public void moveToTopLayer(LayerData currentLayer) {
		for (int i = 0; i < layers.size(); i++)
			if (layers.get(i) == currentLayer) {moveToTopLayer(i); return;}
	}

	/**
	 * moves a layer 1 step to the top
	 * if the layer is already the top most layer the layer will not move
	 * @param layer the layer that needs to be moved 1 step to the top
	 */
	// moveLayerUp is used to move the layer 1 step to the top
	public void moveLayerUp(LayerData layer) {
		int layer_pos = layers.indexOf(layer);
		if (layer_pos == layers.size()-1) return;

		Collections.swap(layers, layer_pos, layer_pos+1);
	}

	/**
	 * moves a layer 1 step down
	 * if the layer is already the bottom most layer the layer will not move
	 * @param layer the layer that needs to be moved 1 step down
	 */
	// moveLayerDown is used to move the layer 1 step to the bottom
	public void moveLayerDown(LayerData layer) {
		int layer_pos = layers.indexOf(layer);
		if (layer_pos == 0) return;

		Collections.swap(layers, layer_pos, layer_pos-1);
	}

	/**
	 * Select Layer takes in a position and based on this position will return the Top Most Layer at this gievn position
	 * null will be returned if there were no layers at this position
	 * @param pos the position in which you want to select a layer from
	 */
	// Select Layer takes in a position and based on this position will return the Top Most Layer at this gievn position
	// and there is no layers at this position null will be returned
	public LayerData selectLayer(Point pos) {
		for (int i = layers.size() - 1; i >= 0; i--) {
			LayerData layerData = layers.get(i);
			if (layerData instanceof DrawingLayer) continue;
			if (layerData.isHidden()) continue;
            
            if (pos.getX() >= layerData.getX() && pos.getX() <= layerData.getEndX())
                if (pos.getY() >= layerData.getY() && pos.getY() <= layerData.getEndY()) {
                    horizontalOffset = (int) pos.getX() - (int) layerData.getX();
                    verticalOffset = (int) pos.getY() - (int) layerData.getY();
                    return layers.get(i);
                }
        }

        return null;
	}

	/**
	 * used to refresh the canvas to update the layers state
	 */
	// updateCanvas() is used to refresh the canvas by redrawing all the layers into the canvas
	public void updateCanvas() {
		canvas.clearCanvas();
		for (LayerData layerData: layers) {
			if (layerData.isHidden()) continue;
			canvas.drawLayer(layerData);
		}
	}

	/**
	 * used to refresh the canvas to update the layers state
	 * also refreshs the canvas to show the selectedLayer as being selected or marked as choosen
	 * simply a selectio border will be displayed on the selected layer
	 * @param selectedLayer the layer that will be rendred/refershed into the canvas as selected or choosen
	 */
	// updateCanvasSelected() is used to refresh the canvas by redrawing all the layers into the canvas and drawing the selectedLayer with a border
	public void updateCanvasSelected(LayerData selectedLayer) {
		canvas.clearCanvas();
		for (LayerData layerData: layers) {
			if (layerData.isHidden()) continue;
			
			if (!(layerData instanceof DrawingLayer) && layerData == selectedLayer) canvas.drawSelectedLayer(layerData);
			else canvas.drawLayer(layerData);
		}
	}

	/**
	 */
	public int getHorizontalOffset() {
		return horizontalOffset;
	}

	/**
	 */
	public int getVerticalOffset() {
		return verticalOffset;
	}

	/**
	 * returns the base/background layer
	 * also called the drawing layer
	 * @return the drawing layer on the Screen/Canvas
	 */
	public LayerData getDrawingLayer() {
		return drawingLayer;
	}

	/**
	 * resizes the drawing area based on the new width and the new height
	 * @param newWidth the new width to resize the drawing area
	 * @param newHeight the new height to resize the drawing area
	 */
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

	/**
	 * returns the number of layers on the Screen/Canvas
	 * @return the number of layers on the Screen/Canvas
	 */
	// Returnes the Number of layers
	public int getLayersCount() {
		return layers.size();
	}

	/**
	 * returns the current selected layer
	 * a selected layer is a layer in which you can perform an operation on (for example drawing into, filling, resizing etc...)
	 * @return the selected layer
	 */
	// Retunrs the Current Selected Layer
	public LayerData getSelectedLayer() {
		if (selectedLayer.isHidden()) return null;
		return selectedLayer;
	}

	/**
	 * Selects a Different layer based on layerPosition
	 * @param layerPos the layer index that should be selected
	 */
	// Selects a Different layer based on layerPosition
	public void changeSelectedLayer(int layerPos) {
		if (layerPos < 0) return;
		if (layerPos >= layers.size()) return;

		selectedLayer = layers.get(layerPos);
	}

	/**
	 * Selects a Different layer
	 * @param layer the layer that will be selected
	 */
	// changes the selectedLayer to the layer that is passed to changeSelectedLayer() function
	public void changeSelectedLayer(LayerData layer) {
		if (!layers.contains(layer)) return;
		selectedLayer = layer;
	}

	/**
	 * returns a collection containing a deep copy of all the layers that exist
	 * @return collection of deeply copied layers
	 */
	// returns a deep copy of all the layers that exist
	public ArrayList<LayerData> getLayersCopy() {
		ArrayList<LayerData> layersCopy = new ArrayList<LayerData>();
		
		for (LayerData layer: layers)
			layersCopy.add(layer.getCopy());
		
		return layersCopy;
	}

	/**
	 * sets the layers to the collection of LayerData that is passed
	 * @param layers a Collection of all layers that the layers handler will handle
	 */
	// setLayers simply sets the layers based on the passed argument
	public void setLayers(ArrayList<LayerData> layers) {
		this.layers = layers;
		this.layers = getLayersCopy();

		for (LayerData layer: this.layers)
			if (layer instanceof DrawingLayer) {this.drawingLayer = layer; break;}

		changeSelectedLayer(0);
		update();
	}

	/**
	 * returns a collection of all the layers that exist in the layers handler
	 * @return collection of LayerData
	 */
	public ArrayList<LayerData> getLayers() {
		return layers;
	}

	/**
	 * replaces a layer with a new layer
	 * @param prevLayer that layer that will be replaced
	 * @param newLayer the layer that will replace the previous layer
	 */
	// replaceLayer simply replaces the Previous Layer with the New Layer
	public void replaceLayer(LayerData prevLayer, LayerData newLayer) {
		int prevLayerIdx = layers.indexOf(prevLayer);
		if (prevLayerIdx == -1) return;

		layers.set(prevLayerIdx, newLayer);
		if (prevLayer == selectedLayer) selectedLayer = newLayer;
	}

	/**
	 * merge all merges all the layers that exist
	 * also mergeAll replaces all the layers that used to exist with the newly merged all Layer
	 * @return a single layer containing all merged layers
	 */
	public LayerData mergeAll() {
		LayerData mergedLayer = new ImageLayer(drawingLayer.getWidth(), drawingLayer.getHeight(), Color.white);

		for (LayerData layer: layers)
			mergedLayer.mergeLayer(layer);

		layers.clear();
		drawingLayer.clear(Color.white);
		mergedLayer.updateSelectionLayer();

		layers.add(drawingLayer);
		layers.add(mergedLayer);

		changeSelectedLayer(0);
		return mergedLayer;
	}

	/**
	 * used to resize all layers based on a factor
	 * @param factor the resizing factor
	 */
	// zoomAllLayers is used to resize all layers based on the factor
	public void zoomAllLayers(double factor) {
		for (LayerData layer: layers)
			layer.zoom(factor);
		
		updateCanvas();
	}

	/**
	 * used to remove all the layers on the canvas and clear the drawingLayer to white color
	 */
	// clear() is used to remove all the layers on the canvas and clear the drawingLayer to white color
	public void clear() {
		layers.clear();
		drawingLayer.clear(Color.white);
		layers.add(drawingLayer);
	}

	/**
	 * sets the drawing layer
	 * @param drawingLayer the new drawing layer that will be set
	 */
	public void setDrawingLayer(DrawingLayer drawingLayer) {
		this.drawingLayer = drawingLayer;
		this.layers.set(0, drawingLayer);
	}

	// Observer Pattern //

	/**
	 * used to resize the drawing area whenever the canvas is resized
	 */
	// update() is called whenver the canvas is resized, and is used to resize the drawing area
	public void update() {
		resizeDrawingArea(canvas.getMainLayer().getWidth(), canvas.getMainLayer().getHeight());
		updateCanvas();
	}

	// Singelton Pattern //
	/**
	 * returns this layers handler
	 * @param canvas the canvas that the layers are displayed on
	 */
	public static LayersHandler getLayersHandler(OurCanvas canvas) {
		if (layersHandler == null) layersHandler = new LayersHandler(canvas);
		return layersHandler;
	}
}