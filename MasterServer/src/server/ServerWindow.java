package server;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import shared.*;

public class ServerWindow extends JFrame implements ActionListener, WindowListener, Updatable {
	
	private JTextArea m_consoleText;
	private Font m_consoleFont;
	private JScrollPane m_consoleScrollPane;
	
	private boolean m_updating;
	
	private JMenuBar menuBar;
	
	private JMenu fileMenu;
	private JMenuItem fileExitMenuItem;
    
	private JMenu settingsMenu;
	private JCheckBoxMenuItem settingsAutoScrollConsoleMenuWindowItem;
	private JMenu settingsSignalsMenu;
	private JRadioButtonMenuItem[] settingsSignalsMenuItem;
	private ButtonGroup settingsSignalsButtonGroup;
	private JMenuItem settingsSaveMenuItem;
	
	private JMenu helpMenu;
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
		menuBar = new JMenuBar();
        
        fileMenu = new JMenu("File");
        fileExitMenuItem = new JMenuItem("Exit");
        
        settingsMenu = new JMenu("Settings");
        settingsAutoScrollConsoleMenuWindowItem = new JCheckBoxMenuItem("Auto-scroll Console Window");
        settingsSignalsMenu = new JMenu("Signal Debugging");
        settingsSignalsMenuItem = new JRadioButtonMenuItem[SignalDebugLevel.signalDebugLevels.length];
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	settingsSignalsMenuItem[i] = new JRadioButtonMenuItem(SignalDebugLevel.signalDebugLevels[i]); 
        }
        settingsSaveMenuItem = new JMenuItem("Save Settings");
        
        helpMenu = new JMenu("Help");
        helpAboutMenuItem = new JMenuItem("About");
        
        settingsAutoScrollConsoleMenuWindowItem.setSelected(true);
        settingsSignalsMenuItem[0].setSelected(true);
        
        settingsSignalsButtonGroup = new ButtonGroup();
        
        fileExitMenuItem.addActionListener(this);
        settingsAutoScrollConsoleMenuWindowItem.addActionListener(this);
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	settingsSignalsMenuItem[i].addActionListener(this);
        }
        settingsSaveMenuItem.addActionListener(this);
        helpAboutMenuItem.addActionListener(this);
        
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	settingsSignalsButtonGroup.add(settingsSignalsMenuItem[i]);
        }
        
        fileMenu.add(fileExitMenuItem);
        
        settingsMenu.add(settingsAutoScrollConsoleMenuWindowItem);
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
		SystemManager.settings.save();
		dispose();
	}
	

	public void actionPerformed(ActionEvent e) {
		if(m_updating) { return; }
		
		if(e.getSource() == fileExitMenuItem) {
			System.exit(0);
		}
		else if(e.getSource() == settingsAutoScrollConsoleMenuWindowItem) {
			SystemManager.settings.setAutoScrollConsoleWindow(settingsAutoScrollConsoleMenuWindowItem.isSelected());
		}
		else if(e.getSource() == settingsSaveMenuItem) {
			SystemManager.settings.save();
		}
		else if(e.getSource() == helpAboutMenuItem) {
			JOptionPane.showMessageDialog(this, "MasterServer Created by Kevin Scroggins (nitro404@hotmail.com).\nCreated for the COMP 4807 Final Project - April 4, 2011.", "About MasterServer", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
	        	if(e.getSource() == settingsSignalsMenuItem[i]) {
	        		SystemManager.settings.setSignalDebugLevel(i);
	        	}
	        }
		}
	}
	
	public void update() {
		m_updating = true;
		
		settingsSignalsMenuItem[SystemManager.settings.getSignalDebugLevel()].setSelected(true);
		settingsAutoScrollConsoleMenuWindowItem.setSelected(SystemManager.settings.getAutoScrollConsoleWindow());
		
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
