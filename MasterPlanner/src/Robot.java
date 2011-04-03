import java.awt.Graphics2D;

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
	
	final public static int SIZE = (int) (10 * 3); // size in cm * pixel scaling
	final public static int DISTANCE_TO_FRONT = (int) (3.55 * 3); // length in cm * pixel scaling
	
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
		return m_actualPosition;
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
			m_lastValidActualPosition = m_actualPosition;
			if(!m_spawnPosition.isValid()) {
				m_spawnPosition = m_actualPosition;
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
	
	public void reset() {
		m_actualPosition = new RobotPosition(-1, -1, -1);
		m_lastValidActualPosition = m_actualPosition;
		m_estimatedPosition = new RobotPosition(-1, -1, -1);
		m_spawnPosition = new RobotPosition(-1, -1, -1);
		m_state = RobotState.Idle;
	}
	
	public void draw(Graphics2D g) {
		if(g == null) { return; }
		
		g.setColor(SystemManager.settings.getRobotColour());
		
		RobotPosition position = !RobotPosition.isValid(m_actualPosition) ? m_initialPosition : (RobotPosition.isValid(m_actualPosition) ? m_actualPosition : m_lastValidActualPosition);
		
		g.drawOval(position.getX() - (SIZE/2), position.getY() - (SIZE/2), SIZE, SIZE);
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
