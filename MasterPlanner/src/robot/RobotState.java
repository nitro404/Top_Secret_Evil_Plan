package robot;

public class RobotState {
	
	final public static byte Idle = 0;
	final public static byte Moving = 1;
	final public static byte FindingBlock = 2;
	final public static byte DeliveringBlock = 3;
	final public static byte FindingPot = 4;
	final public static byte DeliveringPot = 4;
	
	final public static String[] robotStates = {
		"Idle", "Moving", "FindingBlock", "DeliveringBlock", "FindingPot", "DeliveringPot"
	};
	
	public static boolean isValid(byte state) {
		return state >= 0 && state < robotStates.length;
	}
	
	public static byte parseFrom(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		for(byte i=0;i<robotStates.length;i++) {
			if(temp.equalsIgnoreCase(robotStates[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static String toString(byte robotState) {
		return !isValid(robotState) ? "Invalid" : robotStates[robotState];
	}
	
}
