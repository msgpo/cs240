package client;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import shared.model.*;

public class BigWindow extends JFrame
		implements ActionListener,
			FacadeListener,
			IndexingListener{

	EventListenerList actionListeners = new EventListenerList();
	ImagePanel imgPanel;
	ButtonBar buttons;
	MenuPanel menu;
	EntryPanel entry;
	InfoPanel info;

	JSplitPane indexingPane;
	JSplitPane dataPane;

	BatchState bState;

	/*
	* creates a BigWindow
	*/
	public BigWindow(){
		
		// reset to plain BatchState
		bState = new BatchState("");

		imgPanel = new ImagePanel(this);
	//	imgPanel.setMinimumSize(new Dimension(1000,0));
	//	imgPanel.setResizeWeight(1.0);
		buttons = new ButtonBar(this);
		menu = new MenuPanel(this);
		entry = new EntryPanel(bState);
		info = new InfoPanel(this);

		this.setTitle("Indexing by RT Hatfield :: Spring 2015");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		JPanel controls = new JPanel();
		controls.setLayout(new BoxLayout(controls, BoxLayout.PAGE_AXIS));
		controls.add(menu);
		controls.add(buttons);
		controls.setMinimumSize(new Dimension(1024, 64));
		controls.setMaximumSize(new Dimension(1480, 64));
		this.add(controls);
		this.add(Box.createRigidArea(new Dimension(0,10)));
		
		dataPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				entry, info);
		dataPane.setResizeWeight(0.5);
		dataPane.setDividerLocation(0.5);
		dataPane.setMaximumSize(new Dimension(1480,512));
		dataPane.setMinimumSize(new Dimension(1000,264));
		dataPane.setPreferredSize(new Dimension(1000,264));

		JPanel indexingPanel = new JPanel();
		indexingPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				imgPanel, dataPane);
		indexingPane.setResizeWeight(0.6);
		indexingPanel.add(indexingPane);
		indexingPanel.setMinimumSize(new Dimension(1000,256));
		indexingPanel.setPreferredSize(new Dimension(1000,512));
		indexingPanel.setMaximumSize(new Dimension(1464,964));
		this.add(indexingPanel);
		this.add(Box.createRigidArea(new Dimension(0,10)));
		this.setMinimumSize(new Dimension(1024, 980));
		this.setMaximumSize(new Dimension(1480, 1000));
		this.pack();

	}
	
	// methods for having and being ActionListener
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
	*  action handler for ActionListener
	* @param e an ActionEvent to deal with
	*/
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("exit")){
			// exit
			System.out.println("Exit called from main window");
			System.exit(0);		
		}
		if(e.getActionCommand().equals("logout")){
			Point origin = this.getLocationOnScreen();
			bState.windowX = (int) origin.getX();
			bState.windowY = (int) origin.getY();
			bState.windowW = this.getWidth();
			bState.windowH = this.getHeight();
			bState.hsplit = indexingPane.getDividerLocation();
			bState.vsplit = dataPane.getDividerLocation();
			facade.save();
			// clear everything
			imgPanel.clear();
			// this batchstate will never actually be seen
			// then show login
			fire(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
					"show login"));

		}
		if(e.getActionCommand().equals("download batch")){
			ArrayList<project> projects = facade.getBatchInfo();
			new BatchDialog(projects);
		}
		if(e.getActionCommand().equals("save")){
			Point origin = this.getLocationOnScreen();
			bState.windowX = (int) origin.getX();
			bState.windowY = (int) origin.getY();
			bState.windowW = this.getWidth();
			bState.windowH = this.getHeight();
			bState.hsplit = indexingPane.getDividerLocation();
			bState.vsplit = dataPane.getDividerLocation();
			facade.save();
		}
		if(e.getActionCommand().equals("zin")){
			// zoom in
			imgPanel.zoomIn();
		}
		if(e.getActionCommand().equals("zout")){
			// zoom out
			imgPanel.zoomOut();	
		}
		if(e.getActionCommand().equals("invert")){
			// toggle inversion
			imgPanel.toggleInversion();
			bState.invert = !bState.invert;
		}
		if(e.getActionCommand().equals("highlight")){
			// toggle highlight
			if(bState.highlight){
				bState.highlight = false;
				imgPanel.clearGrid();
			}
			else {	
				// MUST be if - else to prevent accidental
				// fall-thru
				bState.highlight = true;
				imgPanel.highlight(bState.rows,
				bState.firstx, bState.firsty,
				bState.fieldHeight, bState.fields);
			}
		}
		if(e.getActionCommand().equals("submit")){
			// submit batch.
			try{
				facade.submit();
			}
			catch(Exception ex){
				JOptionPane.showMessageDialog(this,
						"Can't submit batch\n" +
						ex.getMessage(),
						"Problem!",
						JOptionPane.ERROR_MESSAGE);
			}
				
		}


	}
	
	//methods for FacadeListener
	public void imageChanged(BufferedImage i){
		imgPanel.setImage(i);
	}
	public void newBatch(BatchState bs){
		// extract info from BS and set up
		// the window etc.
		bState = bs;
		// window setup
		this.setLocation(bs.windowX, bs.windowY);
		this.setSize(bs.windowW, bs.windowH);
		indexingPane.setDividerLocation(bs.hsplit);
		dataPane.setDividerLocation(bs.vsplit);
		// image setup
		imgPanel.clear();
		imgPanel.setImage(bs.getImg());
		imgPanel.setZoom(bs.zoom);
		imgPanel.setPos(bState.offsetX, bState.offsetY);
		if(bs.invert){
			imgPanel.toggleInversion();
		}
		if(bs.highlight){
			imgPanel.highlight(bs.rows,
			bs.firstx, bs.firsty,
			bs.fieldHeight, bs.fields);
		}
		// indexing setup
		entry.init(bs);
	}

	//methods for IndexingListener
	public void zoomChanged(double z){
		bState.zoom = z;
	}
	public void scrollChanged(int x, int y){
		bState.offsetX = x;
		bState.offsetY = y;
	}


}


