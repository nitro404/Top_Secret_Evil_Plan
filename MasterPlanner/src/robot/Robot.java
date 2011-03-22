package robot;

import java.awt.Color;
import java.awt.Graphics2D;

import planner.Position;

import block.BlockState;

public class Robot {
	
	private int m_id;
	private int m_robotNumber;
	private String m_name;
	private RobotPose m_actualPose;
	private RobotPose m_estimatedPose;
	private RobotPose m_defaultPose;
	private RobotPose m_initialPose;
	private int m_state;
	
	final public static int SIZE = (int) (10 * 3); // size in cm * pixel scaling
	final public static Color DEFAULT_COLOUR = Color.GREEN;
	
	public Robot(int id, int robotNumber, int x, int y, int angle) {
		this(id, robotNumber, null, new RobotPose(x, y, angle));
	}
	
	public Robot(int id, int robotNumber, Position defaultPosition, int angle) {
		this(id, robotNumber, null, new RobotPose(defaultPosition, angle));
	}
	
	public Robot(int id, int robotNumber, RobotPose defaultPose) {
		this(id, robotNumber, null, defaultPose);
	}
	
	public Robot(int id, int robotNumber, String name, RobotPose defaultPose) {
		m_id = id;
		m_robotNumber = robotNumber;
		m_name = (name == null) ? "" : name.trim();
		m_actualPose = new RobotPose(-1, -1, -1);
		m_estimatedPose = new RobotPose(-1, -1, -1);
		m_defaultPose = (defaultPose == null) ? new RobotPose(-1, -1, -1) : defaultPose;
		m_initialPose = m_defaultPose;
		m_state = RobotState.Idle;
	}
	
	public int getID() {
		return m_id;
	}
	
	public int getRobotNumber() {
		return m_robotNumber;
	}
	
	public String getName() {
		return m_name;
	}

	public RobotPose getActualPose() {
		return m_actualPose;
	}
	
	public RobotPose getEstimatedPose() {
		return m_estimatedPose;
	}
	
	public RobotPose getDefaultPose() {
		return m_defaultPose;
	}
	
	public RobotPose getInitialPose() {
		return m_initialPose;
	}

	public int getState() {
		return m_state;
	}
	
	public void setID(int id) {
		m_id = id;
	}
	
	public void setRobotNumber(int robotNumber) {
		m_robotNumber = robotNumber;
	}

	public boolean setActualPose(RobotPose actualPose) {
		if(!RobotPose.isValid(actualPose)) { return false; }
		m_actualPose = actualPose;
		return true;
	}

	public boolean setEstimatedPose(RobotPose estimatedPose) {
		if(!RobotPose.isValid(estimatedPose)) { return false; }
		m_estimatedPose = estimatedPose;
		return true;
	}
	
	public boolean setDefaultPose(RobotPose defaultPose) {
		if(!RobotPose.isValid(defaultPose)) { return false; }
		m_defaultPose = defaultPose;
		return true;
	}
	
	public boolean setInitialPose(RobotPose initialPose) {
		if(!RobotPose.isValid(initialPose)) { return false; }
		m_initialPose = initialPose;
		return true;
	}

	public boolean setState(int state) {
		if(!RobotState.isValid(state)) { return false; }
		m_state = state;
		return true;
	}
	
	public void draw(Graphics2D g) {
		if(g == null) { return; }
		
		g.setColor(DEFAULT_COLOUR);
		
		RobotPose pose = !RobotPose.isValid(m_actualPose) ? m_defaultPose : (RobotPose.isValid(m_actualPose) ? m_actualPose : m_estimatedPose);
		
		g.drawOval(pose.getX() - (SIZE/2), pose.getY() - (SIZE/2), SIZE, SIZE);
	}
	
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Robot)) { return false; }
		Robot r = (Robot) o;
		return m_id == r.m_id;
	}
	
	public String toString() {
		return "Robot #" + m_robotNumber + " (ID #" + m_id + ") " + " (" + m_name + ") " + m_actualPose + ": " + BlockState.toString(m_state);
	}
	
}
