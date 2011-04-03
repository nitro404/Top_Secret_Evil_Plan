package task;

import java.util.StringTokenizer;
import java.io.PrintWriter;
import settings.Variable;

public class ObjectiveLast extends Objective {
	
	public ObjectiveLast() {
		super();
		m_objectiveType = ObjectiveType.Last;
	}
	
	public void execute() {
		m_objectiveState = ObjectiveState.Completed;
	}

	public static ObjectiveLast parseFrom(String data) {
		if(data == null) { return null; }
		StringTokenizer st = new StringTokenizer(data.trim(), " ", false);
		if(st.countTokens() < 2) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Last")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Objective")) { return null; }
		return new ObjectiveLast();
	}
	
	public boolean writeTo(PrintWriter out) {
		if(out == null) { return false; }
		out.print("Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Last Objective");
		return true;
	}
	
	public String toString() {
		return "Objective " + m_objectiveID + Variable.SEPARATOR_CHAR + " Last Objective";
	}
	
}
