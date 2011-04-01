package task;

import java.io.PrintWriter;

public abstract class Objective {
	
	protected byte m_objectiveState;
	
	private Objective() {
		m_objectiveState = ObjectiveState.New;
	}
	
	abstract void execute();
	
	public boolean isNew() { return m_objectiveState == TaskState.New; }
	
	public boolean isStarted() { return m_objectiveState > TaskState.New; }
	
	public boolean isCompleted() { return m_objectiveState == TaskState.Completed; }
	
	abstract boolean writeTo(PrintWriter out);
	
}
