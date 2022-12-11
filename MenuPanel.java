import javax.swing.*;

class MenuPanel extends JMenuBar {
	private ImageLoader imageLoaderMenu;
	private OurCanvas canvas;

	public MenuPanel(OurCanvas canvas) {
		imageLoaderMenu = new ImageLoader(canvas);
		add(imageLoaderMenu.getMenu());
	}

}