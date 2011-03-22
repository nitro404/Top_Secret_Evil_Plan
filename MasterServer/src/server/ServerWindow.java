package server;

import javax.swing.*;
import java.awt.*;

public class ServerWindow extends JFrame {
	
	private Server m_server;
	
	private static final long serialVersionUID = 1L;
	
	public ServerWindow() {
		m_server = new Server();
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
    }
	
}
