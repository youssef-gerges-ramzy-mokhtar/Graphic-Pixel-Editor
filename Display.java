import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Display extends JFrame /*implements ActionListener*/{
    //private JLabel label;
    OurCanvas canvas;    
    PenGui penGui;
    ColorGui colorGui;

    public Display() {
        setTitle("I want to kermit sewer slide");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);              //doesnt use a layout manager
        setResizable(true);          //resizeable frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);              //sets x and y dimension
        setVisible(true);
        getContentPane().setBackground(Color.blue);  //change background colour

        // Updated Code //
        JPanel main = new JPanel(new BorderLayout());
        main.setBounds(0, 100, 200, 200);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
            
        canvas = new OurCanvas();
        // contentPane.add(canvas, BorderLayout.CENTER);        
        penGui = new PenGui(this);
        penGui.addComponentsToFrame();
        
        colorGui = new ColorGui();    
        // Updated Code //

        JPanel secondpanel = new JPanel();        //creates panel
        secondpanel.setBackground(Color.blue);      //set panel colour
        secondpanel.setBounds(0, 100, 200, getHeight()-100);      //set panel area
        BorderLayout s = new BorderLayout();        //set panel layout
        secondpanel.setLayout(new GridLayout(6,2)); //applies panel layout to panel
        
        //Icon fillImage = new ImageIcon("Icons/Fill");
        JButton fill = new JButton("Fill");
        //Icon eyedropImage = new ImageIcon("Icons/eyedropper");
        JButton eyedrop = new JButton("Eyedrop");
        //Icon sprayImage = new ImageIcon("Icons/Spray");
        JButton airbrush = new JButton("Airbrush");
        //Icon blurImage = new ImageIcon("Icons/Blurred");
        JButton blur = new JButton("Blue");
        
        secondpanel.add(penGui.getPenBtn());
        secondpanel.add(penGui.getEraserBtn());
        secondpanel.add(colorGui.getBtn());
        secondpanel.add(fill);
        secondpanel.add(eyedrop);
        secondpanel.add(airbrush);
        secondpanel.add(blur);
    
        
        // Code Change //
        main.add(secondpanel, BorderLayout.WEST);
        main.add(canvas, BorderLayout.CENTER);
        contentPane.add(main, BorderLayout.CENTER);
        revalidate();
        // Code Change //
    }

    public static void main(String[] args){
        Display dis = new Display();
    }

    public OurCanvas getCanvas() {
        return canvas;
    }

    public Color getColor() {
        return colorGui.getColor();
    }
}