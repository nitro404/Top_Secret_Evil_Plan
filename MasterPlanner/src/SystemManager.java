public class SystemManager {
	
	public static SettingsManager settings;
	public static MasterPlanner planner;
	public static DisplayWindow displayWindow;
	public static DebugWindow debugWindow;
	
	public static void initialize(MasterPlanner masterPlanner) {
		planner = masterPlanner;
		
		settings = new SettingsManager();
		settings.load();
		
		planner.setTrackerFrameRate(settings.getFrameRate());
		
		displayWindow = new DisplayWindow();
		debugWindow = new DebugWindow();
		debugWindow.setLocation(displayWindow.getLocation().x + displayWindow.getWidth(), displayWindow.getLocation().y);
		
		displayWindow.setVisible(true);
		debugWindow.setVisible(true);
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
