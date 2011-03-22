package server;

import javax.swing.*;
import java.awt.*;
import shared.*;

public class ServerWindow extends JFrame implements Updatable {
	
	private Server m_server;
	private SystemConsole m_console;
	
	private JTextField m_consoleText;
	
	private static final long serialVersionUID = 1L;
	
	public ServerWindow() {
		m_server = new Server();
		m_console = m_server.getConsole();
		m_console.setTarget(this);
		initComponents();
	}
	
	public void initialize() {
		initialize(MasterServer.DEFAULT_PORT);
	}
	
	public void initialize(int port) {
		m_server.initialize(port);
		setVisible(true);
	}
	
	private void initComponents() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Master Server");
        setCursor(new java.awt.Cursor(Cursor.DEFAULT_CURSOR));
        setMinimumSize(new Dimension(640, 480));
        setName("ServerWindow");
        
        m_consoleText = new JTextField();
        add(m_consoleText);
    }
	
	public void update() {
		m_consoleText.setText(m_console.toString());
	}
	
}
