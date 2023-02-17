import javax.swing.*;
import javax.swing.event.*;
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
        File file = new File("shortcut_list.txt");
        try {
          FileReader fr = new FileReader(file);
          BufferedReader br = new BufferedReader(fr);
          JFrame menu = new JFrame();
          menu.setBounds(100, 100, 600, 600);
          JLabel label[] = new JLabel[15];
          for (int num = 0; num < 15; num++) {
            label[num] = new JLabel();
            label[num].setText(br.readLine());
            menu.add(label[num]);
          }
          br.close();
          menu.setVisible(true);
        } catch (Exception ex) {
        }
      }
    });
  }
}
