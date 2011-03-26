package settings;

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
	
	final public static String defaultSettingsFileName = "planner.ini";
	final public static String defaultPathDataFileName = "paths.ini";
	final public static String defaultTrackerImageFileName = "TrackerImage.jpg";
	final public static int defaultFrameRate = 2;
	public static InetAddress defaultServerIPAddress;
	final public static int defaultServerPort = Client.DEFAULT_PORT;
	private byte defaultSignalDebugLevel = SignalDebugLevel.Off;
	
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
		
		// group the settings by categories
		m_settings.sort();
		
		return m_settings.writeTo(fileName);
	}
	
}
