import java.awt.Color;
import java.awt.Graphics;

public class Robot {
	
	private byte m_id;
	private byte m_robotNumber;
	private String m_name;
	private RobotPosition m_actualPosition;
	private RobotPosition m_lastValidActualPosition;
	private long m_timeReceivedLastValidActualPosition;
	private RobotPosition m_estimatedPosition;
	private RobotPosition m_initialPosition;
	private RobotPosition m_spawnPosition;
	private byte m_state;
	
	private byte m_activeBlockID;
	private byte m_activeDropOffLocationID;
	private byte m_activePotID;
	
	final public static int SIZE = (int) (10 * 3); // size in cm * pixel scaling
	final public static int SELECTION_RADIUS = SIZE + 6;
	final public static int DISTANCE_TO_FRONT = (int) (4.318 * 3); // length in cm * pixel scaling
	
	public Robot(byte id, byte robotNumber, int x, int y, int angle) {
		this(id, robotNumber, null, new RobotPosition(x, y, angle));
	}
	
	public Robot(byte id, byte robotNumber, Position position, int angle) {
		this(id, robotNumber, null, new RobotPosition(position, angle));
	}
	
	public Robot(byte id, byte robotNumber, RobotPosition position) {
		this(id, robotNumber, null, position);
	}
	
	public Robot(byte id, byte robotNumber, String name, RobotPosition position) {
		m_id = id;
		m_robotNumber = robotNumber;
		m_name = (name == null) ? "" : name.trim();
		m_actualPosition = new RobotPosition(-1, -1, -1);
		m_lastValidActualPosition = m_actualPosition;
		m_estimatedPosition = new RobotPosition(-1, -1, -1);
		m_initialPosition = position;
		m_state = RobotState.Idle;
		m_activeBlockID = -1;
		m_activeDropOffLocationID = -1;
		m_activePotID = -1;
		m_timeReceivedLastValidActualPosition = -1;
	}
	
	public byte getID() {
		return m_id;
	}
	
	public byte getRobotNumber() {
		return m_robotNumber;
	}
	
	public String getName() {
		return m_name;
	}

	public RobotPosition getActualPosition() {
		return (!RobotPosition.isValid(m_actualPosition)) ? m_lastValidActualPosition : m_actualPosition;
	}
	
	public RobotPosition getLastValidActualPosition() {
		return m_lastValidActualPosition;
	}
	
	public long getTimeReceivedLastValidActualPosition() {
		return m_timeReceivedLastValidActualPosition;
	}
	
	public RobotPosition getEstimatedPosition() {
		return m_estimatedPosition;
	}
	
	public RobotPosition getInitialPosition() {
		return m_initialPosition;
	}
	
	public RobotPosition getSpawnPosition() {
		return m_spawnPosition;
	}

	public byte getState() {
		return m_state;
	}
	
	public Block getActiveBlock() {
		return (m_activeBlockID < 0 || m_activeBlockID >= SystemManager.blockSystem.numberOfBlocks()) ? null : SystemManager.blockSystem.getBlock(m_activeBlockID);
	}
	
	public byte getActiveBlockID() {
		return m_activeBlockID;
	}
	
	public boolean hasActiveBlock() {
		return m_activeBlockID >= 0 && m_activeBlockID < SystemManager.blockSystem.numberOfBlocks();
	}
	
	public DropOffLocation getActiveDropOffLocation() {
		return (m_activeDropOffLocationID < 0 || m_activeDropOffLocationID >= SystemManager.blockSystem.numberOfDropOffLocations()) ? null : SystemManager.blockSystem.getDropOffLocation(m_activeDropOffLocationID);
	}
	
	public byte getActiveDropOffLocationID() {
		return m_activeDropOffLocationID;
	}
	
	public boolean hasActiveDropOffLocation() {
		return m_activeDropOffLocationID >= 0 && m_activeDropOffLocationID < SystemManager.blockSystem.numberOfDropOffLocations();
	}
	
	public Pot getActivePot() {
		return m_activePotID < 0 || m_activePotID >= SystemManager.potSystem.numberOfPots() ? null : SystemManager.potSystem.getPot(m_activePotID);
	}
	
	public byte getActivePotID() {
		return m_activePotID;
	}
	
	public boolean hasActivePot() {
		return m_activePotID >= 0 && m_activePotID < SystemManager.potSystem.numberOfPots();
	}
	
	public void setID(byte id) {
		m_id = id;
	}
	
	public void setRobotNumber(byte robotNumber) {
		m_robotNumber = robotNumber;
	}

	public boolean setActualPosition(RobotPosition actualPosition) {
		if(actualPosition == null) { return false; }
		m_actualPosition = actualPosition;
		
		if(m_actualPosition.isValid()) {
			m_lastValidActualPosition = actualPosition;
			if(!RobotPosition.isValid(m_spawnPosition)) {
				m_spawnPosition = actualPosition;
				m_timeReceivedLastValidActualPosition = System.currentTimeMillis();
			}
		}
		return true;
	}

	public boolean setEstimatedPosition(RobotPosition estimatedPosition) {
		if(!RobotPosition.isValid(estimatedPosition)) { return false; }
		m_estimatedPosition = estimatedPosition;
		return true;
	}
	
	public boolean setInitialPosition(RobotPosition initialPosition) {
		if(!RobotPosition.isValid(initialPosition)) { return false; }
		m_initialPosition = initialPosition;
		return true;
	}
	
	public boolean setSpawnPosition(RobotPosition spawnPosition) {
		if(!RobotPosition.isValid(spawnPosition)) { return false; }
		m_spawnPosition = spawnPosition;
		return true;
	}
	
	public boolean setState(byte state) {
		if(!RobotState.isValid(state)) { return false; }
		m_state = state;
		return true;
	}

	public void setActiveBlockID(byte blockID) {
		m_activeBlockID = (blockID < -1) ? -1 : blockID;
	}
	
	public void setActiveDropOffLocationID(byte dropOffLocationID) {
		m_activeDropOffLocationID = (dropOffLocationID < -1) ? -1 : dropOffLocationID;
	}
	
	public void setActivePotID(byte potID) {
		m_activePotID = (potID < -1) ? -1 : potID;
	}
	
	public void reset() {
		m_actualPosition = new RobotPosition(-1, -1, -1);
		m_lastValidActualPosition = m_actualPosition;
		m_timeReceivedLastValidActualPosition = -1;
		m_estimatedPosition = new RobotPosition(-1, -1, -1);
		m_spawnPosition = new RobotPosition(-1, -1, -1);
		m_activeBlockID = -1;
		m_activeDropOffLocationID = -1;
		m_activePotID = -1;
		m_state = RobotState.Idle;
	}
	
	public void drawSelection(Graphics g, Color c) {
		if(g == null || c == null) { return; }
		
		g.setColor(c);
		
		RobotPosition position = !SystemManager.isStarted() ? m_initialPosition : (RobotPosition.isValid(m_actualPosition) ? m_actualPosition : m_lastValidActualPosition);
		
		g.drawOval(position.getX() - (SELECTION_RADIUS/2), position.getY() - (SELECTION_RADIUS/2), SELECTION_RADIUS, SELECTION_RADIUS);
	}
	
	public void draw(Graphics g) {
		if(g == null) { return; }
		
		g.setColor(SystemManager.settings.getRobotColour());
		
		RobotPosition position = !SystemManager.isStarted() ? m_initialPosition : (RobotPosition.isValid(m_actualPosition) ? m_actualPosition : m_lastValidActualPosition);
		
		int x = (int) (position.getX() - (Math.cos(Math.PI - position.getAngleRadians()) * (Robot.SIZE / 2)));
		int y = (int) (position.getY() - (Math.sin(Math.PI - position.getAngleRadians()) * (Robot.SIZE / 2)));
		
		g.drawOval(position.getX() - (SIZE/2), position.getY() - (SIZE/2), SIZE, SIZE);
		g.drawLine(position.getX(), position.getY(), x, y);
	}
	
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Robot)) { return false; }
		Robot r = (Robot) o;
		return m_id == r.m_id;
	}
	
	public String toString() {
		return "Robot #" + m_robotNumber + " (ID #" + m_id + ") " + " (" + m_name + ") " + m_actualPosition + ": " + BlockState.toString(m_state);
	}
	
}
