public class ObjectiveState {
	
	final public static byte New = 0;
	final public static byte Started = 1;
	final public static byte Completed = 2;
	
	final public static String[] objectiveStates = {
		"New", "Started", "Completed"
	};
	
	public static boolean isValid(byte objectiveState) {
		return objectiveState >= 0 && objectiveState < objectiveStates.length;
	}
	
	public static byte parseFrom(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		for(byte i=0;i<objectiveStates.length;i++) {
			if(temp.equalsIgnoreCase(objectiveStates[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static String toString(byte objectiveState) {
		return !isValid(objectiveState) ? "Invalid" : objectiveStates[objectiveState];
	}
	
}
