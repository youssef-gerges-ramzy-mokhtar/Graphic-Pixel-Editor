import javax.swing.*;

class MenuPanel extends JMenuBar {
	private ImageLoader imageLoaderMenu;
	private OurCanvas canvas;

	public MenuPanel(OurCanvas canvas, ImageLoader imageLoaderMenu) {
		this.imageLoaderMenu = imageLoaderMenu;
		add(imageLoaderMenu.getMenu());
	}

}