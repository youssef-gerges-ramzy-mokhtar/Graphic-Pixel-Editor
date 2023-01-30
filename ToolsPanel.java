import javax.swing.*;
import java.awt.*;
import java.util.*;

// ToolsPanel is the Left Panel used to shows all the Tools Buttons
class ToolsPanel extends JPanel implements Observer {
    private ArrayList<Clickable> clickables;

	public ToolsPanel() {
        this.clickables = new ArrayList<Clickable>();

        setBackground(new Color(63, 72, 204));      //set panel colour, store this color in the constants class
        setBounds(0, 100, 200, getHeight() - 100);      //set panel area
        setLayout(new GridLayout(8,2)); //applies panel layout to panel

	}

	public void addClickable(Clickable clickable) {
		add(clickable.getBtn());
		clickable.addObserver(this);
		clickables.add(clickable);
	}

	// // Observer Pattern //
	public void update(int val) {}
	public void update2(Color col) {}
	
	// Whenever a Button is selected update3() will deSelect all other buttons
	public void update3() {
		for (Clickable clickable: clickables)
			if (clickable.isActive()) clickable.deSelect();

	}
}