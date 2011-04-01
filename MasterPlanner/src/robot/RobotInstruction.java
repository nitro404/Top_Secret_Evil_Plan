package robot;

public class RobotInstruction {
	
	final public static byte Null = 0;
	final public static byte Stop = 1;
	final public static byte MoveForward = 2;
	final public static byte BackUp = 3;
	final public static byte TurnLeft = 4;
	final public static byte TurnRight = 5;
	final public static byte ArcLeft = 6;
	final public static byte ArcRight = 7;
	final public static byte PickUp = 8;
	final public static byte DropOff = 9;
	final public static byte Done = 10;
	
	final public static String[] robotInstructions = {
		"Null", "Stop", "MoveForward", "BackUp", "TurnLeft", "TurnRight", "ArcLeft", "ArcRight", "PickUp", "DropOff", "Done"
	};
	
	public static boolean isValid(byte instructionID) {
		return instructionID >= 0 && instructionID < robotInstructions.length;
	}
	
	public static byte parseFrom(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		for(byte i=0;i<robotInstructions.length;i++) {
			if(temp.equalsIgnoreCase(robotInstructions[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static String toString(byte instructionID) {
		return !isValid(instructionID) ? "Invalid" : robotInstructions[instructionID];
	}
	
}
