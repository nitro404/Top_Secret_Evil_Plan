package planner;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import shared.*;

public class DebugWindow extends JFrame implements ActionListener, Updatable {
	
	private JTextArea m_consoleText;
	private Font m_consoleFont;
	private JScrollPane m_consoleScrollPane;
	
	private JMenuBar menuBar;
	
	private JMenu fileMenu;
	private JMenuItem fileStartSimulationMenuItem;
	private JMenuItem fileExitMenuItem;
    
	private JMenu settingsMenu;
	private JMenuItem settingsSaveMenuItem;
	
	private JMenu helpMenu;
	private JMenuItem helpAboutMenuItem;
	
	private static final long serialVersionUID = 1L;
	
	public DebugWindow() {
		setTitle("Debug Window");
		setSize(640, 800);
		setMinimumSize(new Dimension(320, 240));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		initMenu();
		initComponents();
	}
	
	public void initMenu() {
	    menuBar = new JMenuBar();
        
        fileMenu = new JMenu("File");
        fileStartSimulationMenuItem = new JMenuItem("Start Simulation");
        fileExitMenuItem = new JMenuItem("Exit");
        
        settingsMenu = new JMenu("Settings");
        settingsSaveMenuItem = new JMenuItem("Save Settings");
        
        helpMenu = new JMenu("Help");
        helpAboutMenuItem = new JMenuItem("About");
        
        fileStartSimulationMenuItem.addActionListener(this);
        fileExitMenuItem.addActionListener(this);
        settingsSaveMenuItem.addActionListener(this);
        helpAboutMenuItem.addActionListener(this);
        
        fileMenu.add(fileStartSimulationMenuItem);
        fileMenu.add(fileExitMenuItem);
        
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
		try {
			m_consoleText.setText(SystemManager.console.toString());
			m_consoleText.setCaretPosition(m_consoleText.getText().length());
			m_consoleText.scrollRectToVisible(new Rectangle(0, m_consoleText.getHeight() - 2, 1, 1));
		}
		catch(Exception e) { }
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == fileStartSimulationMenuItem) {
			SystemManager.start();
		}
		else if(e.getSource() == fileStartSimulationMenuItem) {
			System.exit(0);
		}
		else if(e.getSource() == settingsSaveMenuItem) {
			SystemManager.settings.save();
		}
		else if(e.getSource() == helpAboutMenuItem) {
			//TODO: Display credits dialog
		}
	}
	
}
