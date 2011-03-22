import java.util.Vector;
import java.awt.Graphics2D;

public class RobotSystem {
	
	private Vector<Robot> m_robots;
	
	final public static RobotPose[] defaultRobotPoses = {
		new RobotPose(0, 0, 270),
		new RobotPose(0, 0, 270),
		new RobotPose(0, 0, 270),
	};
	
	final public static int[] robotIDs = { 0, 5, 7 };
	
	final public static String[] robotNames = { "Pac-man", "Star Warrior", "Walter Chan" };
	
	public RobotSystem() {
		m_robots = new Vector<Robot>(defaultRobotPoses.length);
		for(int i=0;i<defaultRobotPoses.length;i++) {
			m_robots.add(new Robot(i, robotIDs[i], robotNames[i], defaultRobotPoses[i]));
		}
	}
	
	public Robot getRobot(int id) {
		if(id < 0 || id >= m_robots.size()) { return null; }
		return m_robots.elementAt(id);
	}
	
	public boolean setRobotState(int id, int state) {
		if(id < 0 || id >= m_robots.size() || !RobotState.isValid(state)) { return false; }
		return m_robots.elementAt(id).setState(state);
	}
	
	public boolean updateActualPose(int id, RobotPose actualPose) {
		if(id < 0 || id >= m_robots.size() || !RobotPose.isValid(actualPose)) { return false; }
		return m_robots.elementAt(id).setActualPose(actualPose);
	}
	
	public boolean updateEstimatedPose(int id, RobotPose estimatedPose) {
		if(id < 0 || id >= m_robots.size() || !RobotPose.isValid(estimatedPose)) { return false; }
		return m_robots.elementAt(id).setEstimatedPose(estimatedPose);
	}
	
	public boolean updateDefaultPose(int id, RobotPose defaultPose) {
		if(id < 0 || id >= m_robots.size() || !RobotPose.isValid(defaultPose)) { return false; }
		return m_robots.elementAt(id).setDefaultPose(defaultPose);
	}
	
	public boolean updateInitialPose(int id, RobotPose initialPose) {
		if(id < 0 || id >= m_robots.size() || !RobotPose.isValid(initialPose)) { return false; }
		return m_robots.elementAt(id).setInitialPose(initialPose);
	}
	
	public void draw(Graphics2D g) {
		for(int i=0;i<m_robots.size();i++) {
			m_robots.elementAt(i).draw(g);
		}
	}
	
}
