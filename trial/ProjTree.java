package client;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.util.*;
import shared.model.*;

public class ProjTree extends JTree {
	
	ProjModel model;

	public	ProjTree(){

		DefaultMutableTreeNode root =
				new DefaultMutableTreeNode("Projects");
		model = new DefaultTreeModel(root);
		model.addTreeModelListener(new ProjModelListener());		
		new JTree(model);
		this.setEditable(false);
	}
	
	public HashSet<field> getFields(){
		return model.getFields();
	}


}
