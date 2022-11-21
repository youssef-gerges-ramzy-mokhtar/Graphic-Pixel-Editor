import javax.swing.*;
import java.awt.*;

class Window extends JFrame {
	public static void main(String[] args) {
		Window window = new Window();

	}

	public Window() {
		setVisible(true);
		Dimension d = getMaximumSize(); 
		setSize(d.width, d.height);
	}
}