import javax.swing.*;
import javax.swing.event.*;
import java.awt.Color;
import java.io.*;

// Help is used Loads Images from the User's Computer into the Program
class Help {
  private JMenu helpMenu = new JMenu("Help");

  /**
   * creates a button on the menu to save the canvas as a file
   * 
   * @param canvas
   */
  public Help(OurCanvas canvas) {
    addhelpMenuListener();
  }

  /**
   * returns the JMenu for this button *
   * 
   * @return
   */
  public JMenu getMenu() {
    return helpMenu;
  }

  /**
   * Used to attach an event handler to the Menu Button
   **/
  private void addhelpMenuListener() {
    helpMenu.addMenuListener(new MenuListener() {
      public void menuCanceled(MenuEvent e) {
      }

      public void menuDeselected(MenuEvent e) {
      }

      public void menuSelected(MenuEvent e) {
        File file = new File("Help.txt");
        try {
          FileReader fr = new FileReader(file);
          BufferedReader br = new BufferedReader(fr);
          JFrame menu = new JFrame();
          JPanel panel = new JPanel();
          menu.setBounds(100, 100, 600, 600);
          panel.setBounds(100, 400, 600, 200);
          panel.setBackground(Color.WHITE);
          menu.add(panel);
          JLabel label[] = new JLabel[18];
          for (int num = 0; num < 18; num++) {
            label[num] = new JLabel();
            label[num].setText(br.readLine() + "                                                                 ");
            panel.add(label[num]);
          }
          br.close();
          fr.close();
          menu.setVisible(true);
        } catch (Exception ex) {
        }
      }
    });
  }
}
