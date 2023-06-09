import javax.swing.*;
import java.awt.*;
import java.util.*;

// ToolsPanel is the Left Panel used to shows all the Tools Buttons
public class ToolsPanel extends JPanel implements Observer {
    private ArrayList<Clickable> clickables;

    /**
     * ToolsPanel is the Left Panel used to display all the Tools Buttons
     */
	public ToolsPanel() {
        this.clickables = new ArrayList<Clickable>();
        setBackground(new Color(63, 72, 204));      //set panel colour, store this color in the constants class
        setBounds(0, 100, 200, getHeight() - 100);      //set panel area
        setLayout(new GridLayout(9,2)); //applies panel layout to panel
	}

	/**
	 * adds a new clickable to the tools panel
	 * @param clickable is the clickable button that represents the tool
	 */
	public void addClickable(Clickable clickable) {
		add(clickable.getBtn());
		clickable.addObserver(this);
		clickables.add(clickable);
	}

	// // Observer Pattern //
	public void update(int val) {}
	public void update2(Color col) {}
	
	/**
	 * will de-select all other buttons when a clickable/button is selected
	 */
	// Whenever a Button is selected update3() will deSelect all other buttons
	public void update3() {
		for (Clickable clickable: clickables)
			if (clickable.isActive()) clickable.deSelect();

	}
}