package pot;

public class PotState {
	
	final public static byte Origin = 0;
	final public static byte Moving = 1;
	final public static byte Delivered = 2;
	
	final public static String[] potStates = {
		"Origin", "Moving", "Delivered"
	};
	
	public static boolean isValid(byte state) {
		return state >= 0 && state < potStates.length;
	}
	
	public static byte parseFrom(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		for(byte i=0;i<potStates.length;i++) {
			if(temp.equalsIgnoreCase(potStates[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static String toString(byte potState) {
		return !isValid(potState) ? "Invalid" : potStates[potState];
	}
	
}
