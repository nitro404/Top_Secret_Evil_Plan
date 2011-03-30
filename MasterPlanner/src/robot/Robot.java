package robot;

import java.awt.Graphics2D;
import planner.*;
import block.*;
import shared.*;

public class Robot {
	
	private byte m_id;
	private byte m_robotNumber;
	private String m_name;
	private RobotPosition m_actualPosition;
	private RobotPosition m_lastValidActualPosition;
	private RobotPosition m_estimatedPosition;
	private RobotPosition m_defaultPosition;
	private RobotPosition m_initialPosition;
	private byte m_state;
	
	final public static int SIZE = (int) (10 * 3); // size in cm * pixel scaling
	
	public Robot(byte id, byte robotNumber, int x, int y, int angle) {
		this(id, robotNumber, null, new RobotPosition(x, y, angle));
	}
	
	public Robot(byte id, byte robotNumber, Position defaultPosition, int angle) {
		this(id, robotNumber, null, new RobotPosition(defaultPosition, angle));
	}
	
	public Robot(byte id, byte robotNumber, RobotPosition defaultPosition) {
		this(id, robotNumber, null, defaultPosition);
	}
	
	public Robot(byte id, byte robotNumber, String name, RobotPosition defaultPosition) {
		m_id = id;
		m_robotNumber = robotNumber;
		m_name = (name == null) ? "" : name.trim();
		m_actualPosition = new RobotPosition(-1, -1, -1);
		m_lastValidActualPosition = m_actualPosition;
		m_estimatedPosition = new RobotPosition(-1, -1, -1);
		m_defaultPosition = (defaultPosition == null) ? new RobotPosition(-1, -1, -1) : defaultPosition;
		m_initialPosition = m_defaultPosition;
		m_state = RobotState.Idle;
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
	
	public RobotPosition getEstimatedPosition() {
		return m_estimatedPosition;
	}
	
	public RobotPosition getDefaultPosition() {
		return m_defaultPosition;
	}
	
	public RobotPosition getInitialPosition() {
		return m_initialPosition;
	}

	public byte getState() {
		return m_state;
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
		}
		return true;
	}

	public boolean setEstimatedPosition(RobotPosition estimatedPosition) {
		if(!RobotPosition.isValid(estimatedPosition)) { return false; }
		m_estimatedPosition = estimatedPosition;
		return true;
	}
	
	public boolean setDefaultPosition(RobotPosition defaultPosition) {
		if(!RobotPosition.isValid(defaultPosition)) { return false; }
		m_defaultPosition = defaultPosition;
		return true;
	}
	
	public boolean setInitialPosition(RobotPosition initialPosition) {
		if(!RobotPosition.isValid(initialPosition)) { return false; }
		m_initialPosition = initialPosition;
		return true;
	}

	public boolean setState(byte state) {
		if(!RobotState.isValid(state)) { return false; }
		m_state = state;
		return true;
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
