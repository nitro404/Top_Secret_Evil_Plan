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
	public static DebugWindow debugWindow;
	
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
		debugWindow = new DebugWindow();
		debugWindow.setLocation(displayWindow.getLocation().x + displayWindow.getWidth(), displayWindow.getLocation().y);
		displayWindow.setVisible(true);
		debugWindow.setVisible(true);
		
		displayWindow.update();
		debugWindow.update();
		
		console.setTarget(debugWindow);
		
		loadLocalTrackerImage();
		
		if(planner != null) { planner.setTrackerFrameRate(settings.getFrameRate()); }
		
		if(settings.getTakeWebcamSnapshotOnStartup()) {
			updateLocalTrackerImage();
		}
		
		client.initialize();
		if(settings.getAutoConnectOnStartup()) {
			client.connect();
		}
		
		taskManager = new TaskManager();
	}
	
	public static boolean isIdentified() { return trackerNumber > 0; }
	
	public static boolean isStarted() { return m_started; }
	
	public static void start() {
		if(isStarted() || !isIdentified()) { return; }
		
		client.sendSignal(new Signal(SignalType.StartSimulation));
		m_started = true;
		
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
	
	public static void reset() {
		robotSystem.reset();
		blockSystem.reset();
		potSystem.reset();
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
