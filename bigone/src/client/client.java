package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class client  {

	public static BigWindow mainWindow;
	public static LoginWindow loginWindow;

	public static void main(String[] args){
		// run the client:
		// set up facade with the addy & port
		try {
	//		UIManager.setLookAndFeel(
	//				"com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		}
		catch(Exception e){
			// oops
			return;
		}
		try{
			if(args[0].equals("")){
				args[0] = "localhost";
			}
			facade.initialize(args[0], Integer.parseInt(args[1]));
		}
		catch(Exception e){
			System.out.println("Bad args");
			return;
		}
		
		// if address is "" use "localhost", duh
		// manage windows
		EventQueue.invokeLater(new inner());
	}

	private static class inner implements Runnable,
				ActionListener {
			public void run() {
				loginWindow = new LoginWindow();
				loginWindow.addActionListener(this);
				// display this window until login complete, 
				// then switch to mainWindow;
				mainWindow = new BigWindow();
				facade.addFacadeListener(mainWindow);
				mainWindow.addActionListener(this);
				loginWindow.setVisible(true);		
			}
			public void actionPerformed(ActionEvent e){
				if(e.getActionCommand().equals("show main")){
					loginWindow.setVisible(false);
					mainWindow.setVisible(true);
				}
				if(e.getActionCommand().equals("show login")){
					mainWindow.setVisible(false);
					loginWindow.setVisible(true);
				}
			}

		}; // inner class


}

