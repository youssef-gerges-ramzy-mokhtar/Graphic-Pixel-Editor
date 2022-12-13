import javax.swing.*;
import java.awt.*;

// SelectButton is just a Class used to define some common properties that all Clickables Have
class SelectButton {
	private static final Color selectorCol = new Color(255, 242, 0);
	public static void selectBtn(JButton btn) {
		btn.setBackground(selectorCol);
	}

	public static void deSelectBtn(JButton btn) {
		btn.setBackground(new JButton().getBackground());
	}
}