package task;

import java.util.StringTokenizer;
import java.io.PrintWriter;
import robot.*;
import path.*;
import planner.*;
import settings.*;
import shared.*n;

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
		m_backingUpStartTime = System.currentTimeMillis();
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
		
		// instruct the robot to continue forwards (if the angle difference is within a certain accuracy)
		if(Math.abs(angleDifference) < RobotSystem.angleAccuracy) {
			SystemManager.sendInstructionToRobot(RobotInstruction.BackUp);
		}
		// otherwise instruct the robot to turn left (as long as the turn distance is shorter than turning right)
		else if(angle > p.getAngleRadians()) {
			if(Math.abs(angleDifference) <= Math.PI) {
				SystemManager.sendInstructionToRobot(m_backingUpStartTime + RobotSystem.intialBackUpTimeDuration > System.currentTimeMillis() ? RobotInstruction.BackUp : RobotInstruction.TurnLeft);
			}
			else {
				SystemManager.sendInstructionToRobot(m_backingUpStartTime + RobotSystem.intialBackUpTimeDuration > System.currentTimeMillis() ? RobotInstruction.BackUp : RobotInstruction.TurnRight);
			}
		}
		// otherwise instruct the robot to turn right (as long as the turn distance is shorter than turning left)
		else {
			if(Math.abs(angleDifference) <= Math.PI) {
				SystemManager.sendInstructionToRobot(m_backingUpStartTime + RobotSystem.intialBackUpTimeDuration > System.currentTimeMillis() ? RobotInstruction.BackUp : RobotInstruction.TurnRight);
			}
			else {
				SystemManager.sendInstructionToRobot(m_backingUpStartTime + RobotSystem.intialBackUpTimeDuration > System.currentTimeMillis() ? RobotInstruction.BackUp : RobotInstruction.TurnLeft);
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
	
	public boolean writeTo(PrintWriter out) {
		if(out == null) { return false; }
		out.print("Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Back Up to Position " + m_positionIndex + " of Path " + m_pathName);
		return true;
	}
	
	public String toString() {
		return "Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Back Up to Position " + m_positionIndex + " of Path " + m_pathName;
	}
	
}
