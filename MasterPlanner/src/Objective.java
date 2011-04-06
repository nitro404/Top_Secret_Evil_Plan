import java.util.StringTokenizer;
import java.io.PrintWriter;
import java.awt.Graphics;

public abstract class Objective {
	
	protected int m_objectiveID;
	protected byte m_objectiveState;
	protected byte m_objectiveType;
	
	public static int nextObjectiveID = 0;
	
	public Objective() {
		m_objectiveType = -1;
		m_objectiveState = ObjectiveState.New;
	}
	
	public int getID() { return m_objectiveID; }
	
	public byte getState() { return m_objectiveState; }
	
	public byte getType() { return m_objectiveType; }
	
	public void setID(int objectiveID) {
		if(objectiveID >= nextObjectiveID) {
			nextObjectiveID = objectiveID + 1;
		}
		m_objectiveID = objectiveID;
	}
	
	public static int getNextObjectiveID() { return nextObjectiveID++; }
	
	public boolean setState(byte objectiveState) {
		if(!ObjectiveState.isValid(objectiveState)) { return false; }
		m_objectiveState = objectiveState;
		return true;
	}
	
	abstract public void execute();
	
	public boolean isNew() { return m_objectiveState == TaskState.New; }
	
	public boolean isStarted() { return m_objectiveState > TaskState.New; }
	
	public boolean isCompleted() { return m_objectiveState == TaskState.Completed; }
	
	public static int parseObjectiveID(String data) {
		if(data == null) { return -1; }
		StringTokenizer st = new StringTokenizer(data.trim(), " ", false);
		if(st.countTokens() != 2) { return -1; }
		if(!st.nextToken().equalsIgnoreCase("Objective")) { return -1; }
		int objectiveID = -1;
		try { objectiveID = Integer.parseInt(st.nextToken().trim()); }
		catch(NumberFormatException e) { return -1; }
		return objectiveID;
	}
	
	public void reset() {
		m_objectiveState = ObjectiveState.New;
	}
	
	abstract public boolean writeTo(PrintWriter out);
	
	abstract public void draw(Graphics g);
	
}
