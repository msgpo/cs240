package client;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import shared.model.*;

public class ProjTreePanel extends JPanel 
		implements TreeSelectionListener {

	private ProjTree tree;
	private HashSet<field> fieldSet;

	public	ProjTreePanel(){
		tree = new ProjTree();
		tree.addTreeSelectionListener(this);
		JScrollPane projTree = new JScrollPane(tree);
		projTree.setMinimumSize(new Dimension(100,50));
		this.add(projTree);

	}

	// for the Selection Listener:
	@Override
	public void valueChanged(TreeSelectionEvent e){
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
				tree.getLastSelectedPathComponent();

		if(node == null){
			return;
			// do nothing.  when would this happen?
		}

		Object nodeData = node.getUserObject();

		if(node.isLeaf()){
			fieldSet.add((field) nodeData)	
		}
		else {
			// ignore.  we don't want projects included
		}

	}

	public HashSet<field> getFields(){
		return tree.getFields()
	}
		

}
