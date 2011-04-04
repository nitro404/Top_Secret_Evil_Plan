import java.util.Vector;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.*;

public class RobotSystem implements MouseListener, MouseMotionListener {
	
	private byte m_activeRobotID;
	private Vector<Robot> m_robots;
	
	private int m_selectedRobot;
	private int m_robotToMove;
	
	final public static float distanceAccuracy = 15.0f; // +- 15 pixels (5 cm) (total 10 cm)
	final public static float angleAccuracy = (float) Math.toRadians(10.0f); // +- 10 degrees (total 20 degrees)
	final public static float slowDownDistance = 45.0f; // 45 pixels (15 cm)
	final public static float slowDownAngleDifference = (float) Math.toRadians(45.0f); // slow down if distance left to turn is under 45 degrees
	final public static float maxArcAngleDifference = (float) Math.toRadians(30.0f);
	final public static long slowDownTimeDelay = (long) (2.0f * 1000L); // amount of time to wait before slowing down if no valid pose is received (seconds * millisecond scaling)
	final public static long intialBackUpTimeDuration = (long) (1.5f * 1000L); // amount of time to wait before slowing down if no valid pose is received (seconds * millisecond scaling)
	
	final public static RobotPosition[] defaultRobotPositions = {
		new RobotPosition(361, 208, 90),
		new RobotPosition(357, 709, 90),
		new RobotPosition(356, 1189, 90),
	};
	
	final public static byte[] robotNumbers = { 0, 7, 5 };
	
	final public static String[] robotNames = { "Pac-man", "Star Warrior", "Walter Chan" };
	
	public RobotSystem() {
		m_robots = new Vector<Robot>(defaultRobotPositions.length);
		for(byte i=0;i<defaultRobotPositions.length;i++) {
			m_robots.add(new Robot(i, robotNumbers[i], robotNames[i], SystemManager.settings.getInitialRobotPosition(i)));
		}
		m_activeRobotID = -1;
		m_selectedRobot = -1;
		m_robotToMove = -1;
	}
	
	public int numberOfRobots() { return m_robots.size(); }
	
	public Robot getRobot(byte id) {
		if(id < 0 || id >= m_robots.size()) { return null; }
		return m_robots.elementAt(id);
	}
	
	public Robot getActiveRobot() {
		return (m_activeRobotID < 0 || m_activeRobotID >= m_robots.size()) ? null : m_robots.elementAt(m_activeRobotID);
	}
	
	public byte getActiveRobotID() {
		return m_activeRobotID;
	}
	
	public boolean hasActiveRobot() {
		return m_activeRobotID >= 0 && m_activeRobotID < m_robots.size();
	}
	
	public void setActiveRobotID(byte robotID) {
		m_activeRobotID = (robotID < -1) ? -1 : robotID;
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
	
	public boolean setInitialPosition(byte robotID, RobotPosition initialRobotPosition) {
		if(robotID < 0 || robotID >= m_robots.size() || !RobotPosition.isValid(initialRobotPosition)) { return false; }
		return m_robots.elementAt(robotID).setInitialPosition(initialRobotPosition);
	}
	
	public boolean setSpawnPosition(byte robotID, RobotPosition spawnRobotPosition) {
		if(robotID < 0 || robotID >= m_robots.size() || !RobotPosition.isValid(spawnRobotPosition)) { return false; }
		return m_robots.elementAt(robotID).setSpawnPosition(spawnRobotPosition);
	}
	
	public boolean handleRobotResponse(byte responseID) {
		if(!SystemManager.isIdentified() || !hasActiveRobot()) { return false; }
		
		if(!RobotResponse.isValid(responseID)) {
			SystemManager.console.writeLine("Received invalid response id from robot: " + responseID);
			return false;
		}
		
		if(responseID == RobotResponse.FoundBlock) {
			SystemManager.robotSystem.getActiveRobot().setState(RobotState.DeliveringBlock);
			SystemManager.robotSystem.getActiveRobot().getActiveBlock().setState(BlockState.Located);
			
			SystemManager.client.sendSignal(new RobotStateChangeSignal(SystemManager.robotSystem.getActiveRobotID(), SystemManager.robotSystem.getActiveRobot().getState()));
			SystemManager.client.sendSignal(new RobotStateChangeSignal(SystemManager.robotSystem.getActiveRobot().getActiveBlockID(), SystemManager.robotSystem.getActiveRobot().getActiveBlock().getState()));
			return true;
		}
		else if(responseID == RobotResponse.GrabbedBlock) {
			SystemManager.robotSystem.getActiveRobot().getActiveBlock().setState(BlockState.Moving);
			
			SystemManager.client.sendSignal(new RobotStateChangeSignal(SystemManager.robotSystem.getActiveRobot().getActiveBlockID(), SystemManager.robotSystem.getActiveRobot().getActiveBlock().getState()));
			
			SystemManager.taskManager.getTaskList(SystemManager.robotSystem.getActiveRobotID()).getCurrentTask().setTaskState(TaskState.Completed);
			
			return true;
		}
		else if(responseID == RobotResponse.BlockNotFound) {
			SystemManager.robotSystem.getActiveRobot().setState(RobotState.Idle);
			SystemManager.robotSystem.getActiveRobot().getActiveBlock().setState(BlockState.Missing);
			SystemManager.robotSystem.getActiveRobot().setActiveBlockID((byte) -1);
			
			SystemManager.client.sendSignal(new RobotStateChangeSignal(SystemManager.robotSystem.getActiveRobotID(), SystemManager.robotSystem.getActiveRobot().getState()));
			SystemManager.client.sendSignal(new RobotStateChangeSignal(SystemManager.robotSystem.getActiveRobot().getActiveBlockID(), SystemManager.robotSystem.getActiveRobot().getActiveBlock().getState()));
			
			SystemManager.taskManager.getTaskList(SystemManager.robotSystem.getActiveRobotID()).getCurrentTask().setTaskState(TaskState.Completed);
			return true;
		}
		else if(responseID == RobotResponse.DroppedOffBlock) {
			SystemManager.robotSystem.getActiveRobot().setState(RobotState.Idle);
			SystemManager.robotSystem.getActiveRobot().getActiveBlock().setState(BlockState.Delivered);
			SystemManager.robotSystem.getActiveRobot().setActiveBlockID((byte) -1);
			
			SystemManager.client.sendSignal(new RobotStateChangeSignal(SystemManager.robotSystem.getActiveRobotID(), SystemManager.robotSystem.getActiveRobot().getState()));
			SystemManager.client.sendSignal(new RobotStateChangeSignal(SystemManager.robotSystem.getActiveRobot().getActiveBlockID(), SystemManager.robotSystem.getActiveRobot().getActiveBlock().getState()));
			
			SystemManager.taskManager.getTaskList(SystemManager.robotSystem.getActiveRobotID()).getCurrentTask().setTaskState(TaskState.Completed);
			return true;
		}
		return false;
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
		Position position = new Position(p);
		if(!position.isValid()) { return false; }
		
		for(int i=0;i<m_robots.size();i++) {
			if(Math.sqrt(Math.pow(m_robots.elementAt(i).getInitialPosition().getX() - p.x, 2) + Math.pow(m_robots.elementAt(i).getInitialPosition().getY() - p.y, 2)) <= Robot.SIZE / 2) {
				m_selectedRobot = i;
				return true;
			}
		}
		return false;
	}
	
	public void reset() {
		for(int i=0;i<m_robots.size();i++) {
			m_robots.elementAt(i).reset();
		}
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