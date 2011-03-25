package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
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
	
	public void setVisible(boolean visiblity){
		super.setVisible(visiblity);
		
		setSize(getWidth() + getInsets().left + getInsets().right, getHeight());
		SystemManager.debugWindow.setLocation(SystemManager.debugWindow.getX() + getInsets().left + getInsets().right, SystemManager.debugWindow.getY());
	}
	
	public void initComponents() {
		m_displayPanel = new DisplayPanel();
		m_displayScrollPane = new JScrollPane(m_displayPanel);
		add(m_displayScrollPane);
	}
	
	public boolean updateTrackerImage() {
		if(SystemManager.webcam.active()) {
			BufferedImage snapshot = SystemManager.webcam.capture();
			if(snapshot != null) {
				m_displayPanel.setTrackerImage(0, snapshot);
				m_displayPanel.setTrackerImage(1, snapshot);
				m_displayPanel.setTrackerImage(2, snapshot);
				SystemManager.webcam.stop();
				SystemManager.webcam.deallocate();
				return true;
			}
		}
		return false;
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
		repaint();
	}
	
}
