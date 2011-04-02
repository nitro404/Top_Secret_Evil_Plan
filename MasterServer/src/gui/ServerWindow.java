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
	private JCheckBoxMenuItem m_settingsAutoScrollConsoleMenuWindowItem;
	private JMenu m_settingsSignalsMenu;
	private JCheckBoxMenuItem m_settingsSignalsIgnorePingPongMenuItem;
	private JRadioButtonMenuItem[] m_settingsSignalsMenuItem;
	private ButtonGroup m_settingsSignalsButtonGroup;
	private JCheckBoxMenuItem m_settingsAutoSaveOnExitMenuItem;
	private JMenuItem m_settingsSaveMenuItem;
	
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
        m_settingsAutoScrollConsoleMenuWindowItem = new JCheckBoxMenuItem("Auto-scroll Console Window");
        m_settingsSignalsMenu = new JMenu("Signal Debugging");
        m_settingsSignalsIgnorePingPongMenuItem = new JCheckBoxMenuItem("Ignore Ping Pong Signals");
        m_settingsSignalsMenuItem = new JRadioButtonMenuItem[SignalDebugLevel.signalDebugLevels.length];
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	m_settingsSignalsMenuItem[i] = new JRadioButtonMenuItem(SignalDebugLevel.signalDebugLevels[i]); 
        }
        m_settingsAutoSaveOnExitMenuItem = new JCheckBoxMenuItem("Autosave on Exit");
        m_settingsSaveMenuItem = new JMenuItem("Save Settings");
        
        m_helpMenu = new JMenu("Help");
        helpAboutMenuItem = new JMenuItem("About");
        
        m_settingsAutoSaveOnExitMenuItem.setSelected(SettingsManager.defaultAutoSaveOnExit);
        m_settingsAutoScrollConsoleMenuWindowItem.setSelected(SettingsManager.defaultAutoScrollConsoleWindow);
        m_settingsSignalsIgnorePingPongMenuItem.setSelected(SettingsManager.defaultIgnorePingPongSignals);
        m_settingsSignalsMenuItem[SettingsManager.defaultSignalDebugLevel].setSelected(true);
        
        m_settingsSignalsButtonGroup = new ButtonGroup();
        
        m_fileExitMenuItem.addActionListener(this);
        m_settingsAutoScrollConsoleMenuWindowItem.addActionListener(this);
        m_settingsSignalsIgnorePingPongMenuItem.addActionListener(this);
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	m_settingsSignalsMenuItem[i].addActionListener(this);
        }
        m_settingsAutoSaveOnExitMenuItem.addActionListener(this);
        m_settingsSaveMenuItem.addActionListener(this);
        helpAboutMenuItem.addActionListener(this);
        
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	m_settingsSignalsButtonGroup.add(m_settingsSignalsMenuItem[i]);
        }
        
        m_fileMenu.add(m_fileExitMenuItem);
        
        m_settingsMenu.add(m_settingsAutoScrollConsoleMenuWindowItem);
        m_settingsSignalsMenu.add(m_settingsSignalsIgnorePingPongMenuItem);
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	m_settingsSignalsMenu.add(m_settingsSignalsMenuItem[i]);
        }
        m_settingsMenu.add(m_settingsSignalsMenu);
        m_settingsMenu.add(m_settingsAutoSaveOnExitMenuItem);
        m_settingsMenu.add(m_settingsSaveMenuItem);
        
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
			SystemManager.settings.save();
		}
		dispose();
	}
	

	public void actionPerformed(ActionEvent e) {
		if(m_updating) { return; }
		
		if(e.getSource() == m_fileExitMenuItem) {
			System.exit(0);
		}
		else if(e.getSource() == m_settingsAutoScrollConsoleMenuWindowItem) {
			SystemManager.settings.setAutoScrollConsoleWindow(m_settingsAutoScrollConsoleMenuWindowItem.isSelected());
		}
		else if(e.getSource() == m_settingsSignalsIgnorePingPongMenuItem) {
			SystemManager.settings.setIgnorePingPongSignals(m_settingsSignalsIgnorePingPongMenuItem.isSelected());
		}
		else if(e.getSource() == m_settingsAutoSaveOnExitMenuItem) {
			SystemManager.settings.setAutoSaveOnExit(m_settingsAutoSaveOnExitMenuItem.isSelected());
		}
		else if(e.getSource() == m_settingsSaveMenuItem) {
			SystemManager.settings.save();
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
		m_settingsSignalsMenuItem[SystemManager.settings.getSignalDebugLevel()].setSelected(true);
		m_settingsAutoScrollConsoleMenuWindowItem.setSelected(SystemManager.settings.getAutoScrollConsoleWindow());
		
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
