package empty;

import javax.swing.*;


@SuppressWarnings("serial")
public class EmptyFrame extends JFrame {

	public EmptyFrame() {

		// Set the window's title
		this.setTitle("Empty");
		
		// Specify what should happen when the user clicks the window's close icon
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set the location of the window on the desktop
		this.setLocation(100, 100);

		// Set the window's size
		this.setSize(700, 500);
	}

}
