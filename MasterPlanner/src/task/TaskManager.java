package task;

import java.util.Vector;
import java.io.*;
import planner.*;
import settings.*;
import shared.*;

public class TaskManager implements Updatable {
	
	private Vector<TaskList> m_taskLists;
	
	public TaskManager() {
		m_taskLists = new Vector<TaskList>(3);
	}
	
	public int numberOfTaskLists() { return m_taskLists.size(); }
	
	public int totalNumberOfTasksCompleted() {
		int totalNumberOfTasksCompleted = 0;
		for(int i=0;i<m_taskLists.size();i++) {
			totalNumberOfTasksCompleted += m_taskLists.elementAt(i).numberOfTasksCompleted();
		}
		return totalNumberOfTasksCompleted;
	}
	
	public int totalNubmerOfTasks() {
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
	
	public void update() {
		if(SystemManager.robotSystem.hasActiveRobot()) {
			m_taskLists.elementAt(SystemManager.robotSystem.getActiveRobotID()).update();
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
		Vector<TaskList> taskLists = new Vector<TaskList>();
		Task newTask;
		VariableSystem properties = new VariableSystem();
		Variable newVariable;
		byte objectiveType;
		Objective newObjective;
		boolean parseObjectives = false;
		
		try {
			// open the task list data file
			in = new BufferedReader(new FileReader(fileName));
			
			for(byte i=0;i<SystemManager.settings.getNumberOfTrackers();i++) {
				taskLists.add(new TaskList(i));
			}
			
			taskManager.m_taskLists = taskLists;
			
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
						if(newTask.getRobotID() >= 0 && newTask.getRobotID() < taskLists.size()) {
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
					objectiveType = ObjectiveType.parseFromStartOf(data);
					newObjective = null;
					
					if(objectiveType == ObjectiveType.MoveToPosition) {
						newObjective = ObjectiveMoveToPosition.parseFrom(data);
					}
					else if(objectiveType == ObjectiveType.BackUpToPosition) {
						newObjective = ObjectiveBackUpToPosition.parseFrom(data);
					}
					else if(objectiveType == ObjectiveType.LookAtPosition) {
						newObjective = ObjectiveLookAtPosition.parseFrom(data);
					}
					else if(objectiveType == ObjectiveType.PickUpBlock) {
						newObjective = ObjectivePickUpBlock.parseFrom(data);
					}
					else if(objectiveType == ObjectiveType.DropOffBlock) {
						newObjective = ObjectiveDropOffBlock.parseFrom(data);
					}
					
					if(newObjective != null) {
						newTask.addObjective(newObjective);
					}
				}
			}
			
			// if the end of the file is reached while a task was being parsed, store it
			if(parseObjectives) {
				if(newTask.getRobotID() >= 0 && newTask.getRobotID() < taskLists.size()) {
					taskManager.m_taskLists.elementAt(newTask.getRobotID()).addTask(newTask);
				}
			}
			
			in.close();
		}
		catch(IOException e) {
			System.out.println("ERROR: Unable to read task list file.");
			return null;
		}
		return taskManager;
	}
	
}
