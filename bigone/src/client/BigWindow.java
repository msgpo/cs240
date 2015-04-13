package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import shared.model.*;

public class BigWindow extends JFrame
		implements ActionListener{

	EventListenerList actionListeners = new EventListenerList();
	ImagePanel imgPanel;
	ButtonBar buttons;
	MenuPanel menu;
	EntryPanel entry;
	InfoPanel info;

	/*
	* creates a BigWindow
	*/
	public BigWindow(){
		imgPanel = new ImagePanel();
		buttons = new ButtonBar(this);
		menu = new MenuPanel(this);
		entry = new EntryPanel();
		info = new InfoPanel();

		this.setTitle("Indexing by RT Hatfield :: Spring 2015");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		this.add(menu);
		this.add(buttons);
		
		JSplitPane indexingPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				imgPanel, info);
		//indexingPane.setDividerLocation(430);
		this.add(indexingPane);
		this.pack();

	}
	
	
	/*
	* set up an ActionListener, for toggling visibility
	* @param a a listener
	*/
	public void addActionListener(ActionListener a){
		actionListeners.add(ActionListener.class, a);
	}

	/*
	* remove an ActionListener
	* @param a listener to remove
	*/ 
	public void removeActionListener(ActionListener a){
		actionListeners.remove(ActionListener.class, a);
	}

	/*
	* propagate an ActionEvent
	* @param a an event to propagate
	*/
	protected void fire(ActionEvent e){
		Object[] listeners = actionListeners.getListenerList();
		int numListeners = listeners.length;
		for(int i = 0; i<numListeners; i+=2){
			if(listeners[i]==ActionListener.class){
				((ActionListener)listeners[i+1]).actionPerformed(e);
			}
		}
	}

	/*
	*  action handler
	* @param e an ActionEvent to deal with
	*/
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("exit")){
			// exit
			System.out.println("Exit called from main window");
			System.exit(0);		
		}
		if(e.getActionCommand().equals("logout")){
			// save batch etc. (TODO)
			// then show login
			fire(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
					"show login"));

		}
		if(e.getActionCommand().equals("download batch")){
			ArrayList<project> projects = facade.getBatchInfo();
			new BatchDialog(projects);
		}
		if(e.getActionCommand().equals("save")){
			facade.save();
		}

	}


}
