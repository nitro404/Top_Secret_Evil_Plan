package planner;

import java.awt.image.BufferedImage;

import settings.*;
import client.*;
import path.*;
import robot.*;
import block.*;
import pot.*;
import gui.*;
import imaging.*;
import shared.*;
import signal.*;

public class SystemManager {
	
	public static byte trackerNumber;
	public static SettingsManager settings;
	public static SystemConsole console;
	public static Client client;
	public static MasterPlanner planner;
	public static PathSystem pathSystem;
	public static RobotSystem robotSystem;
	public static BlockSystem blockSystem;
	public static PotSystem potSystem;
	public static Webcam webcam;
	public static BufferedImage localTrackerImage;
	
	public static DisplayWindow displayWindow;
	public static DebugWindow debugWindow;
	
	private static boolean m_started = false;
	
	public static void initialize(MasterPlanner masterPlanner) {
		trackerNumber = -1;
		
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
		
		pathSystem = PathSystem.readFrom(settings.getPathDataFile());
		
		robotSystem = new RobotSystem();
		blockSystem = new BlockSystem();
		potSystem = new PotSystem();
		
		webcam = new Webcam(640, 480);
		if(!webcam.initialize()) {
			console.writeLine("Unable to initialize webcam.");
		}
		else {
			console.writeLine("Webcam initialized successfully.");
			while(!updateLocalTrackerImage());
		}
		
		client.initialize();
		client.connect();
	}
	
	public static boolean isIdentified() { return trackerNumber > 0; }
	
	public static boolean isStarted() { return m_started; }
	
	public static void start() {
		if(isStarted() || !isIdentified()) { return; }
		
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
	
	public static void setTrackerImage(byte trackerNumber, BufferedImage trackerImage) {
		displayWindow.setTrackerImage(trackerNumber, trackerImage);
	}
	
	public static boolean updateLocalTrackerImage() {
		if(webcam.active()) {
			BufferedImage snapshot = webcam.capture();
			if(snapshot != null) {
				localTrackerImage = snapshot;
				webcam.deallocate();
				return true;
			}
		}
		return false;
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
