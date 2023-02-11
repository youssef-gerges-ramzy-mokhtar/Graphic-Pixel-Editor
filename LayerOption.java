import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class LayerOption extends JPanel {
	private LayerData layer;
	private LayersHandler layersHandler;

	private JCheckBox mergeCheckBox; 
	private JLabel layerIcon;
	private JButton hideBtn;
	private JButton showBtn;

	private LayerObserver layerObserver;
	private UndoTool undo;

	public LayerOption(LayerObserver layerObserver, LayersHandler layersHandler, LayerData layer, UndoTool undo) {
		this.layersHandler = layersHandler;
		this.layer = layer;
		this.layerObserver = layerObserver;
		this.undo = undo;

		initLayerOption();
		addBtnListeners();
	}

	private void initLayerOption() {
		mergeCheckBox = new JCheckBox();
		layerIcon = new JLabel(new ImageIcon(layer.getImage().getScaledInstance(60, 40, Image.SCALE_SMOOTH))); // In the future the Layer will scale based on its ration on the canvas
		hideBtn = new JButton("hide");
		showBtn = new JButton("show");

		add(mergeCheckBox);
		add(layerIcon);
		add(hideBtn);
		add(showBtn);

		setBackground(new Color(204, 204, 204));
	}

	private void addBtnListeners() {
		hideBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (layer == layersHandler.getDrawingLayer()) return;
				if (layer.isHidden()) return;

				layer.hide();
				update();
			}
		});

		showBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!layer.isHidden()) return;

				layer.show();
				update();
			}
		});

		this.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					LayerOptionPopUp menuOptions = new LayerOptionPopUp();
					menuOptions.show(e.getComponent(), e.getX(), e.getY());
					return;
				}

				layersHandler.changeSelectedLayer(layer);
				layerObserver.update();
			}
		});
	}

	public void delete() {
		layersHandler.removeLayer(layer);
		update();
	}
	public void copy() {
		LayerData layerCopy = layer.getCopy();
		layerCopy.setLocation(0, 0);
		layersHandler.addLayer(layerCopy);
		update();
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
				System.out.println(layerOption);
			}
		});
	}

}