package task;

import java.util.Vector;
import java.io.*;
import planner.*;
import shared.*;

public class TaskList implements Updatable {
	
	private byte m_robotID;
	private int m_currentTask;
	private Vector<Task> m_tasks;
	
	public TaskList(byte robotID) {
		m_robotID = robotID;
		m_tasks = new Vector<Task>();
		m_currentTask = 0;
	}
	
	public int numberOfTasks() { return m_tasks.size(); }
	
	public int numberOfTasksCompleted() {
		int numberOfTasksCompleted = 0;
		for(int i=0;i<m_tasks.size();i++) {
			if(m_tasks.elementAt(i).isCompleted()) {
				numberOfTasksCompleted++;
			}
		}
		return numberOfTasksCompleted;
	}
	
	public Task getTask(int index) {
		if(index < 0 || index >= m_tasks.size()) { return null; }
		return m_tasks.elementAt(index);
	}
	
	public int indexOfTask(String taskName) {
		if(taskName == null) { return -1; }
		String temp = taskName.trim();
		if(temp.length() == 0) { return -1; }
		
		for(int i=0;i<m_tasks.size();i++) {
			if(temp.equalsIgnoreCase(m_tasks.elementAt(i).getTaskName())) {
				return i;
			}
		}
		return -1;
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
	
	public boolean hasMoreTasks() {
		return !(m_tasks.elementAt(m_currentTask).getNextTaskType() == NextTaskType.Last);
	}
	
	public boolean addTask(Task t) {
		if(t == null) { return false; }
		t.setRobotID(m_robotID);
		t.setTaskID((byte) m_tasks.size());
		t.setTaskState(TaskState.New);
		t.setCurrentObjectiveNumber(0);
		m_tasks.add(t);
		return true;
	}
	
	public boolean setTask(int index, Task t) {
		if(t == null || index < 0 || index >= m_tasks.size()) { return false; }
		m_tasks.set(index, t);
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
	
	public void update() {
		if(m_currentTask < 0 || m_currentTask >= m_tasks.size()) { return; }
		
		if(!m_tasks.elementAt(m_currentTask).isCompleted()) {
			if(m_currentTask < 0 || m_currentTask >= m_tasks.size()) { return; }
			m_tasks.elementAt(m_currentTask).update();
		}
		else {
			if(m_tasks.elementAt(m_currentTask).getNextTaskType() == NextTaskType.Normal) {
				String nextTaskName = m_tasks.elementAt(m_currentTask).getNextTaskName();
				
				if(nextTaskName == null) {
					SystemManager.console.writeLine("ERROR: Task " + m_tasks.elementAt(m_currentTask).getTaskName() + " has no next task specified.");
					return;
				}
				
				int nextTaskIndex = indexOfTask(nextTaskName);
				
				if(nextTaskIndex == -1) {
					SystemManager.console.writeLine("ERROR: Next task of task " + m_tasks.elementAt(m_currentTask).getTaskName() + " does not exist.");
					return;
				}
				
				m_currentTask = nextTaskIndex;
				m_tasks.elementAt(m_currentTask).start();
			}
			else if(m_tasks.elementAt(m_currentTask).getNextTaskType() == NextTaskType.Choice) {
				String nextTaskName;
				if(SystemManager.blockSystem.hasActiveBlock()) {
					nextTaskName = m_tasks.elementAt(m_currentTask).getNextTaskName();
				}
				else {
					nextTaskName = m_tasks.elementAt(m_currentTask).getAltTaskName();
				}
				
				if(nextTaskName == null) {
					SystemManager.console.writeLine("ERROR: Task " + m_tasks.elementAt(m_currentTask).getTaskName() + " has no next task specified.");
					return;
				}
				
				int nextTaskIndex = indexOfTask(nextTaskName);
				
				if(nextTaskIndex == -1) {
					SystemManager.console.writeLine("ERROR: Next task of task " + m_tasks.elementAt(m_currentTask).getTaskName() + " does not exist.");
					return;
				}
				
				m_currentTask = nextTaskIndex;
				m_tasks.elementAt(m_currentTask).start();
			}
			else if(m_tasks.elementAt(m_currentTask).getNextTaskType() == NextTaskType.Last) {
				return;
			}
		}
	}
	
	public boolean writeTo(PrintWriter out) {
		if(out == null) { return false; }
		
		for(int i=0;i<m_tasks.size();i++) {
			m_tasks.elementAt(i).writeTo(out);
		}
		
		return true;
	}
	
}
