package settings;

import java.util.Vector;
import java.util.StringTokenizer;
import java.io.File;
import java.net.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import block.*;
import robot.*;
import pot.*;
import imaging.*;
import client.*;
import planner.*;
import shared.*;

public class SettingsManager {
	
	private VariableSystem m_settings;
	
	private String m_pathDataFileName;
	private String m_taskListFileName;
	private String m_trackerImageFileName;
	private String m_staticStationImageFileNameFormat;
	private BufferedImage[] m_staticStationImage;
	private int m_frameRate;
	private InetAddress m_serverIPAddress;
	private int m_serverPort;
	private byte m_signalDebugLevel;
	private boolean m_autoScrollConsoleWindow;
	private boolean m_autoConnectOnStartup;
	private boolean m_takeWebcamSnapshotOnStartup;
	private boolean m_useStaticStationImages;
	private Dimension m_webcamResolution;
	private int m_timeLimit;
	private int m_numberOfTrackers;
	private Color m_selectedColour;
	private Color m_missingColour;
	private Color m_vertexColour;
	private Color m_edgeColour;
	private Color m_robotColour;
	private Color m_blockColour;
	private Color m_potColour;
	private Color m_dropOffLocationColour;
	private Vector<RobotPosition> m_initialRobotPositions;
	private Vector<Position> m_initialBlockPositions;
	private Vector<Position> m_initialPotPositions;
	private Vector<Position> m_dropOffLocations;
	
	final public static String defaultSettingsFileName = "planner.ini";
	final public static String defaultPathDataFileName = "paths.ini";
	final public static String defaultTaskListFileName = "tasklist.ini";
	final public static String defaultTrackerImageFileName = "TrackerImage.jpg";
	final public static String defaultStaticStationImageFileNameFormat = "Station.jpg";
	final public static int defaultFrameRate = 2;
	public static InetAddress defaultServerIPAddress;
	final public static int defaultServerPort = Client.DEFAULT_PORT;
	final public static byte defaultSignalDebugLevel = SignalDebugLevel.Off;
	final public static boolean defaultAutoScrollConsoleWindow = true;
	final public static boolean defaultAutoConnectOnStartup = true;
	final public static boolean defaultTakeWebcamSnapshotOnStartup = true;
	final public static boolean defaultUseStaticStationImages = true;
	final public static Dimension defaultWebcamResolution = Webcam.DEFAULT_RESOLUTION;
	final public static int defaultTimeLimit = 15;
	final public static int defaultNumberOfTrackers = 3;
	final public static Color defaultSelectedColour = Color.RED;
	final public static Color defaultMissingColour = Color.GRAY;
	final public static Color defaultVertexColour = Color.BLUE;
	final public static Color defaultEdgeColour = Color.GREEN;
	final public static Color defaultRobotColour = Color.GREEN;
	final public static Color defaultBlockColour = Color.RED;
	final public static Color defaultPotColour = Color.BLUE;
	final public static Color defaultDropOffLocationColour = Color.MAGENTA;
	
	public SettingsManager() {
		m_settings = new VariableSystem();
		m_pathDataFileName = defaultPathDataFileName;
		m_taskListFileName = defaultTaskListFileName;
		m_trackerImageFileName = defaultTrackerImageFileName;
		m_staticStationImageFileNameFormat = defaultStaticStationImageFileNameFormat;
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
		m_useStaticStationImages = defaultUseStaticStationImages;
		m_webcamResolution = defaultWebcamResolution;
		m_timeLimit = defaultTimeLimit;
		m_numberOfTrackers = defaultNumberOfTrackers;
		m_selectedColour = defaultSelectedColour;
		m_missingColour = defaultMissingColour;
		m_vertexColour = defaultVertexColour;
		m_edgeColour = defaultEdgeColour;
		m_robotColour = defaultRobotColour;
		m_blockColour = defaultBlockColour;
		m_potColour = defaultPotColour;
		m_dropOffLocationColour = defaultDropOffLocationColour;
		m_initialRobotPositions = new Vector<RobotPosition>();
		for(int i=0;i<RobotSystem.defaultRobotPositions.length;i++) {
			m_initialRobotPositions.add(RobotSystem.defaultRobotPositions[i]);
		}
		m_initialBlockPositions = new Vector<Position>();
		for(int i=0;i<BlockSystem.defaultBlockPositions.length;i++) {
			m_initialBlockPositions.add(BlockSystem.defaultBlockPositions[i]);
		}
		m_initialPotPositions = new Vector<Position>();
		for(int i=0;i<PotSystem.defaultPotPositions.length;i++) {
			m_initialPotPositions.add(PotSystem.defaultPotPositions[i]);
		}
		m_dropOffLocations = new Vector<Position>();
		for(int i=0;i<BlockSystem.defaultDropOffLocations.length;i++) {
			m_dropOffLocations.add(BlockSystem.defaultDropOffLocations[i]);
		}
	}
	
	public String getPathDataFileName() { return m_pathDataFileName; }
	
	public String getTaskListFileName() { return m_taskListFileName; }
	
	public String getTrackerImageFileName() { return m_trackerImageFileName; };
	
	public String getStaticStationImageFileNameFormat() { return m_staticStationImageFileNameFormat; }
	
	public String getStaticStationImageFileName(byte trackerNumber) {
		String fileName = null;
		String extension = null;
		
		// locate the beginning of the file extension
		int separatorIndex = -1;
		for(int i=m_staticStationImageFileNameFormat.length()-1;i>=0;i--) {
			if(m_staticStationImageFileNameFormat.charAt(i) == '.') {
				separatorIndex = i;
				break;
			}
		}
		
		// generate the new file name
		if(separatorIndex == -1) {
			fileName = m_staticStationImageFileNameFormat;
			extension = "";
		}
		else {
			fileName = m_staticStationImageFileNameFormat.substring(0, separatorIndex);
			extension = m_staticStationImageFileNameFormat.substring(separatorIndex + 1, m_staticStationImageFileNameFormat.length());
		}
		
		// generate the new output file
		return fileName + " " + trackerNumber + (extension.length() > 0 ? "." + extension : "");
	}
	
	public BufferedImage getStaticStationImage(byte trackerNumber) {
		if(trackerNumber < 1 || trackerNumber > m_numberOfTrackers) { return null; }
		return m_staticStationImage[trackerNumber - 1];
	}
	
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
	
	public boolean getUseStaticStationImages() { return m_useStaticStationImages; }
	
	public Dimension getWebcamResolution() { return m_webcamResolution; }
	
	public int getTimeLimit() { return m_timeLimit; }
	
	public int getNumberOfTrackers() { return m_numberOfTrackers; }
	
	public Color getSelectedColour() { return m_selectedColour; }
	public Color getMissingColour() { return m_missingColour; }
	public Color getVertexColour() { return m_vertexColour; }
	public Color getEdgeColour() { return m_edgeColour; }
	public Color getRobotColour() { return m_robotColour; }
	public Color getBlockColour() { return m_blockColour; }
	public Color getPotColour() { return m_potColour; }
	public Color getDropOffLocationColour() { return m_dropOffLocationColour; }
	
	public RobotPosition getInitialRobotPosition(byte robotID) {
		if(robotID < 0 || robotID >= m_initialRobotPositions.size()) { return null; }
		return m_initialRobotPositions.elementAt(robotID);
	}
	
	public Position getInitialBlockPosition(byte blockID) {
		if(blockID < 0 || blockID >= m_initialBlockPositions.size()) { return null; }
		return m_initialBlockPositions.elementAt(blockID);
	}
	
	public Position getInitialPotPosition(byte potID) {
		if(potID < 0 || potID >= m_initialPotPositions.size()) { return null; }
		return m_initialPotPositions.elementAt(potID);
	}
	
	public Position getDropOffLocation(byte dropOffLocationID) {
		if(dropOffLocationID < 0 || dropOffLocationID >= m_dropOffLocations.size()) { return null; }
		return m_dropOffLocations.elementAt(dropOffLocationID);
	}
	
	public boolean setPathDataFileName(String fileName) {
		if(fileName == null) { return false; }
		String data = fileName.trim();
		if(data.length() == 0) { return false; }
		m_pathDataFileName = data;
		return true;
	}
	
	public boolean setTaskListFileName(String fileName) {
		if(fileName == null) { return false; }
		String data = fileName.trim();
		if(data.length() == 0) { return false; }
		m_taskListFileName = data;
		return true;
	}
	
	public boolean setTrackerImageFileName(String fileName) {
		if(fileName == null) { return false; }
		String data = fileName.trim();
		if(data.length() == 0) { return false; }
		m_trackerImageFileName = data;
		return true;
	}
	
	public boolean setStaticStationImageFileNameFormat(String fileNameFormat) {
		if(fileNameFormat == null) { return false; }
		String data = fileNameFormat.trim();
		if(data.length() == 0) { return false; }
		m_staticStationImageFileNameFormat = data;
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
	
	public void setUseStaticStationImages(boolean staticImages) { m_useStaticStationImages = staticImages; }
	
	public boolean setUseStaticStationImages(String data) {
		if(data == null) { return false; }
		String value = data.trim();
		if(value.equalsIgnoreCase("true")) {
			m_useStaticStationImages = true;
			return true;
		}
		else if(value.equalsIgnoreCase("false")) {
			m_useStaticStationImages = false;
			return true;
		}
		return false;
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
	
	public void resetAllColours() {
		m_selectedColour = defaultSelectedColour;
		m_missingColour = defaultMissingColour;
		m_vertexColour = defaultVertexColour;
		m_edgeColour = defaultEdgeColour;
		m_robotColour = defaultRobotColour;
		m_blockColour = defaultBlockColour;
		m_potColour = defaultPotColour;
		m_dropOffLocationColour = defaultDropOffLocationColour;
	}
	
	public boolean setSelectedColour(Color c) { if(c != null) { m_selectedColour = c; return true; } return false; }
	public boolean setMissingColour(Color c) { if(c != null) { m_missingColour = c; return true; } return false; }
	public boolean setVertexColour(Color c) { if(c != null) { m_vertexColour = c; return true; } return false; }
	public boolean setEdgeColour(Color c) { if(c != null) { m_edgeColour = c; return true; } return false; }
	public boolean setRobotColour(Color c) { if(c != null) { m_robotColour = c; return true; } return false; }
	public boolean setBlockColour(Color c) { if(c != null) { m_blockColour = c; return true; } return false; }
	public boolean setPotColour(Color c) { if(c != null) { m_potColour = c; return true; } return false; }
	public boolean setDropOffLocationColour(Color c) { if(c != null) { m_dropOffLocationColour = c; return true; } return false; }
	
	public boolean setSelectedColour(String data) { return setSelectedColour(parseColour(data)); }
	public boolean setMissingColour(String data) { return setMissingColour(parseColour(data)); }
	public boolean setVertexColour(String data) { return setVertexColour(parseColour(data)); }
	public boolean setEdgeColour(String data) { return setEdgeColour(parseColour(data)); }
	public boolean setRobotColour(String data) { return setRobotColour(parseColour(data)); }
	public boolean setBlockColour(String data) { return setBlockColour(parseColour(data)); }
	public boolean setPotColour(String data) { return setPotColour(parseColour(data)); }
	public boolean setDropOffLocationColour(String data) { return setDropOffLocationColour(parseColour(data)); }
	
	public boolean setInitialRobotPosition(byte robotID, RobotPosition initialPosition) {
		if(robotID < 0 || robotID >= m_initialRobotPositions.size() || !RobotPosition.isValid(initialPosition)) { return false; }
		m_initialRobotPositions.set(robotID, initialPosition);
		return true;
	}
	
	public boolean setInitialBlockPosition(byte blockID, Position initialPosition) {
		if(blockID < 0 || blockID >= m_initialBlockPositions.size() || !Position.isValid(initialPosition)) { return false; }
		m_initialBlockPositions.set(blockID, initialPosition);
		return true;
	}

	public boolean setInitialPotPosition(byte potID, Position initialPosition) {
		if(potID < 0 || potID >= m_initialPotPositions.size() || !Position.isValid(initialPosition)) { return false; }
		m_initialPotPositions.set(potID, initialPosition);
		return true;
	}
	
	public boolean setDropOffLocation(byte dropOffLocationID, Position position) {
		if(dropOffLocationID < 0 || dropOffLocationID >= m_dropOffLocations.size() || !Position.isValid(position)) { return false; }
		m_dropOffLocations.set(dropOffLocationID, position);
		return true;
	}
	
	public boolean setInitialRobotPosition(Variable v) {
		if(v == null) { return false; }
		return setInitialRobotPosition(parseRobotID(v.getID()), parseRobotPosition(v.getValue()));
	}
	
	public boolean setInitialBlockPosition(Variable v) {
		if(v == null) { return false; }
		return setInitialBlockPosition(parseBlockID(v.getID()), parsePosition(v.getValue()));
	}

	public boolean setInitialPotPosition(Variable v) {
		if(v == null) { return false; }
		return setInitialPotPosition(parsePotID(v.getID()), parsePosition(v.getValue()));
	}
	
	public boolean setDropOffLocation(Variable v) {
		if(v == null) { return false; }
		return setDropOffLocation(parseDropOffLocationID(v.getID()), parsePosition(v.getValue()));
	}
	
	private static Color parseColour(String data) {
		if(data == null) { return null; }
		String temp = data.trim();
		if(temp.length() == 0) { return null; }
		StringTokenizer st = new StringTokenizer(temp, ", ");
		if(st.countTokens() != 3) { return null; }
		int r, g, b;
		try {
			r = Integer.parseInt(st.nextToken());
			g = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
		}
		catch(NumberFormatException e) { return null; }
		return new Color(r, g, b);
	}
	
	private static byte parseRobotID(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		if(temp.length() == 0) { return -1; }
		StringTokenizer st = new StringTokenizer(temp, " ", false);
		if(st.countTokens() != 2) { return -1; }
		if(!st.nextToken().equalsIgnoreCase("Robot")) { return -1; }
		byte id;
		try { id = Byte.parseByte(st.nextToken()); }
		catch(NumberFormatException e) { return -1; }
		return id;
	}
	
	private static byte parseBlockID(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		if(temp.length() == 0) { return -1; }
		StringTokenizer st = new StringTokenizer(temp, " ", false);
		if(st.countTokens() != 2) { return -1; }
		if(!st.nextToken().equalsIgnoreCase("Block")) { return -1; }
		byte id;
		try { id = Byte.parseByte(st.nextToken()); }
		catch(NumberFormatException e) { return -1; }
		return id;
	}
	
	private static byte parsePotID(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		if(temp.length() == 0) { return -1; }
		StringTokenizer st = new StringTokenizer(temp, " ", false);
		if(st.countTokens() != 2) { return -1; }
		if(!st.nextToken().equalsIgnoreCase("Pot")) { return -1; }
		byte id;
		try { id = Byte.parseByte(st.nextToken()); }
		catch(NumberFormatException e) { return -1; }
		return id;
	}
	
	private static byte parseDropOffLocationID(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		if(temp.length() == 0) { return -1; }
		StringTokenizer st = new StringTokenizer(temp, " ", false);
		if(st.countTokens() != 4) { return -1; }
		if(!st.nextToken().equalsIgnoreCase("Drop")) { return -1; }
		if(!st.nextToken().equalsIgnoreCase("Off")) { return -1; }
		if(!st.nextToken().equalsIgnoreCase("Location")) { return -1; }
		byte id;
		try { id = Byte.parseByte(st.nextToken()); }
		catch(NumberFormatException e) { return -1; }
		return id;
	}
	
	private static Position parsePosition(String data) {
		if(data == null) { return null; }
		String temp = data.trim();
		if(temp.length() == 0) { return null; }
		StringTokenizer st = new StringTokenizer(temp, "(,) ", false);
		if(st.countTokens() != 2) { return null; }
		int x, y;
		try {
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
		}
		catch(NumberFormatException e) {
			return null;
		}
		Position p = new Position(x, y);
		if(!p.isValid()) { return null; }
		return p;
	}
	
	private static RobotPosition parseRobotPosition(String data) {
		if(data == null) { return null; }
		String temp = data.trim();
		if(temp.length() == 0) { return null; }
		StringTokenizer st = new StringTokenizer(temp, "(,) ", false);
		if(st.countTokens() != 3) { return null; }
		int x, y, a;
		try {
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			a = Integer.parseInt(st.nextToken());
		}
		catch(NumberFormatException e) {
			return null;
		}
		RobotPosition p = new RobotPosition(x, y, a);
		if(!p.isValid()) { return null; }
		return p;
	}
	
	private void loadStaticStationImages() {
		m_staticStationImage = new BufferedImage[m_numberOfTrackers];
		for(byte i=0;i<m_numberOfTrackers;i++) {
			try {
				m_staticStationImage[i] = ImageIO.read(new File(getStaticStationImageFileName((byte) (i+1))));
			}
			catch(Exception e) { }
		}
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
		setTaskListFileName(m_settings.getValue("Task List File", "Data Files"));
		setTrackerImageFileName(m_settings.getValue("Tracker Image", "Data Files"));
		setStaticStationImageFileNameFormat(m_settings.getValue("Static Station Image File Name Format", "Data Files"));
		try { setFrameRate(Integer.parseInt(m_settings.getValue("Framerate", "Settings"))); } catch(NumberFormatException e) { }
		setServerIPAddress(m_settings.getValue("Server IP Address", "Settings"));
		try { setServerPort(Integer.parseInt(m_settings.getValue("Server Port", "Settings"))); } catch(NumberFormatException e) { }
		setSignalDebugLevel(SignalDebugLevel.parseFrom(m_settings.getValue("Signal Debug Level", "Settings")));
		setAutoScrollConsoleWindow(m_settings.getValue("Auto-scroll Console Window", "Settings"));
		setAutoConnectOnStartup(m_settings.getValue("Auto-connect on Startup", "Settings"));
		setTakeWebcamSnapshotOnStartup(m_settings.getValue("Take Webcam Snapshot on Startup", "Settings"));
		setUseStaticStationImages(m_settings.getValue("Use Static Station Image", "Settings"));
		setWebcamResolution(m_settings.getValue("Webcam Resolution", "Settings"));
		try { setTimeLimit(Integer.parseInt(m_settings.getValue("Time Limit", "Settings"))); } catch(NumberFormatException e) { }
		try { setNumberOfTrackers(Integer.parseInt(m_settings.getValue("Number of Trackers", "Settings"))); } catch(NumberFormatException e) { }
		setSelectedColour(parseColour(m_settings.getValue("Selected Colour", "Colours")));
		setVertexColour(parseColour(m_settings.getValue("Vertex Colour", "Colours")));
		setEdgeColour(parseColour(m_settings.getValue("Edge Colour", "Colours")));
		setRobotColour(parseColour(m_settings.getValue("Robot Colour", "Colours")));
		setBlockColour(parseColour(m_settings.getValue("Block Colour", "Colours")));
		setPotColour(parseColour(m_settings.getValue("Pot Colour", "Colours")));
		setDropOffLocationColour(parseColour(m_settings.getValue("Drop Off Location Colour", "Colours")));
		for(int i=0;i<m_initialRobotPositions.size();i++) {
			setInitialRobotPosition(m_settings.getVariable("Robot " + i, "Robot Positions"));
		}
		for(int i=0;i<m_initialBlockPositions.size();i++) {
			setInitialBlockPosition(m_settings.getVariable("Block " + i, "Block Positions"));
		}
		for(int i=0;i<m_initialPotPositions.size();i++) {
			setInitialPotPosition(m_settings.getVariable("Pot " + i, "Pot Positions"));
		}
		for(int i=0;i<m_dropOffLocations.size();i++) {
			setDropOffLocation(m_settings.getVariable("Drop Off Location " + i, "Drop Off Locations"));
		}
		
		// load static tracker images
		loadStaticStationImages();
		
		return true;
	}
	
	public boolean saveTo(String fileName) {
		// update the variable system with the new settings values
		m_settings.setValue("Path File", m_pathDataFileName, "Data Files");
		m_settings.setValue("Task List File", m_taskListFileName, "Data Files");
		m_settings.setValue("Tracker Image", m_trackerImageFileName, "Data Files");
		m_settings.setValue("Static Station Image File Name Format", m_staticStationImageFileNameFormat, "Data Files");
		m_settings.setValue("Framerate", m_frameRate, "Settings");
		m_settings.setValue("Server IP Address", m_serverIPAddress.getHostName(), "Settings");
		m_settings.setValue("Server Port", m_serverPort, "Settings");
		m_settings.setValue("Signal Debug Level", SignalDebugLevel.toString(m_signalDebugLevel), "Settings");
		m_settings.setValue("Auto-scroll Console Window", m_autoScrollConsoleWindow, "Settings");
		m_settings.setValue("Auto-connect on Startup", m_autoConnectOnStartup, "Settings");
		m_settings.setValue("Take Webcam Snapshot on Startup", m_takeWebcamSnapshotOnStartup, "Settings");
		m_settings.setValue("Use Static Station Image", m_useStaticStationImages, "Settings");
		m_settings.setValue("Webcam Resolution", m_webcamResolution.width + ", " + m_webcamResolution.height, "Settings");
		m_settings.setValue("Time Limit", m_timeLimit, "Settings");
		m_settings.setValue("Number of Trackers", m_numberOfTrackers, "Settings");
		m_settings.setValue("Selected Colour", m_selectedColour.getRed() + ", " + m_selectedColour.getGreen() + ", " + m_selectedColour.getBlue(), "Colours");
		m_settings.setValue("Vertex Colour", m_vertexColour.getRed() + ", " + m_vertexColour.getGreen() + ", " + m_vertexColour.getBlue(), "Colours");
		m_settings.setValue("Edge Colour", m_edgeColour.getRed() + ", " + m_edgeColour.getGreen() + ", " + m_edgeColour.getBlue(), "Colours");
		m_settings.setValue("Robot Colour", m_robotColour.getRed() + ", " + m_robotColour.getGreen() + ", " + m_robotColour.getBlue(), "Colours");
		m_settings.setValue("Block Colour", m_blockColour.getRed() + ", " + m_blockColour.getGreen() + ", " + m_blockColour.getBlue(), "Colours");
		m_settings.setValue("Pot Colour", m_potColour.getRed() + ", " + m_potColour.getGreen() + ", " + m_potColour.getBlue(), "Colours");
		m_settings.setValue("Drop Off Location Colour", m_dropOffLocationColour.getRed() + ", " + m_dropOffLocationColour.getGreen() + ", " + m_dropOffLocationColour.getBlue(), "Colours");
		for(byte i=0;i<SystemManager.robotSystem.numberOfRobots();i++) {
			m_settings.setValue("Robot " + i, SystemManager.robotSystem.getRobot(i).getInitialPosition().toString(), "Robot Positions");
		}
		for(byte i=0;i<SystemManager.blockSystem.numberOfBlocks();i++) {
			m_settings.setValue("Block " + i, SystemManager.blockSystem.getBlock(i).getInitialPosition().toString(), "Block Positions");
		}
		for(byte i=0;i<SystemManager.potSystem.numberOfPots();i++) {
			m_settings.setValue("Pot " + i, SystemManager.potSystem.getPot(i).getInitialPosition().toString(), "Pot Positions");
		}
		for(byte i=0;i<SystemManager.blockSystem.numberOfDropOffLocations();i++) {
			m_settings.setValue("Drop Off Location " + i, SystemManager.blockSystem.getDropOffLocation(i).getPosition().toString(), "Drop Off Locations");
		}
		
		// group the settings by categories
		m_settings.sort();
		
		return m_settings.writeTo(fileName);
	}
	
}
