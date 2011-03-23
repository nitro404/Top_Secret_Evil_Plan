package settings;

import java.net.InetAddress;
import java.net.UnknownHostException;
import server.MasterServer;

public class SettingsManager {
	
	private VariableSystem m_settings;
	
	private InetAddress m_trackerIPAddress[];
	private int m_port;
	
	final public static String defaultSettingsFileName = "server.ini";
	public static InetAddress[] defaultTrackerIPAddress;
	final public static int defaultPort = MasterServer.DEFAULT_PORT;
	
	public SettingsManager() {
		m_settings = new VariableSystem();
		
		defaultTrackerIPAddress = new InetAddress[3];
		for(byte i=0;i<defaultTrackerIPAddress.length;i++) {
			try { defaultTrackerIPAddress[i] = InetAddress.getByAddress(new byte[] { (byte) 134, (byte) 117, (byte) 28, (byte) (108 + i) }); } catch(UnknownHostException e) { }
		}
		
		m_port = defaultPort;
		m_trackerIPAddress = defaultTrackerIPAddress;
	}
	
	public int getPort() { return m_port; }
	
	public void setPort(int port) { if(port >= 0 && port <= 65355) { m_port = port; } }
	
	public boolean setTrackerIPAddress(int trackerID, String hostAddress) {
		if(hostAddress == null || trackerID < 1 || trackerID > m_trackerIPAddress.length) { return false; }
		try {
			m_trackerIPAddress[trackerID - 1] = InetAddress.getByName(hostAddress);
		}
		catch(UnknownHostException e) {
			return false;
		}
		return true;
	}
	
	public InetAddress getTrackerIPAddress(int trackerID) {
		if(trackerID < 1 || trackerID > m_trackerIPAddress.length) { return null; }
		return m_trackerIPAddress[trackerID - 1];
	}
	
	public InetAddress getDefaultTrackerIPAddress(int trackerID) {
		if(trackerID < 1 || trackerID > defaultTrackerIPAddress.length) { return null; }
		return defaultTrackerIPAddress[trackerID - 1];
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
		try { setPort(Integer.parseInt(m_settings.getValue("Port", "Settings"))); } catch(NumberFormatException e) { }
		for(int i=0;i<m_trackerIPAddress.length;i++) {
			setTrackerIPAddress((i + 1), m_settings.getValue("Robot Tracker " + (i + 1) + " IP", "Settings"));
		}
		
		return true;
	}
	
	public boolean saveTo(String fileName) {
		// update the variable system with the new settings values
		m_settings.setValue("Port", m_port, "Settings");
		for(int i=0;i<m_trackerIPAddress.length;i++) {
			m_settings.setValue("Robot Tracker " + (i + 1) + " IP Address", m_trackerIPAddress[i].getHostAddress(), "Settings");
		}
		
		// group the settings by categories
		m_settings.sort();
		
		return m_settings.writeTo(fileName);
	}
	
}
