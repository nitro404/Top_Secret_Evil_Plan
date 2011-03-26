package task;

public abstract class Task {
	
	protected byte m_taskID;
	protected byte m_robotID;
	protected byte m_taskState;
	
	public byte getTaskID() { return m_taskID; }
	
	public byte getRobotID() { return m_robotID; }
	
	public byte getTaskState() { return m_taskState; }
	
	public void setTaskID(byte taskID) { m_taskID = taskID; }
	
	public void setRobotID(byte robotID) { m_robotID = robotID; }
	
	public boolean setTaskState(byte taskState) {
		if(TaskState.isValid(taskState)) {
			m_taskState = taskState;
			return true;
		}
		return false;
	}
	
	public boolean start() { return false; }
	
	public boolean isNew() { return m_taskState == TaskState.New; }
	
	public boolean isStarted() { return m_taskState > TaskState.New; }
	
	public boolean isCompleted() { return m_taskState == TaskState.Completed; }
	
}
