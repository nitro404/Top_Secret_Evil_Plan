public class PotState {
	
	final public static int Origin = 0;
	final public static int Moving = 1;
	final public static int Delivered = 2;
	
	final public static String[] potStates = {
		"Origin", "Moving", "Delivered"
	};
	
	public static boolean isValid(int state) {
		return state >= 0 && state < potStates.length;
	}
	
	public static int parseFrom(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		for(int i=0;i<potStates.length;i++) {
			if(temp.equalsIgnoreCase(potStates[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static String toString(int potState) {
		return !isValid(potState) ? "Invalid" : potStates[potState];
	}
	
}
