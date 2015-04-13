import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @version 1.33 2007-06-12
 * @author Cay Horstmann
 */
public class BorderTest
{
   public static void main(String[] args)
   {
      EventQueue.invokeLater(new Runnable()
         {
            public void run()
            {
               ButtonFrame frame = new ButtonFrame();
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.setVisible(true);
            }
         });
   }
}

/**
 * A frame with a button panel
 */
class ButtonFrame extends JFrame
{
   public ButtonFrame()
   {
      setTitle("Border Test");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // create buttons
      JButton yellowButton = new JButton("Yellow");
      JButton blueButton = new JButton("Blue");
      JButton redButton = new JButton("Red");
      JButton greenButton = new JButton("Green");
      
      buttonPanel = new JPanel();
      buttonPanel.setLayout(new BorderLayout());

      // add buttons to panel
      buttonPanel.add(yellowButton, BorderLayout.NORTH);
      buttonPanel.add(blueButton, BorderLayout.SOUTH);
      buttonPanel.add(redButton, BorderLayout.WEST);
      buttonPanel.add(greenButton, BorderLayout.EAST);

      // add panel to frame
      add(buttonPanel);

      // create button actions
      ColorAction yellowAction = new ColorAction(Color.YELLOW);
      ColorAction blueAction = new ColorAction(Color.BLUE);
      ColorAction redAction = new ColorAction(Color.RED);
      ColorAction greenAction = new ColorAction(Color.GREEN);

      // associate actions with buttons
      yellowButton.addActionListener(yellowAction);
      blueButton.addActionListener(blueAction);
      redButton.addActionListener(redAction);
      greenButton.addActionListener(greenAction);
   }

   /**
    * An action listener that sets the panel's background color.
    */
   private class ColorAction implements ActionListener
   {
      public ColorAction(Color c)
      {
         backgroundColor = c;
      }

      public void actionPerformed(ActionEvent event)
      {
         buttonPanel.setBackground(backgroundColor);
      }

      private Color backgroundColor;
   }

   private JPanel buttonPanel;

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;
}