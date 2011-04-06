import java.util.StringTokenizer;
import java.awt.Graphics;
import java.io.PrintWriter;

public class ObjectiveDropOffBlock extends Objective {
	
	private byte m_dropOffLocationID;
	private DropOffLocation m_dropOffLocation;
	private boolean m_atDropOffLocation;
	private boolean m_droppingOffBlock;
	
	public ObjectiveDropOffBlock(byte dropOffLocationID) {
		super();
		m_objectiveType = ObjectiveType.DropOffBlock;
		m_dropOffLocationID = dropOffLocationID;
		m_dropOffLocation = null;
		m_atDropOffLocation = false;
		m_droppingOffBlock = false;
	}
	
	public byte getDropOffLocationID() { return m_dropOffLocationID; }
	
	public void setDropOffLocationID(byte dropOffLocationID) { m_dropOffLocationID = dropOffLocationID; }
	
	public void execute() {
		if(m_objectiveState == ObjectiveState.New) {
			m_objectiveState = ObjectiveState.Started;
		}
		
		if(m_dropOffLocation == null) {
			m_dropOffLocation = SystemManager.blockSystem.getDropOffLocation(m_dropOffLocationID);
		}
		
		if(!m_atDropOffLocation) {
			RobotPosition p = SystemManager.robotSystem.getActiveRobot().getActualPosition();
			float distanceFromPoint = (float) Math.sqrt(Math.pow(p.getX() - m_dropOffLocation.getPosition().x, 2) + Math.pow(p.getY() - m_dropOffLocation.getPosition().y, 2));
			boolean atDropOffLocation = distanceFromPoint < RobotSystem.distanceAccuracy;
			
			if(atDropOffLocation) {
				m_atDropOffLocation = true;
				return;
			}
			
			// compute the vector from the robot's pose to the destination point
			float x = m_dropOffLocation.getPosition().x - p.getX();
			float y = m_dropOffLocation.getPosition().y - p.getY();
			
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
			
			boolean shouldTurnSlowly = Math.abs(angleDifference) <= RobotSystem.slowDownAngleDifference;
			boolean shouldMoveSlowly = distanceFromPoint <= RobotSystem.slowDownDistance;
			
			// instruct the robot to continue forwards (if the angle difference is within a certain accuracy)
			if(Math.abs(angleDifference) < RobotSystem.angleAccuracy) {
				SystemManager.sendInstructionToRobot(shouldMoveSlowly ? RobotInstruction.MoveForwardSlowly : RobotInstruction.MoveForward);
			}
			// otherwise instruct the robot to turn left (as long as the turn distance is shorter than turning right)
			else if(angle > p.getAngleRadians()) {
				if(Math.abs(angleDifference) <= Math.PI) {
					SystemManager.sendInstructionToRobot(shouldTurnSlowly ? RobotInstruction.TurnLeftSlowly : RobotInstruction.TurnLeft);
				}
				else {
					SystemManager.sendInstructionToRobot(shouldTurnSlowly ? RobotInstruction.TurnRightSlowly : RobotInstruction.TurnRight);
				}
			}
			// otherwise instruct the robot to turn right (as long as the turn distance is shorter than turning left)
			else {
				if(Math.abs(angleDifference) <= Math.PI) {
					SystemManager.sendInstructionToRobot(shouldTurnSlowly ? RobotInstruction.TurnRightSlowly : RobotInstruction.TurnRight);
				}
				else {
					SystemManager.sendInstructionToRobot(shouldTurnSlowly ? RobotInstruction.TurnLeftSlowly : RobotInstruction.TurnLeft);
				}
			}
		}
		
		if(!m_droppingOffBlock && m_atDropOffLocation) {
			SystemManager.robotSystem.getActiveRobot().setActiveDropOffLocationID(m_dropOffLocationID);
			SystemManager.sendInstructionToRobot(RobotInstruction.DropOff);
			m_droppingOffBlock = true;
		}
		else if(m_droppingOffBlock && m_atDropOffLocation) {
			SystemManager.sendInstructionToRobot(RobotInstruction.Null);
		}
	}

	public static ObjectiveDropOffBlock parseFrom(String data) {
		if(data == null) { return null; }
		StringTokenizer st = new StringTokenizer(data.trim(), " ", false);
		if(st.countTokens() < 6) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Drop")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Off")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Block")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("at")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Location")) { return null; }
		byte dropOffLocationID = -1;
		try { dropOffLocationID = Byte.parseByte(st.nextToken()); }
		catch(NumberFormatException e) { return null; }
		if(dropOffLocationID < 0) { return null; }
		return new ObjectiveDropOffBlock(dropOffLocationID);
	}
	
	public void reset() {
		super.reset();
		m_atDropOffLocation = false;
		m_droppingOffBlock = false;
		m_dropOffLocation = null;
	}
	
	public boolean writeTo(PrintWriter out) {
		if(out == null) { return false; }
		out.print("Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Drop Off Block at Location " + m_dropOffLocationID);
		return true;
	}
	
	public void draw(Graphics g) {
		if(g == null || m_dropOffLocation == null) { return; }
		
		g.setColor(SystemManager.settings.getObjectiveColour());
		
		RobotPosition p = SystemManager.robotSystem.getActiveRobot().getActualPosition();
		
		g.drawLine(p.getX(), p.getY(), m_dropOffLocation.getPosition().x, m_dropOffLocation.getPosition().y);
		
		m_dropOffLocation.drawSelection(g, SystemManager.settings.getObjectiveColour());
	}
	
	public String toString() {
		return "Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Drop Off Block at Location " + m_dropOffLocationID;
	}

}
