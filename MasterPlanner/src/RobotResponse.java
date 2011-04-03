public class RobotResponse {
	
	final public static byte FoundBlock = 0;
	final public static byte GrabbedBlock = 1;
	final public static byte BlockNotFound = 2;
	final public static byte DroppedOffBlock = 3;
	
	final public static String[] robotResponses = {
		"FoundBlock", "GrabbedBlock", "BlockNotFound", "DroppedOffBlock"
	};
	
	public static boolean isValid(byte responseID) {
		return responseID >= 0 && responseID < robotResponses.length;
	}
	
	public static byte parseFrom(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		for(byte i=0;i<robotResponses.length;i++) {
			if(temp.equalsIgnoreCase(robotResponses[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static String toString(byte responseID) {
		return !isValid(responseID) ? "Invalid" : robotResponses[responseID];
	}
	
}
