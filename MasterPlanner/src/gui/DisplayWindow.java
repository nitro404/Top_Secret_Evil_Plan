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
	
	private boolean m_resized;
	
	private static final long serialVersionUID = 1L;
	
	public DisplayWindow() {
		setTitle("Display Window");
		setMinimumSize(new Dimension(320, 240));
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		int scrollBarWidth = 17;
		try { scrollBarWidth = Integer.parseInt(UIManager.getDefaults().get("ScrollBar.width").toString()); }
		catch(NumberFormatException e) { }
		setSize(Position.getMaxX() + scrollBarWidth, screenHeight < Position.getMaxY() ? screenHeight : Position.getMaxY());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(this);
		
		m_updater = new AutomaticUpdater(33);
		m_updater.setTarget(this);
		m_updater.start();
		
		m_resized = false;
		
		initComponents();
	}
	
	private void initComponents() {
		m_displayPanel = new DisplayPanel();
		m_displayScrollPane = new JScrollPane(m_displayPanel);
		add(m_displayScrollPane);
	}
	
	public byte getEditMode() { return m_displayPanel.getEditMode(); }
	
	public boolean setEditMode(byte editMode) { return m_displayPanel.setEditMode(editMode); }
	
	public void setVisible(boolean visiblity){
		super.setVisible(visiblity);
		
		if(!m_resized) {
			setSize(getWidth() + getInsets().left + getInsets().right, getHeight());
			SystemManager.plannerWindow.setLocation(SystemManager.plannerWindow.getX() + getInsets().left + getInsets().right, SystemManager.plannerWindow.getY());
			m_resized = true;
		}
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
	
	public void windowClosing(WindowEvent e) {
		if(e.getSource() == this) {
			if(SystemManager.settings.getAutoSaveOnExit()) {
				SystemManager.pathSystem.writeTo(SystemManager.settings.getPathDataFileName());
				SystemManager.taskManager.writeTo(SystemManager.settings.getTaskListFileName());
				SystemManager.settings.save();
			}
			dispose();
		}
	}
	
	public void update() {
		try { repaint(); }
		catch(Exception e) { }
	}
	
}
