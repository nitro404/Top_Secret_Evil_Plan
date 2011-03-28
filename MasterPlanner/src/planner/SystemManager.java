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
	
	final public static int MAX_NUMBER_OF_TRACKERS = 3;
	final public static int TIME_LIMIT = 15;
	
	public static void initialize(MasterPlanner masterPlanner) {
		trackerNumber = -1;
		
		planner = masterPlanner;
		
		settings = new SettingsManager();
		settings.load();
		
		client = new Client();
		
		timer = new SystemTimer(TIME_LIMIT * 60);
		
		console = new SystemConsole();
		
		pathSystem = PathSystem.readFrom(settings.getPathDataFileName());
		
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
		
		webcam = new Webcam(Webcam.DEFAULT_RESOLUTION);
		if(!webcam.initialize()) {
			console.writeLine("Unable to initialize webcam.");
		}
		else {
			console.writeLine("Webcam initialized successfully.");
			while(!updateLocalTrackerImage());
		}
		
		client.initialize();
		client.connect();
		
		taskManager = new TaskManager();
	}
	
	public static boolean isIdentified() { return trackerNumber > 0; }
	
	public static boolean isStarted() { return m_started; }
	
	public static void start() {
		if(isStarted() || !isIdentified()) { return; }
		
		client.sendSignal(new Signal(SignalType.StartSimulation));
		m_started = true;
		
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
	
	public static boolean updateLocalTrackerImage() {
		if(webcam.active()) {
			BufferedImage snapshot = webcam.capture();
			if(snapshot != null) {
				localTrackerImage = snapshot;
				saveLocalTrackerImage();
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
