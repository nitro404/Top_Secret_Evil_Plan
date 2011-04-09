import java.util.Vector;
import java.io.*;
import java.awt.Graphics;

public class Task implements Updatable{
	
	private byte m_taskID;
	private String m_taskName;
	private byte m_robotID;
	private byte m_taskState;
	private byte m_nextTaskType;
	private String m_nextTaskName;
	private String m_altTaskName;
	private Vector<Objective> m_objectives;
	private int m_currentObjectiveIndex;
	
	public Task() {
		m_taskID = -1;
		m_taskName = "";
		m_robotID = -1;
		m_taskState = TaskState.New;
		m_nextTaskType = NextTaskType.Last;
		m_nextTaskName = null;
		m_altTaskName = null;
		m_objectives = new Vector<Objective>();
		m_currentObjectiveIndex = 0;
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
		m_currentObjectiveIndex = 0;
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
		m_currentObjectiveIndex = 0;
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
		m_currentObjectiveIndex = 0;
	}
	
	public byte getTaskID() { return m_taskID; }
	
	public String getTaskName() { return m_taskName; }
	
	public byte getNextTaskType() { return m_nextTaskType; }
	
	public String getNextTaskName() { return m_nextTaskName; }
	
	public String getAltTaskName() { return m_altTaskName; }
	
	public byte getRobotID() { return m_robotID; }
	
	public byte getTaskState() { return m_taskState; }
	
	public int getCurrentObjectiveIndex() { return m_currentObjectiveIndex; }
	
	public int getCurrentObjectiveID() {
		if(m_currentObjectiveIndex < 0 || m_currentObjectiveIndex >= m_objectives.size()) { return -1; }
		return m_objectives.elementAt(m_currentObjectiveIndex).getID();
	}
	
	public Objective getCurrentObjective() {
		return (m_currentObjectiveIndex < 0 || m_currentObjectiveIndex >= m_objectives.size()) ? null : m_objectives.elementAt(m_currentObjectiveIndex);
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
	
	public boolean setCurrentObjectiveByIndex(int objectiveIndex) {
		if(objectiveIndex < 0 || objectiveIndex >= m_objectives.size()) { return false; }
		m_currentObjectiveIndex = objectiveIndex;
		return true;
	}
	
	public boolean setCurrentObjectiveByID(int objectiveID) {
		for(int i=0;i<m_objectives.size();i++) {
			if(objectiveID == m_objectives.elementAt(i).getID()) {
				m_currentObjectiveIndex = i;
				return true;
			}
		}
		return false;
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
		return m_objectives.size() == 0 || (m_taskState == TaskState.Completed);
	}
	
	public void reset() {
		m_taskState = TaskState.New;
		m_currentObjectiveIndex = 0;
		for(int i=0;i<m_objectives.size();i++) {
			m_objectives.elementAt(i).reset();
		}
	}
	
	public void update() {
		if(m_taskState == TaskState.New) {
			return;
		}
		
		if(SystemManager.blockSystem.allBlocksInZoneDelivered(SystemManager.trackerNumber) &&
		   m_objectives.elementAt(m_currentObjectiveIndex).getType() == ObjectiveType.Last ||
		   m_currentObjectiveIndex == m_objectives.size() - 1) {
			m_taskState = TaskState.Completed;
			
			SystemManager.sendInstructionToRobot(RobotInstruction.Stop);
			SystemManager.client.sendSignal(new TaskCompletedSignal(SystemManager.robotSystem.getActiveRobotID(), m_taskID));
			return;
		}
		
		if(m_currentObjectiveIndex >= 0 && m_currentObjectiveIndex < m_objectives.size() &&
		   m_objectives.elementAt(m_currentObjectiveIndex).getState() == ObjectiveState.Completed) {
			byte objectiveType = m_objectives.elementAt(m_currentObjectiveIndex).getType();
			if(!(objectiveType == ObjectiveType.SkipTo || objectiveType == ObjectiveType.ChoiceBlock || objectiveType == ObjectiveType.Last)) {
				if(m_currentObjectiveIndex < m_objectives.size() - 1) { 
					m_currentObjectiveIndex++;
				}
			}
		}
		
		if(m_taskState != TaskState.Started) {
			if(m_currentObjectiveIndex == 0 && m_objectives.size() > 0 || m_objectives.elementAt(0).isNew()) {
				m_taskState = TaskState.Started;
				SystemManager.client.sendSignal(new TaskStartedSignal(SystemManager.robotSystem.getActiveRobotID(), m_taskID));
			}
		}
		
		if(m_taskState != TaskState.Completed) {
			if(m_currentObjectiveIndex >= m_objectives.size() - 1 && m_objectives.elementAt(m_objectives.size() - 1).isCompleted()) {
				m_taskState = TaskState.Completed;
				SystemManager.sendInstructionToRobot(RobotInstruction.Stop);
				SystemManager.client.sendSignal(new TaskCompletedSignal(SystemManager.robotSystem.getActiveRobotID(), m_taskID));
			}
		}
		
		if(m_taskState == TaskState.Started) {
			if(m_currentObjectiveIndex < 0 || m_currentObjectiveIndex >= m_objectives.size()) { return; }
			m_objectives.elementAt(m_currentObjectiveIndex).execute();
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
	
	public void draw(Graphics g) {
		if(g == null || m_currentObjectiveIndex < 0 || m_currentObjectiveIndex >= m_objectives.size()) { return; }
		
		m_objectives.elementAt(m_currentObjectiveIndex).draw(g);
	}
	
}
