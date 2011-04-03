package task;

import java.util.StringTokenizer;
import java.io.PrintWriter;
import robot.*;
import block.*;
import planner.*;
import settings.*;

public class ObjectivePickUpBlock extends Objective {
	
	private byte m_blockID;
	private Block m_block;
	private boolean m_pickingUpBlock;
	
	public ObjectivePickUpBlock(byte blockID) {
		super();
		m_objectiveType = ObjectiveType.PickUpBlock;
		m_blockID = blockID;
		m_block = null;
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
		
		if(!m_pickingUpBlock) {
			SystemManager.sendInstructionToRobot(RobotInstruction.PickUp);
		}
		else {
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
	
	public boolean writeTo(PrintWriter out) {
		if(out == null) { return false; }
		out.print("Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Pick Up Block " + m_blockID);
		return true;
	}
	
	public String toString() {
		return "Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Pick Up Block " + m_blockID;
	}

}
