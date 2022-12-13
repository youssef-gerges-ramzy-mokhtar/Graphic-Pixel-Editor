import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class Display extends JFrame {
    //private JLabel label;
    private OurCanvas canvas;
    
    // private PenGui penGui;
    // private EyeDropper eyeDropper;
    private ColorGui colorGui;
    private OptionsPanel optionsPanel;
    private ToolsPanel toolsPanel;
    private ToolsManager toolsManager;
    private ImageLoader imageLoader;
    private LayersHandler layersHandler;

    public Display() {
        initFrame();

        // Updated Code //
        JPanel main = new JPanel(new BorderLayout());
        main.setBounds(0, 100, 200, 200);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        canvas = new OurCanvas();
        colorGui = new ColorGui();

        // Layer Support //
        imageLoader = new ImageLoader(canvas); // For Loading Images from the user computer
        layersHandler = LayersHandler.getLayersHandler(canvas); // For Handling Layers
        imageLoader.addObserver(layersHandler); // So whenever a user imports an image from his computer will automatically be added to the layersHandler
        canvas.addCanvasObserver(layersHandler); // For Observing Changes in the Canvas Size
        // Layer Support //

        // Updated Code //

        // Code Change //
        toolsPanel = new ToolsPanel();
        toolsManager = new ToolsManager(canvas, toolsPanel);
        colorGui.addObserver(toolsManager.getPenGui());
        colorGui.addObserver(toolsManager.getRectangle());
        colorGui.addObserver(toolsManager.getFill());
        colorGui.addObserver(toolsManager.getCircle());
        toolsManager.getEyeDropper().addObserver(colorGui);

        optionsPanel = new OptionsPanel(colorGui);
        addObservers();
        
        main.add(toolsPanel, BorderLayout.WEST);
        main.add(new JScrollPane(canvas, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
        contentPane.add(main, BorderLayout.CENTER);
        contentPane.add(optionsPanel, BorderLayout.NORTH);
        setJMenuBar(new MenuPanel(canvas, imageLoader));
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
        optionsPanel.getPenOptionsPanel().addObserver(toolsManager.getPenGui());
        optionsPanel.getPenOptionsPanel().addObserver(toolsManager.getEraserTool());
        optionsPanel.getPenOptionsPanel().addObserver(toolsManager.getRectangle());
        optionsPanel.getPenOptionsPanel().addObserver(toolsManager.getCircle());
        canvas.addObserver(toolsPanel);
    }

    public static void main(String[] args){
        Display dis = new Display();
    }
}