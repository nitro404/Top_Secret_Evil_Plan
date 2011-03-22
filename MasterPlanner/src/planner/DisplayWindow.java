package planner;

import javax.swing.*;

import shared.Constants;

public class DisplayWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public DisplayWindow() {
		setTitle("Display Window");
		setSize(Constants.MAX_X, Constants.MAX_Y);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
}
