package settings;

import java.net.InetAddress;
import java.net.UnknownHostException;
import server.*;
import shared.*;

public class SettingsManager {
	
	private VariableSystem m_settings;
	
	private InetAddress m_trackerIPAddress[];
	private int m_port;
	private byte m_signalDebugLevel;
	private boolean m_autoScrollConsoleWindow;
	
	final public static String defaultSettingsFileName = "server.ini";
	public static InetAddress[] defaultTrackerIPAddress;
	final public static int defaultPort = Server.DEFAULT_PORT;
	private byte defaultSignalDebugLevel = SignalDebugLevel.Off;
	private boolean defaultAutoScrollConsoleWindow = true;
	
	public SettingsManager() {
		m_settings = new VariableSystem();
		
		defaultTrackerIPAddress = new InetAddress[3];
		for(byte i=0;i<defaultTrackerIPAddress.length;i++) {
			try { defaultTrackerIPAddress[i] = InetAddress.getByAddress(new byte[] { (byte) 134, (byte) 117, (byte) 28, (byte) (108 + i) }); } catch(UnknownHostException e) { }
		}
		
		m_port = defaultPort;
		m_trackerIPAddress = defaultTrackerIPAddress;
		m_signalDebugLevel = defaultSignalDebugLevel;
		m_autoScrollConsoleWindow = defaultAutoScrollConsoleWindow;
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
	
	public byte getSignalDebugLevel() { return m_signalDebugLevel; }
	
	public boolean getAutoScrollConsoleWindow() { return m_autoScrollConsoleWindow; }
	
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
	
	public void setPort(int port) { if(port >= 0 && port <= 65355) { m_port = port; } }
	
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
		setSignalDebugLevel(SignalDebugLevel.parseFrom(m_settings.getValue("Signal Debug Level", "Settings")));
		setAutoScrollConsoleWindow(m_settings.getValue("Auto-scroll Console Window", "Settings"));
		
		return true;
	}
	
	public boolean saveTo(String fileName) {
		// update the variable system with the new settings values
		for(int i=0;i<m_trackerIPAddress.length;i++) {
			m_settings.setValue("Robot Tracker " + (i + 1) + " IP Address", m_trackerIPAddress[i].getHostAddress(), "Settings");
		}
		m_settings.setValue("Port", m_port, "Settings");
		m_settings.setValue("Signal Debug Level", SignalDebugLevel.toString(m_signalDebugLevel), "Settings");
		m_settings.setValue("Auto-scroll Console Window", m_autoScrollConsoleWindow, "Settings");
		
		// group the settings by categories
		m_settings.sort();
		
		return m_settings.writeTo(fileName);
	}
	
}
