package withmoreevents;

import java.awt.*;

public class Drawing {

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {		
			public void run() {
				DrawingFrame frame = new DrawingFrame();
				frame.setVisible(true);
			}
		});

	}

}
