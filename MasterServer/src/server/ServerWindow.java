package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import settings.*;
import shared.*;

public class ServerWindow extends JFrame implements WindowListener, Updatable {
	
	private Server m_server;
	private SystemConsole m_console;
	
	private JTextArea m_consoleText;
	private Font m_consoleFont;
	private JScrollPane m_consoleScrollPane;
	
	private SettingsManager m_settings;
	
	private static final long serialVersionUID = 1L;
	
	public ServerWindow() {
		m_server = new Server();
		m_console = m_server.getConsole();
		m_console.setTarget(this);
		initComponents();
	}
	
	public void initialize() {
		m_settings = new SettingsManager();
		m_settings.load();
		
		m_server.initialize();
		setVisible(true);
	}
	
	public void initialize(int port) {
		initialize(null, port);
	}
	
	public void initialize(SettingsManager settings, int port) {
		m_settings = settings;
		if(m_settings == null) {
			m_settings = new SettingsManager();
			m_settings.load();
		}
		
		m_server.initialize(port);
		setVisible(true);
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
	
	public void windowClosing(WindowEvent e){
		m_settings.save();
		dispose();
	}
	
	public void update() {
		try {
			m_consoleText.setText(m_console.toString());
			m_consoleText.setCaretPosition(m_consoleText.getText().length());
			m_consoleText.scrollRectToVisible(new Rectangle(0, m_consoleText.getHeight() - 2, 1, 1));
		}
		catch(Exception e) { }
	}
	
}
