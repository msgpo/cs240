package client;

import shared.model.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;


public class BatchDialog extends JDialog
		implements ActionListener{
	
	private JComboBox options;
	private JButton view;
	private JButton cancel;
	private JButton download;
	private ArrayList<project> lproj;

	public BatchDialog(ArrayList<project> p){
		
		lproj = p;
		this.setLayout(new BoxLayout(getContentPane(),
				BoxLayout.PAGE_AXIS));

		String[] projOptions = new String[lproj.size()];
		int index = 0;
		for(project t : lproj){
			projOptions[index] = t.getTitle();
			index++;
		}
		options = new JComboBox(projOptions);
		
		view = new JButton("View Sample");
		view.setActionCommand("view");
		view.addActionListener(this);
		cancel = new JButton("Cancel");
		cancel.setActionCommand("cancel");
		cancel.addActionListener(this);
		download = new JButton("Download");
		download.setActionCommand("download");
		download.addActionListener(this);

		JPanel selection = new JPanel();
		selection.setLayout(new BoxLayout(selection,
				BoxLayout.LINE_AXIS));
		selection.add(new JLabel("Project:"));
		selection.add(Box.createHorizontalGlue());
		selection.add(options);
		selection.add(Box.createHorizontalGlue());
		selection.add(view);

		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons,
				BoxLayout.LINE_AXIS));
		buttons.add(Box.createHorizontalGlue());
		buttons.add(cancel);
		buttons.add(Box.createRigidArea(new Dimension(10, 0)));
		buttons.add(download);
		buttons.add(Box.createHorizontalGlue());

		this.add(selection);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(buttons);
		
		this.pack();
		this.setModal(true);
		this.setResizable(false);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("cancel")){
			// hide the dialog
			this.setVisible(false);
		}
		if(e.getActionCommand().equals("view")){
			// get & view sample
			String choice = (String) 
					options.getModel().getSelectedItem();
			System.out.println("Getting image for " + choice);
			int id = 0;
			for(project t : lproj){
				if(t.getTitle().equals(choice)){
					id = t.getID();
				}
			}
			// now actually get that from the server
			try{
				Image img = facade.getSample(id);
				new ImageDialog(img);
			}
			catch(Exception ex){
				JOptionPane.showMessageDialog(this,
						"Can't get image\n" +
						ex.getMessage(),
						"Problem!",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		if(e.getActionCommand().equals("download")){
			String choice = (String) 
			options.getModel().getSelectedItem();
			System.out.println("Getting batch for " + choice);
			try{
				int id = 0;
				for(project t : lproj){
					if(t.getTitle().equals(choice)){
						id = t.getID();
					}
				}
				facade.getBatch(id);
				// close window if all successful
				this.setVisible(false);
			}
			catch(Exception ex){
				System.out.println(ex);
				JOptionPane.showMessageDialog(this,
						"No batch for you!",
						"Problem!",
						JOptionPane.ERROR_MESSAGE);
			}
		}	
	}

	private class ImageDialog extends JDialog
			implements ActionListener{
		private JButton close;
		
		public ImageDialog(Image i){
			this.setLayout(new BoxLayout(getContentPane(),
				BoxLayout.PAGE_AXIS));
			close = new JButton("close");
			close.setActionCommand("close");
			close.addActionListener(this);
			this.add(new JLabel(new ImageIcon(i)));
			this.add(Box.createRigidArea(new Dimension(0,5)));
			this.add(close);
			this.pack();
			this.setModal(true);
			this.setResizable(false);
			this.setVisible(true);
		}	
		
		public void actionPerformed(ActionEvent e){
			if(e.getActionCommand().equals("close")){
				this.setVisible(false);
			}
		}
	}

}

