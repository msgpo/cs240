package client;

import java.awt.*;
import javax.swing.*;

public class SearchGui {

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(
					"com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		}
		catch(Exception e){
			// oops
			return;
		}

		EventQueue.invokeLater(
				new Runnable() {
					public void run() {
						SearchFrame frame = new SearchFrame();
						frame.setVisible(true);
					}
				}
		);
	}

}

