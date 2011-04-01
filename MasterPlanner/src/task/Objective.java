package task;

import java.io.PrintWriter;

public abstract class Objective {
	
	protected byte m_objectiveState;
	protected byte m_objectiveType;
	
	public Objective() {
		m_objectiveType = -1;
		m_objectiveState = ObjectiveState.New;
	}
	
	abstract public void execute();
	
	public boolean isNew() { return m_objectiveState == TaskState.New; }
	
	public boolean isStarted() { return m_objectiveState > TaskState.New; }
	
	public boolean isCompleted() { return m_objectiveState == TaskState.Completed; }
	
	abstract public boolean writeTo(PrintWriter out);
	
}
