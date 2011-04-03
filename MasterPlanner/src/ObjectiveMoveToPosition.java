import java.util.StringTokenizer;
import java.io.PrintWriter;

public class ObjectiveMoveToPosition extends Objective {
	
	private String m_pathName;
	private int m_positionIndex;
	private Vertex m_destinationVertex;
	private boolean m_initiallyLookingAtDestination;
	
	public ObjectiveMoveToPosition(String pathName, int positionIndex) {
		super();
		m_objectiveType = ObjectiveType.MoveToPosition;
		m_pathName = pathName;
		m_positionIndex = positionIndex;
		m_initiallyLookingAtDestination = false;
	}
	
	public String getPathName() { return m_pathName; }
	
	public int getPositionIndex() { return m_positionIndex; }
	
	public void setPathName(String pathName) { m_pathName = pathName; }
	
	public void setPositionIndex(int positionIndex) { m_positionIndex = positionIndex; }
	
	public void execute() {
		if(m_objectiveState == ObjectiveState.New) {
			m_objectiveState = ObjectiveState.Started;
		}
		
		if(m_destinationVertex == null) {
			m_destinationVertex = SystemManager.pathSystem.getPath(m_pathName).getVertex(m_positionIndex);
		}
		
		RobotPosition p = SystemManager.robotSystem.getActiveRobot().getActualPosition();
		boolean atPoint = Math.sqrt(Math.pow(p.getX() - m_destinationVertex.x, 2) + Math.pow(p.getY() - m_destinationVertex.y, 2)) < RobotSystem.distanceAccuracy;
		
		if(atPoint) {
			m_objectiveState = ObjectiveState.Completed;
			return;
		}
		
		// compute the vector from the robot's pose to the destination point
		float x = m_destinationVertex.x - p.getX();
		float y = m_destinationVertex.y - p.getY();
		
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
		
		// instruct the robot to continue forwards (if the angle difference is within a certain accuracy)
		if(Math.abs(angleDifference) < RobotSystem.angleAccuracy) {
			SystemManager.sendInstructionToRobot(RobotInstruction.MoveForward);
			m_initiallyLookingAtDestination = true;
		}
		// otherwise instruct the robot to turn left (as long as the turn distance is shorter than turning right)
		else if(angle > p.getAngleRadians()) {
			if(Math.abs(angleDifference) <= Math.PI) {
				SystemManager.sendInstructionToRobot(m_initiallyLookingAtDestination ? RobotInstruction.ArcLeft : RobotInstruction.TurnLeft);
			}
			else {
				SystemManager.sendInstructionToRobot(m_initiallyLookingAtDestination ? RobotInstruction.ArcRight : RobotInstruction.TurnRight);
			}
		}
		// otherwise instruct the robot to turn right (as long as the turn distance is shorter than turning left)
		else {
			if(Math.abs(angleDifference) <= Math.PI) {
				SystemManager.sendInstructionToRobot(m_initiallyLookingAtDestination ? RobotInstruction.ArcRight : RobotInstruction.TurnRight);
			}
			else {
				SystemManager.sendInstructionToRobot(m_initiallyLookingAtDestination ? RobotInstruction.ArcLeft : RobotInstruction.TurnLeft);
			}
		}
	}
	
	public static ObjectiveMoveToPosition parseFrom(String data) {
		if(data == null) { return null; }
		StringTokenizer st = new StringTokenizer(data.trim(), " ", false);
		if(st.countTokens() < 7) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Move")) { return null; }
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
		return new ObjectiveMoveToPosition(pathName, positionIndex);
	}
	
	public boolean writeTo(PrintWriter out) {
		if(out == null) { return false; }
		out.print("Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Move to Position " + m_positionIndex + " of Path " + m_pathName);
		return true;
	}
	
	public String toString() {
		return "Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Move to Position " + m_positionIndex + " of Path " + m_pathName;
	}
	
}
