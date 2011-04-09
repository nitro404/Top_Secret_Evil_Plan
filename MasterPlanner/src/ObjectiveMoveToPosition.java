import java.util.StringTokenizer;
import java.awt.Graphics;
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
	
	public void setDestinationVertex(int x, int y) {
		m_destinationVertex = new Vertex(x, y);
	}
	
	public void execute() {
		if(m_objectiveState == ObjectiveState.New) {
			m_objectiveState = ObjectiveState.Started;
			
			SystemManager.robotSystem.getActiveRobot().setState(RobotState.Moving);
			
			SystemManager.client.sendSignal(new RobotStateChangeSignal(SystemManager.robotSystem.getActiveRobotID(), SystemManager.robotSystem.getActiveRobot().getState()));
		}
		
		if(m_destinationVertex == null) {
			m_destinationVertex = SystemManager.pathSystem.getPath(m_pathName).getVertex(m_positionIndex);
		}
		
		RobotPosition p = SystemManager.robotSystem.getActiveRobot().getActualPosition();
		float distanceFromPoint = (float) Math.sqrt(Math.pow(p.getX() - m_destinationVertex.x, 2) + Math.pow(p.getY() - m_destinationVertex.y, 2));
		boolean atPoint = distanceFromPoint < RobotSystem.distanceAccuracy;
		
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
		
		boolean shouldArc = m_initiallyLookingAtDestination && Math.abs(angleDifference) <= RobotSystem.maxArcAngleDifference && distanceFromPoint > RobotSystem.slowDownDistance;
		boolean shouldTurnSlowly = Math.abs(angleDifference) <= RobotSystem.slowDownAngleDifference;
		boolean shouldMoveSlowly = distanceFromPoint <= RobotSystem.slowDownDistance;
		
		// instruct the robot to continue forwards (if the angle difference is within a certain accuracy)
		if(Math.abs(angleDifference) < RobotSystem.angleAccuracy) {
			SystemManager.sendInstructionToRobot(shouldMoveSlowly ? RobotInstruction.MoveForwardSlowly : RobotInstruction.MoveForward);
			m_initiallyLookingAtDestination = true;
		}
		// otherwise instruct the robot to turn left (as long as the turn distance is shorter than turning right)
		else if(angle > p.getAngleRadians()) {
			if(Math.abs(angleDifference) <= Math.PI) {
				SystemManager.sendInstructionToRobot(shouldArc ? RobotInstruction.ArcLeft : (shouldTurnSlowly ? RobotInstruction.TurnLeftSlowly : RobotInstruction.TurnLeft));
			}
			else {
				SystemManager.sendInstructionToRobot(shouldArc ? RobotInstruction.ArcRight : (shouldTurnSlowly ? RobotInstruction.TurnRightSlowly : RobotInstruction.TurnRight));
			}
		}
		// otherwise instruct the robot to turn right (as long as the turn distance is shorter than turning left)
		else {
			if(Math.abs(angleDifference) <= Math.PI) {
				SystemManager.sendInstructionToRobot(shouldArc ? RobotInstruction.ArcRight : (shouldTurnSlowly ? RobotInstruction.TurnRightSlowly : RobotInstruction.TurnRight));
			}
			else {
				SystemManager.sendInstructionToRobot(shouldArc ? RobotInstruction.ArcLeft : (shouldTurnSlowly ? RobotInstruction.TurnLeftSlowly : RobotInstruction.TurnLeft));
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
	
	public void reset() {
		super.reset();
		m_initiallyLookingAtDestination = false;
	}
	
	public boolean writeTo(PrintWriter out) {
		if(out == null) { return false; }
		out.print("Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Move to Position " + m_positionIndex + " of Path " + m_pathName);
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
		return "Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Move to Position " + m_positionIndex + " of Path " + m_pathName;
	}
	
}
