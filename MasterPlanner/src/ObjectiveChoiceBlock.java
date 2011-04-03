import java.util.StringTokenizer;
import java.io.PrintWriter;

public class ObjectiveChoiceBlock extends Objective {
	
	private int m_hasBlockObjectiveID;
	private int m_noBlockObjectiveID;
	
	public ObjectiveChoiceBlock(int hasBlockObjectiveIndex, int noBlockObjectiveIndex) {
		super();
		m_objectiveType = ObjectiveType.ChoiceBlock;
		m_hasBlockObjectiveID = hasBlockObjectiveIndex;
		m_noBlockObjectiveID = noBlockObjectiveIndex;
	}
	
	public int getHasBlockObjectiveID() { return m_hasBlockObjectiveID; }
	
	public int getNoBlockObjectiveID() { return m_noBlockObjectiveID; }
	
	public void setHasBlockObjectiveID(int hasBlockObjectiveIndex) { m_hasBlockObjectiveID = hasBlockObjectiveIndex; }
	
	public void setNoBlockObjectiveID(int noBlockObjectiveIndex) { m_noBlockObjectiveID = noBlockObjectiveIndex; }
	
	public void execute() {
		m_objectiveState = ObjectiveState.Completed;
		
		if(SystemManager.robotSystem.getActiveRobot().hasActiveBlock()) {
			SystemManager.taskManager.getTaskList(SystemManager.robotSystem.getActiveRobotID()).getCurrentTask().setCurrentObjectiveByID(m_hasBlockObjectiveID);
		}
		else {
			SystemManager.taskManager.getTaskList(SystemManager.robotSystem.getActiveRobotID()).getCurrentTask().setCurrentObjectiveByID(m_noBlockObjectiveID);
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
		out.print("Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Choice Block Objective - Has Block " + m_hasBlockObjectiveID + " - No Block " + m_noBlockObjectiveID);
		return true;
	}
	
	public String toString() {
		return "Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Choice Block Objective - Has Block " + m_hasBlockObjectiveID + " - No Block " + m_noBlockObjectiveID;
	}
	
}
