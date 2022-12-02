import java.awt.Color;
import java.awt.AWTException;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;  
import java.awt.event.MouseListener;




public class EyeDropper{

    private OurCanvas canvas;
    JButton eyeDropperbtn;
    private Boolean buttonSelected=false;
    
    public EyeDropper(OurCanvas pCanvas)
    {
        canvas = pCanvas;
        eyeDropperbtn = new JButton("EyeDropper");

        eyeDropperbtnListener();
        getColour();
    }

    public JButton getEyeDropperBtn()
    {
        
        return eyeDropperbtn;
    }
        

    public void getColour()
    {
        canvas.addMouseListener(new MouseAdapter()
        {
            public void mouseReleased(MouseEvent mouse) {
                Color col = canvas.getCanvasColor(mouse.getX(), mouse.getY());
                System.out.println(col);
                System.out.println(mouse.getX() + " " + mouse.getY());

                // System.out.println(buttonSelected);
                // if(buttonSelected)
                // {
                //     try {
                //         Robot robot = new Robot();
                
                //         // The pixel color information at 20, 20
                //         Color color = robot.getPixelColor(mouse.getX(), mouse.getY());
                
                //         // Print the RGB information of the pixel color
                //         System.out.println("Red   = " + color.getRed());
                //         System.out.println("Green = " + color.getGreen());
                //         System.out.println("Blue  = " + color.getBlue());
                
                //     } catch (AWTException e) {
                //         e.printStackTrace();
                //     }
                // }



            }
        });



        
    }

    private void eyeDropperbtnListener()
    {
        eyeDropperbtn.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent action)
            {
                System.out.println("EyeDropper button pressed");
                buttonSelected=!buttonSelected;
            }
        });
    }

    

    
    

}