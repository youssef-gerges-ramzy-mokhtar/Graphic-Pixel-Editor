import javax.swing.*;
import java.awt.*;

// Display is the Main Entry for the whole Program and it simply represents the Program Frame and all Gui Components on that Frame
public class Display extends JFrame {
    private ToolsManager toolsManager;

    public Display() {
        this.toolsManager = new ToolsManager(this);
        initFrameProperties();
        initFrameLayout();
    }

    // initFrameProperties() sets the Frame Properties
    private void initFrameProperties() {
        setTitle("Pixel Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);              //doesnt use a layout manager
        setResizable(true);          //resizeable frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);              //sets x and y dimension
        setVisible(true);
        getContentPane().setBackground(Constants.mainColor);  //change background colour
    }

    // initrameLayout() adds the different Gui Components to the Frame
    private void initFrameLayout() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBounds(0, 100, 200, 200);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        main.add(toolsManager.getToolsPanel(), BorderLayout.WEST);
        main.add(new JScrollPane(toolsManager.getCanvas(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
        contentPane.add(main, BorderLayout.CENTER);
        contentPane.add(toolsManager.getOptionsPanel(), BorderLayout.NORTH);
        setJMenuBar(toolsManager.getMenuPanel());
        revalidate();
    }

    public static void main(String[] args) {
        Display dis = new Display();
    }
}