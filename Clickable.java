import javax.swing.JButton;
import java.util.ArrayList;
import java.awt.event.*;

abstract class Clickable implements Observable {
	protected OurCanvas canvas;
	protected boolean btnActive;
	protected JButton btn;
	private ArrayList<Observer> clickObservers;

	public Clickable(OurCanvas canvas) {
		this.canvas = canvas;
		this.clickObservers = new ArrayList<Observer>();
		btnActive = false;

		btn = new JButton("Dummy Text");
		addBtnListener();
	}

	public boolean isActive() {
		return btnActive;
	}

	public void deSelect() {
		btnActive = false;
		SelectButton.deSelectBtn(btn);
	}

	public JButton getBtn() {
		return btn;
	}

	private void addBtnListener() {
		btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent action) {
                notifyObservers();
                btnActive = true;
                SelectButton.selectBtn(btn);
            }
        });
	}

	// Observer Pattern: Used to notify the observers whenver a button is clicked //
	public void notifyObservers() {
		for (Observer observer: clickObservers)
			observer.update3();
	}

	public void addObserver(Observer observer) {
		clickObservers.add(observer);
	}

	public void removeObserver(Observer observer) {
		clickObservers.remove(observer);
	}
}