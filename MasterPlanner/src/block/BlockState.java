package block;

public class BlockState {
	
	final public static int Unknown = 0;
	final public static int Located = 1;
	final public static int Missing = 2;
	final public static int Moving = 3;
	final public static int Delivered = 4;
	
	final public static String[] blockStates = {
		"Unknown", "Located", "Missing", "Moving", "Delivered"
	};
	
	public static boolean isValid(int state) {
		return state >= 0 && state < blockStates.length;
	}
	
	public static int parseFrom(String data) {
		if(data == null) { return -1; }
		String temp = data.trim();
		for(int i=0;i<blockStates.length;i++) {
			if(temp.equalsIgnoreCase(blockStates[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static String toString(int blockState) {
		return !isValid(blockState) ? "Invalid" : blockStates[blockState];
	}
	
}
