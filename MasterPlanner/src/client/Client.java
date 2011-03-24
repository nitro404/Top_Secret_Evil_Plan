package client;

import java.io.*;
import java.net.*;
import planner.*;
import signal.*;

public class Client {
	
	private boolean m_connected;
	private Socket m_connection;
	private DataInputStream m_in;
	private DataOutputStream m_out;
	private ServerInputSignalQueue m_inSignalQueue = null;
	private ServerOutputSignalQueue m_outSignalQueue = null;
	private ClientThread m_clientThread = null;
	
	private String m_hostName;
	private int m_port;
	private ServerDisconnectHandler m_disconnectHandler = null;
	private int m_timeElapsed = 0;
	private boolean m_awaitingResponse = false;
	
	final public static String DEFAULT_HOST = "localhost";
	
	final public static int DEFAULT_PORT = 25500;
	final public static long QUEUE_INTERVAL = 50;
	final public static long CONNECTION_LISTEN_INTERVAL = 75;
	final public static long TIMEOUT_INTERVAL = 100;
	final public static long PING_INTERVAL = 5000;
	final public static long CONNECTION_TIMEOUT = 10000;
	
	public Client() {
		super();
		m_connected = false;
	}
	
	public void initialize() {
		initialize(SystemManager.settings.getServerIPAddressHostName(), SystemManager.settings.getServerPort());
	}
	
	public void initialize(String hostName, int port) {
		if(hostName == null) { m_hostName = SystemManager.settings.getServerIPAddress().getHostName(); }
		else { m_hostName = hostName; }
		if(port < 0 || port > 65355) { m_port = SystemManager.settings.getServerPort(); }
		else { m_port = port; }
	}
	
	public Socket getConnection() { return m_connection; }
	
	public boolean isConnected() {
		return m_connected;
	}
	
	public DataInputStream getInputStream() { return m_in; }
	
	public DataOutputStream getOutputStream() { return m_out; }
	
	public void connect() {
		if(m_connected) { return; }
		
		m_connected = true;
		
		m_timeElapsed = 0;
		m_awaitingResponse = false;
		
		try {
			m_connection = new Socket(m_hostName, m_port);
			m_out = new DataOutputStream(m_connection.getOutputStream());
			m_in = new DataInputStream(m_connection.getInputStream());
			
			if(m_outSignalQueue == null || m_outSignalQueue.isTerminated()) {
				m_outSignalQueue = new ServerOutputSignalQueue();
				m_outSignalQueue.initialize(this, m_out);
			}
			
			if(m_inSignalQueue == null || m_inSignalQueue.isTerminated()) {
				m_inSignalQueue = new ServerInputSignalQueue();
				m_inSignalQueue.initialize(this, m_in, m_outSignalQueue);
			}
			
			if(m_clientThread == null || m_clientThread.isTerminated()) {
				m_clientThread = new ClientThread();
				m_clientThread.initialize(this);
			}
			
			if(m_disconnectHandler == null || m_disconnectHandler.isTerminated()) {
				m_disconnectHandler = new ServerDisconnectHandler();
				m_disconnectHandler.initialize(this);
			}
			
			SystemManager.console.writeLine("Connected to Master Server at " + m_hostName + ":" + m_port + ".");
		}
		catch(IOException e) {
			disconnect();
			SystemManager.console.writeLine("Unable to connect to Master Server at " + m_hostName + ":" + m_port + ": " + e.getMessage());
		}
	}
	
	public void disconnect() {
		try { if(m_out != null) { m_out.close(); } } catch(IOException e) { }
		try { if(m_in != null) { m_in.close(); } } catch(IOException e) { }
		try { if(m_connection != null) { m_connection.close(); } } catch(IOException e) { }
		
		m_out = null;
		m_in = null;
		m_connection = null;
		
		m_connected = false;
	}
	
	public boolean ping() {
		if(!m_awaitingResponse && m_timeElapsed >= PING_INTERVAL) {
			m_timeElapsed = 0;
			m_awaitingResponse = true;
			m_outSignalQueue.addSignal(new Signal(SignalType.Ping));
			return true;
		}
		return false;
	}
	
	public void pong() {
		m_timeElapsed = 0;
		m_awaitingResponse = false;
	}
	
	public boolean awaitingResponse() { return m_awaitingResponse; }
	
	public int timeElapsed() { return m_timeElapsed; }
	
	public void addTime(long time) {
		if(time <= 0) { return; }
		m_timeElapsed += time;
	}
	
	public boolean timeout() {
		return m_awaitingResponse && m_timeElapsed >= Client.CONNECTION_TIMEOUT;
	}
	
	public void sendSignal(Signal s) {
		if(s == null) { return; }
		m_outSignalQueue.addSignal(s);
	}
	
	public void readSignal() {
		m_inSignalQueue.readSignal();
	}
	
}
