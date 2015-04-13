package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ButtonBar extends JPanel{
	
	JButton zIn;
	JButton zOut;
	JButton invert;
	JButton hilite;
	JButton save;
	JButton submit;

	public ButtonBar(ActionListener al){
	 	this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		zIn = new JButton("Zoom In");
		zOut = new JButton("Zoom Out");
		invert = new JButton("Invert Image");
		hilite = new JButton("Toggle Highlights");
		save = new JButton("Save");
		save.setActionCommand("save");
		save.addActionListener(al);
		submit = new JButton("Submit");

		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(zIn);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(zOut);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(invert);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(hilite);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(save);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(submit);
		this.add(Box.createHorizontalGlue());

	}
}

