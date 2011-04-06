public class RobotInstruction {
	
	final public static byte Null = 0;
	final public static byte Stop = 1;
	final public static byte MoveForward = 2;
	final public static byte MoveForwardSlowly = 3;
	final public static byte BackUp = 4;
	final public static byte BackUpSlowly = 5;
	final public static byte TurnLeft = 6;
	final public static byte TurnRight = 7;
	final public static byte TurnLeftSlowly = 8;
	final public static byte TurnRightSlowly = 9;
	final public static byte ArcLeft = 10;
	final public static byte ArcRight = 11;
	final public static byte PickUp = 12;
	final public static byte DropOff = 13;
	final public static byte OpenGrippers = 14;
	final public static byte CloseGrippers = 15;
	final public static byte Finished = 16;
	
	final public static String[] robotInstructions = {
		"Null",
		"Stop",
		"MoveForward",
		"MoveForwardSlowly",
		"BackUp",
		"BackUpSlowly",
		"TurnLeft",
		"TurnRight",
		"TurnLeftSlowly",
		"TurnRightSlowly",
		"ArcLeft",
		"ArcRight",
		"PickUp",
		"DropOff",
		"OpenGrippers",
		"CloseGrippers",
		"Finished"
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
