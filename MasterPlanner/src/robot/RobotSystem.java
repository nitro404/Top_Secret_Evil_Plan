package robot;

import java.util.Vector;
import java.awt.Graphics2D;
import java.awt.event.*;
import shared.*;

public class RobotSystem implements MouseListener, MouseMotionListener {
	
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
	
	public boolean setRobotState(byte robotID, byte robotState) {
		if(robotID < 0 || robotID >= m_robots.size() || !RobotState.isValid(robotState)) { return false; }
		return m_robots.elementAt(robotID).setState(robotState);
	}
	
	public boolean setActualPose(byte robotID, RobotPose actualRobotPose) {
		if(robotID < 0 || robotID >= m_robots.size() || !RobotPose.isValid(actualRobotPose)) { return false; }
		return m_robots.elementAt(robotID).setActualPose(actualRobotPose);
	}
	
	public boolean setEstimatedPose(byte robotID, RobotPose estimatedRobtPose) {
		if(robotID < 0 || robotID >= m_robots.size() || !RobotPose.isValid(estimatedRobtPose)) { return false; }
		return m_robots.elementAt(robotID).setEstimatedPose(estimatedRobtPose);
	}
	
	public boolean setDefaultPose(byte robotID, RobotPose defaultRobotPose) {
		if(robotID < 0 || robotID >= m_robots.size() || !RobotPose.isValid(defaultRobotPose)) { return false; }
		return m_robots.elementAt(robotID).setDefaultPose(defaultRobotPose);
	}
	
	public boolean setInitialPose(byte robotID, RobotPose initialRobotPose) {
		if(robotID < 0 || robotID >= m_robots.size() || !RobotPose.isValid(initialRobotPose)) { return false; }
		return m_robots.elementAt(robotID).setInitialPose(initialRobotPose);
	}
	
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	
	public void mousePressed(MouseEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void mouseDragged(MouseEvent e) {
		
	}
	
	public void mouseMoved(MouseEvent e) {
		
	}
	
	public void draw(Graphics2D g) {
		for(int i=0;i<m_robots.size();i++) {
			m_robots.elementAt(i).draw(g);
		}
	}
	
}