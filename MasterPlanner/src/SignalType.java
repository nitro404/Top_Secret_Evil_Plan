public class SignalType {
	
	final public static int Invalid = -1;
	final public static int Ping = 0;
	final public static int Pong = 1;
	final public static int StartSimulation = 2;
	final public static int BlockStateChange = 3;
	final public static int RobotStateChange = 4;
	final public static int PotStateChange = 5;
	final public static int TaskStarted = 6;
	final public static int TaskCompleted = 7;
	final public static int UpdateBlockPosition = 8;
	final public static int UpdatePotPosition = 9;
	final public static int UpdateActualRobotPosition = 10;
	final public static int UpdateEstimatedRobotPosition = 11;
	final public static int RequestTrackerImage = 12;
	final public static int ReplyTrackerImage = 13;
	final public static int BroadcastTrackerImage = 14;
	final public static int ReceiveTrackerNumber = 15;
	
	final public static String[] signalNames = {
		"Ping",
		"Pong",
		"StartSimulation",
		"BlockStateChange",
		"RobotStateChange",
		"PotStateChange",
		"TaskStarted",
		"TaskCompleted",
		"UpdateBlockPosition",
		"UpdatePotPosition",
		"UpdateActualRobotPosition",
		"UpdateEstimatedRobotPosition",
		"RequestTrackerImage",
		"ReplyTrackerImage",
		"BroadcastTrackerImage",
		"ReceiveTrackerNumber"
	};
	
	public static boolean isValid(int signalType) {
		return signalType >= 0 && signalType < signalNames.length;
	}
	
	public static int parseFrom(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		for(int i=0;i<signalNames.length;i++) {
			if(temp.equalsIgnoreCase(signalNames[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static String toString(int signalType) {
		return !isValid(signalType) ? "Invalid" : signalNames[signalType];
	}
	
}
