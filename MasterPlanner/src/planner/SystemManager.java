package planner;

import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import settings.*;
import client.*;
import path.*;
import robot.*;
import block.*;
import pot.*;
import task.*;
import timer.*;
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
	public static TaskManager taskManager;
	public static SystemTimer timer;
	public static Webcam webcam;
	public static BufferedImage localTrackerImage;
	
	public static DisplayWindow displayWindow;
	public static PlannerWindow plannerWindow;
	public static TaskEditorWindow taskEditorWindow;
	public static AutomaticUpdater plannerWindowUpdater;
	
	private static boolean m_started = false;
	
	public static void initialize(MasterPlanner masterPlanner) {
		trackerNumber = -1;
		
		planner = masterPlanner;
		
		settings = new SettingsManager();
		settings.load();
		
		client = new Client();
		
		timer = new SystemTimer(settings.getTimeLimit() * 60);
		
		console = new SystemConsole();
		
		pathSystem = PathSystem.readFrom(settings.getPathDataFileName());
		if(pathSystem == null) { pathSystem = new PathSystem(); }
		
		robotSystem = new RobotSystem();
		blockSystem = new BlockSystem();
		potSystem = new PotSystem();
		
		displayWindow = new DisplayWindow();
		plannerWindow = new PlannerWindow();
		plannerWindow.setLocation(displayWindow.getLocation().x + displayWindow.getWidth(), displayWindow.getLocation().y);
		plannerWindowUpdater = new AutomaticUpdater(500);
		plannerWindowUpdater.setTarget(plannerWindow);
		plannerWindowUpdater.initialize();
		displayWindow.setVisible(true);
		plannerWindow.setVisible(true);
		taskEditorWindow = new TaskEditorWindow();
		
		displayWindow.update();
		plannerWindow.update();
		
		console.setTarget(plannerWindow);
		
		loadLocalTrackerImage();
		
		if(planner != null) { planner.setTrackerFrameRate(settings.getFrameRate()); }
		
		if(settings.getTakeWebcamSnapshotOnStartup()) {
			updateLocalTrackerImage();
		}
		
		if(settings.getUseStaticStationImages()) {
			for(byte i=0;i<settings.getNumberOfTrackers();i++) {
				displayWindow.setTrackerImage((byte) (i + 1), settings.getStaticStationImage((byte) (i + 1)));
			}
		}
		
		client.initialize();
		if(settings.getAutoConnectOnStartup()) {
			client.connect();
		}
		
		taskManager = TaskManager.readFrom(settings.getTaskListFileName());
		if(taskManager == null) { taskManager = new TaskManager(); }
		
		update();
	}
	
	public static void showTaskEditorWindow() {
		taskEditorWindow.setVisible(true);
	}
	
	public static boolean isIdentified() { return trackerNumber > 0; }
	
	public static boolean isStarted() { return m_started; }
	
	public static void start() {
		if(isStarted() || !isIdentified()) { return; }
		
		client.sendSignal(new Signal(SignalType.StartSimulation));
		m_started = true;
		
		taskEditorWindow.setVisible(false);
		plannerWindow.update();
		
		reset();
		
		taskManager.start();
		timer.start();
	}
	
	public static void setTrackerImage(byte trackerNumber, BufferedImage trackerImage) {
		displayWindow.setTrackerImage(trackerNumber, trackerImage);
	}
	
	public static boolean loadLocalTrackerImage() {
		File trackerImageFile = new File(settings.getTrackerImageFileName());
		if(!trackerImageFile.exists() || !trackerImageFile.isFile()) { return false; }
		BufferedImage trackerImage = null;
		try { trackerImage = ImageIO.read(trackerImageFile); }
		catch(Exception e) { return false; }
		localTrackerImage = trackerImage;
		return true;
	}
	
	public static boolean saveLocalTrackerImage() {
		File trackerImageFile = new File(settings.getTrackerImageFileName());
		try { ImageIO.write(localTrackerImage, "jpg", trackerImageFile); }
		catch(Exception e) { return false; }
		return true;
	}
	
	public static boolean initializeWebcam() {
		webcam = new Webcam(settings.getWebcamResolution());
		if(!webcam.initialize()) {
			console.writeLine("Unable to initialize webcam.");
			return false;
		}
		else {
			console.writeLine("Webcam initialized successfully.");
			return true;
		}
	}
	
	public static boolean updateLocalTrackerImage() {
		if(webcam == null) { initializeWebcam(); }
		
		long startTime = System.currentTimeMillis();
		int maxSeconds = 10;
		boolean snapshotTaken = false;
		do {
			if(webcam.active()) {
				BufferedImage snapshot = webcam.capture();
				if(snapshot != null) {
					localTrackerImage = snapshot;
					saveLocalTrackerImage();
					webcam.deallocate();
					webcam = null;
					snapshotTaken = true;
				}
			}
			else {
				snapshotTaken = false;
			}
		} while(!snapshotTaken && startTime + (maxSeconds * 1000L) > System.currentTimeMillis());
		
		if(snapshotTaken) {
			console.writeLine("Successfully updated tracker image from webcam!");
			if(trackerNumber > 0) {
				displayWindow.setTrackerImage(trackerNumber, localTrackerImage);
			}
			return true;
		}
		else {
			console.writeLine("Failed to take snapshot from webcam.");
			return false;
		}
	}
	
	public static void update() {
		displayWindow.update();
		plannerWindow.update();
		taskEditorWindow.update();
	}
	
	public static void reset() {
		robotSystem.reset();
		blockSystem.reset();
		potSystem.reset();
	}
	
	public static void handlePose(Position position, int angle) {
		if(trackerNumber < 1 || !robotSystem.hasActiveRobot()) { return; }
		
		robotSystem.setActualPosition(robotSystem.getActiveRobotID(), new RobotPosition(position, angle));
		client.sendSignal(new UpdateActualRobotPositionSignal(trackerNumber, robotSystem.getActiveRobot().getActualPosition()));
	}
	
	public static void handleRobotData(byte[] data) {
		if(trackerNumber < 1 || data == null || data.length < 1) { return; }
		robotSystem.handleRobotResponse(data[0]);
	}
	
	public static void handleStationData(int station, byte[] data) {
		
	}
	
	public static void handleTrackerData(byte[] data) {
		
	}
	
	public static boolean sendInstructionToRobot(byte instructionID) {
		if(planner == null || !RobotInstruction.isValid(instructionID)) { return false; }
		sendDataToRobot(new byte[] { instructionID });
		return true;
	}
	
	public static boolean sendDataToRobot(byte[] data) {
		if(planner == null || data == null) { return false; }
		planner.sendDataToRobot(data);
		return true;
	}
	
	public static boolean sendDataToStation(int station, byte[] data) {
		if(planner == null || data == null) { return false; }
		planner.sendDataToStation(station, data);
		return true;
	}
	
	public static boolean sendDataToTraceFile(String data) {
		if(planner == null || data == null) { return false; }
		planner.sendDataToTraceFile(data);
		return true;
	}
	
	public static boolean sendDataToTracker(byte[] data) {
		if(planner == null || data == null) { return false; }
		planner.sendDataToTracker(data);
		return true;
	}
	
	public static boolean sendEstimatedPositionToTracker(RobotPosition robotPosition) {
		if(planner == null || !RobotPosition.isValid(robotPosition)) { return false; }
		planner.sendEstimatedPositionToTracker(robotPosition.getX(), robotPosition.getY(), robotPosition.getAngleDegrees());
		return true;
	}
	
	public static boolean sendEstimatedPositionToTracker(int x, int y, int angle) {
		if(planner == null) { return false; }
		planner.sendEstimatedPositionToTracker(x, y, angle);
		return true;
	}
	
}
