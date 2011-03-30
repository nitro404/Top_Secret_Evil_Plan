package robot;

import java.util.Vector;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.*;
import shared.*;

public class RobotSystem implements MouseListener, MouseMotionListener {
	
	private Vector<Robot> m_robots;
	
	private int m_selectedRobot;
	private int m_robotToMove;
	
	final public static RobotPosition[] defaultRobotPositions = {
		new RobotPosition(0, 0, 270),
		new RobotPosition(0, 0, 270),
		new RobotPosition(0, 0, 270),
	};
	
	final public static byte[] robotNumbers = { 0, 5, 7 };
	
	final public static String[] robotNames = { "Pac-man", "Star Warrior", "Walter Chan" };
	
	public RobotSystem() {
		m_robots = new Vector<Robot>(defaultRobotPositions.length);
		for(byte i=0;i<defaultRobotPositions.length;i++) {
			m_robots.add(new Robot(i, robotNumbers[i], robotNames[i], defaultRobotPositions[i]));
		}
		m_selectedRobot = -1;
		m_robotToMove = -1;
	}
	
	public Robot getRobot(byte id) {
		if(id < 0 || id >= m_robots.size()) { return null; }
		return m_robots.elementAt(id);
	}
	
	public boolean setRobotState(byte robotID, byte robotState) {
		if(robotID < 0 || robotID >= m_robots.size() || !RobotState.isValid(robotState)) { return false; }
		return m_robots.elementAt(robotID).setState(robotState);
	}
	
	public boolean setActualPosition(byte robotID, RobotPosition actualRobotPosition) {
		if(robotID < 0 || robotID >= m_robots.size() || !RobotPosition.isValid(actualRobotPosition)) { return false; }
		return m_robots.elementAt(robotID).setActualPosition(actualRobotPosition);
	}
	
	public boolean setEstimatedPosition(byte robotID, RobotPosition estimatedRobtPosition) {
		if(robotID < 0 || robotID >= m_robots.size() || !RobotPosition.isValid(estimatedRobtPosition)) { return false; }
		return m_robots.elementAt(robotID).setEstimatedPosition(estimatedRobtPosition);
	}
	
	public boolean setDefaultPosition(byte robotID, RobotPosition defaultRobotPosition) {
		if(robotID < 0 || robotID >= m_robots.size() || !RobotPosition.isValid(defaultRobotPosition)) { return false; }
		return m_robots.elementAt(robotID).setDefaultPosition(defaultRobotPosition);
	}
	
	public boolean setInitialPosition(byte robotID, RobotPosition initialRobotPosition) {
		if(robotID < 0 || robotID >= m_robots.size() || !RobotPosition.isValid(initialRobotPosition)) { return false; }
		return m_robots.elementAt(robotID).setInitialPosition(initialRobotPosition);
	}
	
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON2) {
			selectRobot(e.getPoint());
			m_robotToMove = m_selectedRobot;
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON2) {
			m_robotToMove = -1;
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		if(m_robotToMove != -1) {
			m_robots.elementAt(m_robotToMove).setInitialPosition(new RobotPosition(e.getX(), e.getY(), 90));
		}
	}
	
	public void mouseMoved(MouseEvent e) { }
	
	public boolean selectRobot(Point p) {
		m_selectedRobot = -1;
		
		if(p == null) { return false; }
		Position position = (Position) p;
		if(!position.isValid()) { return false; }
		
		for(int i=0;i<m_robots.size();i++) {
System.out.println(Math.sqrt(Math.pow(m_robots.elementAt(i).getInitialPosition().getX() - p.x, 2) + Math.pow(m_robots.elementAt(i).getInitialPosition().getY() - p.y, 2)) + " <= " + Robot.SIZE / 2);
			if(Math.sqrt(Math.pow(m_robots.elementAt(i).getInitialPosition().getX() - p.x, 2) + Math.pow(m_robots.elementAt(i).getInitialPosition().getY() - p.y, 2)) <= Robot.SIZE / 2) {
System.out.println("Selected " + i);
				m_selectedRobot = i;
				return true;
			}
		}
		return false;
	}
	
	public void clearSelection() {
		m_selectedRobot = -1;
		m_robotToMove = -1;
	}
	
	public void draw(Graphics2D g) {
		for(int i=0;i<m_robots.size();i++) {
			m_robots.elementAt(i).draw(g);
		}
	}
	
}