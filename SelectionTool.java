import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class SelectionTool extends Clickable implements Observable {
	private LayersHandler layersHandler;
	private LayerData imgToMove;

	public SelectionTool(OurCanvas canvas, LayersHandler layersHandler) {
		super(canvas);
		this.layersHandler = layersHandler;
	
		btn.setText("Select");	
		addCanvasListener();
	}

	private void addCanvasListener() {
		canvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                imgToMove = layersHandler.selectLayer(new Point(e.getX(), e.getY()));
            }
        });

        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (!btnActive) return;
                if (imgToMove == null) return;

	            canvas.clearCanvas();
                imgToMove.setLocation(e.getX() - layersHandler.getHorizontalOffset(), e.getY() - layersHandler.getVerticalOffset());
                layersHandler.updateCanvas();
            }
        });
	}

	public CanvasObserver getCanvasObserver() {
		return layersHandler;
	}

	public LayersHandler getLayerHandler() {
		return layersHandler;
	}
}