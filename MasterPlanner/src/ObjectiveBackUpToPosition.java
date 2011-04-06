import java.util.StringTokenizer;
import java.awt.Graphics;
import java.io.PrintWriter;

public class ObjectiveBackUpToPosition extends Objective {
	
	private String m_pathName;
	private int m_positionIndex;
	private Vertex m_destinationVertex;
	private long m_backingUpStartTime;
	
	public ObjectiveBackUpToPosition(String pathName, int positionIndex) {
		super();
		m_objectiveType = ObjectiveType.BackUpToPosition;
		m_pathName = pathName;
		m_positionIndex = positionIndex;
		m_destinationVertex = null;
		m_backingUpStartTime = -1;
	}
	
	public String getPathName() { return m_pathName; }
	
	public int getPositionIndex() { return m_positionIndex; }
	
	public void setPathName(String pathName) { m_pathName = pathName; }
	
	public void setPositionIndex(int positionIndex) { m_positionIndex = positionIndex; }
	
	public void execute() {
		if(m_objectiveState == ObjectiveState.New) {
			m_objectiveState = ObjectiveState.Started;
			
			SystemManager.robotSystem.getActiveRobot().setState(RobotState.Moving);
			
			SystemManager.client.sendSignal(new RobotStateChangeSignal(SystemManager.robotSystem.getActiveRobotID(), SystemManager.robotSystem.getActiveRobot().getState()));
		}
		
		if(m_destinationVertex == null) {
			m_destinationVertex = SystemManager.pathSystem.getPath(m_pathName).getVertex(m_positionIndex);
			m_backingUpStartTime = System.currentTimeMillis();
		}
		
		RobotPosition p = SystemManager.robotSystem.getActiveRobot().getActualPosition();
		float distanceFromPoint = (float) Math.sqrt(Math.pow(p.getX() - m_destinationVertex.x, 2) + Math.pow(p.getY() - m_destinationVertex.y, 2));
		boolean atPoint = distanceFromPoint < RobotSystem.distanceAccuracy;
		
		if(atPoint) {
			m_objectiveState = ObjectiveState.Completed;
			return;
		}
		
		// compute the vector from the the destination point to robot's pose
		float x = p.getX() - m_destinationVertex.x;
		float y = p.getY() - m_destinationVertex.y;
		
		// compute the angle of this vector
		float angle = (float) Math.atan2(-y, x);
		if(angle < 0) { angle += Math.PI * 2; }
		
		// compute the amount to turn
		float angleDifference = 0;
		if(angle > p.getAngleRadians()) {
			angleDifference = angle - p.getAngleRadians();
		}
		else {
			angleDifference = p.getAngleRadians() - angle;
		}
		
		boolean minimumBackupTimeElapsed = m_backingUpStartTime + RobotSystem.intialBackUpTimeDuration > System.currentTimeMillis();
		boolean shouldTurnSlowly = Math.abs(angleDifference) <= RobotSystem.slowDownAngleDifference;
		boolean shouldBackUpSlowly = distanceFromPoint <= RobotSystem.slowDownDistance;
		
		// instruct the robot to continue forwards (if the angle difference is within a certain accuracy)
		if(Math.abs(angleDifference) < RobotSystem.angleAccuracy) {
			SystemManager.sendInstructionToRobot(shouldBackUpSlowly ? RobotInstruction.BackUpSlowly : RobotInstruction.BackUp);
		}
		// otherwise instruct the robot to turn left (as long as the turn distance is shorter than turning right)
		else if(angle > p.getAngleRadians()) {
			if(Math.abs(angleDifference) <= Math.PI) {
				SystemManager.sendInstructionToRobot(minimumBackupTimeElapsed ? (shouldBackUpSlowly ? RobotInstruction.BackUpSlowly : RobotInstruction.BackUp) : (shouldTurnSlowly ? RobotInstruction.TurnLeftSlowly : RobotInstruction.TurnLeft));
			}
			else {
				SystemManager.sendInstructionToRobot(minimumBackupTimeElapsed ? (shouldBackUpSlowly ? RobotInstruction.BackUpSlowly : RobotInstruction.BackUp) : (shouldTurnSlowly ? RobotInstruction.TurnRightSlowly : RobotInstruction.TurnRight));
			}
		}
		// otherwise instruct the robot to turn right (as long as the turn distance is shorter than turning left)
		else {
			if(Math.abs(angleDifference) <= Math.PI) {
				SystemManager.sendInstructionToRobot(minimumBackupTimeElapsed ? (shouldBackUpSlowly ? RobotInstruction.BackUpSlowly : RobotInstruction.BackUp) : (shouldTurnSlowly ? RobotInstruction.TurnRightSlowly : RobotInstruction.TurnRight));
			}
			else {
				SystemManager.sendInstructionToRobot(minimumBackupTimeElapsed ? (shouldBackUpSlowly ? RobotInstruction.BackUpSlowly : RobotInstruction.BackUp) : (shouldTurnSlowly ? RobotInstruction.TurnLeftSlowly : RobotInstruction.TurnLeft));
			}
		}
	}

	public static ObjectiveBackUpToPosition parseFrom(String data) {
		if(data == null) { return null; }
		StringTokenizer st = new StringTokenizer(data.trim(), " ", false);
		if(st.countTokens() < 8) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Back")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Up")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("to")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Position")) { return null; }
		int positionIndex = -1;
		try { positionIndex = Integer.parseInt(st.nextToken()); }
		catch(NumberFormatException e) { return null; }
		if(!st.nextToken().equalsIgnoreCase("of")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Path")) { return null; }
		String pathName = "";
		while(st.hasMoreTokens()) {
			pathName += st.nextToken() + " ";
		}
		pathName.trim();
		if(positionIndex < 0 || pathName.length() < 1) { return null; }
		return new ObjectiveBackUpToPosition(pathName, positionIndex);
	}
	
	public void reset() {
		super.reset();
		m_destinationVertex = null;
		m_backingUpStartTime = System.currentTimeMillis();
	}
	
	public boolean writeTo(PrintWriter out) {
		if(out == null) { return false; }
		out.print("Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Back Up to Position " + m_positionIndex + " of Path " + m_pathName);
		return true;
	}
	
	public void draw(Graphics g) {
		if(g == null || m_destinationVertex == null) { return; }
		
		g.setColor(SystemManager.settings.getObjectiveColour());
		
		RobotPosition p = SystemManager.robotSystem.getActiveRobot().getActualPosition();
		
		g.drawLine(p.getX(), p.getY(), m_destinationVertex.x, m_destinationVertex.y);
		
		m_destinationVertex.drawSelection(g, SystemManager.settings.getObjectiveColour());
	}
	
	public String toString() {
		return "Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Back Up to Position " + m_positionIndex + " of Path " + m_pathName;
	}
	
}
