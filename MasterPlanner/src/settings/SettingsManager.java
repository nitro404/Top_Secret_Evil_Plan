package settings;

import java.awt.Dimension;
import java.net.*;
import client.*;
import shared.*;

public class SettingsManager {
	
	private VariableSystem m_settings;
	
	private String m_pathDataFileName;
	private String m_trackerImageFileName;
	private int m_frameRate;
	private InetAddress m_serverIPAddress;
	private int m_serverPort;
	private byte m_signalDebugLevel;
	private boolean m_autoScrollConsoleWindow;
	private boolean m_autoConnectOnStartup;
	private boolean m_takeWebcamSnapshotOnStartup;
	private Dimension m_webcamResolution;
	private int m_timeLimit;
	private int m_numberOfTrackers;
	
	final public static String defaultSettingsFileName = "planner.ini";
	final public static String defaultPathDataFileName = "paths.ini";
	final public static String defaultTrackerImageFileName = "TrackerImage.jpg";
	final public static int defaultFrameRate = 2;
	public static InetAddress defaultServerIPAddress;
	final public static int defaultServerPort = Client.DEFAULT_PORT;
	final public static byte defaultSignalDebugLevel = SignalDebugLevel.Off;
	final public static boolean defaultAutoScrollConsoleWindow = true;
	final public static boolean defaultAutoConnectOnStartup = true;
	final public static boolean defaultTakeWebcamSnapshotOnStartup = true;
	final public static Dimension defaultWebcamResolution = new Dimension(640, 480);
	final public static int defaultTimeLimit = 15;
	final public static int defaultNumberOfTrackers = 3;
	
	public SettingsManager() {
		m_settings = new VariableSystem();
		m_pathDataFileName = defaultPathDataFileName;
		m_trackerImageFileName = defaultTrackerImageFileName;
		m_frameRate = defaultFrameRate;
		try { defaultServerIPAddress = InetAddress.getByName(Client.DEFAULT_HOST); }
		catch(UnknownHostException e) {
			try { defaultServerIPAddress = InetAddress.getLocalHost(); }
			catch(UnknownHostException e2) { }
		}
		m_serverIPAddress = defaultServerIPAddress;
		m_serverPort = defaultServerPort;
		m_signalDebugLevel = defaultSignalDebugLevel;
		m_autoScrollConsoleWindow = defaultAutoScrollConsoleWindow;
		m_autoConnectOnStartup = defaultAutoConnectOnStartup;
		m_takeWebcamSnapshotOnStartup = defaultTakeWebcamSnapshotOnStartup;
		m_webcamResolution = defaultWebcamResolution;
		m_timeLimit = defaultTimeLimit;
		m_numberOfTrackers = defaultNumberOfTrackers;
	}
	
	public String getPathDataFileName() { return m_pathDataFileName; }
	
	public String getTrackerImageFileName() { return m_trackerImageFileName; };
	
	public int getFrameRate() { return m_frameRate; }
	
	public InetAddress getServerIPAddress() {
		return m_serverIPAddress;
	}
	
	public String getServerIPAddressHostName() {
		if(m_serverIPAddress == null) { return null; }
		else { return m_serverIPAddress.getHostName(); }
	}
	
	public int getServerPort() {
		return m_serverPort;
	}
	
	public byte getSignalDebugLevel() { return m_signalDebugLevel; }
	
	public boolean getAutoScrollConsoleWindow() { return m_autoScrollConsoleWindow; }
	
	public boolean getAutoConnectOnStartup() { return m_autoConnectOnStartup; }
	
	public boolean getTakeWebcamSnapshotOnStartup() { return m_takeWebcamSnapshotOnStartup; }
	
	public Dimension getWebcamResolution() { return m_webcamResolution; }
	
	public int getTimeLimit() { return m_timeLimit; }
	
	public int getNumberOfTrackers() { return m_numberOfTrackers; }
	
	public boolean setPathDataFileName(String fileName) {
		if(fileName == null) { return false; }
		m_pathDataFileName = fileName;
		return true;
	}
	
	public boolean setTrackerImageFileName(String fileName) {
		if(fileName == null) { return false; }
		m_trackerImageFileName = fileName;
		return true;
	}
	
	public boolean setFrameRate(int frameRate) {
		if(frameRate < 1 || frameRate > 30) { return false; }
		m_frameRate = frameRate;
		return true;
	}
	
	public boolean setServerIPAddress(String hostName) {
		if(hostName == null) { return false; }
		try {
			setServerIPAddress(InetAddress.getByName(hostName));
			return true;
		}
		catch(UnknownHostException e) { }
		return false;
	}
	
	public boolean setServerIPAddress(InetAddress ipAddress) {
		if(ipAddress == null) { return false; }
		m_serverIPAddress = ipAddress;
		return true;
	}
	
	public void setServerPort(int port) { if(port >= 0 && port <= 65355) { m_serverPort = port; } }
	
	public void setSignalDebugLevel(byte signalDebugLevel) { if(SignalDebugLevel.isValid(signalDebugLevel)) { m_signalDebugLevel = signalDebugLevel; } }
	
	public void setAutoScrollConsoleWindow(boolean autoScroll) { m_autoScrollConsoleWindow = autoScroll; }
	
	public boolean setAutoScrollConsoleWindow(String data) {
		if(data == null) { return false; }
		String value = data.trim();
		if(value.equalsIgnoreCase("true")) {
			m_autoScrollConsoleWindow = true;
			return true;
		}
		else if(value.equalsIgnoreCase("false")) {
			m_autoScrollConsoleWindow = false;
			return true;
		}
		return false;
	}
	
	public void setAutoConnectOnStartup(boolean autoConnect) { m_autoConnectOnStartup = autoConnect; }
	
	public boolean setAutoConnectOnStartup(String data) {
		if(data == null) { return false; }
		String value = data.trim();
		if(value.equalsIgnoreCase("true")) {
			m_autoConnectOnStartup = true;
			return true;
		}
		else if(value.equalsIgnoreCase("false")) {
			m_autoConnectOnStartup = false;
			return true;
		}
		return false;
	}
	
	public void setTakeWebcamSnapshotOnStartup(boolean takeSnapshot) { m_takeWebcamSnapshotOnStartup = takeSnapshot; }
	
	public boolean setTakeWebcamSnapshotOnStartup(String data) {
		if(data == null) { return false; }
		String value = data.trim();
		if(value.equalsIgnoreCase("true")) {
			m_takeWebcamSnapshotOnStartup = true;
			return true;
		}
		else if(value.equalsIgnoreCase("false")) {
			m_takeWebcamSnapshotOnStartup = false;
			return true;
		}
		return false;
	}
	
	public boolean setWebcamResolution(int x, int y) {
		return setWebcamResolution(new Dimension(x, y));
	}
	
	public boolean setWebcamResolution(Dimension resolution) {
		if(resolution == null) { return false; }
		if(resolution.width < 16 || resolution.height < 16) { return false; }
		m_webcamResolution = resolution;
		return true;
	}
	
	public boolean setWebcamResolution(String data) {
		if(data == null) { return false; }
		String value = data.trim();
		if(value == null) { return false; }
		
		String[] values = value.trim().split("x, ");
		if(values.length != 2) { return false; }
		
		int x, y;
		try {
			x = Integer.parseInt(values[0]);
			y = Integer.parseInt(values[1]);
		}
		catch(NumberFormatException e) {
			return false;
		}
		
		if(x < 16 || y < 16) { return false; }
		
		m_webcamResolution = new Dimension(x, y);
		return true;
	}
	
	public boolean setTimeLimit(int timeLimit) {
		if(timeLimit <= 0) { return false; }
		m_timeLimit = timeLimit;
		return true;
	}
	
	public boolean setNumberOfTrackers(int numberOfTrackers) {
		if(numberOfTrackers < 1) { return false; }
		m_numberOfTrackers = numberOfTrackers;
		return true;
	}
	
	public boolean load() { return loadFrom(defaultSettingsFileName); }
	
	public boolean save() { return saveTo(defaultSettingsFileName); }
	
	public boolean loadFrom(String fileName) {
		VariableSystem variables = VariableSystem.readFrom(fileName);
		if(variables == null) {
			System.out.println("ERROR: Unable to load settings file: \"" + fileName + "\".");
			return false;
		}
		
		m_settings = variables;
		
		// create local variables instantiated with data parsed from the variable system
		setPathDataFileName(m_settings.getValue("Path File", "Data Files"));
		setTrackerImageFileName(m_settings.getValue("Tracker Image", "Data Files"));
		try { setFrameRate(Integer.parseInt(m_settings.getValue("Framerate", "Settings"))); } catch(NumberFormatException e) { }
		setServerIPAddress(m_settings.getValue("Server IP Address", "Settings"));
		try { setServerPort(Integer.parseInt(m_settings.getValue("Server Port", "Settings"))); } catch(NumberFormatException e) { }
		setSignalDebugLevel(SignalDebugLevel.parseFrom(m_settings.getValue("Signal Debug Level", "Settings")));
		setAutoScrollConsoleWindow(m_settings.getValue("Auto-scroll Console Window", "Settings"));
		setAutoConnectOnStartup(m_settings.getValue("Auto-connect on Startup", "Settings"));
		setTakeWebcamSnapshotOnStartup(m_settings.getValue("Take Webcam Snapshot on Startup", "Settings"));
		setWebcamResolution(m_settings.getValue("Webcam Resolution", "Settings"));
		try { setTimeLimit(Integer.parseInt(m_settings.getValue("Time Limit", "Settings"))); } catch(NumberFormatException e) { }
		try { setNumberOfTrackers(Integer.parseInt(m_settings.getValue("Number of Trackers", "Settings"))); } catch(NumberFormatException e) { }
		
		return true;
	}
	
	public boolean saveTo(String fileName) {
		// update the variable system with the new settings values
		m_settings.setValue("Path File", m_pathDataFileName, "Data Files");
		m_settings.setValue("Tracker Image", m_trackerImageFileName, "Data Files");
		m_settings.setValue("Framerate", m_frameRate, "Settings");
		m_settings.setValue("Server IP Address", m_serverIPAddress.getHostName(), "Settings");
		m_settings.setValue("Server Port", m_serverPort, "Settings");
		m_settings.setValue("Signal Debug Level", SignalDebugLevel.toString(m_signalDebugLevel), "Settings");
		m_settings.setValue("Auto-scroll Console Window", m_autoScrollConsoleWindow, "Settings");
		m_settings.setValue("Auto-connect on Startup", m_autoConnectOnStartup, "Settings");
		m_settings.setValue("Take Webcam Snapshot on Startup", m_takeWebcamSnapshotOnStartup, "Settings");
		m_settings.setValue("Webcam Resolution", m_webcamResolution.width + ", " + m_webcamResolution.height, "Settings");
		m_settings.setValue("Time Limit", m_timeLimit, "Settings");
		m_settings.setValue("Number of Trackers", m_numberOfTrackers, "Settings");
		
		// group the settings by categories
		m_settings.sort();
		
		return m_settings.writeTo(fileName);
	}
	
}
