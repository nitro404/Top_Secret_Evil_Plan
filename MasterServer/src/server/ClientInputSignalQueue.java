package server;

import java.io.*;
import java.util.*;
import signal.*;
import shared.*;

public class ClientInputSignalQueue extends Thread {
	
	private ArrayDeque<Signal> m_inSignalQueue;
	private DataInputStream m_in;
	private ClientOutputSignalQueue m_outSignalQueue;
	private Server m_server;
	private Client m_client;
	private SystemConsole m_console;

	public ClientInputSignalQueue() {
		m_inSignalQueue = new ArrayDeque<Signal>();
	}

	public void initialize(Server server, Client client, DataInputStream in, ClientOutputSignalQueue out, SystemConsole console) {
		m_server = server;
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
		else if(s.getSignalType() == SignalType.UpdateActualRobotPose) {
			s2 = UpdateActualRobotPoseSignal.readFrom(ByteStream.readFrom(m_in, UpdateActualRobotPoseSignal.LENGTH)); 
		}
		else if(s.getSignalType() == SignalType.UpdateEstimatedRobotPose) {
			s2 = UpdateEstimatedRobotPoseSignal.readFrom(ByteStream.readFrom(m_in, UpdateEstimatedRobotPoseSignal.LENGTH)); 
		}
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
				else if(SignalType.isValid(s.getSignalType())) {
					m_server.forwardSignal(m_client, s);
				}
				else {
					m_console.writeLine("Unexpected input signal of type: " + s.getSignalType());
				}
			}
			
			try { sleep(MasterServer.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}

