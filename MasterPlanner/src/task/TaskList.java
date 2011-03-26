package task;

import java.util.Vector;

public class TaskList {
	
	private byte m_robotID;
	private int m_currentTask;
	private Vector<Task> m_tasks;
	
	public TaskList(byte robotID) {
		m_robotID = robotID;
		m_tasks = new Vector<Task>();
		m_currentTask = 0;
	}
	
	public int numberOfTasks() { return m_tasks.size(); }
	
	public Task getTask(int index) {
		if(index < 0 || index >= m_tasks.size()) { return null; }
		return m_tasks.elementAt(index);
	}
	
	public Task nextTask() {
		if(hasMoreTasks()) {
			return m_tasks.elementAt(m_currentTask++);
		}
		return null;
	}
	
	public boolean startCurrentTask() {
		if(m_tasks.elementAt(m_currentTask).isNew()) {
			return m_tasks.elementAt(m_currentTask).start();
		}
		return false;
	}
	
	public boolean setTaskStarted(byte taskID) {
		if(taskID < 0 || taskID >= m_tasks.size()) { return false; }
		return m_tasks.elementAt(taskID).setTaskState(TaskState.Started);
	}
	
	public boolean setTaskCompleted(byte taskID) {
		if(taskID < 0 || taskID >= m_tasks.size()) { return false; }
		return m_tasks.elementAt(taskID).setTaskState(TaskState.Completed);
	}
	
	public boolean currentTaskNew() {
		return m_tasks.elementAt(m_currentTask).isNew();
	}
	
	public boolean currentTaskStarted() {
		return m_tasks.elementAt(m_currentTask).isStarted();
	}
	
	public boolean currentTaskCompleted() {
		return m_tasks.elementAt(m_currentTask).isCompleted();
	}
	
	public boolean hasMoreTasks() { return m_currentTask < m_tasks.size() - 1; }
	
	public boolean addTask(Task t) {
		if(t == null) { return false; }
		t.setRobotID(m_robotID);
		t.setTaskID((byte) m_tasks.size());
		t.setTaskState(TaskState.New);
		m_tasks.add(t);
		return true;
	}
	
	public boolean removeTask(int index) {
		if(index < 0 || index >= m_tasks.size()) { return false; }
		m_tasks.remove(index);
		return true;
	}
	
	public boolean allTasksCompleted() {
		return !hasMoreTasks() && currentTaskCompleted();
	}
	
}
