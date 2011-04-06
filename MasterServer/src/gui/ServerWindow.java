package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import server.*;
import settings.*;
import shared.*;

public class ServerWindow extends JFrame implements ActionListener, WindowListener, Updatable {
	
	private JTextArea m_consoleText;
	private Font m_consoleFont;
	private JScrollPane m_consoleScrollPane;
	
	private boolean m_updating;
	
	private JMenuBar m_menuBar;
	
	private JMenu m_fileMenu;
	private JMenuItem m_fileExitMenuItem;
    
	private JMenu m_settingsMenu;
	private JCheckBoxMenuItem m_settingsAutoScrollConsoleWindowMenuItem;
	private JMenuItem m_settingsMaxConsoleHistoryMenuItem;
	private JMenu m_settingsSignalsMenu;
	private JCheckBoxMenuItem m_settingsSignalsIgnorePingPongMenuItem;
	private JCheckBoxMenuItem m_settingsSignalsIgnorePositionMenuItem;
	private JRadioButtonMenuItem[] m_settingsSignalsMenuItem;
	private ButtonGroup m_settingsSignalsButtonGroup;
	private JCheckBoxMenuItem m_settingsAutoSaveOnExitMenuItem;
	private JMenuItem m_settingsSaveMenuItem;
	private JMenuItem m_settingsResetMenuItem;
	
	private JMenu m_helpMenu;
	private JMenuItem helpAboutMenuItem;
	
	private static final long serialVersionUID = 1L;
	
	public ServerWindow() {
		setTitle("Server Window");
		setMinimumSize(new Dimension(320, 240));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(this);
		SystemManager.console.setTarget(this);
		
		m_updating = false;
		
		initMenu();
		initComponents();
	}
	
	public void initialize() {
		update();
		setVisible(true);
	}
	
	private void initMenu() {
		m_menuBar = new JMenuBar();
        
        m_fileMenu = new JMenu("File");
        m_fileExitMenuItem = new JMenuItem("Exit");
        
        m_settingsMenu = new JMenu("Settings");
        m_settingsAutoScrollConsoleWindowMenuItem = new JCheckBoxMenuItem("Auto-scroll Console Window");
        m_settingsSignalsMenu = new JMenu("Signal Debugging");
        m_settingsSignalsIgnorePingPongMenuItem = new JCheckBoxMenuItem("Ignore Ping Pong Signals");
        m_settingsSignalsIgnorePositionMenuItem = new JCheckBoxMenuItem("Ignore Position Signals");
        m_settingsSignalsMenuItem = new JRadioButtonMenuItem[SignalDebugLevel.signalDebugLevels.length];
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	m_settingsSignalsMenuItem[i] = new JRadioButtonMenuItem(SignalDebugLevel.signalDebugLevels[i]); 
        }
        m_settingsAutoSaveOnExitMenuItem = new JCheckBoxMenuItem("Autosave on Exit");
        m_settingsSaveMenuItem = new JMenuItem("Save Settings");
    	m_settingsResetMenuItem = new JMenuItem("Reset Settings");
        
        m_helpMenu = new JMenu("Help");
        helpAboutMenuItem = new JMenuItem("About");
        
        m_settingsAutoSaveOnExitMenuItem.setSelected(SettingsManager.defaultAutoSaveOnExit);
        m_settingsAutoScrollConsoleWindowMenuItem.setSelected(SettingsManager.defaultAutoScrollConsoleWindow);
        m_settingsMaxConsoleHistoryMenuItem = new JMenuItem("Max Console History");
        m_settingsSignalsIgnorePingPongMenuItem.setSelected(SettingsManager.defaultIgnorePingPongSignals);
        m_settingsSignalsIgnorePositionMenuItem.setSelected(SettingsManager.defaultIgnorePositionSignals);
        m_settingsSignalsMenuItem[SettingsManager.defaultSignalDebugLevel].setSelected(true);
        
        m_settingsSignalsButtonGroup = new ButtonGroup();
        
        m_fileExitMenuItem.addActionListener(this);
        m_settingsAutoScrollConsoleWindowMenuItem.addActionListener(this);
        m_settingsMaxConsoleHistoryMenuItem.addActionListener(this);
        m_settingsSignalsIgnorePingPongMenuItem.addActionListener(this);
        m_settingsSignalsIgnorePositionMenuItem.addActionListener(this);
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	m_settingsSignalsMenuItem[i].addActionListener(this);
        }
        m_settingsAutoSaveOnExitMenuItem.addActionListener(this);
        m_settingsSaveMenuItem.addActionListener(this);
    	m_settingsResetMenuItem.addActionListener(this);
        helpAboutMenuItem.addActionListener(this);
        
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	m_settingsSignalsButtonGroup.add(m_settingsSignalsMenuItem[i]);
        }
        
        m_fileMenu.add(m_fileExitMenuItem);
        
        m_settingsMenu.add(m_settingsAutoScrollConsoleWindowMenuItem);
        m_settingsMenu.add(m_settingsMaxConsoleHistoryMenuItem);
        m_settingsSignalsMenu.add(m_settingsSignalsIgnorePingPongMenuItem);
        m_settingsSignalsMenu.add(m_settingsSignalsIgnorePositionMenuItem);
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	m_settingsSignalsMenu.add(m_settingsSignalsMenuItem[i]);
        }
        m_settingsMenu.add(m_settingsSignalsMenu);
        m_settingsMenu.add(m_settingsAutoSaveOnExitMenuItem);
        m_settingsMenu.add(m_settingsSaveMenuItem);
        m_settingsMenu.add(m_settingsResetMenuItem);
        
        m_helpMenu.add(helpAboutMenuItem);

        m_menuBar.add(m_fileMenu);
        m_menuBar.add(m_settingsMenu);
        m_menuBar.add(m_helpMenu);

        setJMenuBar(m_menuBar);
	}
	
	private void initComponents() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Master Server");
        setCursor(new java.awt.Cursor(Cursor.DEFAULT_CURSOR));
        setMinimumSize(new Dimension(640, 480));
        setName("ServerWindow");
        
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
		if(SystemManager.settings.getAutoSaveOnExit()) {
			SystemManager.save();
		}
		dispose();
	}
	

	public void actionPerformed(ActionEvent e) {
		if(m_updating) { return; }
		
		if(e.getSource() == m_fileExitMenuItem) {
			System.exit(0);
		}
		else if(e.getSource() == m_settingsAutoScrollConsoleWindowMenuItem) {
			SystemManager.settings.setAutoScrollConsoleWindow(m_settingsAutoScrollConsoleWindowMenuItem.isSelected());
		}
		else if(e.getSource() == m_settingsMaxConsoleHistoryMenuItem) {
			String input = JOptionPane.showInputDialog(this, "Please enter the maximum console history size:", SystemManager.settings.getMaxConsoleHistory());
			if(input == null) { return; }
			
			int maxConsoleHistory = -1;
			try {
				maxConsoleHistory = Integer.parseInt(input);
			}
			catch(NumberFormatException e2) { }
			
			SystemManager.settings.setMaxConsoleHistory(maxConsoleHistory);
		}
		else if(e.getSource() == m_settingsSignalsIgnorePingPongMenuItem) {
			SystemManager.settings.setIgnorePingPongSignals(m_settingsSignalsIgnorePingPongMenuItem.isSelected());
		}
		else if(e.getSource() == m_settingsSignalsIgnorePositionMenuItem) {
			SystemManager.settings.setIgnorePositionSignals(m_settingsSignalsIgnorePositionMenuItem.isSelected());
		}
		else if(e.getSource() == m_settingsAutoSaveOnExitMenuItem) {
			SystemManager.settings.setAutoSaveOnExit(m_settingsAutoSaveOnExitMenuItem.isSelected());
		}
		else if(e.getSource() == m_settingsSaveMenuItem) {
			SystemManager.settings.save();
		}
		else if(e.getSource() == m_settingsResetMenuItem) {
			SystemManager.settings.reset();
		}
		else if(e.getSource() == helpAboutMenuItem) {
			JOptionPane.showMessageDialog(this, "MasterServer Created by Kevin Scroggins (nitro404@hotmail.com).\nCreated for the COMP 4807 Final Project - April 4, 2011.", "About MasterServer", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
	        	if(e.getSource() == m_settingsSignalsMenuItem[i]) {
	        		SystemManager.settings.setSignalDebugLevel(i);
	        	}
	        }
		}
	}
	
	public void update() {
		m_updating = true;
		
		m_settingsSignalsIgnorePingPongMenuItem.setSelected(SystemManager.settings.getIgnorePingPongSignals());
		m_settingsSignalsIgnorePositionMenuItem.setSelected(SystemManager.settings.getIgnorePositionSignals());
		m_settingsSignalsMenuItem[SystemManager.settings.getSignalDebugLevel()].setSelected(true);
		m_settingsAutoScrollConsoleWindowMenuItem.setSelected(SystemManager.settings.getAutoScrollConsoleWindow());
		
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
