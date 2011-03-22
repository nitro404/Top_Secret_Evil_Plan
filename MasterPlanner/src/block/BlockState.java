package block;

public class BlockState {
	
	final public static byte Unknown = 0;
	final public static byte Located = 1;
	final public static byte Missing = 2;
	final public static byte Moving = 3;
	final public static byte Delivered = 4;
	
	final public static String[] blockStates = {
		"Unknown", "Located", "Missing", "Moving", "Delivered"
	};
	
	public static boolean isValid(byte state) {
		return state >= 0 && state < blockStates.length;
	}
	
	public static byte parseFrom(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		for(byte i=0;i<blockStates.length;i++) {
			if(temp.equalsIgnoreCase(blockStates[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static String toString(byte blockState) {
		return !isValid(blockState) ? "Invalid" : blockStates[blockState];
	}
	
}
