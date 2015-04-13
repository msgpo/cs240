package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import shared.communication.authToken;

public class LoginWindow extends JFrame 
		implements ActionListener{
	
	JPasswordField passBox;
	JTextField nameBox;
	EventListenerList actionListeners = new EventListenerList();
	
	/*
	* creates a new LoginWindow
	*/
	public LoginWindow() {
		this.setTitle("Login");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		passBox = new JPasswordField(20);
		nameBox = new JTextField(20);
		JPanel fields = new JPanel();
		fields.setLayout(new BoxLayout(fields, BoxLayout.PAGE_AXIS));

		JPanel name = new JPanel();
		name.setLayout(new BoxLayout(name, BoxLayout.LINE_AXIS));
		name.add(new JLabel("Username"));
		name.add(Box.createRigidArea(new Dimension(0, 10)));
		name.add(nameBox);

		JPanel pass = new JPanel();
		pass.setLayout(new BoxLayout(pass, BoxLayout.LINE_AXIS));
		pass.add(new JLabel("Password"));
		pass.add(Box.createRigidArea(new Dimension(0, 10)));
		pass.add(passBox);
		
		fields.add(Box.createVerticalGlue());
		fields.add(name);
		fields.add(Box.createRigidArea(new Dimension(0, 10)));
		fields.add(pass);
		fields.add(Box.createVerticalGlue());
		this.add(fields, BorderLayout.CENTER);

		JButton go = new JButton("Login");
		go.setActionCommand("go");
		JButton exit = new JButton("Exit");
		exit.setActionCommand("exit");

		go.addActionListener(this);
		exit.addActionListener(this);

		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
		buttons.add(Box.createHorizontalGlue());
		buttons.add(go);
		buttons.add(Box.createRigidArea(new Dimension(5, 0)));
		buttons.add(exit);
		buttons.add(Box.createHorizontalGlue());
		this.add(buttons, BorderLayout.SOUTH);
		
		this.setLocation(256, 512); // should work on most monitors
		this.setSize(512, 128); //determined by experiment
		this.setResizable(false);
	}

	/* 
	* ActionListener for the buttons
	* @param e an ActionEvent from a button
	*/
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("go")){
			// try to log in
			System.out.println("Login attempting");
			try {
				char[] passInput = passBox.getPassword();
				String password = new String(passInput);
				authToken aToken = facade.login(nameBox.getText(), password);
				// pop up welcome dialog
				passBox.setText("");
				nameBox.setText("");
				JOptionPane.showMessageDialog(this, 
						aToken.getMessage(),
						"Welcome to Indexing!",
						JOptionPane.PLAIN_MESSAGE);
				// open the main window
				fire(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
						"show main"));
			}
			catch(ClientException ce){
				passBox.setText("");
				nameBox.setText("");
				// pop up a dialog
				JOptionPane.showMessageDialog(this, "Bad name / password",
						"Problem!",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		if(e.getActionCommand().equals("exit")){
			// exit
			System.out.println("Exit called from login screen");
			System.exit(0);		
		}
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
}
