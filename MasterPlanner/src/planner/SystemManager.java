package planner;

import path.*;
import settings.*;
import robot.*;
import block.*;
import pot.*;
import client.*;
import shared.*;
import signal.*;

public class SystemManager {
	
	public static SettingsManager settings;
	public static SystemConsole console;
	public static Client client;
	public static MasterPlanner planner;
	public static PathSystem paths;
	public static RobotSystem robotSystem;
	public static BlockSystem blockSystem;
	public static PotSystem potSystem;
	
	public static DisplayWindow displayWindow;
	public static DebugWindow debugWindow;
	
	private static boolean m_started = false;
	
	public static void initialize(MasterPlanner masterPlanner) {
		planner = masterPlanner;
		
		settings = new SettingsManager();
		settings.load();
		
		client = new Client();
		
		console = new SystemConsole();
		
		displayWindow = new DisplayWindow();
		debugWindow = new DebugWindow();
		debugWindow.setLocation(displayWindow.getLocation().x + displayWindow.getWidth(), displayWindow.getLocation().y);
		displayWindow.setVisible(true);
		debugWindow.setVisible(true);
		
		console.setTarget(debugWindow);
		
		if(planner != null) { planner.setTrackerFrameRate(settings.getFrameRate()); }
		
		paths = PathSystem.readFrom(settings.getPathDataFile());
		
		robotSystem = new RobotSystem();
		blockSystem = new BlockSystem();
		potSystem = new PotSystem();
		
		client.initialize();
		client.connect();
	}
	
	public boolean isStarted() { return m_started; }
	
	public static void start() {
		if(m_started) { return; }
		client.sendSignal(new Signal(SignalType.StartSimulation));
		m_started = true;
		
		/*
		client.sendSignal(new BlockStateChangeSignal((byte) 1, (byte) 1, BlockState.Delivered));
		client.sendSignal(new PotStateChangeSignal((byte) 0, (byte) 2, PotState.Delivered));
		client.sendSignal(new RobotStateChangeSignal((byte) 2, RobotState.FindingBlock));
		client.sendSignal(new TaskCompletedSignal((byte) 4, (byte) 2));
		client.sendSignal(new TaskStartedSignal((byte) 5, (byte) 2));
		client.sendSignal(new UpdateActualRobotPoseSignal((byte) 2, 320, 240, 90));
		client.sendSignal(new UpdateEstimatedRobotPoseSignal((byte) 1, 259, 136, 42));
		client.sendSignal(new UpdateBlockPositionSignal((byte) 8, 124, 248));
		client.sendSignal(new UpdatePotPositionSignal((byte) 2, 90, 138));
		*/
	}
	
	public static void handlePose(Position position, int angle) {
		
	}
	
	public static void handleRobotData(byte[] data) {
		
	}
	
	public static void handleStationData(int station, byte[] data) {
		
	}
	
	public static void handleTrackerData(byte[] data) {
		
	}
	
}
