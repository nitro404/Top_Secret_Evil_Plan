import java.util.Vector;
import java.io.*;
import java.awt.Graphics;

public class TaskManager implements Updatable {
	
	private Vector<TaskList> m_taskLists;
	private ObjectiveMoveToPosition m_returnToSpawnPosition;
	private boolean m_isFinished;
	
	public TaskManager() {
		m_taskLists = new Vector<TaskList>(SystemManager.robotSystem.numberOfRobots());
		
		for(byte i=0;i<SystemManager.robotSystem.numberOfRobots();i++) {
			m_taskLists.add(new TaskList(i));
		}
		
		m_returnToSpawnPosition = null;
		m_isFinished = false;
	}
	
	public int numberOfTaskLists() { return m_taskLists.size(); }
	
	public int totalNumberOfTasksCompleted() {
		int totalNumberOfTasksCompleted = 0;
		for(int i=0;i<m_taskLists.size();i++) {
			totalNumberOfTasksCompleted += m_taskLists.elementAt(i).numberOfTasksCompleted();
		}
		return totalNumberOfTasksCompleted;
	}
	
	public int totalNumberOfTasks() {
		int totalNumberOfTasks = 0;
		for(int i=0;i<m_taskLists.size();i++) {
			totalNumberOfTasks += m_taskLists.elementAt(i).numberOfTasks();
		}
		return totalNumberOfTasks;
	}
	
	public TaskList getTaskList(int index) {
		return (index < 0 || index >= m_taskLists.size()) ? null : m_taskLists.elementAt(index);
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
		for(int i=0;i<m_taskLists.size();i++) {
			if(!m_taskLists.elementAt(i).allTasksCompleted()) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isObjectiveIDTaken(int objectiveID) {
		if(objectiveID < 0) { return false; }
		for(int i=0;i<m_taskLists.size();i++) {
			for(int j=0;j<m_taskLists.elementAt(i).numberOfTasks();j++) {
				for(int k=0;k<m_taskLists.elementAt(i).getTask(j).numberOfObjectives();k++) {
					if(m_taskLists.elementAt(i).getTask(j).getObjective(k).getID() == objectiveID) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void reset() {
		m_returnToSpawnPosition = null;
		m_isFinished = false;
		for(int i=0;i<m_taskLists.size();i++) {
			m_taskLists.elementAt(i).reset();
		}
	}
	
	public void update() {
		if(SystemManager.robotSystem.hasActiveRobot()) {
			if(!m_taskLists.elementAt(SystemManager.robotSystem.getActiveRobotID()).allTasksCompleted()) {
				m_taskLists.elementAt(SystemManager.robotSystem.getActiveRobotID()).update();
			}
			else {
				if(m_returnToSpawnPosition == null) {
					m_returnToSpawnPosition = new ObjectiveMoveToPosition(null, -1);
					m_returnToSpawnPosition.setDestinationVertex(SystemManager.robotSystem.getActiveRobot().getSpawnPosition().getX(), SystemManager.robotSystem.getActiveRobot().getSpawnPosition().getY());
					m_returnToSpawnPosition.setID(Objective.getNextObjectiveID());
				}
				
				if(!m_returnToSpawnPosition.isCompleted()) {
					m_returnToSpawnPosition.execute();
				}
				else {
					if(!m_isFinished) {
						SystemManager.sendInstructionToRobot(RobotInstruction.Finished);
						m_isFinished = true;
					}
				}
			}
		}
	}
	
	public boolean writeTo(String fileName) {
		if(fileName == null) { return false; }
		
		PrintWriter out;
		try {
			out = new PrintWriter(new FileWriter(fileName));
			
			for(int i=0;i<m_taskLists.size();i++) {
				m_taskLists.elementAt(i).writeTo(out);
			}
			
			out.close();
		}
		catch(IOException e) {
			System.out.println("ERROR: Unable to write tasks lists to file.");
			return false;
		}
		return true;
	}
	
	public static TaskManager readFrom(String fileName) {
		if(fileName == null) { return null; }
		
		BufferedReader in;
		String input, data;
		TaskManager taskManager = new TaskManager();
		Task newTask;
		VariableSystem properties = new VariableSystem();
		Variable newVariable;
		byte objectiveType;
		Objective newObjective;
		int objectiveID;
		boolean parseObjectives = false;
		
		try {
			// open the task list data file
			in = new BufferedReader(new FileReader(fileName));
			
			newTask = new Task();
			
			while((input = in.readLine()) != null) {
				data = input.trim();
				
				// ignore empty lines
				if(data.length() == 0) {
					continue;
				}
				
				// parse a task name
				if(data.charAt(0) == '[' && data.charAt(data.length() - 1) == ']') {
					// if a task was already read, then store it
					if(parseObjectives) {
						if(newTask.getRobotID() >= 0 && newTask.getRobotID() < taskManager.numberOfTaskLists()) {
							taskManager.m_taskLists.elementAt(newTask.getRobotID()).addTask(newTask);
						}
						newTask = new Task();
						parseObjectives = false;
						properties = new VariableSystem();
					}
					
					//set the name for the new task
					newTask.setTaskName(data.substring(1, data.length() - 1).trim());
				}
				// parse task properties
				else if(!parseObjectives) {
					newVariable = Variable.parseFrom(data);
					if(newVariable != null) {
						if(!newVariable.getID().equalsIgnoreCase("Objectives")) {
							properties.add(newVariable);
						}
						else {
							try { newTask.setRobotID(Byte.parseByte(properties.getValue("Robot ID"))); }
							catch(NumberFormatException e) { }
							newTask.setNextTaskType(NextTaskType.parseFrom(properties.getValue("Next Task Type")));
							newTask.setNextTaskName(properties.getValue("Next Task Name"));
							newTask.setAltTaskName(properties.getValue("Alternate Task Name"));
							parseObjectives = true;
						}
					}
				}
				// parse task objectives
				else {
					newObjective = null;
					newVariable = Variable.parseFrom(data);
					
					if(newVariable != null) {
						objectiveID = Objective.parseObjectiveID(newVariable.getID());
						objectiveType = ObjectiveType.parseFromStartOf(newVariable.getValue());
						
						if(objectiveID >= 0) {
							if(objectiveType == ObjectiveType.MoveToPosition) {
								newObjective = ObjectiveMoveToPosition.parseFrom(newVariable.getValue());
							}
							else if(objectiveType == ObjectiveType.BackUpToPosition) {
								newObjective = ObjectiveBackUpToPosition.parseFrom(newVariable.getValue());
							}
							else if(objectiveType == ObjectiveType.LookAtPosition) {
								newObjective = ObjectiveLookAtPosition.parseFrom(newVariable.getValue());
							}
							else if(objectiveType == ObjectiveType.PickUpBlock) {
								newObjective = ObjectivePickUpBlock.parseFrom(newVariable.getValue());
							}
							else if(objectiveType == ObjectiveType.DropOffBlock) {
								newObjective = ObjectiveDropOffBlock.parseFrom(newVariable.getValue());
							}
							else if(objectiveType == ObjectiveType.SkipTo) {
								newObjective = ObjectiveSkipTo.parseFrom(newVariable.getValue());
							}
							else if(objectiveType == ObjectiveType.ChoiceBlock) {
								newObjective = ObjectiveChoiceBlock.parseFrom(newVariable.getValue());
							}
							else if(objectiveType == ObjectiveType.Last) {
								newObjective = ObjectiveLast.parseFrom(newVariable.getValue());
							}
							
							if(newObjective != null) {
								newObjective.setID(objectiveID);
								newTask.addObjective(newObjective);
							}
						}
					}
				}
			}
			
			// if the end of the file is reached while a task was being parsed, store it
			if(parseObjectives) {
				if(newTask.getRobotID() >= 0 && newTask.getRobotID() < taskManager.numberOfTaskLists()) {
					taskManager.m_taskLists.elementAt(newTask.getRobotID()).addTask(newTask);
				}
			}
			
			Objective o;
			int largestObjectiveID = 0;
			for(int i=0;i<taskManager.numberOfTaskLists();i++) {
				for(int j=0;j<taskManager.getTaskList(i).numberOfTasks();j++) {
					for(int k=0;k<taskManager.getTaskList(i).getTask(j).numberOfObjectives();k++) {
						o = taskManager.getTaskList(i).getTask(j).getObjective(k);
						if(o.getID() > largestObjectiveID) {
							largestObjectiveID = o.getID();
						}
					}
				}
			}
			
			Objective.nextObjectiveID = largestObjectiveID + 1;
			
			in.close();
		}
		catch(IOException e) {
			System.out.println("ERROR: Unable to read task list file.");
			return null;
		}
		return taskManager;
	}
	
	public void draw(Graphics g) {
		if(g == null || !SystemManager.robotSystem.hasActiveRobot() || !SystemManager.settings.getDrawObjectives()) { return; }
		
		m_taskLists.elementAt(SystemManager.robotSystem.getActiveRobotID()).draw(g);
	}
	
}
