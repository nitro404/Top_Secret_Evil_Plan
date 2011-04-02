package server;

import java.io.*;
import java.util.*;
import shared.*;
import signal.*;

public class ClientInputSignalQueue extends Thread {
	
	private ArrayDeque<Signal> m_inSignalQueue;
	private DataInputStream m_in;
	private ClientOutputSignalQueue m_outSignalQueue;
	private Server m_server;
	private Client m_client;

	public ClientInputSignalQueue() {
		m_inSignalQueue = new ArrayDeque<Signal>();
	}

	public void initialize(Server server, Client client, DataInputStream in, ClientOutputSignalQueue out) {
		m_server = server;
		m_client = client;
		m_in = in;
		m_outSignalQueue = out;
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
		else if(s.getSignalType() == SignalType.StartSimulation) {
			s2 = s;
		}
		else if(s.getSignalType() == SignalType.BlockStateChange) {
			s2 = BlockStateChangeSignal.readFrom(ByteStream.readFrom(m_in, BlockStateChangeSignal.LENGTH)); 
		}
		else if(s.getSignalType() == SignalType.RobotStateChange) {
			s2 = RobotStateChangeSignal.readFrom(ByteStream.readFrom(m_in, RobotStateChangeSignal.LENGTH)); 
		}
		else if(s.getSignalType() == SignalType.PotStateChange) {
			s2 = PotStateChangeSignal.readFrom(ByteStream.readFrom(m_in, PotStateChangeSignal.LENGTH)); 
		}
		else if(s.getSignalType() == SignalType.TaskStarted) {
			s2 = TaskStartedSignal.readFrom(ByteStream.readFrom(m_in, TaskStartedSignal.LENGTH)); 
		}
		else if(s.getSignalType() == SignalType.TaskCompleted) {
			s2 = TaskCompletedSignal.readFrom(ByteStream.readFrom(m_in, TaskCompletedSignal.LENGTH)); 
		}
		else if(s.getSignalType() == SignalType.UpdateBlockPosition) {
			s2 = UpdateBlockPositionSignal.readFrom(ByteStream.readFrom(m_in, UpdateBlockPositionSignal.LENGTH)); 
		}
		else if(s.getSignalType() == SignalType.UpdatePotPosition) {
			s2 = UpdatePotPositionSignal.readFrom(ByteStream.readFrom(m_in, UpdatePotPositionSignal.LENGTH)); 
		}
		else if(s.getSignalType() == SignalType.UpdateActualRobotPosition) {
			s2 = UpdateActualRobotPositionSignal.readFrom(ByteStream.readFrom(m_in, UpdateActualRobotPositionSignal.LENGTH)); 
		}
		else if(s.getSignalType() == SignalType.UpdateEstimatedRobotPosition) {
			s2 = UpdateEstimatedRobotPositionSignal.readFrom(ByteStream.readFrom(m_in, UpdateEstimatedRobotPositionSignal.LENGTH)); 
		}
		else if(s.getSignalType() == SignalType.RequestTrackerImage) {
			s2 = RequestTrackerImageSignal.readFrom(ByteStream.readFrom(m_in, RequestTrackerImageSignal.LENGTH)); 
		}
		else if(s.getSignalType() == SignalType.ReplyTrackerImage) {
			s2 = ReplyTrackerImageSignal.readFrom(ByteStream.readFrom(m_in, ReplyTrackerImageSignal.LENGTH), m_in); 
		}
		else if(s.getSignalType() == SignalType.BroadcastTrackerImage) {
			s2 = BroadcastTrackerImageSignal.readFrom(ByteStream.readFrom(m_in, BroadcastTrackerImageSignal.LENGTH), m_in); 
		}
		else if(s.getSignalType() == SignalType.ReceiveTrackerNumber) {
			s2 = ReceiveTrackerNumberSignal.readFrom(ByteStream.readFrom(m_in, ReceiveTrackerNumberSignal.LENGTH)); 
		}
		else {
			SystemManager.console.writeLine("Unexpected input signal of type: " + s.getSignalType());
		}
		
		addSignal(s2);
	}
	
	public void run() {
		while(m_client.isConnected()) {
			if(!m_inSignalQueue.isEmpty()) {
				Signal s = m_inSignalQueue.remove();
				
				if(SystemManager.settings.getSignalDebugLevel() == SignalDebugLevel.Incoming ||
				   SystemManager.settings.getSignalDebugLevel() == SignalDebugLevel.Both) {
					if(!SystemManager.settings.getIgnorePingPongSignals() || 
					   !((s.getSignalType() == SignalType.Ping || s.getSignalType() == SignalType.Pong) && SystemManager.settings.getIgnorePingPongSignals())) {
						SystemManager.console.writeLine("Received from " + m_client.getName() + ": " + s.toString());
					}
				}
				
				if(s.getSignalType() == SignalType.Ping) {
					sendSignal(new Signal(SignalType.Pong));
				}
				if(s.getSignalType() == SignalType.Pong) {
					m_client.pong();
				}
				else if(s.getSignalType() == SignalType.ReplyTrackerImage) {
					ReplyTrackerImageSignal s2 = (ReplyTrackerImageSignal) s;
					
					if(m_client.isIdentified()) {
						m_server.forwardToTracker(m_client.getTrackerNumber(), s2.getDestinationTrackerNumber(), s);
					}
				}
				else if(SignalType.isValid(s.getSignalType())) {
					if(m_client.isIdentified()) {
						m_server.forwardSignal(m_client.getClientNumber(), s);
					}
				}
				else {
					SystemManager.console.writeLine("Unexpected input signal of type: " + s.getSignalType());
				}
			}
			
			try { sleep(Server.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}

