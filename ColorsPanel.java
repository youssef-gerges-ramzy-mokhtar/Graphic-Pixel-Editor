import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.*;
import java.util.*;

class ColorsPanel extends JPanel {
	private ArrayList<Color> colorSwatches;
	private ColorGui colorGui;
	private final int rows;
	private final int cols;
	private final int swatchSz;

	public ColorsPanel(ColorGui colorGui) {
		rows = 3;
		cols = 6;
		swatchSz = 30;

		this.colorGui = colorGui;
		setLayout(new GridLayout(rows, cols));
		initColorSwatches();

		for (Color col : colorSwatches) {
			JButton swatchBtn = new JButton();
			swatchBtn.setBackground(col);
			swatchBtn.setPreferredSize(new Dimension(swatchSz, swatchSz));
            swatchBtn.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
            swatchBtn.setBorderPainted(false);
		
            MouseAdapter adapter = new MouseAdapter() {
	            public void mouseEntered(MouseEvent e) {
	                JButton source = (JButton) e.getSource();
	                source.setBorderPainted(true);
	            }

	            public void mouseExited(MouseEvent e) {
	                JButton source = (JButton) e.getSource();
	                source.setBorderPainted(false);
	            }
	        };

	        swatchBtn.addMouseListener(adapter);

	        swatchBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					colorGui.update2(((JButton) e.getSource()).getBackground());
				}
			});

	        add(swatchBtn);
		}
	}

	private void initColorSwatches() {
		colorSwatches = new ArrayList<>();
		colorSwatches.add(Color.white);
		colorSwatches.add(new Color(195, 195, 195));
		colorSwatches.add(new Color(88, 88, 88));
		colorSwatches.add(new Color(0, 0, 0));
		colorSwatches.add(new Color(136, 0, 27));
		colorSwatches.add(new Color(236, 38, 36));
		colorSwatches.add(new Color(255, 127, 39));
		colorSwatches.add(new Color(255, 202, 24));
		colorSwatches.add(new Color(253, 236, 166));
		colorSwatches.add(new Color(255, 242, 0));
		colorSwatches.add(new Color(196, 255, 14));
		colorSwatches.add(new Color(14, 209, 69));
		colorSwatches.add(new Color(140, 255, 251));
		colorSwatches.add(new Color(0, 168, 243));
		colorSwatches.add(new Color(63, 72, 204));
		colorSwatches.add(new Color(184, 61, 186));
		colorSwatches.add(new Color(255, 174, 200));
		colorSwatches.add(new Color(185, 122, 86));
	}
}