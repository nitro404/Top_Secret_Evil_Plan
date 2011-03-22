package server;

import java.io.*;
import java.util.*;
import signal.*;
import shared.*;

public class ClientOutputSignalQueue extends Thread {
	
	private ArrayDeque<Signal> m_outSignalQueue;
	private DataOutputStream m_out;
	private Client m_client;
	private SystemConsole m_console;

	public ClientOutputSignalQueue() {
		m_outSignalQueue = new ArrayDeque<Signal>();
	}

	public void initialize(Client client, DataOutputStream out, SystemConsole console) {
		m_client = client;
		m_out = out;
		m_console = console;
		if(getState() == Thread.State.NEW) { start(); }
	}
	
	public boolean isTerminated() {
		return getState() == Thread.State.TERMINATED; 
	}
	
	public void addSignal(Signal s) {
		if (s == null) { return; }

		m_outSignalQueue.add(s);
	}

	public void run() {
		while(m_client.isConnected()) {
			if(!m_outSignalQueue.isEmpty()) {
				Signal s = m_outSignalQueue.remove();
				
				if(SignalType.isValid(s.getSignalType())) {
					s.writeTo(m_out);
				}
				else {
					m_console.writeLine("Unexpected output signal of type: " + s.getSignalType());
				}
			}
			
			try { sleep(MasterServer.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}
