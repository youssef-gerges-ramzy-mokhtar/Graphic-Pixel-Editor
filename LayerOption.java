import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class LayerOption extends JPanel {
	private LayerData layer;
	private LayersHandler layersHandler;

	private JCheckBox mergeCheckBox; 
	private JLabel layerIcon;
	private JButton layerNumBtn;
	private JButton moveUpBtn;
	private JButton moveDownBtn;
	private JButton copyBtn;
	private JButton deleteBtn;
	private JButton hideBtn;
	private JButton showBtn;

	private LayerObserver layerObserver;
	private UndoTool undo;

	public LayerOption(LayerObserver layerObserver, LayersHandler layersHandler, LayerData layer, UndoTool undo, int layerNum) {
		this.layersHandler = layersHandler;
		this.layer = layer;
		this.layerObserver = layerObserver;
		this.undo = undo;

		initLayerOption(layerNum);
		addBtnListeners();
	}

	private void initLayerOption(int layerNum) {
		mergeCheckBox = new JCheckBox();
		layerIcon = new JLabel(new ImageIcon(layer.getImage().getScaledInstance(60, 40, Image.SCALE_SMOOTH))); // In the future the Layer will scale based on its ration on the canvas
		layerNumBtn = new JButton("" + layerNum);
		moveUpBtn = new JButton("up");
		moveDownBtn = new JButton("dwn");
		copyBtn = new JButton("cpy");
		deleteBtn = new JButton("del");
		hideBtn = new JButton("hide");
		showBtn = new JButton("show");

		add(mergeCheckBox);
		add(layerIcon);
		add(layerNumBtn);
		add(moveUpBtn);
		add(moveDownBtn);
		add(copyBtn);
		add(deleteBtn);
		add(hideBtn);
		add(showBtn);

		setBackground(new Color(204, 204, 204));
	}

	private void addBtnListeners() {
		moveUpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layersHandler.moveLayerUp(layer);
				update();
			}
		});

		moveDownBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layersHandler.moveLayerDown(layer);
				update();
			}
		});

		copyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LayerData layerCopy = layer.getCopy();
				layerCopy.setLocation(0, 0);
				layersHandler.addLayer(layerCopy);
				update();
			}
		});

		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layersHandler.removeLayer(layer);
				update();
			}
		});

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
			public void mouseClicked(MouseEvent e) {
				if (layer.isHidden()) return;
				layersHandler.changeSelectedLayer(layer);
				layerObserver.update();

			}
		});
	}

	private void update() {
		layersHandler.updateCanvas();
		layerObserver.update();
		undo.recordHistory();
	}

	private void recordHistory() {
		if (undo != null) undo.recordHistory();
	}

	public void select() {
		setBackground(new Color(126, 126, 126));
	}
}