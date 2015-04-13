package withmoreevents;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;


@SuppressWarnings("serial")
public class DrawingFrame extends JFrame {

	private DrawingComponent component;
	
	public DrawingFrame() {
		this.setTitle("Drawing");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.addWindowListener(windowAdapter);
		this.addWindowFocusListener(windowAdapter);
		this.addWindowStateListener(windowAdapter);
		
		component = new DrawingComponent();
		this.add(component, BorderLayout.CENTER);
		
		this.setLocation(100, 100);

		//this.setSize(650, 300);
		this.pack();
	}
	
	private WindowAdapter windowAdapter = new WindowAdapter() {

		@Override
		public void windowActivated(WindowEvent e) {
			return;
		}

		@Override
		public void windowClosed(WindowEvent e) {
			return;
		}

		@Override
		public void windowClosing(WindowEvent e) {
			return;
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			return;
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			return;
		}

		@Override
		public void windowGainedFocus(WindowEvent e) {
			component.requestFocusInWindow();
		}

		@Override
		public void windowIconified(WindowEvent e) {
			return;
		}

		@Override
		public void windowLostFocus(WindowEvent e) {
			return;
		}

		@Override
		public void windowOpened(WindowEvent e) {
			return;
		}

		@Override
		public void windowStateChanged(WindowEvent e) {
			return;
		}
		
	};
	
}
