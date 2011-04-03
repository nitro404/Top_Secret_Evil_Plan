public class NextTaskType {
	
	final public static byte Normal = 0;
	final public static byte Choice = 1;
	final public static byte Last = 2;
	
	final public static String[] nextTaskTypes = {
		"Normal", "Choice", "Last"
	};
	
	public static boolean isValid(byte nextTaskType) {
		return nextTaskType >= 0 && nextTaskType < nextTaskTypes.length;
	}
	
	public static byte parseFrom(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		for(byte i=0;i<nextTaskTypes.length;i++) {
			if(temp.equalsIgnoreCase(nextTaskTypes[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static String toString(byte nextTaskType) {
		return !isValid(nextTaskType) ? "Invalid" : nextTaskTypes[nextTaskType];
	}
	
}
