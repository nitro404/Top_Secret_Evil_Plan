package task;

public class TaskState {
	
	final public static byte New = 0;
	final public static byte Started = 1;
	final public static byte Completed = 2;
	
	final public static String[] taskStates = {
		"New", "Started", "Completed"
	};
	
	public static boolean isValid(byte state) {
		return state >= 0 && state < taskStates.length;
	}
	
	public static byte parseFrom(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		for(byte i=0;i<taskStates.length;i++) {
			if(temp.equalsIgnoreCase(taskStates[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static String toString(byte taskState) {
		return !isValid(taskState) ? "Invalid" : taskStates[taskState];
	}
	
}
