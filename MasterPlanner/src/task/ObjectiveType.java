package task;

public class ObjectiveType {
	
	final public static byte MoveToPosition = 0;
	final public static byte BackUpToPosition = 1;
	final public static byte LookAtPosition = 2;
	final public static byte PickUpBlock = 3;
	final public static byte DropOffBlock = 4;
	final public static byte SkipTo = 5;
	final public static byte ChoiceBlock = 6;
	final public static byte Last = 7;
	
	final public static String[] objectiveTypes = {
		"Move to Position",
		"Back Up to Position",
		"Look at Position",
		"Pick Up Block",
		"Drop Off Block",
		"Skip to Objective",
		"Choice Block Objective",
		"Last Objective"
	};
	
	public static boolean isValid(byte objectiveType) {
		return objectiveType >= 0 && objectiveType < objectiveTypes.length;
	}
	
	public static byte parseFrom(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		for(byte i=0;i<objectiveTypes.length;i++) {
			if(temp.equalsIgnoreCase(objectiveTypes[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static byte parseFromStartOf(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		for(byte i=0;i<objectiveTypes.length;i++) {
			if(temp.length() >= objectiveTypes[i].length() &&
			   temp.substring(0, objectiveTypes[i].length()).equalsIgnoreCase(objectiveTypes[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static String toString(byte objectiveType) {
		return !isValid(objectiveType) ? "Invalid" : objectiveTypes[objectiveType];
	}
	
}
