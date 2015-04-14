package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class InfoPanel extends JTabbedPane{
	
	public InfoPanel(ActionListener al, ImagePanel ip){
		new JTabbedPane();

		JPanel helpPanel = new JPanel();
		JTextArea helpArea = new JTextArea(40, 40);
		helpPanel.add(helpArea);
		helpPanel.setMinimumSize(new Dimension(512, 256));

		JPanel navPanel = new JPanel();
		navPanel.add(ip);
		navPanel.setMinimumSize(new Dimension(512, 256));

		this.addTab("Field Help", helpPanel);
		this.addTab("Image Navigation", navPanel);
		this.setMinimumSize(new Dimension(512, 256));
		
	}
}

