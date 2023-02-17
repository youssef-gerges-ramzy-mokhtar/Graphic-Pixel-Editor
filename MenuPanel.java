import javax.swing.*;

// MenuPanel is just used to represent the Menu Button in the Frame to import & export images from the user computer
class MenuPanel extends JMenuBar {
	private ImageLoader imageLoaderMenu;
	private OurCanvas canvas;
	private Help helpMenu;

	public MenuPanel(OurCanvas canvas, ImageLoader imageLoaderMenu, SaveAs imageSaver, Help helpmenu) {
		this.imageLoaderMenu = imageLoaderMenu;
		add(imageLoaderMenu.getMenu());
		add(imageSaver.getMenu());
		add(helpMenu.getMenu());
	}
}