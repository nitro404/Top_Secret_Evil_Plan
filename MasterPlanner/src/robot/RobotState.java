package robot;

public class RobotState {
	
	final public static int Idle = 0;
	final public static int Moving = 1;
	final public static int FindingBlock = 2;
	final public static int DeliveringBlock = 3;
	final public static int FindingPot = 4;
	final public static int DeliveringPot = 4;
	
	final public static String[] robotStates = {
		"Idle", "Moving", "FindingBlock", "DeliveringBlock", "FindingPot", "DeliveringPot"
	};
	
	public static boolean isValid(int state) {
		return state >= 0 && state < robotStates.length;
	}
	
	public static int parseFrom(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		for(int i=0;i<robotStates.length;i++) {
			if(temp.equalsIgnoreCase(robotStates[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static String toString(int robotState) {
		return !isValid(robotState) ? "Invalid" : robotStates[robotState];
	}
	
}
