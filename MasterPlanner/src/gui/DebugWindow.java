package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import planner.*;
import shared.*;

public class DebugWindow extends JFrame implements ActionListener, WindowListener, Updatable {
	
	private JTextArea m_consoleText;
	private Font m_consoleFont;
	private JScrollPane m_consoleScrollPane;
	
	private boolean m_updating;
	
	private JMenuBar menuBar;
	
	private JMenu fileMenu;
	private JMenuItem fileConnectMenuItem;
	private JMenuItem fileDisconnectMenuItem;
	private JMenuItem fileStartSimulationMenuItem;
	private JMenuItem fileExitMenuItem;
    
	private JMenu settingsMenu;
	private JMenu settingsSignalsMenu;
	private JRadioButtonMenuItem[] settingsSignalsMenuItem;
	private ButtonGroup settingsSignalsButtonGroup;
	private JMenuItem settingsSaveMenuItem;
	
	private JMenu helpMenu;
	private JMenuItem helpAboutMenuItem;
	
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
	
	public void initMenu() {
	    menuBar = new JMenuBar();
        
        fileMenu = new JMenu("File");
        fileConnectMenuItem = new JMenuItem("Connect");
    	fileDisconnectMenuItem = new JMenuItem("Disconnect");
        fileStartSimulationMenuItem = new JMenuItem("Start Simulation");
        fileExitMenuItem = new JMenuItem("Exit");
        
        settingsMenu = new JMenu("Settings");
        settingsSignalsMenu = new JMenu("Signal Debugging");
        settingsSignalsMenuItem = new JRadioButtonMenuItem[SignalDebugLevel.signalDebugLevels.length];
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	settingsSignalsMenuItem[i] = new JRadioButtonMenuItem(SignalDebugLevel.signalDebugLevels[i]); 
        }
        settingsSaveMenuItem = new JMenuItem("Save Settings");
        
        helpMenu = new JMenu("Help");
        helpAboutMenuItem = new JMenuItem("About");
        
        settingsSignalsMenuItem[0].setSelected(true);
        
        settingsSignalsButtonGroup = new ButtonGroup();
        
        fileConnectMenuItem.addActionListener(this);
        fileDisconnectMenuItem.addActionListener(this);
        fileStartSimulationMenuItem.addActionListener(this);
        fileExitMenuItem.addActionListener(this);
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	settingsSignalsMenuItem[i].addActionListener(this);
        }
        settingsSaveMenuItem.addActionListener(this);
        helpAboutMenuItem.addActionListener(this);
        
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	settingsSignalsButtonGroup.add(settingsSignalsMenuItem[i]);
        }
        
        fileMenu.add(fileConnectMenuItem);
        fileMenu.add(fileDisconnectMenuItem);
        fileMenu.add(fileStartSimulationMenuItem);
        fileMenu.add(fileExitMenuItem);
        
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	settingsSignalsMenu.add(settingsSignalsMenuItem[i]);
        }
        settingsMenu.add(settingsSignalsMenu);
        settingsMenu.add(settingsSaveMenuItem);
        
        helpMenu.add(helpAboutMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }
	
	public void initComponents() {
		m_consoleText = new JTextArea();
        m_consoleFont = new Font("Verdana", Font.PLAIN, 14);
        m_consoleText.setFont(m_consoleFont);
        m_consoleText.setEditable(false);
        m_consoleScrollPane = new JScrollPane(m_consoleText);
        add(m_consoleScrollPane);
	}
	
	public void update() {
		m_updating = true;
		
		try {
			m_consoleText.setText(SystemManager.console.toString());
			m_consoleText.setCaretPosition(m_consoleText.getText().length());
			m_consoleText.scrollRectToVisible(new Rectangle(0, m_consoleText.getHeight() - 2, 1, 1));
		}
		catch(Exception e) { }
		
		m_updating = false;
	}
	
	public void windowActivated(WindowEvent e) { }
	public void windowClosed(WindowEvent e) { }
	public void windowDeactivated(WindowEvent e) { }
	public void windowDeiconified(WindowEvent e) { }
	public void windowIconified(WindowEvent e) { }
	public void windowOpened(WindowEvent e) { }
	
	public void windowClosing(WindowEvent e) {
		SystemManager.settings.save();
		dispose();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(m_updating) { return; }
		
		if(e.getSource() == fileConnectMenuItem) {
			SystemManager.client.connect();
		}
		else if(e.getSource() == fileDisconnectMenuItem) {
			SystemManager.client.disconnect();
		}
		else if(e.getSource() == fileStartSimulationMenuItem) {
			SystemManager.start();
		}
		else if(e.getSource() == fileExitMenuItem) {
			System.exit(0);
		}
		else if(e.getSource() == settingsSaveMenuItem) {
			SystemManager.settings.save();
		}
		else if(e.getSource() == helpAboutMenuItem) {
			JOptionPane.showMessageDialog(this, "MasterPlanner Created by Kevin Scroggins (nitro404@hotmail.com).\nCreated for the COMP 4807 Final Project - April 4, 2011.", "About MasterPlanner", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
	        	if(e.getSource() == settingsSignalsMenuItem[i]) {
	        		SystemManager.settings.setSignalDebugLevel(i);
	        	}
	        }
		}
	}
	
}
