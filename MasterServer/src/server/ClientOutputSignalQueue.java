package server;

import java.io.*;
import java.util.*;
import shared.*;
import signal.*;

public class ClientOutputSignalQueue extends Thread {
	
	private ArrayDeque<Signal> m_outSignalQueue;
	private DataOutputStream m_out;
	private Client m_client;

	public ClientOutputSignalQueue() {
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
						if(!SystemManager.settings.getIgnorePositionSignals() || 
						   !((s.getSignalType() == SignalType.UpdateActualRobotPosition || s.getSignalType() == SignalType.UpdateEstimatedRobotPosition || s.getSignalType() == SignalType.UpdateBlockPosition || s.getSignalType() == SignalType.UpdatePotPosition) && SystemManager.settings.getIgnorePositionSignals())) {
							SystemManager.console.writeLine("Sending to " + m_client.getName() + ": " + s.toString());
						}
					}
				}
				
				if(SignalType.isValid(s.getSignalType())) {
					s.writeTo(m_out);
				}
				else {
					SystemManager.console.writeLine("Unexpected output signal of type: " + s.getSignalType());
				}
			}
			
			try { sleep(Server.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}
