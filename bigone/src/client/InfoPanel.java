package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class InfoPanel extends JPanel{
	
	public InfoPanel(ActionListener al){
		new JPanel();

		JTabbedPane tabs = new JTabbedPane();

		JPanel helpPanel = new JPanel();
		JTextArea helpArea = new JTextArea(40, 40);
		helpPanel.add(helpArea);
	//	helpPanel.setMinimumSize(new Dimension(512, 256));

		JPanel navPanel = new JPanel();
	//	navPanel.setMinimumSize(new Dimension(512, 256));

		tabs.addTab("Field Help", helpPanel);
		tabs.addTab("Image Navigation", navPanel);
		this.add(tabs);
	//	this.setMinimumSize(new Dimension(512, 256));
		
	}
}

