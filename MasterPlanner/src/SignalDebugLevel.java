public class SignalDebugLevel {
	
	final public static byte Off = 0;
	final public static byte Incoming = 1;
	final public static byte Outgoing = 2;
	final public static byte Both = 3;
	
	final public static String[] signalDebugLevels = {
		"Off", "Incoming", "Outgoing", "Both"
	};
	
	public static boolean isValid(byte debugLevel) {
		return debugLevel >= 0 && debugLevel < signalDebugLevels.length;
	}
	
	public static byte parseFrom(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		for(byte i=0;i<signalDebugLevels.length;i++) {
			if(temp.equalsIgnoreCase(signalDebugLevels[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static String toString(byte debugLevel) {
		return !isValid(debugLevel) ? "Invalid" : signalDebugLevels[debugLevel];
	}
	
}
