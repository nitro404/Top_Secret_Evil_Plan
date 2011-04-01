package task;

import java.util.Vector;
import java.io.*;
import settings.*;
import shared.*;

public class Task implements Updatable{
	
	private byte m_taskID;
	private String m_taskName;
	private byte m_robotID;
	private byte m_taskState;
	private byte m_nextTaskType;
	private String m_nextTaskName;
	private String m_altTaskName;
	private Vector<Objective> m_objectives;
	private int m_currentObjective;
	
	public Task() {
		m_taskID = -1;
		m_taskName = "";
		m_robotID = -1;
		m_taskState = TaskState.New;
		m_nextTaskType = NextTaskType.Last;
		m_nextTaskName = null;
		m_altTaskName = null;
		m_objectives = new Vector<Objective>();
		m_currentObjective = 0;
	}
	
	public Task(String taskName) {
		m_taskID = -1;
		m_taskName = (taskName == null) ? "" : taskName;
		m_robotID = -1;
		m_taskState = TaskState.New;
		m_nextTaskType = NextTaskType.Last;
		m_nextTaskName = null;
		m_altTaskName = null;
		m_objectives = new Vector<Objective>();
		m_currentObjective = 0;
	}
	
	public Task(String taskName, String nextTaskName) {
		m_taskID = -1;
		m_taskName = (taskName == null) ? "" : taskName;
		m_robotID = -1;
		m_taskState = TaskState.New;
		m_nextTaskType = NextTaskType.Normal;
		m_nextTaskName = nextTaskName;
		m_altTaskName = null;
		m_objectives = new Vector<Objective>();
		m_currentObjective = 0;
	}
	
	public Task(String taskName, String nextTaskName, String altTaskName) {
		m_taskID = -1;
		m_taskName = (taskName == null) ? "" : taskName;
		m_robotID = -1;
		m_taskState = TaskState.New;
		m_nextTaskType = NextTaskType.Choice;
		m_nextTaskName = nextTaskName;
		m_altTaskName = altTaskName;
		m_objectives = new Vector<Objective>();
		m_currentObjective = 0;
	}
	
	public byte getTaskID() { return m_taskID; }
	
	public String getTaskName() { return m_taskName; }
	
	public byte getNextTaskType() { return m_nextTaskType; }
	
	public String getNextTaskName() { return m_nextTaskName; }
	
	public String getAltTaskName() { return m_altTaskName; }
	
	public byte getRobotID() { return m_robotID; }
	
	public byte getTaskState() { return m_taskState; }
	
	public int getCurrentObjectiveNumber() { return m_currentObjective; }
	
	public Objective getCurrentObjective() {
		return (m_currentObjective < 0 || m_currentObjective >= m_objectives.size()) ? null : m_objectives.elementAt(m_currentObjective);
	}
	
	public int numberOfObjectives() { return m_objectives.size(); }
	
	public Objective getObjective(int objectiveIndex) {
		return (objectiveIndex < 0 || objectiveIndex >= m_objectives.size()) ? null : m_objectives.elementAt(objectiveIndex);
	}
	
	public Vector<Objective> getObjectives() { return m_objectives; }
	
	public boolean addObjective(Objective o) {
		if(o == null) { return false; }
		m_objectives.add(o);
		return true;
	}
	
	public boolean setObjective(int index, Objective o) {
		if(o == null || index < 0 || index >= m_objectives.size()) { return false; }
		m_objectives.set(index, o);
		return true;
	}
	
	public boolean setObjectives(Vector<Objective> objectives) {
		if(objectives == null) { return false; }
		m_objectives = objectives;
		return true;
	}
	
	public boolean removeObjective(int index) {
		if(index < 0 || index >= m_objectives.size()) { return false; }
		m_objectives.remove(index);
		return true;
	}
	
	public void setTaskID(byte taskID) { m_taskID = taskID; }
	
	public boolean setTaskName(String taskName) {
		if(taskName == null) { return false; }
		m_taskName = taskName;
		return true;
	}
	
	public boolean setNextTaskType(byte nextTaskType) {
		if(!NextTaskType.isValid(nextTaskType)) { return false; }
		m_nextTaskType = nextTaskType;
		return true;
	}
	
	public void setNextTaskName(String nextTaskName) { m_nextTaskName = nextTaskName; }
	
	public void setAltTaskName(String altTaskName) { m_altTaskName = altTaskName; }
	
	public void setRobotID(byte robotID) { m_robotID = robotID; }
	
	public boolean setTaskState(byte taskState) {
		if(TaskState.isValid(taskState)) {
			m_taskState = taskState;
			return true;
		}
		return false;
	}
	
	public boolean setCurrentObjectiveNumber(int objectiveNumber) {
		if(objectiveNumber < 0 || objectiveNumber >= m_objectives.size()) { return false; }
		m_currentObjective = objectiveNumber;
		return true;
	}
	
	public boolean start() {
		m_taskState = TaskState.Started;
		return true;
	}
	
	public boolean isNew() {
		return m_taskState == TaskState.New;
	}
	
	public boolean isStarted() {
		return m_taskState > TaskState.New; 
	}
	
	public boolean isCompleted() {
		return m_taskState == TaskState.Completed;
	}
	
	public void update() {
		if(m_taskState == TaskState.New) {
			return;
		}
		
		if(m_taskState != TaskState.Started) {
			if(m_currentObjective == 0 && m_objectives.size() > 0 || m_objectives.elementAt(0).isNew()) {
				m_taskState = TaskState.Started;
			}
		}
		
		if(m_taskState != TaskState.Completed) {
			if(m_currentObjective >= m_objectives.size() - 1 && m_objectives.elementAt(m_objectives.size() - 1).isCompleted()) {
				m_taskState = TaskState.Completed;
			}
		}
		
		if(m_taskState == TaskState.Started) {
			if(m_currentObjective < 0 || m_currentObjective >= m_objectives.size()) { return; }
			m_objectives.elementAt(m_currentObjective).execute();
		}
	}
	
	public boolean writeTo(PrintWriter out) {
		if(out == null) { return false; }
		
		Vector<Variable> properties = new Vector<Variable>();
		properties.add(new Variable("Robot ID", Byte.toString(m_robotID)));
		properties.add(new Variable("Next Task Type", NextTaskType.toString(m_nextTaskType)));
		properties.add(new Variable("Next Task Name", m_nextTaskName));
		properties.add(new Variable("Alternate Task Name", m_altTaskName));
		properties.add(new Variable("Objectives", ""));
		
		out.println("[" + m_taskName + "]");
		for(int i=0;i<properties.size();i++) {
			try { properties.elementAt(i).writeTo(out); }
			catch(IOException e) { }
		}
		for(int i=0;i<m_objectives.size();i++) {
			out.print("\t");
			m_objectives.elementAt(i).writeTo(out);
			out.println();
		}
		out.println();
		
		return true;
	}
	
}
