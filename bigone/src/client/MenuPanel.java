package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class MenuPanel extends JMenuBar {
	
	/*
	* creates a new MenuPanel
	* @param the actionlistener (ie BigWindow) to send notes to
	*/
	public MenuPanel(ActionListener al){
		// a very simple menu bar:
		new JMenuBar();
		JMenu menu = new JMenu("File");
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(menu);
		this.add(Box.createHorizontalGlue());
		this.setMinimumSize(new Dimension(1024, 24));

		// items in menu:
		JMenuItem downBatch = new JMenuItem("Download Batch");
		downBatch.addActionListener(al);
		downBatch.setActionCommand("download batch");
		menu.add(downBatch);
		JMenuItem logout = new JMenuItem("Logout");
		logout.addActionListener(al);
		logout.setActionCommand("logout");
		menu.add(logout);
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(al);
		exit.setActionCommand("exit");
		menu.add(exit);
	}

}

