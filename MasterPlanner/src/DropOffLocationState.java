public class DropOffLocationState {
	
	final public static byte Empty = 0;
	final public static byte Full = 1;
	
	final public static String[] dropOffLocationStates = {
		"Empty", "Full"
	};
	
	public static boolean isValid(byte state) {
		return state >= 0 && state < dropOffLocationStates.length;
	}
	
	public static byte parseFrom(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		for(byte i=0;i<dropOffLocationStates.length;i++) {
			if(temp.equalsIgnoreCase(dropOffLocationStates[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static String toString(byte dropOffLocationState) {
		return !isValid(dropOffLocationState) ? "Invalid" : dropOffLocationStates[dropOffLocationState];
	}
	
}
