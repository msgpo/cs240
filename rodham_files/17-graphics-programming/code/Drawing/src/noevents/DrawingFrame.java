package noevents;

import java.awt.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class DrawingFrame extends JFrame {

	public DrawingFrame() {
		this.setTitle("Drawing");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(new DrawingComponent(), BorderLayout.CENTER);
		
		this.setLocation(100, 100);

		//this.setSize(650, 300);
		this.pack();
	}

}
