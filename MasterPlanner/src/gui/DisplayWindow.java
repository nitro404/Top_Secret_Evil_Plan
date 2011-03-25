package gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.*;
import planner.*;
import shared.*;

public class DisplayWindow extends JFrame implements WindowListener, Updatable {
	
	private DisplayPanel m_displayPanel;
	private JScrollPane m_displayScrollPane;
	private AutomaticUpdater m_updater;
	
	private static final long serialVersionUID = 1L;
	
	public DisplayWindow() {
		setTitle("Display Window");
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		int scrollBarWidth = 17;
		try { scrollBarWidth = Integer.parseInt(UIManager.getDefaults().get("ScrollBar.width").toString()); }
		catch(NumberFormatException e) { }
		setSize(Position.MAX_X + scrollBarWidth, screenHeight < Position.MAX_Y ? screenHeight : Position.MAX_Y);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(this);
		
		m_updater = new AutomaticUpdater(250);
		m_updater.setTarget(this);
		m_updater.start();
		
		initComponents();
	}
	
	public void initComponents() {
		m_displayPanel = new DisplayPanel();
		m_displayScrollPane = new JScrollPane(m_displayPanel);
		add(m_displayScrollPane);
	}

	public void setVisible(boolean visiblity){
		super.setVisible(visiblity);
		
		setSize(getWidth() + getInsets().left + getInsets().right, getHeight());
		SystemManager.debugWindow.setLocation(SystemManager.debugWindow.getX() + getInsets().left + getInsets().right, SystemManager.debugWindow.getY());
	}
	
	public void setTrackerImage(byte trackerNumber, BufferedImage trackerImage) {
		m_displayPanel.setTrackerImage(trackerNumber, trackerImage);
	}
	
	public void windowActivated(WindowEvent e) { }
	public void windowClosed(WindowEvent e) { }
	public void windowDeactivated(WindowEvent e) { }
	public void windowDeiconified(WindowEvent e) { }
	public void windowIconified(WindowEvent e) { }
	public void windowOpened(WindowEvent e) { }
	
	public void windowClosing(WindowEvent e){
		SystemManager.settings.save();
		dispose();
	}
	
	public void update() {
		try { repaint(); }
		catch(Exception e) { }
	}
	
}
