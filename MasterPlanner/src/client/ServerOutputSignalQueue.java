package client;

import java.io.*;
import java.util.*;

import planner.SystemManager;
import shared.SignalDebugLevel;
import signal.*;

public class ServerOutputSignalQueue extends Thread {
	
	private ArrayDeque<Signal> m_outSignalQueue;
	private DataOutputStream m_out;
	private Client m_client;
	
	public ServerOutputSignalQueue(){
		m_outSignalQueue = new ArrayDeque<Signal>();
	}
	
	public void initialize(Client client, DataOutputStream out) {
		m_client = client;
		m_out = out;
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
				
				if(SystemManager.settings.getSignalDebugLevel() == SignalDebugLevel.Outgoing ||
				   SystemManager.settings.getSignalDebugLevel() == SignalDebugLevel.Both) {
					if(!SystemManager.settings.getIgnorePingPongSignals() || 
					   !((s.getSignalType() == SignalType.Ping || s.getSignalType() == SignalType.Pong) && SystemManager.settings.getIgnorePingPongSignals())) {
						SystemManager.console.writeLine("Sending: " + s.toString());
					}
				}
				
				if(s.getSignalType() == SignalType.Ping) {
					s.writeTo(m_out);
				}
				else if(s.getSignalType() == SignalType.Pong) {
					s.writeTo(m_out);
				}
				else if(SignalType.isValid(s.getSignalType())) {
					s.writeTo(m_out);
				}
			}
			
			try { sleep(Client.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
			
		}
	}
	
}
