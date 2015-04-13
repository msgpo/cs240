

import java.awt.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class DrawingFrame extends JFrame {

	private DrawingComponent component;
	private JSlider slider;
	
	public DrawingFrame() {
		this.setTitle("Drawing");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		component = new DrawingComponent();
		this.add(component, BorderLayout.CENTER);
		
		slider = new JSlider(1, 100, 20);
		this.add(slider, BorderLayout.SOUTH);
		
		this.setLocation(100, 100);

		this.pack();
	}
	
}
