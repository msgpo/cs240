package client;

import java.awt.*;
import javax.swing.*;

public class EntryPanel extends JTabbedPane{
	
	public EntryPanel(){
	 	new JTabbedPane();

		JPanel tablePanel = new JPanel();
		tablePanel.setMinimumSize(new Dimension(512, 256));
		JPanel formPanel = new JPanel();
		formPanel.setMinimumSize(new Dimension(512, 256));

		this.addTab("Table Entry", tablePanel);
		this.addTab("Form Entry", formPanel);
		this.setMinimumSize(new Dimension(512, 256));
	}
}

