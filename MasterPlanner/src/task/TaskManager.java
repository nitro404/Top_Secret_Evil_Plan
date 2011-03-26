package task;

import java.util.Vector;

public class TaskManager {
	
	private Vector<TaskList> m_taskLists;
	
	public TaskManager() {
		m_taskLists = new Vector<TaskList>(3);
	}
	
	public boolean start() {
		boolean allTasksStarted = true;
		for(int i=0;i<m_taskLists.size();i++) {
			allTasksStarted = allTasksStarted && m_taskLists.elementAt(i).startCurrentTask();
		}
		return allTasksStarted;
	}
	
	public boolean setTaskStarted(byte robotID, byte taskID) {
		if(robotID < 0 || robotID >= m_taskLists.size()) { return false; }
		return m_taskLists.elementAt(robotID).setTaskStarted(taskID);
	}
	
	public boolean setTaskCompleted(byte robotID, byte taskID) {
		if(robotID < 0 || robotID >= m_taskLists.size()) { return false; }
		return m_taskLists.elementAt(robotID).setTaskCompleted(taskID);
	}
	
	public boolean isCompleted() {
		boolean completed = true;
		for(int i=0;i<m_taskLists.size();i++) {
			completed = completed && m_taskLists.elementAt(i).allTasksCompleted();
		}
		return completed;
	}
	
}
