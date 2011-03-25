package server;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import settings.*;
import shared.*;
import signal.*;

public class Server extends Thread {
	
	private ServerSocket m_connection;
	private Vector<Client> m_clients;
	private ClientDisconnectHandler m_disconnectHandler;
	private TrackerIdentifier m_trackerIdentifier;
	private static int m_clientCounter = 0;
	
	private SettingsManager m_settings;
	private SystemConsole m_console;
	
	final public static int DEFAULT_PORT = 25500;
	final public static long QUEUE_INTERVAL = 50;
	final public static long CONNECTION_LISTEN_INTERVAL = 75;
	final public static long TIMEOUT_INTERVAL = 100;
	final public static long PING_INTERVAL = 5000;
	final public static long CONNECTION_TIMEOUT = 10000;
	
	public Server() {
		m_clients = new Vector<Client>();
		m_console = new SystemConsole();
		m_disconnectHandler = new ClientDisconnectHandler();
		m_trackerIdentifier = new TrackerIdentifier();
	}
	
	public void initialize() {
		initialize(-1);
	}
	
	public void initialize(SettingsManager settings) {
		m_settings = settings;
		initialize(-1);
	}
	
	public void initialize(int port, SettingsManager settings) {
		m_settings = settings;
		initialize(port);
	}
	
	public void initialize(int port) {
		if(m_settings == null) {
			m_settings = new SettingsManager();
			m_settings.load();
		}
		
		if(port < 0 || port > 65355) { port = m_settings.getPort(); }
		
		try {
			m_connection = new ServerSocket(port);
		}
		catch(Exception e) {
			m_console.writeLine("Unable to initialize server on port " + port + ": " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Unable to initialize server on port " + port + ": " + e.getMessage(), "Error Initializing Server", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		
		m_console.writeLine("Successfully started server on port: " + port);
		
		m_disconnectHandler.initialize(m_clients, m_console);
		m_trackerIdentifier.initialize(this, m_settings, m_console);
		if(getState() == Thread.State.NEW) { start(); }
	}
	
	public void run() {
		Client newClient;
		while(true) {
			newClient = null;
			m_clientCounter++;
			try {
				newClient = new Client(m_connection.accept(), m_clientCounter);
			}
			catch(IOException e) {
				m_console.writeLine("Unable to connect to client #" + m_clientCounter);
			}
			
			// if a connection was established to the client, store the client object
			if(newClient != null) {
				newClient.initialize(this, m_console);
				m_clients.add(newClient);
				m_trackerIdentifier.add(newClient);
				m_console.writeLine("Established connection to client #" + newClient.getClientNumber() + " at " + newClient.getIPAddressString());
			}
			
			try { sleep(CONNECTION_LISTEN_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
	public SystemConsole getConsole() {
		return m_console;
	}
	
	public int numberOfClients() { return m_clients.size(); }
	
	public Client getClient(int index) {
		if(index < 0 || index >= m_clients.size()) { return null; }
		return m_clients.elementAt(index);
	}
	
	public void forwardSignal(int clientNumber, Signal signal) {
		if(signal == null) { return; }
		
		for(int i=0;i<m_clients.size();i++) {
			if(clientNumber != m_clients.elementAt(i).getClientNumber() && m_clients.elementAt(i).isIdentified()) {
				m_clients.elementAt(i).sendSignal(signal);
			}
		}
	}
	
	public void forwardToTracker(byte sourceTrackerID, byte destinationTrackerID, Signal signal) {
		if(signal == null || sourceTrackerID <= 0 || destinationTrackerID <= 0) { return; }
		for(int i=0;i<m_clients.size();i++) {
			if(sourceTrackerID != m_clients.elementAt(i).getTrackerNumber() && m_clients.elementAt(i).getTrackerNumber() == destinationTrackerID) {
				m_clients.elementAt(i).sendSignal(signal);
			}
		}
	}
	
	public void requestTrackerImages(byte sourceTrackerID) {
		if(sourceTrackerID <= 0) { return; }
		
		RequestTrackerImageSignal s = new RequestTrackerImageSignal(sourceTrackerID);
		for(int i=0;i<m_clients.size();i++) {
			if(sourceTrackerID != m_clients.elementAt(i).getTrackerNumber()) {
				m_clients.elementAt(i).sendSignal(s);
			}
		}
	}
	
}
