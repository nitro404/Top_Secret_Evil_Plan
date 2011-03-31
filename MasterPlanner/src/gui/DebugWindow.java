package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import planner.*;
import settings.*;
import shared.*;

public class DebugWindow extends JFrame implements ActionListener, WindowListener, Updatable {
	
	private JTextArea m_consoleText;
	private Font m_consoleFont;
	private JScrollPane m_consoleScrollPane;
	
	private boolean m_updating;
	
	private JMenuBar m_menuBar;
	
	private JMenu m_fileMenu;
	private JMenuItem m_fileConnectMenuItem;
	private JMenuItem m_fileDisconnectMenuItem;
	private JMenuItem m_fileStartSimulationMenuItem;
	private JMenuItem m_fileExitMenuItem;
    
	private JMenu m_editMenu;
	private JMenu m_editModeMenu;
	private JRadioButtonMenuItem[] m_editModeMenuItem;
	private ButtonGroup m_editModeButtonGroup;
	private JMenu m_editColourMenu;
	private JMenuItem m_editColourSelectedMenuItem;
	private JMenuItem m_editColourMissingMenuItem;
	private JMenuItem m_editColourVertexMenuItem;
	private JMenuItem m_editColourEdgeMenuItem;
	private JMenuItem m_editColourRobotMenuItem;
	private JMenuItem m_editColourBlockMenuItem;
	private JMenuItem m_editColourPotMenuItem;
	private JMenuItem m_editColourResetAllMenuItem;
	private JMenuItem m_editUpdateTrackerImageMenuItem;
	
	private JMenu m_settingsMenu;
	private JCheckBoxMenuItem m_settingsAutoConnectOnStartupMenuItem;
	private JCheckBoxMenuItem m_settingsTakeWebcamSnapshotOnStartupMenuItem;
	private JCheckBoxMenuItem m_useStaticStationImagesMenuItem;
	private JMenuItem m_staticStationImageFileNameFormatMenuItem;
	private JCheckBoxMenuItem m_settingsAutoScrollConsoleWindowMenuItem;
	private JMenu m_settingsSignalsMenu;
	private JRadioButtonMenuItem[] m_settingsSignalsMenuItem;
	private ButtonGroup m_settingsSignalsButtonGroup;
	private JMenuItem m_settingsNumberOfTrackersMenuItem;
	private JMenuItem m_settingsTimeLimitMenuItem;
	private JMenuItem m_settingsWebcamResolutionMenuItem;
	private JMenuItem m_settingsSaveMenuItem;
	
	private JMenu m_helpMenu;
	private JMenuItem m_helpAboutMenuItem;
	
	private static final long serialVersionUID = 1L;
	
	public DebugWindow() {
		setTitle("Debug Window");
		setSize(640, 800);
		setMinimumSize(new Dimension(320, 240));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(this);
		
		m_updating = false;
		
		initMenu();
		initComponents();
	}
	
	private void initMenu() {
	    m_menuBar = new JMenuBar();
        
        m_fileMenu = new JMenu("File");
        m_fileConnectMenuItem = new JMenuItem("Connect");
    	m_fileDisconnectMenuItem = new JMenuItem("Disconnect");
        m_fileStartSimulationMenuItem = new JMenuItem("Start Simulation");
        m_fileExitMenuItem = new JMenuItem("Exit");
        
        m_editMenu = new JMenu("Edit");
    	m_editModeMenu = new JMenu("Editing Mode");
    	m_editModeMenuItem = new JRadioButtonMenuItem[EditMode.displayEditModes.length];
    	for(int i=0;i<EditMode.displayEditModes.length;i++) {
    		m_editModeMenuItem[i] = new JRadioButtonMenuItem(EditMode.displayEditModes[i]);
    	}
    	m_editColourMenu = new JMenu("Colours");
    	m_editColourSelectedMenuItem = new JMenuItem("Selected Colour");
    	m_editColourMissingMenuItem = new JMenuItem("Missing Colour");
    	m_editColourVertexMenuItem = new JMenuItem("Vertex Colour");
    	m_editColourEdgeMenuItem = new JMenuItem("Edge Colour");
    	m_editColourRobotMenuItem = new JMenuItem("Robot Colour");
    	m_editColourBlockMenuItem = new JMenuItem("Block Colour");
    	m_editColourPotMenuItem = new JMenuItem("Pot Colour");
    	m_editColourResetAllMenuItem = new JMenuItem("Reset All Colours");
    	m_editUpdateTrackerImageMenuItem = new JMenuItem("Update Tracker Image");
        
        m_settingsMenu = new JMenu("Settings");
        m_settingsAutoConnectOnStartupMenuItem = new JCheckBoxMenuItem("Auto-connect on Startup");
        m_settingsTakeWebcamSnapshotOnStartupMenuItem = new JCheckBoxMenuItem("Take Webcam Snapshot on Startup");
        m_useStaticStationImagesMenuItem = new JCheckBoxMenuItem("Use Static Station Images");
    	m_staticStationImageFileNameFormatMenuItem = new JMenuItem("Static Station Image File Name Format");
        m_settingsAutoScrollConsoleWindowMenuItem = new JCheckBoxMenuItem("Auto-scroll Console Window");
        m_settingsSignalsMenu = new JMenu("Signal Debugging");
        m_settingsSignalsMenuItem = new JRadioButtonMenuItem[SignalDebugLevel.signalDebugLevels.length];
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	m_settingsSignalsMenuItem[i] = new JRadioButtonMenuItem(SignalDebugLevel.signalDebugLevels[i]); 
        }
        m_settingsNumberOfTrackersMenuItem = new JMenuItem("Number of Trackers");
    	m_settingsTimeLimitMenuItem = new JMenuItem("Time Limit");
    	m_settingsWebcamResolutionMenuItem = new JMenuItem("Webcam Resolution");
        m_settingsSaveMenuItem = new JMenuItem("Save Settings");
        
        m_helpMenu = new JMenu("Help");
        m_helpAboutMenuItem = new JMenuItem("About");
        
        m_settingsAutoConnectOnStartupMenuItem.setSelected(SettingsManager.defaultAutoConnectOnStartup);
        m_settingsTakeWebcamSnapshotOnStartupMenuItem.setSelected(SettingsManager.defaultTakeWebcamSnapshotOnStartup);
        m_useStaticStationImagesMenuItem.setSelected(SettingsManager.defaultUseStaticStationImages);
        m_settingsAutoScrollConsoleWindowMenuItem.setSelected(SettingsManager.defaultAutoScrollConsoleWindow);
        m_editModeMenuItem[0].setSelected(true);
        m_settingsSignalsMenuItem[0].setSelected(true);
		m_fileDisconnectMenuItem.setEnabled(false);
		m_fileStartSimulationMenuItem.setEnabled(false);
        
        m_editModeButtonGroup = new ButtonGroup();
        m_settingsSignalsButtonGroup = new ButtonGroup();
        
        m_fileConnectMenuItem.addActionListener(this);
        m_fileDisconnectMenuItem.addActionListener(this);
        m_fileStartSimulationMenuItem.addActionListener(this);
        m_fileExitMenuItem.addActionListener(this);
        for(byte i=0;i<EditMode.displayEditModes.length;i++) {
        	m_editModeMenuItem[i].addActionListener(this);
        }
    	m_editColourSelectedMenuItem.addActionListener(this);
    	m_editColourMissingMenuItem.addActionListener(this);
    	m_editColourVertexMenuItem.addActionListener(this);
    	m_editColourEdgeMenuItem.addActionListener(this);
    	m_editColourRobotMenuItem.addActionListener(this);
    	m_editColourBlockMenuItem.addActionListener(this);
    	m_editColourPotMenuItem.addActionListener(this);
    	m_editColourResetAllMenuItem.addActionListener(this);
        m_editUpdateTrackerImageMenuItem.addActionListener(this);
        m_settingsAutoConnectOnStartupMenuItem.addActionListener(this);
        m_settingsTakeWebcamSnapshotOnStartupMenuItem.addActionListener(this);
        m_useStaticStationImagesMenuItem.addActionListener(this);
    	m_staticStationImageFileNameFormatMenuItem.addActionListener(this);
        m_settingsAutoScrollConsoleWindowMenuItem.addActionListener(this);
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	m_settingsSignalsMenuItem[i].addActionListener(this);
        }
        m_settingsNumberOfTrackersMenuItem.addActionListener(this);
    	m_settingsTimeLimitMenuItem.addActionListener(this);
    	m_settingsWebcamResolutionMenuItem.addActionListener(this);
        m_settingsSaveMenuItem.addActionListener(this);
        m_helpAboutMenuItem.addActionListener(this);
        
        for(byte i=0;i<EditMode.displayEditModes.length;i++) {
        	m_editModeButtonGroup.add(m_editModeMenuItem[i]);
        }
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	m_settingsSignalsButtonGroup.add(m_settingsSignalsMenuItem[i]);
        }
        
        m_fileMenu.add(m_fileConnectMenuItem);
        m_fileMenu.add(m_fileDisconnectMenuItem);
        m_fileMenu.add(m_fileStartSimulationMenuItem);
        m_fileMenu.add(m_fileExitMenuItem);
        
        m_editMenu.add(m_editModeMenu);
        for(byte i=0;i<EditMode.displayEditModes.length;i++) {
        	m_editModeMenu.add(m_editModeMenuItem[i]);
        }
		m_editColourMenu.add(m_editColourSelectedMenuItem);
		m_editColourMenu.add(m_editColourMissingMenuItem);
		m_editColourMenu.add(m_editColourVertexMenuItem);
		m_editColourMenu.add(m_editColourEdgeMenuItem);
		m_editColourMenu.add(m_editColourRobotMenuItem);
		m_editColourMenu.add(m_editColourBlockMenuItem);
		m_editColourMenu.add(m_editColourPotMenuItem);
		m_editColourMenu.add(m_editColourResetAllMenuItem);
        m_editMenu.add(m_editColourMenu);
        m_editMenu.add(m_editUpdateTrackerImageMenuItem);
        
        m_settingsMenu.add(m_settingsAutoConnectOnStartupMenuItem);
        m_settingsMenu.add(m_settingsTakeWebcamSnapshotOnStartupMenuItem);
        m_settingsMenu.add(m_useStaticStationImagesMenuItem);
    	m_settingsMenu.add(m_staticStationImageFileNameFormatMenuItem);
        m_settingsMenu.add(m_settingsAutoScrollConsoleWindowMenuItem);
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	m_settingsSignalsMenu.add(m_settingsSignalsMenuItem[i]);
        }
        m_settingsMenu.add(m_settingsSignalsMenu);
        m_settingsMenu.add(m_settingsNumberOfTrackersMenuItem);
        m_settingsMenu.add(m_settingsTimeLimitMenuItem);
        m_settingsMenu.add(m_settingsWebcamResolutionMenuItem);
        m_settingsMenu.add(m_settingsSaveMenuItem);
        
        m_helpMenu.add(m_helpAboutMenuItem);

        m_menuBar.add(m_fileMenu);
        m_menuBar.add(m_editMenu);
        m_menuBar.add(m_settingsMenu);
        m_menuBar.add(m_helpMenu);

        setJMenuBar(m_menuBar);
    }
	
	private void initComponents() {
		m_consoleText = new JTextArea();
        m_consoleFont = new Font("Verdana", Font.PLAIN, 14);
        m_consoleText.setFont(m_consoleFont);
        m_consoleText.setEditable(false);
        m_consoleScrollPane = new JScrollPane(m_consoleText);
        add(m_consoleScrollPane);
	}
	
	public void windowActivated(WindowEvent e) { }
	public void windowClosed(WindowEvent e) { }
	public void windowDeactivated(WindowEvent e) { }
	public void windowDeiconified(WindowEvent e) { }
	public void windowIconified(WindowEvent e) { }
	public void windowOpened(WindowEvent e) { }
	
	public void windowClosing(WindowEvent e) {
		SystemManager.settings.save();
		SystemManager.pathSystem.writeTo(SystemManager.settings.getPathDataFileName());
		dispose();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(m_updating) { return; }
		
		if(e.getSource() == m_fileConnectMenuItem) {
			SystemManager.client.connect();
			m_fileConnectMenuItem.setEnabled(!SystemManager.client.isConnected());
			m_fileDisconnectMenuItem.setEnabled(SystemManager.client.isConnected());
			m_fileStartSimulationMenuItem.setEnabled(SystemManager.client.isConnected());
			m_editModeMenu.setEnabled(!SystemManager.client.isConnected());
		}
		else if(e.getSource() == m_fileDisconnectMenuItem) {
			SystemManager.client.disconnect();
			m_fileConnectMenuItem.setEnabled(true);
			m_fileDisconnectMenuItem.setEnabled(false);
			m_fileStartSimulationMenuItem.setEnabled(false);
			m_editModeMenu.setEnabled(true);
		}
		else if(e.getSource() == m_fileStartSimulationMenuItem) {
			SystemManager.start();
		}
		else if(e.getSource() == m_fileExitMenuItem) {
			System.exit(0);
		}
		else if(e.getSource() == m_editColourSelectedMenuItem) {
			Color c = JColorChooser.showDialog(this, "Choose a colour for selected items:", SystemManager.settings.getSelectedColour());
			SystemManager.settings.setSelectedColour(c);
		}
		if(e.getSource() == m_editColourMissingMenuItem) {
			Color c = JColorChooser.showDialog(this, "Choose a colour for missing items:", SystemManager.settings.getMissingColour());
			SystemManager.settings.setMissingColour(c);
		}
		else if(e.getSource() == m_editColourVertexMenuItem) {
			Color c = JColorChooser.showDialog(this, "Choose a colour for vertices:", SystemManager.settings.getVertexColour());
			SystemManager.settings.setVertexColour(c);
		}
		else if(e.getSource() == m_editColourEdgeMenuItem) {
			Color c = JColorChooser.showDialog(this, "Choose a colour for edges:", SystemManager.settings.getEdgeColour());
			SystemManager.settings.setEdgeColour(c);
		}
		else if(e.getSource() == m_editColourRobotMenuItem) {
			Color c = JColorChooser.showDialog(this, "Choose a colour for robots:", SystemManager.settings.getRobotColour());
			SystemManager.settings.setRobotColour(c);
		}
		else if(e.getSource() == m_editColourBlockMenuItem) {
			Color c = JColorChooser.showDialog(this, "Choose a colour for blocks:", SystemManager.settings.getBlockColour());
			SystemManager.settings.setBlockColour(c);
		}
		else if(e.getSource() == m_editColourPotMenuItem) {
			Color c = JColorChooser.showDialog(this, "Choose a colour for pots:", SystemManager.settings.getPotColour());
			SystemManager.settings.setPotColour(c);
		}
		else if(e.getSource() == m_editColourResetAllMenuItem) {
			SystemManager.settings.resetAllColours();
		}
		else if(e.getSource() == m_editUpdateTrackerImageMenuItem) {
			SystemManager.updateLocalTrackerImage();
		}
		else if(e.getSource() == m_settingsAutoConnectOnStartupMenuItem) {
			SystemManager.settings.setAutoConnectOnStartup(m_settingsAutoConnectOnStartupMenuItem.isSelected());
		}
		else if(e.getSource() == m_settingsTakeWebcamSnapshotOnStartupMenuItem) {
			SystemManager.settings.setTakeWebcamSnapshotOnStartup(m_settingsTakeWebcamSnapshotOnStartupMenuItem.isSelected());
		}
		else if(e.getSource() == m_useStaticStationImagesMenuItem) {
			SystemManager.settings.setUseStaticStationImages(m_useStaticStationImagesMenuItem.isSelected());
		}
		else if(e.getSource() == m_staticStationImageFileNameFormatMenuItem) {
			String input = JOptionPane.showInputDialog(this, "Enter the file name format for static tracker images (ie. \"Station.jpg\":\n(Images will be read as \"Station X.jpg\" where X is the tracker number.", SystemManager.settings.getStaticStationImageFileNameFormat());
			if(input == null) { return; }
			SystemManager.settings.setStaticStationImageFileNameFormat(input);
		}
		else if(e.getSource() == m_settingsAutoScrollConsoleWindowMenuItem) {
			SystemManager.settings.setAutoScrollConsoleWindow(m_settingsAutoScrollConsoleWindowMenuItem.isSelected());
		}
		else if(e.getSource() == m_settingsNumberOfTrackersMenuItem) {
			String input = JOptionPane.showInputDialog(this, "Please enter the number of trackers:", SystemManager.settings.getNumberOfTrackers());
			if(input == null) { return; }
			
			int numberOfTrackers = -1;
			try {
				numberOfTrackers = Integer.parseInt(input);
			}
			catch(NumberFormatException e2) { }
			
			if(SystemManager.settings.setNumberOfTrackers(numberOfTrackers)) {
				JOptionPane.showMessageDialog(this, "Successfully changed number of trackers to " + SystemManager.settings.getNumberOfTrackers() + ".", "Number of Trackers Changed", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(this, "Failed to change number of trackers.", "Number of Trackers Change Failed", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getSource() == m_settingsTimeLimitMenuItem) {
			String input = JOptionPane.showInputDialog(this, "Please enter a new time limit (in minutes):", SystemManager.settings.getTimeLimit());
			if(input == null) { return; }
			
			int timeLimit = -1;
			try {
				timeLimit = Integer.parseInt(input);
			}
			catch(NumberFormatException e2) { }
			
			if(SystemManager.settings.setTimeLimit(timeLimit)) {
				JOptionPane.showMessageDialog(this, "Successfully changed time limit to " + SystemManager.settings.getTimeLimit() + " minutes.", "Time Limit Changed", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(this, "Failed to change time limit.", "Time Limit Change Failed", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getSource() == m_settingsWebcamResolutionMenuItem) {
			String input = JOptionPane.showInputDialog(this, "Please enter a webcam resolution in the form \"width, height\":", SystemManager.settings.getWebcamResolution().width + ", " + SystemManager.settings.getWebcamResolution().height);
			if(input == null) { return; }
			
			if(SystemManager.settings.setWebcamResolution(input)) {
				JOptionPane.showMessageDialog(this, "Webcam resolution successfully changed to " + SystemManager.settings.getWebcamResolution().width + "x" + SystemManager.settings.getWebcamResolution().height + ".\nPlease restart the program for changes to take full effect.", "Webcam Resolution Changed", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(this, "Failed to change webcam resolution.", "Webcam Resolution Change Failed", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getSource() == m_settingsSaveMenuItem) {
			SystemManager.settings.save();
		}
		else if(e.getSource() == m_helpAboutMenuItem) {
			JOptionPane.showMessageDialog(this, "MasterPlanner Created by Kevin Scroggins (nitro404@hotmail.com).\nCreated for the COMP 4807 Final Project - April 4, 2011.", "About MasterPlanner", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
	        	if(e.getSource() == m_settingsSignalsMenuItem[i]) {
	        		SystemManager.settings.setSignalDebugLevel(i);
	        		return;
	        	}
	        }
			
			for(byte i=0;i<EditMode.displayEditModes.length;i++) {
				if(e.getSource() == m_editModeMenuItem[i]) {
					SystemManager.displayWindow.setEditMode(i);
					return;
				}
			}
		}
	}
	
	public void update() {
		m_updating = true;
		
		m_editModeMenuItem[SystemManager.displayWindow.getEditMode()].setSelected(true);
		m_settingsSignalsMenuItem[SystemManager.settings.getSignalDebugLevel()].setSelected(true);
		m_settingsAutoConnectOnStartupMenuItem.setSelected(SystemManager.settings.getAutoConnectOnStartup());
        m_settingsTakeWebcamSnapshotOnStartupMenuItem.setSelected(SystemManager.settings.getTakeWebcamSnapshotOnStartup());
		m_useStaticStationImagesMenuItem.setSelected(SystemManager.settings.getUseStaticStationImages());
        m_settingsAutoScrollConsoleWindowMenuItem.setSelected(SystemManager.settings.getAutoScrollConsoleWindow());
		m_fileConnectMenuItem.setEnabled(!SystemManager.client.isConnected());
		m_fileDisconnectMenuItem.setEnabled(SystemManager.client.isConnected());
		m_fileStartSimulationMenuItem.setEnabled(SystemManager.client.isConnected());
		m_editModeMenu.setEnabled(!SystemManager.client.isConnected());
		
		try {
			m_consoleText.setText(SystemManager.console.toString());
			if(SystemManager.settings.getAutoScrollConsoleWindow()) {
				m_consoleText.setCaretPosition(m_consoleText.getText().length());
				m_consoleText.scrollRectToVisible(new Rectangle(0, m_consoleText.getHeight() - 2, 1, 1));
			}
		}
		catch(Exception e) { }
		
		m_updating = false;
	}
	
}
