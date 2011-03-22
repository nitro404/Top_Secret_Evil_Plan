package robot;

import java.util.Vector;
import java.awt.Graphics2D;

import shared.RobotPose;

public class RobotSystem {
	
	private Vector<Robot> m_robots;
	
	final public static RobotPose[] defaultRobotPoses = {
		new RobotPose(0, 0, 270),
		new RobotPose(0, 0, 270),
		new RobotPose(0, 0, 270),
	};
	
	final public static byte[] robotNumbers = { 0, 5, 7 };
	
	final public static String[] robotNames = { "Pac-man", "Star Warrior", "Walter Chan" };
	
	public RobotSystem() {
		m_robots = new Vector<Robot>(defaultRobotPoses.length);
		for(byte i=0;i<defaultRobotPoses.length;i++) {
			m_robots.add(new Robot(i, robotNumbers[i], robotNames[i], defaultRobotPoses[i]));
		}
	}
	
	public Robot getRobot(byte id) {
		if(id < 0 || id >= m_robots.size()) { return null; }
		return m_robots.elementAt(id);
	}
	
	public boolean setRobotState(byte id, byte state) {
		if(id < 0 || id >= m_robots.size() || !RobotState.isValid(state)) { return false; }
		return m_robots.elementAt(id).setState(state);
	}
	
	public boolean updateActualPose(byte id, RobotPose actualPose) {
		if(id < 0 || id >= m_robots.size() || !RobotPose.isValid(actualPose)) { return false; }
		return m_robots.elementAt(id).setActualPose(actualPose);
	}
	
	public boolean updateEstimatedPose(byte id, RobotPose estimatedPose) {
		if(id < 0 || id >= m_robots.size() || !RobotPose.isValid(estimatedPose)) { return false; }
		return m_robots.elementAt(id).setEstimatedPose(estimatedPose);
	}
	
	public boolean updateDefaultPose(byte id, RobotPose defaultPose) {
		if(id < 0 || id >= m_robots.size() || !RobotPose.isValid(defaultPose)) { return false; }
		return m_robots.elementAt(id).setDefaultPose(defaultPose);
	}
	
	public boolean updateInitialPose(byte id, RobotPose initialPose) {
		if(id < 0 || id >= m_robots.size() || !RobotPose.isValid(initialPose)) { return false; }
		return m_robots.elementAt(id).setInitialPose(initialPose);
	}
	
	public void draw(Graphics2D g) {
		for(int i=0;i<m_robots.size();i++) {
			m_robots.elementAt(i).draw(g);
		}
	}
	
}