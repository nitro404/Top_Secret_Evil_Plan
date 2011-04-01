package task;

import java.util.StringTokenizer;
import java.io.PrintWriter;

public class ObjectiveBackUpToPosition extends Objective {
	
	private String m_pathName;
	private int m_positionIndex;
	
	public ObjectiveBackUpToPosition(String pathName, int positionIndex) {
		super();
		m_objectiveType = ObjectiveType.BackUpToPosition;
		m_pathName = pathName;
		m_positionIndex = positionIndex;
	}
	
	public void execute() {
		
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
		out.print("Back Up to Position " + m_positionIndex + " of Path " + m_pathName);
		return true;
	}
	
	public String toString() {
		return "Back Up to Position " + m_positionIndex + " of Path " + m_pathName;
	}
	
}
