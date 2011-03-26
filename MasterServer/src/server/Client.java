package server;

import java.io.*;
import java.net.*;
import signal.*;

public class Client {
	
	private int m_clientNumber;
	private InetAddress m_ipAddress;
	
	private byte m_trackerNumber = -1;
	
	private Socket m_connection;
	private boolean m_connected = false;
	private DataInputStream m_in;
	private DataOutputStream m_out;
	private ClientInputSignalQueue m_inSignalQueue;
	private ClientOutputSignalQueue m_outSignalQueue;
	private ClientThread m_clientThread = null;
	
	private int m_timeElapsed = 0;
	private boolean m_awaitingResponse = false;
	
	private Server m_server;
	
	public static int currentPort = 25502;
	
	public Client(Socket connection, int clientNumber) {
		m_clientNumber = clientNumber;
		m_connection = connection;
		m_inSignalQueue = new ClientInputSignalQueue();
		m_outSignalQueue = new ClientOutputSignalQueue();
		m_ipAddress = connection.getInetAddress();
	}
	
	public void initialize(Server server) {
		m_connected = true;
		
		try {
			m_server = server;
			m_out = new DataOutputStream(m_connection.getOutputStream());
			m_in = new DataInputStream(m_connection.getInputStream());
			m_inSignalQueue.initialize(m_server, this, m_in, m_outSignalQueue);
			m_outSignalQueue.initialize(this, m_out);
			
			if(m_clientThread == null || m_clientThread.isTerminated()) {
				m_clientThread = new ClientThread();
				m_clientThread.initialize(this);
			}
		}
		catch(IOException e) {
			m_connected = false;
			SystemManager.console.writeLine("Unable to initalize connection to client #" + m_clientNumber);
		}
	}
	
	public boolean isIdentified() { return m_trackerNumber >= 1; }
	
	public byte getTrackerNumber() { return m_trackerNumber; }
	
	public void setTrackerNumber(byte trackerNumber) {
		m_trackerNumber = trackerNumber;
		sendSignal(new ReceiveTrackerNumberSignal(m_trackerNumber));
		m_server.requestTrackerImages(m_trackerNumber);
	}
	
	public Socket getConnection() { return m_connection; }
	
	public InetAddress getIPAddress() { return m_ipAddress; }
	
	public String getIPAddressString() { return (m_ipAddress == null) ? "" : m_ipAddress.getHostAddress(); }
	
	public boolean isConnected() {
		return m_connected && !timeout();
	}
	
	public boolean ping() {
		if(!m_awaitingResponse && m_timeElapsed >= Server.PING_INTERVAL) {
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
		return m_awaitingResponse && m_timeElapsed >= Server.CONNECTION_TIMEOUT;
	}
	
	public void disconnect() {
		m_connected = false;
		
		try { if(m_out != null) { m_out.close(); } } catch(IOException e) { }
		try { if(m_in != null) { m_in.close(); } } catch(IOException e) { }
		try { if(m_connection != null) { m_connection.close(); } } catch(IOException e) { }

		m_out = null;
		m_in = null;
		m_connection = null;
	}
	
	public DataInputStream getInputStream() { return m_in; }
	
	public DataOutputStream getOutputStream() { return m_out; }
	
	public int getClientNumber() { return m_clientNumber; }
	
	public String getName() { return isIdentified() ? "Tracker #" + m_trackerNumber : "Client #" + m_clientNumber; }
	
	public void sendSignal(Signal s) {
		if(s == null) { return; }
		m_outSignalQueue.addSignal(s);
	}
	
	public void readSignal() {
		if(m_inSignalQueue == null) { return; }
		m_inSignalQueue.readSignal();
	}
	
}
