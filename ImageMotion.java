import java.awt.event.*;
import java.awt.*;

class ImageMotion {
	private OurCanvas canvas;
	private LayersHandler layerHandler;
	private ImageLoader imageLoader;
	private LayerData imgToMove;

	public ImageMotion(OurCanvas canvas, ImageObservable imageLoader) {
		layerHandler = new LayersHandler(canvas);
		imageLoader.addObserver(layerHandler);
		this.canvas = canvas;
		
		addCanvasListener();
	}
}