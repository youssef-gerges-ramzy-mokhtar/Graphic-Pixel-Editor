import javax.swing.*;

// MenuPanel is just used to represent the Menu Button in the Frame to import images from the user computer
class MenuPanel extends JMenuBar {
	private ImageLoader imageLoaderMenu;
	private OurCanvas canvas;

	public MenuPanel(OurCanvas canvas, ImageLoader imageLoaderMenu) {
		this.imageLoaderMenu = imageLoaderMenu;
		add(imageLoaderMenu.getMenu());
	}
}