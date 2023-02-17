import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

// LayerOption is a JPanel representing the look and feel of a single layer in the layers panel
class LayerOption extends JPanel {
	private LayerData layer;
	private LayersHandler layersHandler;
	private LayerObserver layerObserver;
	private UndoTool undo;

	private JCheckBox mergeCheckBox; 
	private JCheckBox visibilityCheckBox;
	private JLabel layerIcon;

	private boolean ctrlClicked = false; // ctrlClicked is used to check if the ctrl key is currently clicked or not
	private boolean selected = false; // selected is used to mark the current layer as selected

	public LayerOption(LayerObserver layerObserver, LayersHandler layersHandler, LayerData layer, UndoTool undo) {
		this.layersHandler = layersHandler;
		this.layer = layer;
		this.layerObserver = layerObserver;
		this.undo = undo;

		initLayerOption();
		addBtnListeners();
	}

	// initLayerOption() is used to initialize the graphical look of any Layer Option
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
		// visibilityCheckBox when clicked is used to toggle between hiding the layer and making the layer visible
		visibilityCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (layer.isHidden()) layer.show();
				else layer.hide();
				update();
			}
		});

		// mergeCheckBox is used to set the layer as ready for mergey with other selected layers
		mergeCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layer.setSelectedForMerge(!layer.isSelectedForMerge());					
				recordHistory();
			}
		});

		// we are attaching an event listener to the LayerOption itself
		this.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				// simply this lines mean that when the user uses right click on the mouse a Popup Menu containg option will appear
				if (e.isPopupTrigger()) {
					LayerOptionPopUp menuOptions = new LayerOptionPopUp();
					menuOptions.show(e.getComponent(), e.getX(), e.getY());
					return;
				}

				// if the ctrl is clicked then just select the layer
				if (ctrlClicked) select();
				else {
					layersHandler.changeSelectedLayer(layer); // change the selected layer to the layers option that has been choosen
					layersHandler.updateCanvasSelected(layer); // is used to refresh the canvas to show for the user that the layer has been choosen and putting a border on the choosen layer 
					layerObserver.update(); // updates the Layer Observer because the layers structure has changed
				}
			}
		});

		// Here we are just checking if the ctrl key is clicked or not
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
	  		public boolean dispatchKeyEvent(KeyEvent e) {
	        	ctrlClicked = e.isControlDown();
	        	return false;
	      	}
		});
	}


	// copy() is used to create a copy of the Layer and the Layer Option Panel
	public void copy() {
		if (isSelected()) ((LayersOptions) layerObserver).copyLayerOptoins();	
		else {copyOperation(); update();}
	}

	// copyOperation contains the logic of copying a layer
	public void copyOperation() {
		LayerData layerCopy = layer.getCopy();
		layerCopy.setLocation(0, 0);
		layersHandler.addLayer(layerCopy);
	}

	// delete() is used to delete the layer and the layer option panel that represente the layer
	public void delete() {
		if (isSelected()) ((LayersOptions) layerObserver).deleteLayerOptions();
		else {deleteOperation(); update();}
	}

	// deleteOperation() contains the logic of deleting a layer
	public void deleteOperation() {
		layersHandler.removeLayer(layer);
	}

	// merge() is used to call mergeLayerOptions on the layerObserver to merge all the selected layers 
	public void merge() {
		if (!isSelected()) return;
		((LayersOptions) layerObserver).mergeLayerOptions();
	}

	// update refresh the canvas and notifies the layerObserver that the layers structure have been changed and record history for undo tool
	private void update() {
		layersHandler.updateCanvas();
		layerObserver.update();
		recordHistory();		
	}

	// recordHisotyr is used to record the history for the undo tool if the undo is not null
	private void recordHistory() {
		if (undo != null) undo.recordHistory();
	}

	// select() is used to mark the Layer Option as selected and change its color to indicate that it is selected
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


// LayerOptionPopUp is used to represent the Popup Menu that appears when right clicking on a Layer Option
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
		// if delete is clicked then we call the delete method on the layer option that needs to be deleted
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LayerOption layerOption = (LayerOption) getInvoker();
				layerOption.delete();
			}
		});

		// if copy is clicked then we call the copy method on the layer option that needs to be copied
		copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LayerOption layerOption = (LayerOption) getInvoker();
				layerOption.copy();
			}
		});

		// if merge is clicked then we call the merge method on the layer option that needs to be copied
		merge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LayerOption layerOption = (LayerOption) getInvoker();
				if (!layerOption.isSelected()) return;
				layerOption.merge();
			}
		});
	}
}