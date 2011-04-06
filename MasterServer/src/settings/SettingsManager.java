package settings;

import java.awt.Dimension;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import server.*;
import shared.*;

public class SettingsManager {
	
	private VariableSystem m_settings;
	
	private InetAddress m_trackerIPAddress[];
	private int m_port;
	private boolean m_autoSaveOnExit;
	private boolean m_ignorePingPongSignals;
	private boolean m_ignorePositionSignals;
	private byte m_signalDebugLevel;
	private boolean m_autoScrollConsoleWindow;
	private int m_maxConsoleHistory;
	private int m_numberOfTrackers;
	private Dimension m_webcamResolution;
	
	final public static String defaultSettingsFileName = "server.ini";
	public static InetAddress[] defaultTrackerIPAddress;
	final public static int defaultPort = Server.DEFAULT_PORT;
	final public static boolean defaultAutoSaveOnExit = true;
	final public static boolean defaultIgnorePingPongSignals = true;
	final public static boolean defaultIgnorePositionSignals = true;
	final public static byte defaultSignalDebugLevel = SignalDebugLevel.Both;
	final public static boolean defaultAutoScrollConsoleWindow = true;
	final public static int defaultMaxConsoleHistory = 1024;
	final public static int defaultNumberOfTrackers = 3;
	final public static Dimension defaultWebcamResolution = new Dimension(640, 480);
	
	public SettingsManager() {
		m_settings = new VariableSystem();
		
		reset();
	}
	
	public void reset() {
		defaultTrackerIPAddress = new InetAddress[3];
		for(byte i=0;i<defaultTrackerIPAddress.length;i++) {
			try { defaultTrackerIPAddress[i] = InetAddress.getByAddress(new byte[] { (byte) 134, (byte) 117, (byte) 28, (byte) (108 + i) }); } catch(UnknownHostException e) { }
		}
		m_port = defaultPort;
		m_autoSaveOnExit = defaultAutoSaveOnExit;
		m_trackerIPAddress = defaultTrackerIPAddress;
		m_ignorePingPongSignals = defaultIgnorePingPongSignals;
		m_ignorePositionSignals = defaultIgnorePositionSignals;
		m_signalDebugLevel = defaultSignalDebugLevel;
		m_autoScrollConsoleWindow = defaultAutoScrollConsoleWindow;
		m_maxConsoleHistory = defaultMaxConsoleHistory;
		m_numberOfTrackers = defaultNumberOfTrackers;
		m_webcamResolution = defaultWebcamResolution;
	}

	public InetAddress getTrackerIPAddress(int trackerNumber) {
		if(trackerNumber < 1 || trackerNumber > m_trackerIPAddress.length) { return null; }
		return m_trackerIPAddress[trackerNumber - 1];
	}
	
	public InetAddress getDefaultTrackerIPAddress(int trackerNumber) {
		if(trackerNumber < 1 || trackerNumber > defaultTrackerIPAddress.length) { return null; }
		return defaultTrackerIPAddress[trackerNumber - 1];
	}
	
	public int getPort() { return m_port; }
	
	public boolean getAutoSaveOnExit() { return m_autoSaveOnExit; }
	
	public boolean getIgnorePingPongSignals() { return m_ignorePingPongSignals; }
	
	public boolean getIgnorePositionSignals() { return m_ignorePositionSignals; }
	
	public byte getSignalDebugLevel() { return m_signalDebugLevel; }
	
	public boolean getAutoScrollConsoleWindow() { return m_autoScrollConsoleWindow; }
	
	public int getMaxConsoleHistory() { return m_maxConsoleHistory; }
	
	public int getNumberOfTrackers() { return m_numberOfTrackers; }
	
	public Dimension getWebcamResolution() { return m_webcamResolution; }
	
	public boolean setTrackerIPAddress(int trackerNumber, String hostAddress) {
		if(hostAddress == null || trackerNumber < 1 || trackerNumber > m_trackerIPAddress.length) { return false; }
		try {
			m_trackerIPAddress[trackerNumber - 1] = InetAddress.getByName(hostAddress);
		}
		catch(UnknownHostException e) {
			return false;
		}
		return true;
	}
	
	public void setAutoSaveOnExit(boolean autoSave) { m_autoSaveOnExit = autoSave; }
	
	public boolean setAutoSaveOnExit(String data) {
		if(data == null) { return false; }
		String value = data.trim();
		if(value.equalsIgnoreCase("true")) {
			m_autoSaveOnExit = true;
			return true;
		}
		else if(value.equalsIgnoreCase("false")) {
			m_autoSaveOnExit = false;
			return true;
		}
		return false;
	}
	
	public void setPort(int port) { if(port >= 0 && port <= 65355) { m_port = port; } }

	public void setIgnorePingPongSignals(boolean ignorePingPongSignals) { m_ignorePingPongSignals = ignorePingPongSignals; }
	
	public boolean setIgnorePingPongSignals(String data) {
		if(data == null) { return false; }
		String value = data.trim();
		if(value.equalsIgnoreCase("true")) {
			m_ignorePingPongSignals = true;
			return true;
		}
		else if(value.equalsIgnoreCase("false")) {
			m_ignorePingPongSignals = false;
			return true;
		}
		return false;
	}
	
	public void setIgnorePositionSignals(boolean ignorePositionSignals) { m_ignorePositionSignals = ignorePositionSignals; }
	
	public boolean setIgnorePositionSignals(String data) {
		if(data == null) { return false; }
		String value = data.trim();
		if(value.equalsIgnoreCase("true")) {
			m_ignorePositionSignals = true;
			return true;
		}
		else if(value.equalsIgnoreCase("false")) {
			m_ignorePositionSignals = false;
			return true;
		}
		return false;
	}
	
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
	
	public void setMaxConsoleHistory(int maxConsoleHistory) { m_maxConsoleHistory = maxConsoleHistory; }
	
	public boolean setNumberOfTrackers(int numberOfTrackers) {
		if(numberOfTrackers < 1) { return false; }
		m_numberOfTrackers = numberOfTrackers;
		return true;
	}
	
	public boolean setWebcamResolution(int x, int y) {
		return setWebcamResolution(new Dimension(x, y));
	}
	
	public boolean setWebcamResolution(Dimension resolution) {
		if(resolution == null) { return false; }
		if(resolution.width < 16 || resolution.height < 16 || resolution.height > 4096 || resolution.width > 4096) { return false; }
		m_webcamResolution = resolution;
		return true;
	}
	
	public boolean setWebcamResolution(String data) {
		if(data == null) { return false; }
		String value = data.trim();
		if(value == null) { return false; }
		
		StringTokenizer st = new StringTokenizer(value, ",x ", false);
		if(st.countTokens() != 2) { return false; }
		
		int x, y;
		try {
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
		}
		catch(NumberFormatException e) {
			return false;
		}
		
		if(x < 16 || y < 16 || x > 4096 || y > 4096) { return false; }
		
		m_webcamResolution = new Dimension(x, y);
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
		for(int i=0;i<m_trackerIPAddress.length;i++) {
			setTrackerIPAddress((i + 1), m_settings.getValue("Robot Tracker " + (i + 1) + " IP", "Settings"));
		}
		try { setPort(Integer.parseInt(m_settings.getValue("Port", "Settings"))); } catch(NumberFormatException e) { }
		setAutoSaveOnExit(m_settings.getValue("Autosave on Exit", "Settings"));
		setIgnorePingPongSignals(m_settings.getValue("Ignore Ping Pong Signals", "Settings"));
		setIgnorePositionSignals(m_settings.getValue("Ignore Position Signals", "Settings"));
		setSignalDebugLevel(SignalDebugLevel.parseFrom(m_settings.getValue("Signal Debug Level", "Settings")));
		setAutoScrollConsoleWindow(m_settings.getValue("Auto-scroll Console Window", "Settings"));
		try { setMaxConsoleHistory(Integer.parseInt(m_settings.getValue("Max Console History", "Settings"))); } catch(NumberFormatException e) { }
		try { setNumberOfTrackers(Integer.parseInt(m_settings.getValue("Number of Trackers", "Settings"))); } catch(NumberFormatException e) { }
		setWebcamResolution(m_settings.getValue("Webcam Resolution", "Settings"));
		
		return true;
	}
	
	public boolean saveTo(String fileName) {
		// update the variable system with the new settings values
		for(int i=0;i<m_trackerIPAddress.length;i++) {
			m_settings.setValue("Robot Tracker " + (i + 1) + " IP Address", m_trackerIPAddress[i].getHostAddress(), "Settings");
		}
		m_settings.setValue("Port", m_port, "Settings");
		m_settings.setValue("Autosave on Exit", m_autoSaveOnExit, "Settings");
		m_settings.setValue("Ignore Ping Pong Signals", m_ignorePingPongSignals, "Settings");
		m_settings.setValue("Ignore Position Signals", m_ignorePositionSignals, "Settings");
		m_settings.setValue("Signal Debug Level", SignalDebugLevel.toString(m_signalDebugLevel), "Settings");
		m_settings.setValue("Auto-scroll Console Window", m_autoScrollConsoleWindow, "Settings");
		m_settings.setValue("Max Console History", m_maxConsoleHistory, "Settings");
		m_settings.setValue("Number of Trackers", m_numberOfTrackers, "Settings");
		m_settings.setValue("Webcam Resolution", m_webcamResolution.width + ", " + m_webcamResolution.height, "Settings");
		
		// group the settings by categories
		m_settings.sort();
		
		return m_settings.writeTo(fileName);
	}
	
}
