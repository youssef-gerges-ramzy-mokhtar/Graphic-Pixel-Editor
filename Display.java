import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Display /*implements ActionListener*/{
    //private JLabel label;
    
    public Display() {
        JFrame frame = new JFrame();
        frame.setTitle("I want to kermit sewer slide");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);              //doesnt use a layout manager
        frame.setResizable(true);          //resizeable frame
        frame.setSize(1000,850);              //sets x and y dimension
        frame.getContentPane().setBackground(Color.black);  //change background colour

        JPanel mainpanel = new JPanel();        //creates panel
        mainpanel.setBackground(Color.blue);        //set b ackground colour
        mainpanel.setBounds(0, 0, 1000, 100);
        BorderLayout b = new BorderLayout();
        mainpanel.setLayout(b);
        

        JPanel secondpanel = new JPanel();        //creates panel
        secondpanel.setBackground(Color.blue);      //set panel colour
        secondpanel.setBounds(0, 100, 200, frame.getHeight()-100);      //set panel area
        BorderLayout s = new BorderLayout();        //set panel layout
        secondpanel.setLayout(new GridLayout(6,2)); //applies panel layout to panel
        //Icon penImage = new ImageIcon("Icons/Paint");
        JButton pen = new JButton();
        //Icon fillImage = new ImageIcon("Icons/Fill");
        JButton fill = new JButton();
        //Icon colourImage = new ImageIcon("Icons/Colour");
        JButton colour = new JButton();
        //Icon eraserImage = new ImageIcon("Icons/eraser");
        JButton eraser = new JButton();
        //Icon eyedropImage = new ImageIcon("Icons/eyedropper");
        JButton eyedrop = new JButton();
        //Icon sprayImage = new ImageIcon("Icons/Spray");
        JButton airbrush = new JButton();
        //Icon blurImage = new ImageIcon("Icons/Blurred");
        JButton blur = new JButton();
        
        secondpanel.add(pen);
        secondpanel.add(eraser);
        secondpanel.add(colour);
        secondpanel.add(fill);
        secondpanel.add(eyedrop);
        secondpanel.add(airbrush);
        secondpanel.add(blur);
        


        /*left panel -- creates panel and assigns all creation details*/
        OurCanvas leftpanel = new OurCanvas();
        leftpanel.setBackground(Color.WHITE);
        // JPanel leftpanel = new JPanel();        
        // leftpanel.setBackground(Color.WHITE);
        leftpanel.setBounds(204, 104, 900, 800);
        
        frame.add(mainpanel);
        frame.add(secondpanel);
        frame.add(leftpanel); 
        frame.setVisible(true);
    
        PenGui initial_pen = new PenGui(leftpanel);
    }
}