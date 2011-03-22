package server;

import java.io.*;
import java.net.*;
import shared.*;
import signal.*;

public class Client {
	
	private int m_clientNumber;
	protected InetAddress m_ipAddress;
	
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
	private SystemConsole m_console;
	
	public static int currentPort = 25502;
	
	public Client(Socket connection, int clientNumber) {
		m_clientNumber = clientNumber;
		m_connection = connection;
		m_inSignalQueue = new ClientInputSignalQueue();
		m_outSignalQueue = new ClientOutputSignalQueue();
		m_ipAddress = connection.getInetAddress();
	}
	
	public void initialize(Server server, SystemConsole console) {
		m_connected = true;
		
		try {
			m_server = server;
			m_console = console;
			m_out = new DataOutputStream(m_connection.getOutputStream());
			m_in = new DataInputStream(m_connection.getInputStream());
			m_inSignalQueue.initialize(m_server, this, m_in, m_outSignalQueue, m_console);
			m_outSignalQueue.initialize(this, m_out, m_console);
			
			if(m_clientThread == null || m_clientThread.isTerminated()) {
				m_clientThread = new ClientThread();
				m_clientThread.initialize(this);
			}
		}
		catch(IOException e) {
			m_connected = false;
			m_console.writeLine("Unable to initalize connection to client #" + m_clientNumber);
		}
	}
	
	public Socket getConnection() { return m_connection; }
	
	public InetAddress getIPAddress() { return m_ipAddress; }
	
	public String getIPAddressString() { return (m_ipAddress == null) ? "" : m_ipAddress.getHostAddress(); }
	
	public boolean isConnected() {
		return m_connected && !timeout();
	}
	
	public boolean ping() {
		if(!m_awaitingResponse && m_timeElapsed >= MasterServer.PING_INTERVAL) {
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
		return m_awaitingResponse && m_timeElapsed >= MasterServer.CONNECTION_TIMEOUT;
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
	
	public void sendSignal(Signal s) {
		m_outSignalQueue.addSignal(s);
	}
	
	public DataInputStream getInputStream() { return m_in; }
	
	public DataOutputStream getOutputStream() { return m_out; }
	
	public int getClientNumber() { return m_clientNumber; }
	
	public void readSignal() {
		m_inSignalQueue.readSignal();
	}
	
}
