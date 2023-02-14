import java.awt.image.*;
import java.awt.*;

abstract class ShapeLayer extends LayerData {
	private SpecificGraphic specificGraphic;
	protected Color strokeCol;
	protected Color fillCol;

	public ShapeLayer(int width, int height, Color col) {super(width, height, col);}
	public ShapeLayer(int width, int height, Color col, Point layerPos) {super(width, height, col, layerPos);}

	public void resize(int width, int height) {
		if (width <= 5 || height <= 5) return;
		
		
		BufferedImage layerImg = getImage();
		this.fillCol = new Color(layerImg.getRGB(layerImg.getWidth() / 2, layerImg.getHeight() / 2)); // That is not accurate because there might be drawings on the Shape Layer

		specificGraphic = getSpecificGraphic(width, height);
		
		
		BufferedImage oldLayer = getImage();

		setImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
		Graphics2D g2d = getLayerGraphics();
		updateGraphics(specificGraphic);

		updateSelectionLayer();
		
	}

	public void resize(Point newLayerEndPos) {
		System.out.println(newLayerEndPos.x - getX());
		System.out.println(newLayerEndPos.y - getY());
		//System.out.println(newLayerEndPos);
		int layerWidth = Math.abs(newLayerEndPos.x - getX());
		int layerHeight = Math.abs(newLayerEndPos.y - getY());
		if(layerWidth<15 || newLayerEndPos.x - getX() < 0) layerWidth=15;
		if(layerHeight<15 || newLayerEndPos.y - getY() < 0) layerHeight=15;
		
		resize(layerWidth, layerHeight);
		
		if(newLayerEndPos.x - getX() > 15 && newLayerEndPos.y -getY() > 15)
		setLocation(validPoint(getCoords(), newLayerEndPos));
		

	}

	public void setStrokeCol(Color col) {
		this.strokeCol = col;
	}
	public void setFillCol(Color col) {
		this.fillCol = col;
	}

	public ShapeLayer getCopy() {
		ShapeLayer copy = getShapeLayerCopy();
		resetLayerProperties(copy);
		return copy;
	}

	public LayerData rasterize() {
		// Code Refactor Here
		ImageLayer rasterizedShape = new ImageLayer(layerWidth(), layerHeight(), Color.white);
		rasterizedShape.clear(new Color(0, 0, 0, 0));

		rasterizedShape.mergeLayer(this.getImage(), 0, 0);
		rasterizedShape.setLocation(new Point(getX(), getY()));
		rasterizedShape.updateSelectionLayer();
		return rasterizedShape;
	}

	protected abstract SpecificGraphic getSpecificGraphic(int width, int heights);
	protected abstract ShapeLayer getShapeLayerCopy();
}