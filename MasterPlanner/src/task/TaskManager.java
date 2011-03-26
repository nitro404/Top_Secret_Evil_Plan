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
	
	public boolean isCompleted() {
		boolean completed = true;
		for(int i=0;i<m_taskLists.size();i++) {
			completed = completed && m_taskLists.elementAt(i).allTasksCompleted();
		}
		return completed;
	}
	
}
