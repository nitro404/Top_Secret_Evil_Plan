import java.util.StringTokenizer;
import java.io.PrintWriter;

public class ObjectiveSkipTo extends Objective {
	
	private int m_skipToObjectiveID;
	
	public ObjectiveSkipTo(int skipToObjectiveID) {
		super();
		m_objectiveType = ObjectiveType.SkipTo;
		m_skipToObjectiveID = skipToObjectiveID;
	}
	
	public int getSkipToObjectiveID() { return m_skipToObjectiveID; }
	
	public void setSkipToObjectiveID(int skipToObjectiveID) { m_skipToObjectiveID = skipToObjectiveID; }
	
	public void execute() {
		m_objectiveState = ObjectiveState.Completed;
		
		SystemManager.taskManager.getTaskList(SystemManager.robotSystem.getActiveRobotID()).getCurrentTask().setCurrentObjectiveByID(m_skipToObjectiveID);
	}

	public static ObjectiveSkipTo parseFrom(String data) {
		if(data == null) { return null; }
		StringTokenizer st = new StringTokenizer(data.trim(), " ", false);
		if(st.countTokens() < 4) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Skip")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("to")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Objective")) { return null; }
		int objectiveID = -1;
		try { objectiveID = Integer.parseInt(st.nextToken()); }
		catch(NumberFormatException e) { return null; }
		if(objectiveID < 0) { return null; }
		return new ObjectiveSkipTo(objectiveID);
	}
	
	public boolean writeTo(PrintWriter out) {
		if(out == null) { return false; }
		out.print("Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Skip to Objective " + m_skipToObjectiveID);
		return true;
	}
	
	public String toString() {
		return "Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Skip to Objective " + m_skipToObjectiveID;
	}
	
}
