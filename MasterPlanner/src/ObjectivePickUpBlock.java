import java.util.StringTokenizer;
import java.awt.Graphics;
import java.io.PrintWriter;

public class ObjectivePickUpBlock extends Objective {
	
	private byte m_blockID;
	private Block m_block;
	private boolean m_lookingAtBlock;
	private boolean m_pickingUpBlock;
	
	public ObjectivePickUpBlock(byte blockID) {
		super();
		m_objectiveType = ObjectiveType.PickUpBlock;
		m_blockID = blockID;
		m_block = null;
		m_lookingAtBlock = false;
		m_pickingUpBlock = false;
	}
	
	public byte getBlockID() { return m_blockID; }
	
	public void setBlockID(byte blockID) { m_blockID = blockID; }
	
	public void execute() {
		if(m_objectiveState == ObjectiveState.New) {
			m_objectiveState = ObjectiveState.Started;
		}
		
		if(m_block == null) {
			m_block = SystemManager.blockSystem.getBlock(m_blockID);
		}
		
		if(!m_lookingAtBlock) {
			RobotPosition p = SystemManager.robotSystem.getActiveRobot().getActualPosition();
			
			// compute the vector from the robot's pose to the destination point
			float x = (float) (m_block.getActualPosition().getX() - p.getX());
			float y = (float) (m_block.getActualPosition().getY() - p.getY());
			
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
			
			// instruct the robot to continue forwards (if the angle difference is within a certain accuracy)
			if(Math.abs(angleDifference) < RobotSystem.angleAccuracy) {
				m_lookingAtBlock = true;
			}
			// otherwise instruct the robot to turn left (as long as the turn distance is shorter than turning right)
			else if(angle > p.getAngleRadians()) {
				if(Math.abs(angleDifference) <= Math.PI) {
					SystemManager.sendInstructionToRobot(shouldTurnSlowly ? RobotInstruction.TurnLeftSlowly : RobotInstruction.TurnLeftSlowly);
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
					SystemManager.sendInstructionToRobot(shouldTurnSlowly ? RobotInstruction.TurnLeftSlowly : RobotInstruction.TurnLeftSlowly);
				}
			}
		}
		
		if(!m_pickingUpBlock && m_lookingAtBlock) {
			SystemManager.sendInstructionToRobot(RobotInstruction.PickUp);
			SystemManager.robotSystem.getActiveRobot().setState(RobotState.FindingBlock);
			SystemManager.robotSystem.getActiveRobot().setActiveBlockID(m_blockID);
			
			SystemManager.client.sendSignal(new RobotStateChangeSignal(SystemManager.robotSystem.getActiveRobotID(), SystemManager.robotSystem.getActiveRobot().getState()));
			
			m_pickingUpBlock = true;
		}
		else if(m_pickingUpBlock && m_lookingAtBlock) {
			SystemManager.sendInstructionToRobot(RobotInstruction.Null);
		}
	}

	public static ObjectivePickUpBlock parseFrom(String data) {
		if(data == null) { return null; }
		StringTokenizer st = new StringTokenizer(data.trim(), " ", false);
		if(st.countTokens() < 4) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Pick")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Up")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Block")) { return null; }
		byte blockID = -1;
		try { blockID = Byte.parseByte(st.nextToken()); }
		catch(NumberFormatException e) { return null; }
		if(blockID < 0) { return null; }
		return new ObjectivePickUpBlock(blockID);
	}
	
	public void reset() {
		super.reset();
		m_block = null;
		m_lookingAtBlock = false;
		m_pickingUpBlock = false;
	}
	
	public boolean writeTo(PrintWriter out) {
		if(out == null) { return false; }
		out.print("Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Pick Up Block " + m_blockID);
		return true;
	}
	
	public void draw(Graphics g) {
		if(g == null || m_block == null) { return; }
		
		m_block.drawSelection(g, SystemManager.settings.getObjectiveColour());
	}
	
	public String toString() {
		return "Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Pick Up Block " + m_blockID;
	}

}
