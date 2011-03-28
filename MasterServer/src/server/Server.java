package server;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import signal.*;

public class Server extends Thread {
	
	private ServerSocket m_connection;
	private Vector<Client> m_clients;
	private ClientDisconnectHandler m_disconnectHandler;
	private TrackerIdentifier m_trackerIdentifier;
	private static int m_clientCounter = 0;
	
	final public static int DEFAULT_PORT = 25500;
	final public static long QUEUE_INTERVAL = 50;
	final public static long CONNECTION_LISTEN_INTERVAL = 75;
	final public static long TIMEOUT_INTERVAL = 100;
	final public static long PING_INTERVAL = 5000;
	final public static long CONNECTION_TIMEOUT = 12000;
	
	public Server() {
		m_clients = new Vector<Client>();
		m_disconnectHandler = new ClientDisconnectHandler();
		m_trackerIdentifier = new TrackerIdentifier();
	}
	
	public void initialize() {
		initialize(-1);
	}
	
	public void initialize(int port) {
		if(port < 0 || port > 65355) { port = SystemManager.settings.getPort(); }
		
		try {
			m_connection = new ServerSocket(port);
		}
		catch(Exception e) {
			SystemManager.console.writeLine("Unable to initialize server on port " + port + ": " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Unable to initialize server on port " + port + ": " + e.getMessage(), "Error Initializing Server", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		
		SystemManager.console.writeLine("Successfully started server on port: " + port);
		
		m_disconnectHandler.initialize(m_clients);
		m_trackerIdentifier.initialize(this);
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
				SystemManager.console.writeLine("Unable to connect to client #" + m_clientCounter);
			}
			
			// if a connection was established to the client, store the client object
			if(newClient != null) {
				newClient.initialize(this);
				m_clients.add(newClient);
				m_trackerIdentifier.add(newClient);
				SystemManager.console.writeLine("Established connection to client #" + newClient.getClientNumber() + " at " + newClient.getIPAddressString());
			}
			
			try { sleep(CONNECTION_LISTEN_INTERVAL); }
			catch (InterruptedException e) { }
		}
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
	
	public void forwardToTracker(byte sourceTrackerNumber, byte destinationTrackerNumber, Signal signal) {
		if(signal == null || sourceTrackerNumber <= 0 || destinationTrackerNumber <= 0) { return; }
		for(int i=0;i<m_clients.size();i++) {
			if(sourceTrackerNumber != m_clients.elementAt(i).getTrackerNumber() && m_clients.elementAt(i).getTrackerNumber() == destinationTrackerNumber) {
				m_clients.elementAt(i).sendSignal(signal);
			}
		}
	}
	
	public void requestTrackerImages(byte sourceTrackerNumber) {
		if(sourceTrackerNumber <= 0) { return; }
		
		RequestTrackerImageSignal s = new RequestTrackerImageSignal(sourceTrackerNumber);
		for(int i=0;i<m_clients.size();i++) {
			if(sourceTrackerNumber != m_clients.elementAt(i).getTrackerNumber()) {
				m_clients.elementAt(i).sendSignal(s);
			}
		}
	}
	
}
