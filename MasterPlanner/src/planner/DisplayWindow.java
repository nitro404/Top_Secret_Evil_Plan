package planner;

import javax.swing.*;
import shared.*;

public class DisplayWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public DisplayWindow() {
		setTitle("Display Window");
		setSize(Position.MAX_X, Position.MAX_Y);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
}
