import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

class LayerOption extends JPanel {
	private LayerData layer;
	private LayersHandler layersHandler;
	private LayerObserver layerObserver;
	private UndoTool undo;

	private JCheckBox mergeCheckBox; 
	private JCheckBox visibilityCheckBox;
	private JLabel layerIcon;

	private boolean ctrlClicked = false;
	private boolean selected = false;

	public LayerOption(LayerObserver layerObserver, LayersHandler layersHandler, LayerData layer, UndoTool undo) {
		this.layersHandler = layersHandler;
		this.layer = layer;
		this.layerObserver = layerObserver;
		this.undo = undo;

		initLayerOption();
		addBtnListeners();
	}

	private void initLayerOption() {
		// We can have a better code structure here
		setLayout(new FlowLayout(FlowLayout.LEFT));
		mergeCheckBox = new JCheckBox();
		if (layer.isSelectedForMerge()) mergeCheckBox.setSelected(true);

		visibilityCheckBox = new JCheckBox();
		try {
			BufferedImage none = new BufferedImage(12, 12, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = (Graphics2D) none.getGraphics();
			g2d.setBackground(new Color(0, 0, 0, 0));
			g2d.clearRect(0, 0, layer.getWidth(), layer.getHeight());

			ImageIcon noneIcon = new ImageIcon(none);
			ImageIcon visibleIcon = new ImageIcon("./visible.jpeg");

			visibilityCheckBox.setPreferredSize(new Dimension(20, 20));
			visibilityCheckBox.setIcon(noneIcon);
			visibleIcon = new ImageIcon(visibleIcon.getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH));
			visibilityCheckBox.setSelectedIcon(visibleIcon);

			if (!layer.isHidden()) visibilityCheckBox.setSelected(true);
		} catch (Exception e) {}


		LayerData drawingLayer = layersHandler.getDrawingLayer();
		ImageLayer layerPreview = new ImageLayer(Math.max(drawingLayer.getWidth(), layer.getX()), Math.max(drawingLayer.getHeight(), layer.getY()), Color.white);
		layerPreview.mergeLayer(layer);
		layerIcon = new JLabel(new ImageIcon(layerPreview.getImage().getScaledInstance(60, 40, Image.SCALE_SMOOTH))); // In the future the Layer will scale based on its ration on the canvas

		add(mergeCheckBox);
		add(visibilityCheckBox);
		add(layerIcon);

		setBackground(new Color(204, 204, 204));
	}

	private void addBtnListeners() {
		visibilityCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (layer.isHidden()) layer.show();
				else layer.hide();
				update();
			}
		});

		mergeCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layer.setSelectedForMerge(!layer.isSelectedForMerge());					
				if (undo != null) undo.recordHistory();
			}
		});

		this.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					LayerOptionPopUp menuOptions = new LayerOptionPopUp();
					menuOptions.show(e.getComponent(), e.getX(), e.getY());
					return;
				}

				if (ctrlClicked) select();
				else {
					layersHandler.changeSelectedLayer(layer);
					layersHandler.updateCanvasSelected(layer);
					layerObserver.update();
				}
			}
		});

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
	  		public boolean dispatchKeyEvent(KeyEvent e) {
	        	ctrlClicked = e.isControlDown();
	        	return false;
	      	}
		});
	}

	public void copy() {
		if (isSelected()) ((LayersOptions) layerObserver).copyLayerOptoins();	
		else {copyOperation(); update();}
	}
	public void copyOperation() {
		LayerData layerCopy = layer.getCopy();
		layerCopy.setLocation(0, 0);
		layersHandler.addLayer(layerCopy);
	}
	public void delete() {
		if (isSelected()) ((LayersOptions) layerObserver).deleteLayerOptions();
		else {deleteOperation(); update();}
	}
	public void deleteOperation() {
		layersHandler.removeLayer(layer);
	}
	public void merge() {
		if (!isSelected()) return;
		((LayersOptions) layerObserver).mergeLayerOptions();
	}

	private void update() {
		layersHandler.updateCanvas();
		layerObserver.update();
		
		if (undo != null) undo.recordHistory();
	}

	private void recordHistory() {
		if (undo != null) undo.recordHistory();
	}

	public void select() {
		setBackground(new Color(126, 126, 126));
		selected = true;
	}
	public boolean isSelected() {
		return selected;
	}

	public LayerData getLayer() {
		return layer;
	}
}


class LayerOptionPopUp extends JPopupMenu {
	private JMenuItem delete;
	private JMenuItem copy;
	private JMenuItem merge;

	public LayerOptionPopUp() {
		initOptionChoices();
		attachEventHandlers();
	}

	private void initOptionChoices() {
		delete = new JMenuItem("delete");
		copy = new JMenuItem("copy");
		merge = new JMenuItem("merge layers");

		add(delete);
		add(copy);
		add(merge);
	}

	private void attachEventHandlers() {
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LayerOption layerOption = (LayerOption) getInvoker();
				layerOption.delete();
			}
		});

		copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LayerOption layerOption = (LayerOption) getInvoker();
				layerOption.copy();
			}
		});

		merge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LayerOption layerOption = (LayerOption) getInvoker();
				if (!layerOption.isSelected()) return;
				layerOption.merge();
			}
		});
	}

}