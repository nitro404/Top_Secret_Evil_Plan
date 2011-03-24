package server;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import shared.*;
import signal.*;

public class Server extends Thread {
	
	private ServerSocket m_connection;
	private Vector<Client> m_clients;
	private ClientDisconnectHandler m_disconnectHandler;
	private static int m_clientCounter = 0;
	
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
	}
	
	public void initialize(int port) {
		if(port < 0 || port > 65355) { port = DEFAULT_PORT; }
		
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
			if(clientNumber != m_clients.elementAt(i).getClientNumber()) {
				m_clients.elementAt(i).sendSignal(signal);
			}
		}
	}
	
}
