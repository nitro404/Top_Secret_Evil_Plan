package server;

import java.io.*;
import java.util.*;
import signal.*;
import shared.*;

public class ClientInputSignalQueue extends Thread {
	
	private ArrayDeque<Signal> m_inSignalQueue;
	private DataInputStream m_in;
	private ClientOutputSignalQueue m_outSignalQueue;
	private Client m_client;
	private SystemConsole m_console;

	public ClientInputSignalQueue() {
		m_inSignalQueue = new ArrayDeque<Signal>();
	}

	public void initialize(Client client, DataInputStream in, ClientOutputSignalQueue out, SystemConsole console) {
		m_client = client;
		m_in = in;
		m_outSignalQueue = out;
		m_console = console;
		if(getState() == Thread.State.NEW) { start(); }
	}
	
	public boolean isTerminated() {
		return getState() == Thread.State.TERMINATED; 
	}
	
	public void addSignal(Signal s) {
		if(s == null) { return; }

		m_inSignalQueue.add(s);
	}
	
	private void sendSignal(Signal s) {
		if(s == null) { return; }
		
		m_outSignalQueue.addSignal(s);
	}
	
	public void readSignal() {
		if(!m_client.isConnected()) { return; }
		
		Signal s = Signal.readFrom(ByteStream.readFrom(m_in, Signal.LENGTH));
		Signal s2 = null;
		
		if(s == null) { return; }
		
		
		if(s.getSignalType() == SignalType.Ping) {
			s2 = s;
		}
		else if(s.getSignalType() == SignalType.Pong) {
			s2 = s;
		}
		/*else if(s.getSignalType() == SignalType.LoginRequest) {
			s2 = LoginRequestSignal.readFrom(ByteStream.readFrom(m_in, LoginRequestSignal.LENGTH)); 
		}*/
		else {
			m_console.writeLine("Unexpected input signal of type: " + s.getSignalType());
		}
		
		addSignal(s2);
	}
	
	public void run() {
		while(m_client.isConnected()) {
			if(!m_inSignalQueue.isEmpty()) {
				Signal s = m_inSignalQueue.remove();
				
				if(s.getSignalType() == SignalType.Ping) {
					sendSignal(new Signal(SignalType.Pong));
				}
				else if(s.getSignalType() == SignalType.Pong) {
					m_client.pong();
				}
				/*else if(s.getSignalType() == SignalType.LoginRequest) {
					LoginRequestSignal s2 = (LoginRequestSignal) s;
					
					boolean authenticated = m_server.userLogin(m_client, s2.getUserName(), s2.getPassword());
					sendSignal(new LoginAuthenticatedSignal(m_client.getUserName(), m_client.getNickName(), m_client.getPersonalMessage(), authenticated, m_client.getPort()));
					
					m_logger.addCommand(s2.getUserName(), "Login Request: " + ((authenticated) ? "Accepted" : "Rejected"));
				}*/
				else {
					m_console.writeLine("Unexpected input signal of type: " + s.getSignalType());
				}
			}
			
			try { sleep(MasterServer.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}

