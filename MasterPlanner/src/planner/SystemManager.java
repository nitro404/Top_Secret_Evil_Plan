package planner;

import path.*;
import settings.*;
import robot.*;
import block.*;
import pot.*;
import client.*;
import shared.*;

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
		
		client.initialize(Client.DEFAULT_HOST, Client.DEFAULT_PORT);
		client.connect();
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
