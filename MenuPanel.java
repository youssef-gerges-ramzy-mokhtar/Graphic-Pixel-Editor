import javax.swing.*;

// MenuPanel is just used to represent the Menu Button in the Frame to import & export images from the user computer
class MenuPanel extends JMenuBar {
	private OurCanvas canvas;

	/**
	 * Used to represent the Menu Bar that contains all the Menu Buttons
	 * @param canvas is the current canvas that holds all the layers
	 */
	public MenuPanel(OurCanvas canvas) {
		this.canvas = canvas;
	}

	/**
	 * adds a menu button to the Menu Bar
	 * @param menu menu button
	 */
	public void addMenu(JMenu menu) {
		add(menu);
	}
}