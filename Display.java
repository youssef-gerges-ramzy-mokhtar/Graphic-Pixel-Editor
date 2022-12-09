import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class Display extends JFrame{
    //private JLabel label;
    private OurCanvas canvas;
    
    // private PenGui penGui;
    private ColorGui colorGui;
    private OptionsPanel optionsPanel;
    private ToolsPanel toolsPanel;
    // private EyeDropper eyeDropper;

    public Display() {   
        initFrame();

        // Updated Code //
        JPanel main = new JPanel(new BorderLayout());
        main.setBounds(0, 100, 200, 200);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        canvas = new OurCanvas();
        colorGui = new ColorGui();
        // Updated Code //

        // Code Change //
        toolsPanel = new ToolsPanel(canvas);
        colorGui.addObserver(toolsPanel.getPenGui());
        colorGui.addObserver(toolsPanel.getRectangle());
        colorGui.addObserver(toolsPanel.getFill());
        colorGui.addObserver(toolsPanel.getCircle());
        toolsPanel.getEyeDropper().addColorObserver(colorGui);

        optionsPanel = new OptionsPanel(colorGui);
        addObservers();
        
        main.add(toolsPanel, BorderLayout.WEST);
        main.add(new JScrollPane(canvas, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
        contentPane.add(main, BorderLayout.CENTER);
        contentPane.add(optionsPanel, BorderLayout.NORTH);
        revalidate();
        // Code Change //
    }

    private void initFrame() {
        setTitle("Pixel Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);              //doesnt use a layout manager
        setResizable(true);          //resizeable frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);              //sets x and y dimension
        setVisible(true);
        getContentPane().setBackground(Constants.mainColor);  //change background colour
    }

    public OurCanvas getCanvas() {
        return canvas;
    }

    public void addObservers() {
        optionsPanel.getPenOptionsPanel().addObserver(toolsPanel.getPenGui());
        optionsPanel.getPenOptionsPanel().addObserver(toolsPanel.getRectangle());
        optionsPanel.getPenOptionsPanel().addObserver(toolsPanel.getCircle());
        // colorGui.addObserver(penGui);
    }

    public static void main(String[] args){
        Display dis = new Display();
    }
}