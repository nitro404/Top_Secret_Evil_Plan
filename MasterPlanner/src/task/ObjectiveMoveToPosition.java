package task;

import java.util.StringTokenizer;
import java.io.PrintWriter;

public class ObjectiveMoveToPosition extends Objective {
	
	private String m_pathName;
	private int m_positionIndex;
	
	public ObjectiveMoveToPosition(String pathName, int positionIndex) {
		super();
		m_objectiveType = ObjectiveType.MoveToPosition;
		m_pathName = pathName;
		m_positionIndex = positionIndex;
	}
	
	public String getPathName() { return m_pathName; }
	
	public int getPositionIndex() { return m_positionIndex; }
	
	public void setPathName(String pathName) { m_pathName = pathName; }
	
	public void setPositionIndex(int positionIndex) { m_positionIndex = positionIndex; }
	
	public void execute() {
		
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
	
	public boolean writeTo(PrintWriter out) {
		if(out == null) { return false; }
		out.print("Move to Position " + m_positionIndex + " of Path " + m_pathName);
		return true;
	}
	
	public String toString() {
		return "Move to Position " + m_positionIndex + " of Path " + m_pathName;
	}
	
}
