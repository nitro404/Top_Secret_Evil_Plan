package client;

import java.io.*;
import java.util.*;

import planner.SystemManager;
import shared.*;
import signal.*;

public class ServerInputSignalQueue extends Thread {
	
	private ArrayDeque<Signal> m_inSignalQueue;
	private DataInputStream m_in;
	private ServerOutputSignalQueue m_outSignalQueue;
	private Client m_client;
	
	public ServerInputSignalQueue(){
		m_inSignalQueue = new ArrayDeque<Signal>();
	}
	
	public void initialize(Client client, DataInputStream in, ServerOutputSignalQueue out) {
		m_client = client;
		m_in = in;
		m_outSignalQueue = out;
		if(getState() == Thread.State.NEW) { start(); }
	}
	
	public boolean isTerminated() {
		return getState() == Thread.State.TERMINATED; 
	}
	
	public void addSignal(Signal s) {
		if (s == null){ return; }
		
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
			return;
		}
		
		addSignal(s2);
	}
	
	public void run() {
		while(m_client.isConnected()) {
			if(!m_inSignalQueue.isEmpty()){
				Signal s = m_inSignalQueue.remove();
				
				if(s == null) { continue; }
				
				if(SystemManager.settings.getSignalDebugLevel() == SignalDebugLevel.Incoming ||
				   SystemManager.settings.getSignalDebugLevel() == SignalDebugLevel.Both) {
					SystemManager.console.writeLine("Received: " + s.toString());
				}
				
				if(s.getSignalType() == SignalType.Ping) {
					sendSignal(new Signal(SignalType.Pong));
				}
				else if(s.getSignalType() == SignalType.Pong) {
					m_client.pong();
				}
				else if(s.getSignalType() == SignalType.StartSimulation) {
					SystemManager.start();
				}
				else if(s.getSignalType() == SignalType.BlockStateChange) {
					BlockStateChangeSignal s2 = (BlockStateChangeSignal) s;
					SystemManager.blockSystem.setBlockState(s2.getBlockID(), s2.getRobotID(), s2.getBlockState());
				}
				else if(s.getSignalType() == SignalType.RobotStateChange) {
					RobotStateChangeSignal s2 = (RobotStateChangeSignal) s;
					SystemManager.robotSystem.setRobotState(s2.getRobotID(), s2.getRobotState());
				}
				else if(s.getSignalType() == SignalType.PotStateChange) {
					PotStateChangeSignal s2 = (PotStateChangeSignal) s;
					SystemManager.potSystem.setPotState(s2.getPotID(), s2.getRobotID(), s2.getPotState());
				}
				else if(s.getSignalType() == SignalType.TaskStarted) {
					TaskStartedSignal s2 = (TaskStartedSignal) s;
					//SystemManager.taskSystem.taskStarted(s2.getRobotID(), s2.getTaskID());
				}
				else if(s.getSignalType() == SignalType.TaskCompleted) {
					TaskCompletedSignal s2 = (TaskCompletedSignal) s;
					//SystemManager.taskSystem.taskCompleted(s2.getRobotID(), s2.getTaskID());
				}
				else if(s.getSignalType() == SignalType.UpdateBlockPosition) {
					UpdateBlockPositionSignal s2 = (UpdateBlockPositionSignal) s;
					SystemManager.blockSystem.setActualBlockPosition(s2.getBlockID(), s2.getPosition());
				}
				else if(s.getSignalType() == SignalType.UpdatePotPosition) {
					UpdatePotPositionSignal s2 = (UpdatePotPositionSignal) s;
					SystemManager.potSystem.setActualPotPosition(s2.getPotID(), s2.getPosition());
				}
				else if(s.getSignalType() == SignalType.UpdateActualRobotPose) {
					UpdateActualRobotPoseSignal s2 = (UpdateActualRobotPoseSignal) s;
					SystemManager.robotSystem.setActualPose(s2.getRobotID(), s2.getPose());
				}
				else if(s.getSignalType() == SignalType.UpdateEstimatedRobotPose) {
					UpdateEstimatedRobotPoseSignal s2 = (UpdateEstimatedRobotPoseSignal) s;
					SystemManager.robotSystem.setEstimatedPose(s2.getRobotID(), s2.getPose());
				}
				else if(s.getSignalType() == SignalType.RequestTrackerImage) {
					RequestTrackerImageSignal s2 = (RequestTrackerImageSignal) s;
					sendSignal(new ReplyTrackerImageSignal(SystemManager.trackerNumber, s2.getSourceTrackerID(), SystemManager.localTrackerImage));
				}
				else if(s.getSignalType() == SignalType.ReplyTrackerImage) {
					ReplyTrackerImageSignal s2 = (ReplyTrackerImageSignal) s;
					if(s2.getDestinationTrackerID() == SystemManager.trackerNumber) {
						SystemManager.setTrackerImage(s2.getSourceTrackerID(), s2.getSourceTrackerImage());
					}
				}
				else if(s.getSignalType() == SignalType.BroadcastTrackerImage) {
					BroadcastTrackerImageSignal s2 = (BroadcastTrackerImageSignal) s;
					SystemManager.setTrackerImage(s2.getSourceTrackerID(), s2.getSourceTrackerImage());
				}
				else if(s.getSignalType() == SignalType.ReceiveTrackerNumber) {
					ReceiveTrackerNumberSignal s2 = (ReceiveTrackerNumberSignal) s;
					SystemManager.trackerNumber = s2.getTrackerID();
					SystemManager.setTrackerImage(SystemManager.trackerNumber, SystemManager.localTrackerImage);
					sendSignal(new BroadcastTrackerImageSignal(SystemManager.trackerNumber, SystemManager.localTrackerImage));
				}
			}
			
			try { sleep(Client.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}
