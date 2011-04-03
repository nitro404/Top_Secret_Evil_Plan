package task;

import java.util.StringTokenizer;
import java.io.PrintWriter;
import settings.*;
import planner.*;

public class ObjectiveChoiceBlock extends Objective {
	
	private int m_hasBlockObjectiveIndex;
	private int m_noBlockObjectiveIndex;
	
	public ObjectiveChoiceBlock(int hasBlockObjectiveIndex, int noBlockObjectiveIndex) {
		super();
		m_objectiveType = ObjectiveType.ChoiceBlock;
		m_hasBlockObjectiveIndex = hasBlockObjectiveIndex;
		m_noBlockObjectiveIndex = noBlockObjectiveIndex;
	}
	
	public int getHasBlockObjectiveIndex() { return m_hasBlockObjectiveIndex; }
	
	public int getNoBlockObjectiveIndex() { return m_noBlockObjectiveIndex; }
	
	public void setHasBlockObjectiveIndex(int hasBlockObjectiveIndex) { m_hasBlockObjectiveIndex = hasBlockObjectiveIndex; }
	
	public void setNoBlockObjectiveIndex(int noBlockObjectiveIndex) { m_noBlockObjectiveIndex = noBlockObjectiveIndex; }
	
	public void execute() {
		m_objectiveState = ObjectiveState.Completed;
		
		if(SystemManager.robotSystem.getActiveRobot().hasActiveBlock()) {
			SystemManager.taskManager.getTaskList(SystemManager.robotSystem.getActiveRobotID()).getCurrentTask().setCurrentObjectiveNumber(m_hasBlockObjectiveIndex);
		}
		else {
			SystemManager.taskManager.getTaskList(SystemManager.robotSystem.getActiveRobotID()).getCurrentTask().setCurrentObjectiveNumber(m_noBlockObjectiveIndex);
		}
	}

	public static ObjectiveChoiceBlock parseFrom(String data) {
		if(data == null) { return null; }
		StringTokenizer st = new StringTokenizer(data.trim(), " ", false);
		if(st.countTokens() < 11) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Choice")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Block")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Objective")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("-")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Has")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Block")) { return null; }
		
		int hasBlockObjectiveID = -1;
		try { hasBlockObjectiveID = Integer.parseInt(st.nextToken()); }
		catch(NumberFormatException e) { return null; }
		if(hasBlockObjectiveID < 0) { return null; }
		
		if(!st.nextToken().equalsIgnoreCase("-")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("No")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Block")) { return null; }
		
		int noBlockObjectiveID = -1;
		try { noBlockObjectiveID = Integer.parseInt(st.nextToken()); }
		catch(NumberFormatException e) { return null; }
		if(noBlockObjectiveID < 0) { return null; }
		
		return new ObjectiveChoiceBlock(hasBlockObjectiveID, noBlockObjectiveID);
	}
	
	public boolean writeTo(PrintWriter out) {
		if(out == null) { return false; }
		out.print("Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Choice Block Objective - Has Block " + m_hasBlockObjectiveIndex + " - No Block " + m_noBlockObjectiveIndex);
		return true;
	}
	
	public String toString() {
		return "Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Choice Block Objective - Has Block " + m_hasBlockObjectiveIndex + " - No Block " + m_noBlockObjectiveIndex;
	}
	
}
