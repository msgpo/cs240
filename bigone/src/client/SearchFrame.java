package client;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

public class SearchFrame extends JFrame {

	communicator comm;


	public SearchFrame(){
		new JFrame("Record Indexing Search Tool");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ProjTreePanel projTree = new ProjTreePanel();
		
		this.getContentPane().add(projTree, BorderLayout.WEST);
		
	}

}
